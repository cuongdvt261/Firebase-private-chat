package chatapp.work.cuongdvt.chatapp.UI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import chatapp.work.cuongdvt.chatapp.Helper.Define;
import chatapp.work.cuongdvt.chatapp.R;

public class ResetPasswordActivity extends Activity implements View.OnClickListener {
    private EditText edtEmail;
    private Button btnReset, btnBack;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        initComponent();

        btnReset.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reset_password:
                if (!isValidate())
                    return;
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage(Define.LOADING_MESSAGE);
                progressDialog.show();
                doResetPassword(edtEmail.getText().toString().trim());
                break;
            case R.id.btn_back:
                finish();
                break;
            default:
                break;
        }
    }

    public void initComponent() {
        edtEmail = (EditText) findViewById(R.id.email_reset);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        btnBack = (Button) findViewById(R.id.btn_back);
        progressDialog = new ProgressDialog(ResetPasswordActivity.this, R.style.AppTheme_Dark_Dialog);
    }

    public boolean isValidate() {
        boolean isValid = true;
        String str = edtEmail.getText().toString().trim();
        if (TextUtils.isEmpty(str)
                || !Patterns.EMAIL_ADDRESS.matcher(str).matches()) {
            edtEmail.setError(Define.INPUT_EMAIL_ERROR);
            isValid = false;
        } else {
            edtEmail.setError(null);
        }
        return isValid;
    }

    public void doResetPassword(String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            onResetSuccess();
                        } else {
                            onResetFailed();
                        }
                    }
                });
    }

    public void onResetSuccess() {
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(), Define.SEND_MAIL_SUCCESS, Toast.LENGTH_LONG).show();
        startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
    }

    public void onResetFailed() {
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(), Define.SEND_MAIL_FAILED, Toast.LENGTH_LONG).show();
    }
}
