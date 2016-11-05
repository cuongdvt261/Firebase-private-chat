package chatapp.work.cuongdvt.chatapp.Model;

/**
 * Created by cuongdvt on 16/10/2016.
 */

public class Message {
    private String message;
    private boolean isFromYou;
    private String sender;
    private String avaName;

    public Message() {
    }

    public Message(String message, boolean isFromYou, String sender, String avaName) {
        this.message = message;
        this.isFromYou = isFromYou;
        this.sender = sender;
        this.avaName = avaName;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getIsFromYou() {
        return isFromYou;
    }

    public void setIsFromYou(boolean isFromYou) {
        this.isFromYou = isFromYou;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getAvaName() {
        return avaName;
    }

    public void setAvaName(String avaName) {
        this.avaName = avaName;
    }

}
