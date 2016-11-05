package chatapp.work.cuongdvt.chatapp.Model;

public class ListOnlineModel {
    private String avaName;
    private String statusName;
    private String userName;

    public ListOnlineModel(String avaName, String statusName, String userName) {
        this.avaName = avaName;
        this.statusName = statusName;
        this.userName = userName;
    }

    public String getAvaName() {
        return avaName;
    }

    public void setAvaName(String avaName) {
        this.avaName = avaName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
