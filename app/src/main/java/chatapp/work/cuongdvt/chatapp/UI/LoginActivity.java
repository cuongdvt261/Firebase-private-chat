package chatapp.work.cuongdvt.chatapp.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import chatapp.work.cuongdvt.chatapp.Helper.Define;
import chatapp.work.cuongdvt.chatapp.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //region Variable
    private EditText edtEmail, edtPassword;
    private Button btnLogin, btnSignup, btnReset;
    private DatabaseReference mData;

    private FirebaseAuth auth;

    private ProgressDialog progressDialog;
    //endregion

    //region Event
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference();
        checkAuth();
        setContentView(R.layout.activity_login);

        initComponent();

        btnSignup.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                if (!isValidate()) {
                    onLoginFailed();
                    return;
                } else {
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage(Define.LOADING_MESSAGE);
                    progressDialog.show();
                    doLogin(getTextEdt(edtEmail), getTextEdt(edtPassword));
                }
                break;
            case R.id.btn_signup:
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                break;
            case R.id.btn_reset_password:
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
                break;
            default:
                break;
        }
    }
    //endregion

    //region Method
    public void checkAuth() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    public void initComponent() {
        edtEmail = (EditText) findViewById(R.id.email);
        edtPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btn);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnReset = (Button) findViewById(R.id.btn_reset_password);

        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
    }

    public boolean isValidate() {
        boolean valid = true;
        if (TextUtils.isEmpty(getTextEdt(edtEmail)) ||
                !Patterns.EMAIL_ADDRESS.matcher(getTextEdt(edtEmail)).matches()) {
            edtEmail.setError(Define.INPUT_EMAIL_ERROR);
            valid = false;
        } else {
            edtEmail.setError(null);
        }
        if (TextUtils.isEmpty(getTextEdt(edtPassword))
                || getTextEdt(edtPassword).length() < 6) {
            edtPassword.setError(Define.INPUT_PASSWORD_ERROR);
            valid = false;
        } else {
            edtPassword.setError(null);
        }
        return valid;
    }

    public String getTextEdt(EditText edt) {
        return edt.getText().toString().trim();
    }

    public void doLogin(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            onLoginFailed();
                        } else {
                            onLoginSuccess();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    public void onLoginSuccess() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getApplicationContext(), Define.LOGIN_ERROR_MESSAGE, Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
    }
    //endregion
}
