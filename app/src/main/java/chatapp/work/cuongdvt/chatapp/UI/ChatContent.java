package chatapp.work.cuongdvt.chatapp.UI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import chatapp.work.cuongdvt.chatapp.Adapter.MessageListAdapter;
import chatapp.work.cuongdvt.chatapp.Helper.DataHelper;
import chatapp.work.cuongdvt.chatapp.Helper.Define;
import chatapp.work.cuongdvt.chatapp.Model.Message;
import chatapp.work.cuongdvt.chatapp.R;

public class ChatContent extends AppCompatActivity {

    private ListView listMsg;
    private Toolbar toolbar;
    private Button btnSend;
    private EditText edtInput;

    private MessageListAdapter adapter;
    private ArrayList<Message> lstMessages;

    private String toUsername = null;
    private String fromUser = null;
    private String toUserAvatar = null;
    public String str = null;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_chat_content);

        Bundle extras = getIntent().getExtras();
        if (extras == null) return;

        toUsername = extras.getString(Define.INTENT_GET_USERNAME);
        toUserAvatar = extras.getString(Define.INTENT_GET_USER_AVATAR);
        fromUser = DataHelper.getInstance().usernameOfEmail();
        str = extras.getString("a");

        lstMessages = new ArrayList<>();

        InitComponent();
        SendMessages();
        ReceiveData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_chat_content, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(ChatContent.this, MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void SendMessages() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edtInput.getText().toString())) {
                    String msg = edtInput.getText().toString();
                    String url = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString();
                    DataHelper.getInstance().setMessageFrom(fromUser, toUsername, url, msg, true);
                    DataHelper.getInstance().setMessageFrom(toUsername, fromUser, url, msg, false);
                    edtInput.setText("");
                }
            }
        });
    }

    public void UpdateData(DataSnapshot ds) {
        lstMessages.add(new Message(ds.child(Define.MESAGE_NODE).getValue().toString(),
                Boolean.valueOf(ds.child("isFromYou").getValue().toString()),
                ds.child("sender").getValue().toString(),
                ds.child("avaName").getValue().toString(),
                ds.child("timing").getValue().toString()
        ));
        if (lstMessages.size() > 0) {
            adapter = new MessageListAdapter(getApplicationContext(), lstMessages);
            listMsg.setAdapter(adapter);
        } else {
            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_LONG).show();
        }
    }

    public void ReceiveData() {
        mDatabase.child("messages").child(fromUser + "_" + toUsername).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                UpdateData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                UpdateData(dataSnapshot);
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

    public void InitComponent() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(toUsername);
        getSupportActionBar().setSubtitle(DataHelper.getInstance().getNow());
        toolbar.setTitleTextColor(Color.WHITE);
        listMsg = (ListView) findViewById(R.id.list_view_messages);
        edtInput = (EditText) findViewById(R.id.inputMsg);
        btnSend = (Button) findViewById(R.id.btnSend);
    }
}
