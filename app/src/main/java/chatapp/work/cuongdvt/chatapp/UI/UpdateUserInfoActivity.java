package chatapp.work.cuongdvt.chatapp.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import chatapp.work.cuongdvt.chatapp.Helper.DataHelper;
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
    private ProgressDialog progressDialog;
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
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage(Define.LOADING_MESSAGE);
                progressDialog.show();
                doUploadImage(imgAva);
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
        progressDialog = new ProgressDialog(UpdateUserInfoActivity.this, R.style.AppTheme_Dark_Dialog);
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

    public void doUploadImage(ImageView imageView) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(Define.STORAGE_URL);
        StorageReference mountainsRef = storageRef.child(Define.AVATAR_PREFIX + Calendar.getInstance().getTimeInMillis());
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                onChangeFailed();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Log.d("AAAAA", downloadUrl.toString());
                String fullname = edtFullname.getText().toString().trim();
                String username = edtUsername.getText().toString().trim();
                doUpdateUserInfo(fullname, downloadUrl.toString());
                updateData(fullname, username, downloadUrl.toString());
                onChangeSuccess();
                progressDialog.dismiss();
            }
        });
    }

    public void onChangeSuccess() {
        startActivity(new Intent(UpdateUserInfoActivity.this, MainActivity.class));
        finish();
    }

    public void onChangeFailed() {
        Toast.makeText(getApplicationContext(), "Xay ra loi !!", Toast.LENGTH_SHORT).show();
    }

    public void doUpdateUserInfo(String displayName, String avatarUrl) {
        DataHelper.getInstance().updateCurrentUserProfile(displayName, avatarUrl);
    }

    public void updateData(String fullName, String username, String avatarLink) {
        updateNode(Define.USERS_FULLNAME_NODE, fullName);
        updateNode(Define.USERS_USERNAME_NODE, username);
        updateNode(Define.USERS_AVATAR_URL_NODE, avatarLink);
    }

    public void updateNode(String node, String data) {
        FirebaseDatabase.getInstance().getReference()
                .child(Define.USERS_CHILD)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(node)
                .setValue(data);
    }
    //endregion
}
