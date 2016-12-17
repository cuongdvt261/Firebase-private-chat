package chatapp.work.cuongdvt.chatapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import chatapp.work.cuongdvt.chatapp.Adapter.UserHelperAdapter;
import chatapp.work.cuongdvt.chatapp.Model.UserHelperModel;
import chatapp.work.cuongdvt.chatapp.R;
import chatapp.work.cuongdvt.chatapp.UI.LoginActivity;
import chatapp.work.cuongdvt.chatapp.UI.UpdateUserInfoActivity;

public class UserInfoFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    //region Param
    private ListView listView;
    private List<UserHelperModel> lstData;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ImageView imgAva;
    private TextView txtName, txtEmail;
    //endregion

    //region Event
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.user_info_layout, container, false);
        initComponent(view);
        initRecycleview();
        updateUserInfo();

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
        listView.setOnItemClickListener(this);
        imgAva.setOnClickListener(this);
        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch ((int) id) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                break;
            case 3:
                FirebaseAuth.getInstance().signOut();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_avatar:
                startActivity(new Intent(getActivity(), UpdateUserInfoActivity.class));
                break;
        }
    }
    //endregion

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

    public void initComponent(View view) {
        listView = (ListView) view.findViewById(R.id.lstUserInfoContent);
        imgAva = (ImageView) view.findViewById(R.id.img_avatar);
        txtName = (TextView) view.findViewById(R.id.textview_display_name);
        txtEmail = (TextView) view.findViewById(R.id.textview_email);
    }

    public void initRecycleview() {
        lstData = getListData();
        listView.setAdapter(new UserHelperAdapter(getActivity(), lstData));
    }

    public void updateUserInfo() {
        Picasso.with(getActivity()).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(imgAva);
        txtName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        txtEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }
}
