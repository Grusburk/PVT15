package grupp2.satansdemocracy;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joakim on 2016-04-29.
 */
public class MessageHandler extends Thread {

    private DBHandler dbHandler = new DBHandler();
    private List<String> eventIDs = new ArrayList<>();
    private String facebookID;
    private boolean running;
    private long timestart;
    String UUID = "Vettig variabel här";
    String event = "Vettig variabel här";

    public MessageHandler(String facebookID) {
        this.facebookID = facebookID;
    }

    @Override
    public void run() {
        completeTask();
    }

    public void lookForMessage() {
        running = true;
        timestart = System.currentTimeMillis();

        while (running) {
            JSONObject messageResponse = dbHandler.getMessageFromDB(facebookID);
            JSONObject eventResponse  = dbHandler.getEvent();
            try {
                if (!messageResponse.getBoolean("error") && messageResponse.has("data")) {
                    String[] messages = new String[messageResponse.getJSONArray("data").length()];
                    for(int i = 0; i < messages.length; i++) {
                        messages[i] = messageResponse.getJSONArray("data").getString(i);
                    }
                    for (int i = 0; i < messages.length; i++){
                        //KICK.IT(messages[i]);
                    }
                }
                if(!eventResponse.getBoolean("error") && eventResponse.has("data")) {
                    for (int i = 0; i < eventResponse.getJSONArray("data").length(); i++) {
                        int eventID = eventResponse.getJSONArray("data").getInt(i);
                        if (!eventIDs.contains(eventID)) {
                            switch (eventID) {

                                case 1:
                                    //SEND TO ....
                                    break;

                                case 2:
                                    //SEND TO ....
                                    break;

                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            completeTask();
        }
    }

    public void stopSearch() {
        running = false;
    }

    public boolean completeTask() {

        try {
            this.sleep(30 * 1000);
        } catch (InterruptedException e) {
            running = false;
            e.printStackTrace();
        }
        long timeran = System.currentTimeMillis()-timestart;
        if(timeran > 4*1000*60*60)
            running = false;
        return true;
    }
}
