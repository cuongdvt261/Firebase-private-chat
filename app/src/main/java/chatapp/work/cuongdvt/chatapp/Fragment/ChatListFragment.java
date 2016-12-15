package chatapp.work.cuongdvt.chatapp.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import chatapp.work.cuongdvt.chatapp.Adapter.ChatListItemAdapter;
import chatapp.work.cuongdvt.chatapp.Decoration.DividerItemDecoration;
import chatapp.work.cuongdvt.chatapp.Event.RecyclerItemClickListener;
import chatapp.work.cuongdvt.chatapp.Helper.Helper;
import chatapp.work.cuongdvt.chatapp.Model.ChatListModel;
import chatapp.work.cuongdvt.chatapp.Model.Message;
import chatapp.work.cuongdvt.chatapp.R;
import chatapp.work.cuongdvt.chatapp.UI.ChatContent;

public class ChatListFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private List<ChatListModel> list;
    private DatabaseReference mData;
    private ChatListItemAdapter adapter;
    private ArrayList<String> arrToUser;

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
        arrToUser = new ArrayList<>();
        InitComponent(view);
        InitRecyclerView(recyclerView);
        ReceiveData();
        return view;
    }

    public void InitComponent(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleMenuItem);
    }

    public void InitRecyclerView(RecyclerView recyclerView) {
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
    }

    private void GetData(DataSnapshot ds) {
        Message msg = new Message();
        if (ds.getKey().split("_")[0].toString().equals(Helper.getInstance().usernameOfEmail())) {
            for (DataSnapshot data :
                    ds.getChildren()) {
                msg = data.getValue(Message.class);
            }
            list.add(new ChatListModel(ds.getKey().split("_")[1].toString(), msg.getAvaName(), msg.getMessage(), "08/11/2016"));
            if (list.size() > 0) {
                adapter = new ChatListItemAdapter(getActivity(), list);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getActivity(), "No Data", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void onListClickItem() {
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ChatListModel model = list.get(position);
                Intent intent = new Intent(getActivity(), ChatContent.class);
                intent.putExtra("TO_USER", model.getSender());
                startActivity(intent);
            }
        }));
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
