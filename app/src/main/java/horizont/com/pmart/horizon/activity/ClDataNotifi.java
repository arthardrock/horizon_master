package horizont.com.pmart.horizon.activity;

public class ClDataNotifi {
    private String title_notifi;
    private String message_notifi;
    private  String img_noti;

    public ClDataNotifi(String title_notifi, String message_notifi, String img_noti) {
        this.title_notifi = title_notifi;
        this.message_notifi = message_notifi;
        this.img_noti = img_noti;
    }

    public String getTitle() {
        return title_notifi;
    }

    public void setTitle(String title_notifi) {
        this.title_notifi = title_notifi;
    }

    public String getMessage() {
        return message_notifi;
    }

    public void setMessage(String message) {
        this.message_notifi = message;
    }

    public String getImg_noti() {
        return img_noti;
    }

    public void setImg_noti(String img_noti) {
        this.img_noti = img_noti;
    }
}
