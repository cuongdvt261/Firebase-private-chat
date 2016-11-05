package chatapp.work.cuongdvt.chatapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import chatapp.work.cuongdvt.chatapp.Model.ListOnlineModel;
import chatapp.work.cuongdvt.chatapp.R;

/**
 * Created by cuongdvt on 17/10/2016.
 */

public class ChatStatusAdapter extends BaseAdapter {
    private List<ListOnlineModel> lstStatus;
    private LayoutInflater layoutInflater;
    private Context context;

    public ChatStatusAdapter(List<ListOnlineModel> lstStatus, Context context) {
        this.lstStatus = lstStatus;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lstStatus.size();
    }

    @Override
    public Object getItem(int position) {
        return lstStatus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.chat_list_online_item, null);
            holder = new ViewHolder();
            holder.avaName = (ImageView) convertView.findViewById(R.id.imgvAvatarOnline);
            holder.userName = (TextView) convertView.findViewById(R.id.txtUsername);
            holder.statusName = (ImageView) convertView.findViewById(R.id.imgvStatus);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ListOnlineModel status = this.lstStatus.get(position);
        holder.userName.setText(status.getUserName());

        int imgAvaOnline = this.getMipmapResIdByName(status.getAvaName());
        int imgStatus = this.getMipmapResIdByName(status.getStatusName());
        holder.avaName.setImageResource(imgAvaOnline);
        holder.statusName.setImageResource(imgStatus);
        return convertView;
    }

    public int getMipmapResIdByName(String resName) {
        String pkgName = context.getPackageName();
        int resID = context.getResources().getIdentifier(resName, "mipmap", pkgName);
        return resID;
    }

    public static class ViewHolder {
        private ImageView avaName;
        private ImageView statusName;
        private TextView userName;

        public ViewHolder() {
        }
    }
}
