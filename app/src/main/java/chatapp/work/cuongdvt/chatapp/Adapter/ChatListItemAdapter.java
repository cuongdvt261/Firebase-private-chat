package chatapp.work.cuongdvt.chatapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import chatapp.work.cuongdvt.chatapp.Model.ChatListModel;
import chatapp.work.cuongdvt.chatapp.R;

/**
 * Created by cuongdvt on 17/10/2016.
 */

public class ChatListItemAdapter extends RecyclerView.Adapter<ChatListItemAdapter.RecyclerViewHolder> {
    private List<ChatListModel> lstContent;
    private Context context;
    private LayoutInflater layoutInflater;

    public ChatListItemAdapter(Context context, List<ChatListModel> lstData) {
        this.lstContent = lstData;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void addItem(int position, ChatListModel data) {
        this.lstContent.add(position, data);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        this.lstContent.remove(position);
        notifyItemRemoved(position);
    }

    public void updateList(List<ChatListModel> data) {
        this.lstContent = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.chat_list_items_content, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.txtSender.setText(lstContent.get(position).getSender());
        holder.txtShortContent.setText(lstContent.get(position).getContent());
        holder.txtTiming.setText(lstContent.get(position).getTiming());
        Picasso.with(context).load(lstContent.get(position).getAvaName()).into(holder.imgAva);
    }

    @Override
    public int getItemCount() {
        return lstContent.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAva;
        private TextView txtSender;
        private TextView txtShortContent;
        private TextView txtTiming;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imgAva = (ImageView) itemView.findViewById(R.id.customImg);
            txtSender = (TextView) itemView.findViewById(R.id.txtSender);
            txtShortContent = (TextView) itemView.findViewById(R.id.txtContent);
            txtTiming = (TextView) itemView.findViewById(R.id.textview_timing);
        }
    }
}


