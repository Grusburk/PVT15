package grupp2.satansdemocracy;

import android.util.Log;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class MessageHandler extends Thread {

    private MessageListener listener;

    void setListener(MessageListener listener) {
        this.listener = listener;
    }

    private DBHandler dbHandler = new DBHandler();
    private List<Integer> eventIDs = new ArrayList<>();
    private String facebookID;
    private boolean running;
    private long timeStart;

    MessageHandler(String facebookID) {
        this.facebookID = facebookID;
    }

    @Override
    public void run() {
        completeTask();
    }

    void lookForMessage() {
        running = true;
        timeStart = System.currentTimeMillis();

        while (running) {
            JSONObject messageResponse = dbHandler.getMessageFromDB(facebookID);
            JSONObject eventResponse  = dbHandler.getEvent();
            try {
                if (!messageResponse.getBoolean("error") && messageResponse.has("data")) {
                    String[] messages = new String[messageResponse.getJSONArray("data").length()];
                    for(int i = 0; i < messages.length; i++) {
                        messages[i] = messageResponse.getJSONArray("data").getString(i);
                    }
                    for(String message : messages) {
                        listener.didReceiveMessage(message);
                    }
                }else
                if(!eventResponse.getBoolean("error") && eventResponse.has("data")) {
                    for (int i = 0; i < eventResponse.getJSONArray("data").length(); i++) {
                        int eventID = eventResponse.getJSONArray("data").getInt(i);
                        if (!eventIDs.contains(eventID)) {
                            listener.didReceiveEventID(eventID);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            completeTask();
        }
    }

    void stopSearch() {
        running = false;
    }

    private boolean completeTask() {
        try {
            sleep(30 * 1000);
            Log.i("test", "30 seccccccc");
        } catch (InterruptedException e) {
            running = false;
            e.printStackTrace();
        }
        long timeRan = System.currentTimeMillis()-timeStart;
        if(timeRan > 4*1000*60*60)
            running = false;
        return true;
    }
}
