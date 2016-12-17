package chatapp.work.cuongdvt.chatapp.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import chatapp.work.cuongdvt.chatapp.Helper.Define;
import chatapp.work.cuongdvt.chatapp.Helper.Permission;
import chatapp.work.cuongdvt.chatapp.Model.UserModel;
import chatapp.work.cuongdvt.chatapp.R;

public class UpdateUserInfoActivity extends AppCompatActivity implements View.OnClickListener {
    //region Param
    private TextView txtEmail;
    private EditText edtUsername, edtFullname;
    private Button btnSave;
    private ImageView imgAva;
    //endregion

    //region Event
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);

        initComponent();
        ReceiveData();

        imgAva.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_avatar:
                if (!Permission.getInstance(this).checkPermissionForCamera()) {
                    Permission.getInstance(this).requestPermissionForCamera();
                } else {
                    if (!Permission.getInstance(this).checkPermissionForExternalStorage()) {
                        Permission.getInstance(this).requestPermissionForExternalStorage();
                    } else {
                        Intent photoPicker = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(photoPicker, Define.CAMERA_REQUEST_CODE);
                    }
                }
                break;
            case R.id.button_change:
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Define.CAMERA_REQUEST_CODE
                && resultCode == RESULT_OK
                && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get(Define.INTENT_GET_BITMAP);
            imgAva.setImageBitmap(bitmap);
        }
    }

    //endregion

    //region Method
    public void initComponent() {
        txtEmail = (TextView) findViewById(R.id.textview_email);
        edtFullname = (EditText) findViewById(R.id.edit_change_full_name);
        edtUsername = (EditText) findViewById(R.id.edit_user_name);
        btnSave = (Button) findViewById(R.id.button_change);
        imgAva = (ImageView) findViewById(R.id.img_avatar);

        txtEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }

    private void GetData(DataSnapshot ds) {
        UserModel um = new UserModel();
        if (ds.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            um = ds.getValue(UserModel.class);
            edtFullname.setText(um.getFullName());
            edtUsername.setText(um.getUsername());
        }
    }

    public void ReceiveData() {
        FirebaseDatabase.getInstance().getReference()
                .child(Define.USERS_CHILD).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                GetData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                GetData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    //endregion
}
