package com.example.jyoti.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.jyoti.myproject.Fragments.EmailFragment;
import com.example.jyoti.myproject.Fragments.MessageFragment;
import com.example.jyoti.myproject.Fragments.ReminderFragment;
import com.example.jyoti.myproject.Fragments.TodolistFragment;
import com.example.jyoti.myproject.MenuItems.AboutUs;
import com.example.jyoti.myproject.MenuItems.SendQuery;
import com.example.jyoti.myproject.MenuItems.recyclebin.RecycleBin;

import java.util.ArrayList;
import java.util.List;

import static com.example.jyoti.myproject.R.id.container;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView tvcreate;
    Toolbar toolbar;
    private ViewPager mViewPager;
    TabLayout tabLayout;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private FragmentManager fragmentManager;

    Fragment fragment=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     //bjhbj
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("RemindMe");

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(container);
        setupViewPager(mViewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);



/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
  fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
/*
        tvcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MenuActivity.class);
                startActivity(intent);
            }
        });*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
      //  SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
        //    adapter.addFrag(new ReminderFragment(), "Reminder");
            TabLayout.Tab tab = tabLayout.getTabAt(0);
            tab.select();
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            tab.select();
        } else if (id == R.id.nav_slideshow) {
            TabLayout.Tab tab = tabLayout.getTabAt(2);
            tab.select();

        } else if (id == R.id.nav_manage) {
            TabLayout.Tab tab = tabLayout.getTabAt(3);
            tab.select();

        } else if (id == R.id.nav_bin) {
            Intent intent =new Intent(MainActivity.this, RecycleBin.class);
            intent.putExtra("bin","bin");
            startActivity(intent);

        }
        else if(id == R.id.nav_share)
        {
            Intent intent =new Intent(MainActivity.this, AboutUs.class);
            startActivity(intent);
        }else if (id == R.id.nav_send) {
            Intent intent =new Intent(MainActivity.this, SendQuery.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ReminderFragment(), "Reminder");
        adapter.addFrag(new TodolistFragment(), "To do List");
        adapter.addFrag(new MessageFragment(), "Messages");
        adapter.addFrag(new EmailFragment(), "Emails");
/*
        adapter.addFrag(new FiveFragment(), "FIVE");
        adapter.addFrag(new SixFragment(), "SIX");
        adapter.addFrag(new SevenFragment(), "SEVEN");
        adapter.addFrag(new EightFragment(), "EIGHT");
        adapter.addFrag(new NineFragment(), "NINE");
        adapter.addFrag(new TenFragment(), "TEN");
*/
        viewPager.setAdapter(adapter);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return mFragmentList.get(position);        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }
}
