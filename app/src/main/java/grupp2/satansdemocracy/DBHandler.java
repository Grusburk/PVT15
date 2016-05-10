package grupp2.satansdemocracy;

import android.util.Log;
import com.facebook.Profile;
import org.json.JSONObject;

class DBHandler {


    /**
     * calls for ApiMessageClass "MessageModel"
     **/
    private MessageModel model = new MessageModel("https://people.dsv.su.se/~anth3046/SatansDemokrati/api/v1/");
    private MessageModel modelId = new MessageModel("https://people.dsv.su.se/~anth3046/SatansDemokrati/api/v2/");
    private MessageModel modelMessageAndEvent = new MessageModel("https://people.dsv.su.se/~anth3046/SatansDemokrati/api/v3/");


    DBHandler() {
        /**
         * EMPTY CONSTRUCTOR
         */
    }

    boolean idExist(Profile profile) {
        /**
         * Sends the user ID to the server
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
    void postNewProfileToDB(Profile profile) {

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

    void postIDToMessageDB(String id) {

        String userJson = "{'id':\"" + id + "\"}";

        try {
            JSONObject response = modelId.apiPost("add_id/", new JSONObject(userJson));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    JSONObject getMessageFromDB(String id) {
        JSONObject jMessage = null;
        try {
            jMessage = modelMessageAndEvent.apiGet("get_message/" + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jMessage;
    }

    JSONObject getEvent() {
        JSONObject jEvent = null;
        try {
            jEvent = modelMessageAndEvent.apiGet("get_event/");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jEvent;
    }

}
