package chatapp.work.cuongdvt.chatapp.Helper;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import chatapp.work.cuongdvt.chatapp.Model.Message;

public class DataHelper {
    private static DataHelper _instance;
    private DatabaseReference mData;
    private FirebaseAuth mAuth;

    public DataHelper() {
        mData = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public static DataHelper getInstance() {
        if (_instance == null) _instance = new DataHelper();
        return _instance;
    }

    public String usernameOfEmail() {
        String _email = mAuth.getCurrentUser().getEmail();
        if (_email.contains("@"))
            return _email.split("@")[0];
        else
            return _email;
    }

    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public DatabaseReference getDatabase() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getMainChild(String childName) {
        return getDatabase().child(childName);
    }

    public void setUserOnline(boolean isOnline) {
        getMainChild(Define.USERS_CHILD)
                .child(getCurrentUser().getUid())
                .child(Define.USERS_ONLINE_CHILD)
                .setValue(isOnline);
    }

    public void setMessageFrom(String from, String to, String ava, String msg, boolean isFrom) {
        getMainChild(Define.MESSAGES_CHILD)
                .child(from + "_" + to)
                .push()
                .setValue(new Message(msg, isFrom, from, ava));
    }

    public void updateCurrentUserProfile(String displayName, String avatarUrl) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                .setPhotoUri(Uri.parse(avatarUrl))
                .build();
        getCurrentUser().updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("U_STATUS", "User profile updated.");
                        }
                    }
                });
    }

    public void setAvatarDefault(String displayName) {
        updateCurrentUserProfile(displayName, Define.NO_AVATAR_IMAGE_LINK);
    }

    public void reauthFirebase(String email, String password) {
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, password);
        getCurrentUser().reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("DISPLAY_NAME", getCurrentUser().getDisplayName());
                    }
                });
    }
}
