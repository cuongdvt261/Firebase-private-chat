package chatapp.work.cuongdvt.chatapp.UI;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import chatapp.work.cuongdvt.chatapp.Adapter.ChatListItemAdapter;
import chatapp.work.cuongdvt.chatapp.Model.ChatListModel;
import chatapp.work.cuongdvt.chatapp.R;

public class ChatListFragment extends Fragment {

    public ListView lsvItems;
    private List<ChatListModel> list;

    public ChatListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_list_items, container, false);
        List<ChatListModel> items = GetListContent();
        lsvItems = (ListView) view.findViewById(R.id.lsvMenuItem);
        lsvItems.setAdapter(new ChatListItemAdapter(getActivity(), items));
        lsvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent iChat = new Intent(getActivity(), ChatContent.class);
                startActivity(iChat);
            }
        });
        return view;
    }

    private List<ChatListModel> GetListContent() {
        list = new ArrayList<>();
        ChatListModel item_1 = new ChatListModel("Cuong", "ava_1", "Short Content 1 ...", "10 phut truoc");
        ChatListModel item_2 = new ChatListModel("Van la Cuong", "ava_2", "Short Content 2 ...", "5 ngay truoc");
        ChatListModel item_3 = new ChatListModel("Chinh la Cuong", "ava_3", "Short Content 3 ...", "20/10/2016");
        ChatListModel item_4 = new ChatListModel("Khong phai Cuong", "ava_4", "Short Content 4 ...", "22/10/2016");

        list.add(item_1);
        list.add(item_2);
        list.add(item_3);
        list.add(item_4);

        return list;
    }

}
