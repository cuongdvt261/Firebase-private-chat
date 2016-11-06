package chatapp.work.cuongdvt.chatapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import chatapp.work.cuongdvt.chatapp.Adapter.ChatStatusAdapter;
import chatapp.work.cuongdvt.chatapp.Model.ListOnlineModel;
import chatapp.work.cuongdvt.chatapp.R;

/**
 * Created by cuongdvt on 16/10/2016.
 */

public class ListOnlineFragment extends Fragment {
    public ListView lstChat;
    private FirebaseAuth auth;
    private DatabaseReference mData;

    private List<ListOnlineModel> lstOnline;
    private ChatStatusAdapter chatAdapter;

    public ListOnlineFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference();
        lstOnline = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.chat_list_items, container, false);
        lstChat = (ListView) view.findViewById(R.id.lsvMenuItem);

        ReceiveData();
        return view;
    }

    private void onListClickItem() {
        lstChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListOnlineModel model = (ListOnlineModel) parent.getAdapter().getItem(position);
                Intent intent = new Intent(getActivity(), ChatContent.class);
                intent.putExtra("TO_USER", model.getUserName());
                startActivity(intent);
            }
        });
    }

    public void GetUpdate(DataSnapshot ds) {
        lstOnline.add(new ListOnlineModel("ava_1",
                Boolean.valueOf(ds.child("online").getValue().toString()) ? "online" : "offline",
                ds.child("username").getValue().toString()));
        if (lstOnline.size() > 0) {
            chatAdapter = new ChatStatusAdapter(lstOnline, getActivity());
            lstChat.setAdapter(chatAdapter);
        } else {
            Toast.makeText(getActivity(), "No Data", Toast.LENGTH_LONG).show();
        }

    }

    public void ReceiveData() {
        mData.child("users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                GetUpdate(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                GetUpdate(dataSnapshot);
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
        onListClickItem();
    }
}
