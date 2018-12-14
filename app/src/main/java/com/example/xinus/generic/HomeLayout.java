package com.example.xinus.generic;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.squareup.picasso.Picasso;

public class HomeLayout extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public TextView navUserName,navName,navEmail,navPhoneNumber;
    DatabaseReference profileDataBaseReference;
    FirebaseAuth firebaseAuth;
    String current_user_id;
    MaterialSearchView materialSearchView;
    ImageView imageView;

    FragmentClassNotes fragmentClassNotes;
    FragmentClassLecture fragmentClassLecture;
    FragmentClassMaterialsUpload fragmentClassMaterialsUpload;

    private ViewPager viewPagerProfile;
    private TabLayout tabLayoutProfile;
    public TabPagerAdapter tabPagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        viewPagerProfile = findViewById(R.id.profileviewerPager);
        tabLayoutProfile = findViewById(R.id.profileBottomToolbar);


        materialSearchView=(MaterialSearchView)findViewById(R.id.profileSearchView);
        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        View navigationViewHeaderView = navigationView.getHeaderView(0);
        navUserName = (TextView)navigationViewHeaderView.findViewById(R.id.nav_userName);
        navName = (TextView)navigationViewHeaderView.findViewById(R.id.nav_Name);
        navEmail= (TextView)navigationViewHeaderView.findViewById(R.id.nav_Email);
        navPhoneNumber= (TextView)navigationViewHeaderView.findViewById(R.id.nav_phoneNumber);
        imageView = navigationViewHeaderView.findViewById(R.id.nav_proPic);


        Typeface typeface = Typeface.createFromAsset(getAssets() , "impact.ttf");
        navUserName.setTypeface(typeface);
        navName.setTypeface(typeface);
        navEmail.setTypeface(typeface);
        navPhoneNumber.setTypeface(typeface);


        firebaseAuth = FirebaseAuth.getInstance();
        current_user_id = firebaseAuth.getUid();
        profileDataBaseReference=FirebaseDatabase.getInstance().getReference("Users").child(current_user_id);

        profileDataBaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null)
                {

                    Users users = dataSnapshot.getValue(Users.class);
                    Picasso.get().load(users.getProfileImage()).into(imageView);
                    navUserName.setText(users.getUserName());
                    navName.setText(users.getName());
                    navEmail.setText(users.getEmail());
                    navPhoneNumber.setText(users.getPhoneNumber());
                }
                else
                {
                    Toast.makeText(getApplicationContext() , "Error 2" , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(
                            getApplicationContext()
                            ,"Error"
                            ,Toast.LENGTH_LONG
                    ).show();
            }
        });




        tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager() , tabLayoutProfile.getTabCount());
        viewPagerProfile.setOffscreenPageLimit(1);
        viewPagerProfile.setAdapter(tabPagerAdapter);
        viewPagerProfile.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

        setupViewPager(viewPagerProfile);
        tabLayoutProfile.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerProfile.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode == KeyEvent.KEYCODE_BACK)
//        {
//            return true;
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }

    private void setupViewPager(ViewPager viewPagerProfile) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        FragmentClassNotes fragmentClassNotes =  new FragmentClassNotes();
        FragmentClassLecture fragmentClassLecture= new FragmentClassLecture();
        FragmentClassMaterialsUpload fragmentClassMaterialsUpload = new FragmentClassMaterialsUpload();
        adapter.addFragment(fragmentClassNotes);
        adapter.addFragment(fragmentClassLecture);
        adapter.addFragment(fragmentClassMaterialsUpload);
        viewPagerProfile.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.home_layout, menu);
        MenuItem itemMenu = menu.findItem(R.id.searchView);
        materialSearchView.setMenuItem(itemMenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();



        if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(this , MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private class TabPagerAdapter extends FragmentPagerAdapter {
        private int tabCount;
        TabPagerAdapter(FragmentManager fm , int tabCount) {
            super(fm);
            this.tabCount = tabCount;
        }
        @Override
       public Fragment getItem(int position) {

           switch (position){
               case 0:
                   return new FragmentClassNotes();
               case 1:
                   return new FragmentClassLecture();
               case 2:
                   return new FragmentClassMaterialsUpload();

            }
            return null;
        }



        @Override
        public int getCount() {
            return tabCount;
        }
    }
}

