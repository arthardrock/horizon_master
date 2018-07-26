package horizont.com.pmart.horizon.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import horizont.com.pmart.horizon.fragment.ClFavorite;
import horizont.com.pmart.horizon.fragment.ClHome;
import horizont.com.pmart.horizon.fragment.ClLocation;
import horizont.com.pmart.horizon.fragment.ClProfile;
import horizont.com.pmart.horizon.fragment.ClPromotion;
import horizont.com.pmart.horizon.R;

public class MainActivity extends AppCompatActivity {
private Toolbar myToolbar;
private TextView profile;
private DrawerLayout drawerLayout;
private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        setToolbar();
        setHamburgerButton();

        profile = (TextView)findViewById(R.id.txt_nav_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegis();
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
}
