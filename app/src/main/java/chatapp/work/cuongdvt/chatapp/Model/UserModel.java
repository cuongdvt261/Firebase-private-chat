package chatapp.work.cuongdvt.chatapp.Model;

public class UserModel {
    private String id;
    private String username;
    private boolean isOnline;
    private String avaUrl;
    private String groups;

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

    public String getAvaUrl() {
        return avaUrl;
    }

    public void setAvaUrl(String avaUrl) {
        this.avaUrl = avaUrl;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

}
