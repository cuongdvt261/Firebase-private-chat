package chatapp.work.cuongdvt.chatapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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
        chatAdapter = new ChatStatusAdapter(lstOnline, getActivity());
        lstChat.setAdapter(chatAdapter);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lstOnline.clear();
    }

    @Override
    public void onStart() {
        super.onStart();
        mData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                lstOnline.add(new ListOnlineModel("ava_1",
                        dataSnapshot.child("State").getValue().toString(),
                        dataSnapshot.getKey().toString()));
                chatAdapter.notifyDataSetChanged();
                return;
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
}
