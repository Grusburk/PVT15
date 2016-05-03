package grupp2.satansdemocracy;

/**
 * OTHER: Server info:
 *          - Server:           mysql.dsv.su.se
 *          - Port:             3306 (default)
 *          - Database name:    joso8829
 *          - User name:        joso8829
 *          - Password:         vaeB3iebi9ro
 */

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import com.facebook.*;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    Profile profile;
    DBHandler dbHandler = new DBHandler();
    private ProfileTracker mProfileTracker;
    private AccessTokenTracker mAccessTokenTracker;

    /**
     * Called upon creation of this activity
     * Basic set-up
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        /**
         * Super wierd bug-fix, facebook uses persistent storage for Profile data.
         * We need to wait for it to be populated before continuing, else Profile.getCurrentProfile() will return null.
         */
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * Get current profile
         */
        profile = Profile.getCurrentProfile();

        /**
         * If already logged in, go directly to main activity
         */
        if (profile != null) {
            loginAndChangeActivity(profile);
        }

        /**
         * Instantiate Facebook Callbackmanager
         */
        callbackManager = CallbackManager.Factory.create();

        /**
         * Sets layout..
         */
        setContentView(R.layout.activity_login);

        /**
         * LoginButton and permissions
         */
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        assert loginButton != null;
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));

        /**
         * "on-click" but not really
         *  Using the the login button in the way it was intended gets us out of
         *  callback-hell, using the onClick event requires two callbacks inside one another.
         */
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                /**
                 * If the current profile is not set, track changes to the current profile and wait for it to be set.
                 */
                if (Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                            // profile2 is the new profile
                            Log.v("facebook - profile", profile2.getFirstName());
                            mProfileTracker.stopTracking();
                            loginAndChangeActivity(profile2);
                        }
                    };
                    mProfileTracker.startTracking();
                }

                /**
                 * If current profile is set, Log In.
                 */
                else {
                    Profile profile = Profile.getCurrentProfile();
                    Log.v("facebook - profile", profile.getFirstName());
                    loginAndChangeActivity(profile);
                }


            }


            @Override
            public void onCancel() {
                Log.d("LoginActivity", "Facebook_LOGIN_CANCEL");
            }

            @Override
            public void onError(FacebookException e) {
                Log.d("LoginActivity", "Facebook_LOGIN_ERROR");
                e.printStackTrace();
            }
        });

        /**
         * LOGIN BYPASS
         * TODO: Remove this after application completion
         */
        Button nextViewButton = (Button) findViewById(R.id.bypass_button);

        assert nextViewButton != null;
        nextViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMain();
            }
        });
    }

    /**
     * Facebook Standard
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Former asynchronous execute. This method was called from several places so its now a refactored function.
     */
    private void loginAndChangeActivity(final Profile profile) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (dbHandler.idExist(profile)) {
                    goToMain();
                } else {
                    dbHandler.postNewProfileToDB(profile);
                    goToMain();
                }
            }
        });
    }

    /**
     * Method for changeing view to main.
     *
     */
    private void goToMain() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("facebookID", profile.getId()));
        finish();
    }

    /**
     * Attaches the font in assets/fonts to the application
     * @param newBase
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}