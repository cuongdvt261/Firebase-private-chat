package chatapp.work.cuongdvt.chatapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import chatapp.work.cuongdvt.chatapp.Adapter.UserHelperAdapter;
import chatapp.work.cuongdvt.chatapp.Model.UserHelperModel;
import chatapp.work.cuongdvt.chatapp.R;

/**
 * Created by cuongdvt on 22/11/2016.
 */

public class UserInfoFragment extends Fragment {

    private ListView listView;
    private List<UserHelperModel> lstData;

    public UserInfoFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.user_info_layout, container, false);
        listView = (ListView) view.findViewById(R.id.lstUserInfoContent);
        lstData = getListData();
        listView.setAdapter(new UserHelperAdapter(getActivity(), lstData));
        return view;
    }

    private List<UserHelperModel> getListData() {
        List<UserHelperModel> list = new ArrayList<>();
        UserHelperModel chaneEmail = new UserHelperModel("ic_change_email", "Thay đổi email");
        UserHelperModel changePass = new UserHelperModel("ic_change_pass", "Thay đổi mật khẩu");
        UserHelperModel resetMail = new UserHelperModel("ic_reset_email", "Lấy lại Email");
        UserHelperModel logout = new UserHelperModel("ic_logout", "Đăng xuất");


        list.add(chaneEmail);
        list.add(changePass);
        list.add(resetMail);
        list.add(logout);

        return list;
    }

}
