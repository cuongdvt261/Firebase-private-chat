package chatapp.work.cuongdvt.chatapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import chatapp.work.cuongdvt.chatapp.Model.ListOnlineModel;
import chatapp.work.cuongdvt.chatapp.R;

public class ChatStatusAdapter extends RecyclerView.Adapter<ChatStatusAdapter.ViewHolder> {
    private List<ListOnlineModel> lstOnline;
    private LayoutInflater layoutInflater;
    private Context context;

    public ChatStatusAdapter(List<ListOnlineModel> lstStatus, Context context) {
        this.lstOnline = lstStatus;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void addItem(int position, ListOnlineModel data) {
        this.lstOnline.add(position, data);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        this.lstOnline.remove(position);
        notifyItemRemoved(position);
    }

    public void updateList(List<ListOnlineModel> data) {
        this.lstOnline = data;
        notifyDataSetChanged();
    }

    public ListOnlineModel getItemByPos(int position) {
        return lstOnline.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.chat_list_online_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.userName.setText(lstOnline.get(position).getUserName());
        int imgAvaOnline = this.getMipmapResIdByName(lstOnline.get(position).getAvaName());
        int imgStatus = this.getMipmapResIdByName(lstOnline.get(position).getStatusName());
        holder.avaName.setImageResource(imgAvaOnline);
        holder.statusName.setImageResource(imgStatus);
    }

    @Override
    public int getItemCount() {
        return lstOnline.size();
    }

    public int getMipmapResIdByName(String resName) {
        String pkgName = context.getPackageName();
        int resID = context.getResources().getIdentifier(resName, "mipmap", pkgName);
        return resID;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView avaName;
        private ImageView statusName;
        private TextView userName;

        public ViewHolder(final View itemView) {
            super(itemView);
            avaName = (ImageView) itemView.findViewById(R.id.imgvAvatarOnline);
            userName = (TextView) itemView.findViewById(R.id.txtUsername);
            statusName = (ImageView) itemView.findViewById(R.id.imgvStatus);
        }
    }
}
