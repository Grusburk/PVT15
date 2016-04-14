package grupp2.satansdemocracy;

/**
 * Created by dödsadde on 2016-04-04.
 * <p>
 * ISSUE: android.os.NetworkOnMainThreadException
 * at grupp2.satansdemocracy.LoginActivity.apiGet(LoginActivity.java:46)
 * at grupp2.satansdemocracy.LoginActivity$1$1.onSuccess(LoginActivity.java:88)
 * at grupp2.satansdemocracy.LoginActivity$1$1.onSuccess(LoginActivity.java:63)
 * at grupp2.satansdemocracy.LoginActivity.onActivityResult(LoginActivity.java:176)
 * <p>
 * CHECK: http://stackoverflow.com/questions/6343166/how-to-fix-android-os-networkonmainthreadexception
 * <p>
 * <p>
 * OPEN QUESTION: IS jObject.put gonna work since we declare it as null?
 * <p>
 * TODO: Hantera eventuella anslutningssvårigheter
 * TODO: Save UserInformation To DB
 * OTHER: Server info:
 * - Server:           mysql.dsv.su.se
 * - Port:             3306 (default)
 * - Database name:    joso8829
 * - User name:        joso8829
 * - Password:         vaeB3iebi9ro
 * checkDB
 * ifExist(GO TO LOGIN)
 * ifNotExist(SAVE PROFILE TO DB; GO TO LOGIN)
 * FINITO
 * <p>
 * ISSUE: android.os.NetworkOnMainThreadException
 * at grupp2.satansdemocracy.LoginActivity.apiGet(LoginActivity.java:46)
 * at grupp2.satansdemocracy.LoginActivity$1$1.onSuccess(LoginActivity.java:88)
 * at grupp2.satansdemocracy.LoginActivity$1$1.onSuccess(LoginActivity.java:63)
 * at grupp2.satansdemocracy.LoginActivity.onActivityResult(LoginActivity.java:176)
 * <p>
 * CHECK: http://stackoverflow.com/questions/6343166/how-to-fix-android-os-networkonmainthreadexception
 * <p>
 * <p>
 * OPEN QUESTION: IS jObject.put gonna work since we declare it as null?
 * <p>
 * TODO: Hantera eventuella anslutningssvårigheter
 * TODO: Save UserInformation To DB
 * OTHER: Server info:
 * - Server:           mysql.dsv.su.se
 * - Port:             3306 (default)
 * - Database name:    joso8829
 * - User name:        joso8829
 * - Password:         vaeB3iebi9ro
 * checkDB
 * ifExist(GO TO LOGIN)
 * ifNotExist(SAVE PROFILE TO DB; GO TO LOGIN)
 * FINITO
 * <p>
 * ISSUE: android.os.NetworkOnMainThreadException
 * at grupp2.satansdemocracy.LoginActivity.apiGet(LoginActivity.java:46)
 * at grupp2.satansdemocracy.LoginActivity$1$1.onSuccess(LoginActivity.java:88)
 * at grupp2.satansdemocracy.LoginActivity$1$1.onSuccess(LoginActivity.java:63)
 * at grupp2.satansdemocracy.LoginActivity.onActivityResult(LoginActivity.java:176)
 * <p>
 * CHECK: http://stackoverflow.com/questions/6343166/how-to-fix-android-os-networkonmainthreadexception
 * <p>
 * <p>
 * OPEN QUESTION: IS jObject.put gonna work since we declare it as null?
 * <p>
 * TODO: Hantera eventuella anslutningssvårigheter
 * TODO: Save UserInformation To DB
 * OTHER: Server info:
 * - Server:           mysql.dsv.su.se
 * - Port:             3306 (default)
 * - Database name:    joso8829
 * - User name:        joso8829
 * - Password:         vaeB3iebi9ro
 * checkDB
 * ifExist(GO TO LOGIN)
 * ifNotExist(SAVE PROFILE TO DB; GO TO LOGIN)
 * FINITO
 * <p>
 * ISSUE: android.os.NetworkOnMainThreadException
 * at grupp2.satansdemocracy.LoginActivity.apiGet(LoginActivity.java:46)
 * at grupp2.satansdemocracy.LoginActivity$1$1.onSuccess(LoginActivity.java:88)
 * at grupp2.satansdemocracy.LoginActivity$1$1.onSuccess(LoginActivity.java:63)
 * at grupp2.satansdemocracy.LoginActivity.onActivityResult(LoginActivity.java:176)
 * <p>
 * CHECK: http://stackoverflow.com/questions/6343166/how-to-fix-android-os-networkonmainthreadexception
 * <p>
 * <p>
 * OPEN QUESTION: IS jObject.put gonna work since we declare it as null?
 * <p>
 * TODO: Hantera eventuella anslutningssvårigheter
 * TODO: Save UserInformation To DB
 * OTHER: Server info:
 * - Server:           mysql.dsv.su.se
 * - Port:             3306 (default)
 * - Database name:    joso8829
 * - User name:        joso8829
 * - Password:         vaeB3iebi9ro
 * checkDB
 * ifExist(GO TO LOGIN)
 * ifNotExist(SAVE PROFILE TO DB; GO TO LOGIN)
 * FINITO
 */

/**
 * ISSUE: android.os.NetworkOnMainThreadException
 at grupp2.satansdemocracy.LoginActivity.apiGet(LoginActivity.java:46)
 at grupp2.satansdemocracy.LoginActivity$1$1.onSuccess(LoginActivity.java:88)
 at grupp2.satansdemocracy.LoginActivity$1$1.onSuccess(LoginActivity.java:63)
 at grupp2.satansdemocracy.LoginActivity.onActivityResult(LoginActivity.java:176)

 * CHECK: http://stackoverflow.com/questions/6343166/how-to-fix-android-os-networkonmainthreadexception
 *
 *
 * OPEN QUESTION: IS jObject.put gonna work since we declare it as null?
 */

/**
 * TODO: Hantera eventuella anslutningssvårigheter
 * TODO: Save UserInformation To DB
 * OTHER: Server info:
 *          - Server:           mysql.dsv.su.se
 *          - Port:             3306 (default)
 *          - Database name:    joso8829
 *          - User name:        joso8829
 *          - Password:         vaeB3iebi9ro
 * checkDB
 * ifExist(GO TO LOGIN)
 * ifNotExist(SAVE PROFILE TO DB; GO TO LOGIN)
 * FINITO
 */

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import android.widget.Toast;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import com.facebook.Profile;

import java.io.IOException;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    Profile profile = null;
    DBHandler dbHandler = new DBHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);

        /**
         * Ny setOnClickListener (The application went straight into "facebook screen" on start. Didnt notice until I started it on my phone)
         */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "user_photos", "public_profile"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        profile = Profile.getCurrentProfile();

                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                if (dbHandler.idExist(profile)) {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                } else {
                                    dbHandler.postNewProfileToDB(profile);
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                }
                            }
                        });
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
            }
        });


        /**
         * START: LOGIN BYPASS
         */
        Button nextViewButton = (Button) findViewById(R.id.bypass_button);

        nextViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
        /**
         * END: LOGIN BYPASS
         */
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}