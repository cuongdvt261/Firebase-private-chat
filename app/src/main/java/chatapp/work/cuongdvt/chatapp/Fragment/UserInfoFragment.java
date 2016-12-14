package chatapp.work.cuongdvt.chatapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import chatapp.work.cuongdvt.chatapp.Adapter.UserHelperAdapter;
import chatapp.work.cuongdvt.chatapp.Model.UserHelperModel;
import chatapp.work.cuongdvt.chatapp.R;
import chatapp.work.cuongdvt.chatapp.UI.LoginActivity;

public class UserInfoFragment extends Fragment {

    private ListView listView;
    private List<UserHelperModel> lstData;
    private FirebaseAuth.AuthStateListener authStateListener;

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

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                }
            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch ((int) id) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        FirebaseAuth.getInstance().signOut();
                        break;
                    default:
                        break;
                }
            }
        });
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
