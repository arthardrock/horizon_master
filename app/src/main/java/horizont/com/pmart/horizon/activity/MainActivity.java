package horizont.com.pmart.horizon.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import horizont.com.pmart.horizon.AdViewPagerAdapter;
import horizont.com.pmart.horizon.BuildConfig;
import horizont.com.pmart.horizon.ClCustomAdapter;
import horizont.com.pmart.horizon.fragment.ClFavorite;
import horizont.com.pmart.horizon.fragment.ClHome;
import horizont.com.pmart.horizon.fragment.ClLocation;
import horizont.com.pmart.horizon.fragment.ClProfile;
import horizont.com.pmart.horizon.fragment.ClPromotion;
import horizont.com.pmart.horizon.R;

public class MainActivity extends AppCompatActivity {
    private Toolbar myToolbar;
    private TextView  versionName;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private int dotscount;
    private ImageView[] dots;

    ViewPager viewAdPager;
    LinearLayout slideDots,profile,basket,store;


    ClCustomAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        setToolbar();
        setHamburgerButton();

        // Set viewPage ad and dotslid
        viewAdPager = (ViewPager)findViewById(R.id.viewAdPager);
        //slideDots = (LinearLayout)findViewById(R.id.sliderDot);
        AdViewPagerAdapter adViewPagerAdapter = new AdViewPagerAdapter(this);
        viewAdPager.setAdapter(adViewPagerAdapter);


        dotscount = adViewPagerAdapter.getCount();
//        dots = new ImageView[dotscount];
//
//        for (int i = 1; i < dotscount; i++){
//
//            dots[i] = new ImageView(this);
//            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.noactive_dot));
//
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//            params.setMargins(8,0,8,0);

            //slideDots.addView(dots[i],params);
//              }

        //dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.noactive_dot));

        /*viewAdPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i< dotscount; i++ ){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.noactive_dot));
                }
                    dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimeTask(),2000,4000);

        // Set Text version name
        versionName = (TextView)findViewById(R.id.txt_version);
        versionName.setText("Version : "+ BuildConfig.VERSION_NAME);

        profile = (LinearLayout)findViewById(R.id.txt_nav_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegis();
            }
        });

        basket = (LinearLayout)findViewById(R.id.txt_nav_cart);
        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCart();
            }
        });
        store = (LinearLayout)findViewById(R.id.txt_nav_store);
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        final BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.nav_home:
                                selectedFragment = ClHome.newInstance();
                                break;
                            case R.id.nav_location:
                                selectedFragment = ClLocation.newInstance();
                                break;
                            case R.id.nav_favorite:
                                selectedFragment = ClFavorite.newInstance();
                                break;
                            case R.id.nav_promotion:
                                selectedFragment = ClPromotion.newInstance();
                                break;
                            case R.id.nav_profile:
                                selectedFragment = ClProfile.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });
        startActivity(new Intent(MainActivity.this,PopInfo.class));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, ClHome.newInstance());
        transaction.commit();
    }
    public class MyTimeTask extends TimerTask{

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewAdPager.getCurrentItem() == 0){
                        viewAdPager.setCurrentItem(1);
                    } else if (viewAdPager.getCurrentItem() == 1){
                        viewAdPager.setCurrentItem(2);
                    }else {
                        viewAdPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    private void setToolbar(){
        myToolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    /*Set HamburgerButton
        drawerLayout
    */
    @SuppressLint("ResourceAsColor")
    private void setHamburgerButton(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this
                ,drawerLayout
                ,R.string.navigation_drawer_open
                ,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    protected void onPostCreate(@Nullable Bundle saveInstanceState) {
        super.onPostCreate(saveInstanceState);
        actionBarDrawerToggle.syncState();
    }
    // Search Data
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                //adapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void openRegis(){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
    public void openCart(){
        Intent intent = new Intent(this,ClCartShopping.class);
        startActivity(intent);
    }
}
