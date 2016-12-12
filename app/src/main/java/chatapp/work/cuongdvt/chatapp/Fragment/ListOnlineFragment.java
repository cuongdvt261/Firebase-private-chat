package chatapp.work.cuongdvt.chatapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import chatapp.work.cuongdvt.chatapp.Decoration.DividerItemDecoration;
import chatapp.work.cuongdvt.chatapp.Event.RecyclerItemClickListener;
import chatapp.work.cuongdvt.chatapp.Model.ListOnlineModel;
import chatapp.work.cuongdvt.chatapp.Model.UserModel;
import chatapp.work.cuongdvt.chatapp.R;
import chatapp.work.cuongdvt.chatapp.UI.ChatContent;

/**
 * Created by cuongdvt on 16/10/2016.
 */

public class ListOnlineFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

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
        final View view = inflater.inflate(R.layout.list_online_content, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycleOnline);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));

        ReceiveData();
        return view;
    }

    private void onListClickItem() {
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ListOnlineModel model = chatAdapter.getItemByPos(position);
                Intent intent = new Intent(getActivity(), ChatContent.class);
                intent.putExtra("TO_USER", model.getUserName());
                startActivity(intent);
            }
        }));
    }

    public void GetUpdate(DataSnapshot ds) {
        lstOnline.clear();
        if (ds.getKey().equals("users")) {
            for (DataSnapshot data :
                    ds.getChildren()) {
                UserModel um = data.getValue(UserModel.class);
                lstOnline.add(new ListOnlineModel("ava_1",
                        um.isOnline() ? "online" : "offline",
                        um.getUsername()));
                if (lstOnline.size() > 0) {
                    chatAdapter = new ChatStatusAdapter(lstOnline, getActivity());
                    recyclerView.setAdapter(chatAdapter);
                    chatAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "No Data", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void ReceiveData() {
        mData.addChildEventListener(new ChildEventListener() {
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
