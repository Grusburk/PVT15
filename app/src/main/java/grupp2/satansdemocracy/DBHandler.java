package grupp2.satansdemocracy;

import android.util.Log;
import com.facebook.Profile;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import grupp2.satansdemocracy.LoginActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dödsadde on 2016-04-14.
 */
public class DBHandler {

    /**
     * The URL to our API
     */
    private static final String API_URL = "https://people.dsv.su.se/~anth3046/SatansDemokrati/api/v1/";
    /**
     * Our HttpHandler (CALL FOR HTTP METHODS)
     */
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * Sends HttpRequest and tries to handle the response
     * (might have to be rewritten, straight out copied from internet with a slight edit)
     */
    JSONObject apiGet(String url) throws IOException, JSONException {
        Request request = new Request.Builder()
                .url(API_URL + url)
                .build();
        Response response = client.newCall(request).execute();
        return new JSONObject(response.body().string());
    }

    String apiPut(String url, String json) throws IOException, JSONException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }


    public DBHandler() {
        /**
         * EMPTY CONSTRUCTOR
         */
    }

    public void tryConnection() {
        /**
         * TODO: http://developer.android.com/reference/java/net/HttpURLConnection.html
         */
    }

    public boolean idExist(Profile profile) {
        /**
         * TODO: Send profileID to server/DB
         * Sends the user ID to the server
         * (NOT DONE, JUST TEST)
         */
        JSONObject jProfile = null;
        try {
            jProfile = apiGet("get_user/" + profile.getId());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("LoginActivity", String.valueOf(jProfile));

        return jProfile != null;
    }

    /**
     * Creates a new entry in the DB
     *
     * @param profile
     */
    public void postNewProfileToDB(Profile profile) {

        try {
            /** Adding data to JSONObjects */

/*            String profil = "{'profile':["
                            + "{'id':'" + profile.getId()
                            + "','firstName':'" + profile.getFirstName()
                            + "','middleName':'" + profile.getMiddleName()
                            + "','lastName':'" + profile.getLastName()
                            + "','name':'" + profile.getName()
                            + "','linkUri':'" + profile.getLinkUri()
                            + "'}]}";*/

            JSONObject profileID = new JSONObject();
            profileID.put("id", profile.getId());
            JSONObject firstName = new JSONObject();
            firstName.put("firstName", profile.getFirstName());
            JSONObject middleName = new JSONObject();
            middleName.put("middleName", profile.getMiddleName());
            JSONObject lastName = new JSONObject();
            lastName.put("lastName", profile.getLastName());
            JSONObject name = new JSONObject();
            name.put("name", profile.getName());
            JSONObject linkUri = new JSONObject();
            linkUri.put("linkUri", profile.getLinkUri());

            /** Adding JSONObjects to JSONArray */
            JSONArray profileArr = new JSONArray();

            profileArr.put(profileID);
            profileArr.put(firstName);
            profileArr.put(middleName);
            profileArr.put(lastName);
            profileArr.put(name);
            profileArr.put(linkUri);

            /** Adding JSONArray to a JSONObject ready for DB */
            JSONObject prof = new JSONObject();

            prof.put("profile", profileArr);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
