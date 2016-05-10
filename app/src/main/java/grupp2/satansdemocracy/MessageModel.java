package grupp2.satansdemocracy;

import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Separate class to handle REST VERBS
 */
class MessageModel {

    /**
     * The URL to our API
     */
    private String baseUrl;
    /**
     * Our HttpHandler (CALL FOR HTTP METHODS)
     */
    private OkHttpClient client = new OkHttpClient();

    /**
     * Takes URL to API (makes it easier for further implementation.
     */
    MessageModel(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * JSON_GET
     */
    JSONObject apiGet(String url) throws IOException, JSONException {
        Request request = new Request.Builder()
                .url(baseUrl + url)
                .build();
        Response response = client.newCall(request).execute();
        return new JSONObject(response.body().string());
    }

    /**
     * JSON_POST
     */
    JSONObject apiPost(String url, JSONObject json) throws IOException, JSONException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json.toString());

        Request request = new Request.Builder().url(baseUrl + url).post(body).build();
        Response response = client.newCall(request).execute();
        return new JSONObject(response.body().string());
    }
}
