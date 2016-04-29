package grupp2.satansdemocracy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joakim on 2016-04-29.
 */
public class MessageHandler extends Thread {

    private DBHandler dbHandler = new DBHandler();
    private List<String> messageUID = new ArrayList<>();
    private String facebookID;
    private boolean running;
    String UUID = "Vettig variabel här";

    public MessageHandler(String facebookID) {
        this.facebookID = facebookID;
    }

    @Override
    public void run() {
        completeTask();
    }

    public void lookForMessage() {
        running = true;
        while (true && running) {
            dbHandler.getMessageID(facebookID);
            //TODO: Parse message from DB

            if (!messageUID.contains(UUID)) {
                dbHandler.getMessageFromDB(facebookID, UUID);
                //TODO: somethingsomething dark side
                messageUID.add(UUID);
            }
            completeTask();
        }
    }

    public void stopSearch() {
        running = false;
    }

    public void longTimer() {
        try {
            Thread.sleep(4 * 1000 * 60 * 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
        running = false;
    }

    public boolean completeTask() {
        try {
            Thread.sleep(30 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}
