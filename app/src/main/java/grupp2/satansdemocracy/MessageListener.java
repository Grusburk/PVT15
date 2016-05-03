package grupp2.satansdemocracy;

/**
 * Created by Mattin on 2016-05-03.
 */
public interface MessageListener {

    void didRecieveEventID(int id);
    void didReceiveMessage(String message);
}
