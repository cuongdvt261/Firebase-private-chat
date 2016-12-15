package chatapp.work.cuongdvt.chatapp.Helper;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by cuongdvt on 30/10/2016.
 */

public class Helper {
    private static Helper _instance;
    private DatabaseReference mData;
    private FirebaseAuth mAuth;

    public Helper() {
        mData = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public static Helper getInstance() {
        if (_instance == null) _instance = new Helper();
        return _instance;
    }

    public String usernameOfEmail() {
        String _email = mAuth.getCurrentUser().getEmail();
        if (_email.contains("@"))
            return _email.split("@")[0];
        else
            return _email;
    }

    public void ToastTextShort(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    public void ToastTextLong(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }
}
