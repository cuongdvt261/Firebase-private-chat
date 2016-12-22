package chatapp.work.cuongdvt.chatapp.UI;

import android.content.Intent;
import android.os.Bundle;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import chatapp.work.cuongdvt.chatapp.Fragment.ChatListFragment;
import chatapp.work.cuongdvt.chatapp.Fragment.ListOnlineFragment;
import chatapp.work.cuongdvt.chatapp.Fragment.UserInfoFragment;
import chatapp.work.cuongdvt.chatapp.Helper.DataHelper;
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
        setupTabIcons();

        // Set User is online
        DataHelper.getInstance().setUserOnline(true);

        //Re-auth firebase
        Bundle extras = getIntent().getExtras();
        if (extras == null) return;

        String password = extras.getString("PASS");
        DataHelper.getInstance().reauthFirebase(auth.getCurrentUser().getEmail(), password);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DataHelper.getInstance().setUserOnline(false);
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            return;
        }
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

    private void SetNavigationDrawer() {
        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.navigation);
        View v = navView.getHeaderView(0);
        ImageView imgHeader = (ImageView) v.findViewById(R.id.image_ava_header);
        TextView txtName = (TextView) v.findViewById(R.id.name_header);
        TextView txtEmail = (TextView) v.findViewById(R.id.email_header);
        Picasso.with(getApplicationContext())
                .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl())
                .into(imgHeader);
        txtName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        txtEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                Fragment frag = null;
                int itemId = menuItem.getItemId();

                switch (itemId) {
                    case R.id.navigation_item_1:
                        startActivity(new Intent(MainActivity.this, UpdateUserInfoActivity.class));
                        break;
                    case R.id.navigation_item_2:
                        break;
                    case R.id.navigation_item_3:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        break;
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
    //endregion

    //region Tab
    private int[] tabIcons = {
            R.drawable.ic_a,
            R.drawable.ic_fullname,
            R.drawable.ic_username
    };

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText(Define.FRAGMENT_FIRST_NAME);
        tabOne.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_a, 0, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText(Define.FRAGMENT_SECOND_NAME);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_b, 0, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText(Define.FRAGMENT_THIRD_NAME);
        tabThree.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_c, 0, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
    }

    private void setupTabIcon() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }
    //endregion
}
