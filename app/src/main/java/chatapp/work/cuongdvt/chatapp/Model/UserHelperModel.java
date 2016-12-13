package chatapp.work.cuongdvt.chatapp.Model;

/**
 * Created by cuong on 12-Dec-16.
 */

public class UserHelperModel {
    private String icItem;
    private String titleItem;

    public UserHelperModel(String icItem, String titleItem) {
        this.icItem = icItem;
        this.titleItem = titleItem;
    }

    public String getTitleItem() {
        return titleItem;
    }

    public void setTitleItem(String titleItem) {
        this.titleItem = titleItem;
    }

    public String getIcItem() {
        return icItem;
    }

    public void setIcItem(String icItem) {
        this.icItem = icItem;
    }
}
