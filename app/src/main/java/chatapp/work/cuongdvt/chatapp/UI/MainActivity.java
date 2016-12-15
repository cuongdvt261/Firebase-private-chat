package chatapp.work.cuongdvt.chatapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import chatapp.work.cuongdvt.chatapp.Fragment.ChatListFragment;
import chatapp.work.cuongdvt.chatapp.Fragment.ListOnlineFragment;
import chatapp.work.cuongdvt.chatapp.Fragment.UserInfoFragment;
import chatapp.work.cuongdvt.chatapp.Helper.Define;
import chatapp.work.cuongdvt.chatapp.R;

public class MainActivity extends AppCompatActivity {
    //region Define
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout dLayout;
    private MaterialSearchView searchView;

    private FirebaseAuth auth;
    private DatabaseReference mData;
    //endregion

    //region Event
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitFirebase();
        InitComponent();
        InitToolbar();
        InitViewPage();
        SetNavigationDrawer();
        QueryData();

        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mData.child(Define.USERS_CHILD)
                .child(auth.getCurrentUser().getUid())
                .child(Define.USERS_ONLINE_CHILD)
                .setValue(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            // Android home
            case android.R.id.home: {
                dLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }
    //endregion

    //region Method
    public void InitComponent() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
    }

    public void InitToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void InitViewPage() {
        SetupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void InitFirebase() {
        auth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference();
    }

    private void SetupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ChatListFragment(), Define.FRAGMENT_FIRST_NAME);
        adapter.addFragment(new ListOnlineFragment(), Define.FRAGMENT_SECOND_NAME);
        adapter.addFragment(new UserInfoFragment(), Define.FRAGMENT_THIRD_NAME);
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

    private void SetNavigationDrawer() {
        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.navigation);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                Fragment frag = null;
                int itemId = menuItem.getItemId();

                if (itemId == R.id.navigation_item_1) {
                    Toast.makeText(getApplicationContext(), "Test1", Toast.LENGTH_LONG).show();
                } else if (itemId == R.id.navigation_item_2) {
                    Toast.makeText(getApplicationContext(), "Test2", Toast.LENGTH_LONG).show();
                }

                if (frag != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    transaction.replace(R.id.frame, frag);
                    transaction.commit();
                    dLayout.closeDrawers();
                    return true;
                }

                return false;
            }
        });
    }

    public void QueryData() {
        mData.child(Define.USERS_CHILD)
                .child(auth.getCurrentUser().getUid())
                .child(Define.USERS_ONLINE_CHILD)
                .setValue(true);
    }
    //endregion
}
