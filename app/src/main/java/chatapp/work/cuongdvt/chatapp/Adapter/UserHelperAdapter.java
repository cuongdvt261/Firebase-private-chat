package chatapp.work.cuongdvt.chatapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import chatapp.work.cuongdvt.chatapp.Model.UserHelperModel;
import chatapp.work.cuongdvt.chatapp.R;

/**
 * Created by cuong on 12-Dec-16.
 */

public class UserHelperAdapter extends BaseAdapter {

    private List<UserHelperModel> lstData;
    private LayoutInflater inflater;
    private Context context;

    public UserHelperAdapter(Context _context, List<UserHelperModel> _list) {
        this.lstData = _list;
        this.context = _context;
        this.inflater = LayoutInflater.from(_context);
    }

    @Override
    public int getCount() {
        return this.lstData.size();
    }

    @Override
    public Object getItem(int position) {
        return this.lstData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.user_info_item, null);
            viewHolder = new ViewHolder();
            viewHolder.imgIcItem = (ImageView) convertView.findViewById(R.id.img_icon_item);
            viewHolder.txtTitleItem = (TextView) convertView.findViewById(R.id.txt_item_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        UserHelperModel model = this.lstData.get(position);
        int imgId = this.getMipmapResIdByName(model.getIcItem());
        viewHolder.imgIcItem.setImageResource(imgId);
        viewHolder.txtTitleItem.setText(model.getTitleItem());
        return convertView;
    }

    public int getMipmapResIdByName(String resName) {
        String pkgName = context.getPackageName();
        int resID = context.getResources().getIdentifier(resName, "mipmap", pkgName);
        return resID;
    }

    static class ViewHolder {
        private ImageView imgIcItem;
        private TextView txtTitleItem;
    }
}
