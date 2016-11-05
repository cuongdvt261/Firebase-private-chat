package chatapp.work.cuongdvt.chatapp.Helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by cuongdvt on 30/10/2016.
 */

public class Param {
    private static Param _instance;
    private DatabaseReference mData;
    private FirebaseAuth mAuth;

    public Param() {
        mData = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public static Param getInstance() {
        if (_instance == null) _instance = new Param();
        return _instance;
    }

    public String usernameOfEmail() {
        String _email = mAuth.getCurrentUser().getEmail();
        if (_email.contains("@"))
            return _email.split("@")[0];
        else
            return _email;
    }
}
