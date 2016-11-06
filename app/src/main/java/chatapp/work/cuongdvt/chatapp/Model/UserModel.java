package chatapp.work.cuongdvt.chatapp.Model;

/**
 * Created by cuong on 06-Nov-16.
 */

public class UserModel {
    private String id;
    private String username;
    private boolean isOnline;

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public UserModel() {
    }

    public UserModel(String username, String id, boolean isOnline) {
        this.username = username;
        this.id = id;
        this.isOnline = isOnline;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
