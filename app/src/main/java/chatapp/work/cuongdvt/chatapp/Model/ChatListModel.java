package chatapp.work.cuongdvt.chatapp.Model;

/**
 * Created by cuongdvt on 17/10/2016.
 */

public class ChatListModel {
    private String sender;
    private String avaName;
    private String content;

    private String timing;

    public ChatListModel(String sender, String avaName, String content, String timing) {
        this.sender = sender;
        this.avaName = avaName;
        this.content = content;
        this.timing = timing;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }
}
