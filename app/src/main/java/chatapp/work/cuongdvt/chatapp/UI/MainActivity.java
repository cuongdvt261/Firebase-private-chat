package chatapp.work.cuongdvt.chatapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import chatapp.work.cuongdvt.chatapp.Fragment.ChatListFragment;
import chatapp.work.cuongdvt.chatapp.Fragment.ListOnlineFragment;
import chatapp.work.cuongdvt.chatapp.Fragment.UserInfoFragment;
import chatapp.work.cuongdvt.chatapp.Helper.Param;
import chatapp.work.cuongdvt.chatapp.Model.UserModel;
import chatapp.work.cuongdvt.chatapp.R;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private FirebaseAuth auth;
    private DatabaseReference mData;

    private FloatingSearchView mSearchView;
    private int[] tabIcons = {
            R.drawable.ic_action_chat,
            R.drawable.ic_action_online,
            R.drawable.ic_action_user
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("users")
                .child(auth.getCurrentUser().getUid())
                .setValue(new UserModel(Param.getInstance().usernameOfEmail(),
                        auth.getCurrentUser().getUid(),
                        true));

        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupIcons();

        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
        };

        mSearchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mData.child("users")
                .child(auth.getCurrentUser().getUid())
                .child("online")
                .setValue(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ChatListFragment(), "Tin nhắn");
        adapter.addFragment(new ListOnlineFragment(), "Trực tuyến");
        adapter.addFragment(new UserInfoFragment(), "Tài khoản");
        viewPager.setAdapter(adapter);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    private void setupIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }
}
