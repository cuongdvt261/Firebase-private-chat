package chatapp.work.cuongdvt.chatapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import chatapp.work.cuongdvt.chatapp.Adapter.ChatStatusAdapter;
import chatapp.work.cuongdvt.chatapp.Decoration.DividerItemDecoration;
import chatapp.work.cuongdvt.chatapp.Event.RecyclerItemClickListener;
import chatapp.work.cuongdvt.chatapp.Helper.Define;
import chatapp.work.cuongdvt.chatapp.Model.ListOnlineModel;
import chatapp.work.cuongdvt.chatapp.Model.UserModel;
import chatapp.work.cuongdvt.chatapp.R;
import chatapp.work.cuongdvt.chatapp.UI.ChatContent;

public class ListOnlineFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseAuth auth;
    private DatabaseReference mData;

    private List<ListOnlineModel> lstOnline;
    private ChatStatusAdapter chatAdapter;

    private String state = null;

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
        final View view = inflater.inflate(R.layout.list_online_content, container, false);

        initComponent(view);
        initRecyclerView(recyclerView);
        ReceiveData();
        return view;
    }

    public void initComponent(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleOnline);
        layoutManager = new LinearLayoutManager(getActivity());
    }

    public void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
    }

    private void onListClickItem() {
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ListOnlineModel model = chatAdapter.getItemByPos(position);
                Intent intent = new Intent(getActivity(), ChatContent.class);
                intent.putExtra(Define.INTENT_GET_USERNAME, model.getUserName());
                intent.putExtra("a", model.getStatusName());
                startActivity(intent);
            }
        }));
    }

    public void GetUpdate(DataSnapshot ds) {
        UserModel um = ds.getValue(UserModel.class);
        lstOnline.add(new ListOnlineModel(um.getAvatarUrl(),
                um.isOnline() ? Define.IC_ONLINE : Define.IC_OFFLINE,
                um.getUsername()));
        if (lstOnline.size() > 0) {
            chatAdapter = new ChatStatusAdapter(lstOnline, getActivity());
            recyclerView.setAdapter(chatAdapter);
            chatAdapter.notifyDataSetChanged();
        } else {
            Log.d(Define.TAG_LIST_NO_DATA, "No Data");
        }
        state = String.valueOf(um.isOnline());
    }


    public void ReceiveData() {
        mData.child(Define.USERS_CHILD).addChildEventListener(new ChildEventListener() {
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
                GetUpdate(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                GetUpdate(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        onListClickItem();
    }
}
