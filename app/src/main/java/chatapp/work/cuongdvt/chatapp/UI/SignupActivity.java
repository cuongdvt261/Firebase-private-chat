package chatapp.work.cuongdvt.chatapp.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import chatapp.work.cuongdvt.chatapp.R;

public class SignupActivity extends AppCompatActivity {

    private Toolbar topToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle(null);
        Toolbar topToolBar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(topToolBar);
    }

}
