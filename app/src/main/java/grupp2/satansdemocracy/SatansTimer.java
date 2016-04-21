package grupp2.satansdemocracy;

import java.util.TimerTask;

/**
 * Created by Joakim on 2016-04-21.
 */
public class SatansTimer extends Thread {

    @Override
    public void run() {
        completeTask();
    }

    public boolean completeTask() {
        try {
            Thread.sleep(4 * 1000 * 60 * 60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}
