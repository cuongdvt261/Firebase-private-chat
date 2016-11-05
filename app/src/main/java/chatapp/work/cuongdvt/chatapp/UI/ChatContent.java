package chatapp.work.cuongdvt.chatapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import chatapp.work.cuongdvt.chatapp.Adapter.MessageListAdapter;
import chatapp.work.cuongdvt.chatapp.Model.Message;
import chatapp.work.cuongdvt.chatapp.R;

public class ChatContent extends AppCompatActivity {

    private ListView listMsg;
    private Toolbar toolbar;
    private Button btnSend;
    private EditText edtInput;
    private MessageListAdapter adapter;
    private ArrayList<Message> listMessages = new ArrayList<Message>();

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_chat_content);

        Bundle extras = getIntent().getExtras();
        if (extras == null) return;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle(extras.getString("TO_USER"));

        listMsg = (ListView) findViewById(R.id.list_view_messages);
        edtInput = (EditText) findViewById(R.id.inputMsg);

        mDatabase.child("Messages").push().setValue(listMessages);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.setIsFromYou(true);
                msg.setMessage(edtInput.getText().toString());
                msg.setSender("You");
                msg.setAvaName("ava_1");
                mDatabase.child("Messages").push().setValue(msg);
                listMessages.add(msg);
                adapter = new MessageListAdapter(getApplicationContext(), listMessages);
                listMsg.setAdapter(adapter);
                edtInput.setText("");
            }
        });
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
}
