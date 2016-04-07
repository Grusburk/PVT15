package grupp2.satansdemocracy;

/**
 * Created by dödsadde on 2016-04-04.
 */

import android.content.Context;
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
    private static final String API_URL = "https://people.dsv.su.se/~anth3046/SatansDemokrati/api/v1/";
    Profile profile = null;
    OkHttpClient client = new OkHttpClient();

    JSONObject apiGet(String url) throws IOException, JSONException {
        Request request = new Request.Builder()
                .url(API_URL+url)
                .build();
        Response response = client.newCall(request).execute();
        return new JSONObject(response.body().string());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton =(LoginButton)findViewById(R.id.login_button);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "user_photos", "public_profile"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        /**
                         * TODO: Hantera eventuella anslutningssvårigheter
                         * TODO: Save UserInformation To DB
                         * TODO: Server info:
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
                        profile = Profile.getCurrentProfile();
                        profile.getId();

                        /**
                         * TODO: Send profileID to server/DB
                         */
                        JSONObject jProfile = null;
                        try {
                            jProfile = apiGet("get_user/"+profile.getId());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("LoginActivity", String.valueOf(jProfile));

                        /**
                         * TODO: Handle JSON answer
                         */

                        if (true/** Check profileID against hash table in DB (JSON answer bool == true) */) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            /**
                             * TODO: If user doesnt exist in DB, create it
                             */
                            JSONObject firstName = null,
                                    middleName = null,
                                    lastName = null,
                                    name = null,
                                    linkUri = null;
                            JSONArray profileArr = null;
                            JSONObject prof = null;
                            try {
                                /** Adding data to JSONObjects */
                                firstName.put("firstName", profile.getFirstName());
                                middleName.put("middleName", profile.getMiddleName());
                                lastName.put("lastName", profile.getLastName());
                                name.put("name", profile.getName());
                                linkUri.put("linkUri", profile.getLinkUri());

                                /** Adding JSONObjects to JSONArray */
                                //profileArr.put(profileID);
                                profileArr.put(firstName);
                                profileArr.put(middleName);
                                profileArr.put(lastName);
                                profileArr.put(name);
                                profileArr.put(linkUri);

                                /** Adding JSONArray to a JSONObject ready for DB */
                                prof.put("profile", profileArr);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            /**
                             * TODO: send JSON.prof to DB
                             */
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                    }

                    @Override
                    public void onCancel() {
                        /**
                         *  TODO: CancelMessage
                         */
                        Log.d("LoginActivity", "Facebook_LOGIN_CANCEL");
                    }

                    @Override
                    public void onError(FacebookException e) {
                        /**
                         * TODO: TOAST ERROR
                         */
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