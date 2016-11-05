package chatapp.work.cuongdvt.chatapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import chatapp.work.cuongdvt.chatapp.Model.ChatListModel;
import chatapp.work.cuongdvt.chatapp.R;

/**
 * Created by cuongdvt on 17/10/2016.
 */

public class ChatListItemAdapter extends BaseAdapter {
    private List<ChatListModel> lstContent;
    private LayoutInflater layoutInflater;
    private Context context;

    public ChatListItemAdapter(Context context, List<ChatListModel> lstData) {
        this.lstContent = lstData;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lstContent.size();
    }

    @Override
    public Object getItem(int position) {
        return lstContent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.chat_list_items_content, null);
            holder = new ViewHolder();
            holder.imgAva = (ImageView) convertView.findViewById(R.id.customImg);
            holder.txtSender = (TextView) convertView.findViewById(R.id.txtSender);
            holder.txtShortContent = (TextView) convertView.findViewById(R.id.txtContent);
            holder.txtTiming = (TextView) convertView.findViewById(R.id.textview_timing);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ChatListModel items = this.lstContent.get(position);
//        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/myfont.ttf");
        holder.txtSender.setText(items.getSender());
        holder.txtShortContent.setText(items.getContent());
        holder.txtTiming.setText(items.getTiming());

//        holder.txtSender.setTypeface(face);
//        holder.txtShortContent.setTypeface(face);
//        holder.txtTiming.setTypeface(face);

        int imgId = this.getMipmapResIdByName(items.getAvaName());
        holder.imgAva.setImageResource(imgId);
        return convertView;
    }

    public int getMipmapResIdByName(String resName) {
        String pkgName = context.getPackageName();
        int resID = context.getResources().getIdentifier(resName, "mipmap", pkgName);
        return resID;
    }

    public static class ViewHolder {
        private ImageView imgAva;
        private TextView txtSender;
        private TextView txtShortContent;
        private TextView txtTiming;

        public ViewHolder() {
        }
    }
}


