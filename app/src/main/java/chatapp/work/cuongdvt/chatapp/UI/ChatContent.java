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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import chatapp.work.cuongdvt.chatapp.Adapter.MessageListAdapter;
import chatapp.work.cuongdvt.chatapp.Helper.Helper;
import chatapp.work.cuongdvt.chatapp.Model.Message;
import chatapp.work.cuongdvt.chatapp.R;

public class ChatContent extends AppCompatActivity {

    private ListView listMsg;
    private Toolbar toolbar;
    private Button btnSend;
    private EditText edtInput;

    private MessageListAdapter adapter;
    private ArrayList<Message> lstMessages;

    private String toUser = "";
    private String fromUser = "";

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_chat_content);

        Bundle extras = getIntent().getExtras();
        if (extras == null) return;

        toUser = extras.getString("TO_USER");
        fromUser = Helper.getInstance().usernameOfEmail();

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
                Intent _iMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(_iMain);
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
                    mDatabase
                            .child("messages")
                            .child(fromUser + "_" + toUser)
                            .push()
                            .setValue(new Message(edtInput.getText().toString(),
                                    true,
                                    fromUser,
                                    "ava_1"));
                    mDatabase
                            .child("messages")
                            .child(toUser + "_" + fromUser)
                            .push()
                            .setValue(new Message(edtInput.getText().toString(),
                                    false,
                                    fromUser,
                                    "ava_1"));
                    edtInput.setText("");
                }
            }
        });
    }

    public void UpdateData(DataSnapshot ds) {
        lstMessages.add(new Message(ds.child("message").getValue().toString(),
                Boolean.valueOf(ds.child("isFromYou").getValue().toString()),
                ds.child("sender").getValue().toString(),
                "ava_1"));
        if (lstMessages.size() > 0) {
            adapter = new MessageListAdapter(getApplicationContext(), lstMessages);
            listMsg.setAdapter(adapter);
        } else {
            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_LONG).show();
        }
    }

    public void ReceiveData() {
        mDatabase.child("messages").child(fromUser + "_" + toUser).addChildEventListener(new ChildEventListener() {
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
        getSupportActionBar().setTitle(toUser);
        getSupportActionBar().setSubtitle("Online 12g truoc");
        toolbar.setTitleTextColor(Color.WHITE);
        listMsg = (ListView) findViewById(R.id.list_view_messages);
        edtInput = (EditText) findViewById(R.id.inputMsg);
        btnSend = (Button) findViewById(R.id.btnSend);
    }
}
