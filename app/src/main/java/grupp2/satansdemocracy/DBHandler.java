package grupp2.satansdemocracy;

import android.util.Log;
import com.facebook.Profile;
import org.json.JSONObject;

/**
 * Created by dödsadde on 2016-04-14.
 */
public class DBHandler {


    /**
     * calls for ApiMessageClass "MessageModel"
     **/
    private MessageModel model = new MessageModel("https://people.dsv.su.se/~anth3046/SatansDemokrati/api/v1/");
    private MessageModel modelIdToDB = new MessageModel("https://people.dsv.su.se/~anth3046/SatansDemokrati/api/v2/");
    private MessageModel modelMessageFromDB = new MessageModel("URL_Y");


    public DBHandler() {
        /**
         * EMPTY CONSTRUCTOR
         */
    }

    public boolean idExist(Profile profile) {
        /**
         * TODO: Send profileID to server/DB
         * Sends the user ID to the server
         * (NOT DONE, JUST TEST)
         */
        boolean exists = false;
        try {
            JSONObject jProfile = model.apiGet("get_user/" + profile.getId());
            Log.d("LoginActivity", String.valueOf(jProfile));
            exists = !jProfile.getBoolean("error");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }

    /**
     * Creates a new entry in the DB
     */
    public void postNewProfileToDB(Profile profile) {

        /** Adding data to JSONObjects */
        String userJson = "{'id':\"" + profile.getId()
                + "\",'firstName':\"" + profile.getFirstName()
                + "\",'middleName':\"" + profile.getMiddleName()
                + "\",'lastName':\"" + profile.getLastName()
                + "\",'name':\"" + profile.getName()
                + "\",'linkUri':\"" + profile.getLinkUri()
                + "\"}";
        /**
         * Call the API(MessageModel) with the JSonUserData.
         */
        try {
            JSONObject response = model.apiPost("add_user/", new JSONObject(userJson));
            Log.d("LoginActivity", String.valueOf(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void postIDToMessageDB(Profile profile) {

        String userJson = "{'id':\"" + profile.getId() + "\"}";

        try {
            JSONObject response = modelIdToDB.apiPost("add_id/", new JSONObject(userJson));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMessageFromDB(Profile profile) {

        try {
            JSONObject jMessage = modelMessageFromDB.apiGet("get_message/" + profile.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
