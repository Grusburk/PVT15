package grupp2.satansdemocracy;

/**
 * Created by Mattin on 2016-05-03.
 */
public class Tweet {
    private String userName, userMessage;
//    private URL userPicture;

    public Tweet (){

    }


    public Tweet (String userName, String userMessage){
        this.userName = userName;
        this.userMessage = userMessage;
//        this.userPicture = userPicture;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

//    public URL getUserPicture() {
//        return userPicture;
//    }
//
//    public void setUserPicture(URL userPicture) {
//        this.userPicture = userPicture;
//    }

}
