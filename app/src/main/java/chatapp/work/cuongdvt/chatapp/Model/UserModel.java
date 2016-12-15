package chatapp.work.cuongdvt.chatapp.Model;

public class UserModel {
    private String id;
    private String username;
    private boolean isOnline;
    private String avatarUrl;
    private String fullName;

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public UserModel() {
    }

    public UserModel(String _id, String _username, boolean _isOnline) {
        this.id = _id;
        this.username = _username;
        this.isOnline = _isOnline;
    }

    public UserModel(String id, String username, boolean isOnline, String avatarUrl, String fullName) {
        this.id = id;
        this.username = username;
        this.isOnline = isOnline;
        this.avatarUrl = avatarUrl;
        this.fullName = fullName;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
