package chatapp.work.cuongdvt.chatapp.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import chatapp.work.cuongdvt.chatapp.Helper.DataHelper;
import chatapp.work.cuongdvt.chatapp.Helper.Define;
import chatapp.work.cuongdvt.chatapp.Helper.Permission;
import chatapp.work.cuongdvt.chatapp.Model.UserModel;
import chatapp.work.cuongdvt.chatapp.R;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    //region Variable
    private EditText edtEmail, edtFullname, edtUsername, edtPassword, edtPassConfirm;
    private Button btnUploadAva, btnSignUp;
    private Toolbar topToolbar;
    private ProgressDialog progressDialog;
    //endregion

    //region Event
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        InitComponent();
        InitToolbar();

        // Event Onclick
        btnUploadAva.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK & data != null) {
            switch (requestCode) {
                case Define.CAMERA_REQUEST_CODE:
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upload_avatar:
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
            case R.id.btn_signup:
                if (!isValidation()) {
                    onSignupFailed();
                    return;
                }

                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Đang xử lý ...");
                progressDialog.show();
                doLogin(getTextEdt(edtEmail), getTextEdt(edtPassword));
                break;
            default:
                break;
        }
    }
    //endregion

    //region Method
    public void ToastTextShort(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    public void ToastTextLong(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
    }

    public void InitComponent() {
        edtEmail = (EditText) findViewById(R.id.email);
        edtFullname = (EditText) findViewById(R.id.fullname);
        edtUsername = (EditText) findViewById(R.id.username);
        edtPassword = (EditText) findViewById(R.id.password);
        edtPassConfirm = (EditText) findViewById(R.id.password_confirm);

        btnUploadAva = (Button) findViewById(R.id.upload_avatar);
        btnSignUp = (Button) findViewById(R.id.btn_signup);

        topToolbar = (Toolbar) findViewById(R.id.top_toolbar);
        progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
    }

    public void InitToolbar() {
        setTitle(null);
        setSupportActionBar(topToolbar);
    }

    public String getTextEdt(EditText edt) {
        return edt.getText().toString().trim();
    }

    public boolean checkPassmatcher(String pass, String passConfirm) {
        return pass.equals(passConfirm) ? true : false;
    }

    public boolean isValidation() {
        boolean valid = true;
        if (TextUtils.isEmpty(getTextEdt(edtEmail)) ||
                !Patterns.EMAIL_ADDRESS.matcher(getTextEdt(edtEmail)).matches()) {
            edtEmail.setError(Define.INPUT_EMAIL_ERROR);
            valid = false;
        } else {
            edtEmail.setError(null);
        }
        if (TextUtils.isEmpty(getTextEdt(edtUsername))) {
            edtUsername.setError(Define.INPUT_USERNAME_ERROR);
            valid = false;
        } else {
            edtUsername.setError(null);
        }
        if (TextUtils.isEmpty(getTextEdt(edtPassword))
                || getTextEdt(edtPassword).length() < 6) {
            edtPassword.setError(Define.INPUT_PASSWORD_ERROR);
            valid = false;
        } else {
            if (!checkPassmatcher(getTextEdt(edtPassword), getTextEdt(edtPassConfirm))) {
                edtPassword.setError(Define.PASS_CONFIRM_ERROR);
                valid = false;
            } else {
                edtPassword.setError(null);
            }
        }
        return valid;
    }

    public void onSignupSuccess() {
        onCreateChildInFirebase();
    }

    public void onCreateChildInFirebase() {
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Define.USERS_CHILD)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(new UserModel(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                        getTextEdt(edtUsername),
                        true,
                        Define.NO_AVATAR_IMAGE_LINK,
                        getTextEdt(edtFullname)));
    }

    public void onSignupFailed() {
        ToastTextLong(Define.SIGNUP_ERROR_MESSAGE);
    }

    public void doLogin(String email, String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            onSignupFailed();
                        } else {
                            onSignupSuccess();
                            DataHelper.getInstance().setAvatarDefault(getTextEdt(edtFullname));
                            final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            progressDialog.dismiss();
                            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                            intent.putExtra("PASS", getTextEdt(edtPassword));
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
    //endregion
}