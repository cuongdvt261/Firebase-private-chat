package chatapp.work.cuongdvt.chatapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import chatapp.work.cuongdvt.chatapp.Model.Message;
import chatapp.work.cuongdvt.chatapp.R;

/**
 * Created by cuongdvt on 16/10/2016.
 */

public class MessageListAdapter extends BaseAdapter {
    private Context context;
    private List<Message> messagesItems;

    public MessageListAdapter(Context context, List<Message> navDrawerItems) {
        this.context = context;
        this.messagesItems = navDrawerItems;
    }

    @Override
    public int getCount() {
        return messagesItems.size();
    }

    @Override
    public Message getItem(int position) {
        return messagesItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message m = messagesItems.get(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (messagesItems.get(position).getIsFromYou()) {
            convertView = mInflater.inflate(R.layout.list_item_message_right,
                    null);
        } else {
            convertView = mInflater.inflate(R.layout.list_item_message_left,
                    null);
        }

        TextView txtMsg = (TextView) convertView.findViewById(R.id.txtMsg);
        txtMsg.setText(m.getMessage());
        TextView txtTimeing = (TextView) convertView.findViewById(R.id.textview_time);
        txtTimeing.setText(m.getTiming());

        ImageView imgAva = (ImageView) convertView.findViewById(R.id.imgvAva);
        Picasso.with(context).load(m.getAvaName()).into(imgAva);

        return convertView;
    }

    public int getMipmapResIdByName(String resName) {
        String pkgName = context.getPackageName();
        int resID = context.getResources().getIdentifier(resName, "mipmap", pkgName);
        return resID;
    }
}
