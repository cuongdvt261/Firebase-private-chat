package chatapp.work.cuongdvt.chatapp.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import chatapp.work.cuongdvt.chatapp.R;

public class UpdateUserInfoActivity extends AppCompatActivity {
    //region Param
    private TextView txtEmail;
    private EditText edtUsername, edtFullname;
    private Button btnSave;
    private ImageView imgAva;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);

        initComponent();
    }

    public void initComponent() {
        txtEmail = (TextView) findViewById(R.id.textview_email);
        edtFullname = (EditText) findViewById(R.id.edit_change_full_name);
        edtUsername = (EditText) findViewById(R.id.edit_user_name);
        btnSave = (Button) findViewById(R.id.button_change);
        imgAva = (ImageView) findViewById(R.id.img_avatar);
    }
}
