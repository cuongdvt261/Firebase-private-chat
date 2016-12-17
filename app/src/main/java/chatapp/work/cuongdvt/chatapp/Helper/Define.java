package chatapp.work.cuongdvt.chatapp.Helper;

/**
 * Created by cuong on 13-Dec-16.
 */

public class Define {
    // FIREBASE_CHILD_NAME
    public static final String USERS_CHILD = "users";
    public static final String USERS_ONLINE_CHILD = "online";
    public static final String USERS_AVATAR_URL_CHILD = "avatarUrl";
    public static final String MESSAGES_CHILD = "messages";
    public static final String MESAGE_NODE = "message";

    // FRAGMENT_NAME
    public static final String FRAGMENT_FIRST_NAME = "Tin nhắn";
    public static final String FRAGMENT_SECOND_NAME = "Trực tuyến";
    public static final String FRAGMENT_THIRD_NAME = "Tài khoản";

    // PERMISSION_CODE
    public static final int CAMERA_REQUEST_CODE = 1;

    // STORAGE_LINK
    public static final String STORAGE_NAME = "gs://chatapp-2f48f.appspot.com";
    public static final String NO_AVATAR_IMAGE_LINK = "https://firebasestorage.googleapis.com/v0/b/chatapp-2f48f.appspot.com/o/no_avatar.jpg?alt=media&token=ad8af72a-4d76-49dd-9a99-54e5de41a03c";

    // STRING_ERROR
    public static final String INPUT_EMAIL_ERROR = "Email không đúng định dạng";
    public static final String INPUT_PASSWORD_ERROR = "Password nhiều hơn 6 kí tự";
    public static final String INPUT_USERNAME_ERROR = "Username không được để trống";
    public static final String PASS_CONFIRM_ERROR = "Không khớp";
    public static final String SIGNUP_ERROR_MESSAGE = "Đăng kí không thành công !";
    public static final String LOGIN_ERROR_MESSAGE = "Đăng nhập không thành công !";

    // STRING_MESSAGE
    public static final String LOADING_MESSAGE = "Đang xử lý";
    public static final String SEND_MAIL_SUCCESS = "Tin nhắn đã được gửi !";
    public static final String SEND_MAIL_FAILED = "Gửi tin nhắn không thành công !";

    // INTENT_NAME
    public static final String INTENT_GET_USERNAME = "TO_USERNAME";
    public static final String INTENT_GET_USER_AVATAR = "TO_USER_AVATAR";
    public static final String INTENT_GET_BITMAP = "data";
}
