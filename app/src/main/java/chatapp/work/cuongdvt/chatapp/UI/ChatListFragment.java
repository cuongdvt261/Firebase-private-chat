package chatapp.work.cuongdvt.chatapp.UI;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import chatapp.work.cuongdvt.chatapp.Adapter.ChatListItemAdapter;
import chatapp.work.cuongdvt.chatapp.Helper.Param;
import chatapp.work.cuongdvt.chatapp.Model.ChatListModel;
import chatapp.work.cuongdvt.chatapp.Model.Message;
import chatapp.work.cuongdvt.chatapp.R;

public class ChatListFragment extends Fragment {

    public ListView lsvItems;
    private List<ChatListModel> list;

    private DatabaseReference mData;
    private ChatListItemAdapter adapter;

    private String chatWith = "";

    public ChatListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mData = FirebaseDatabase.getInstance().getReference();
        View view = inflater.inflate(R.layout.chat_list_items, container, false);
        list = new ArrayList<>();
        lsvItems = (ListView) view.findViewById(R.id.lsvMenuItem);
        ReceiveData();
        return view;
    }

    private void GetData(DataSnapshot ds) {
        if (ds.getKey().split("_")[0].toString().equals(Param.getInstance().usernameOfEmail())) {
            chatWith = ds.getKey().split("_")[1].toString();
            Message m = new Message();
            for (DataSnapshot data :
                    ds.getChildren()) {
                m = data.getValue(Message.class);
            }
            list.add(new ChatListModel(m.getSender(), m.getAvaName(), m.getMessage(), "08/11/2016"));
            if (list.size() > 0) {
                adapter = new ChatListItemAdapter(getActivity(), list);
                lsvItems.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void onListClickItem() {
        lsvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ChatContent.class);
                intent.putExtra("TO_USER", chatWith);
                startActivity(intent);
            }
        });
    }

    private void ReceiveData() {
        mData.child("messages").addChildEventListener(new ChildEventListener() {
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
        onListClickItem();
    }
}
