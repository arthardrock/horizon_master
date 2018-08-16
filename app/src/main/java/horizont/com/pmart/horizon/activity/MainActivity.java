package horizont.com.pmart.horizon.activity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import horizont.com.pmart.horizon.BuildConfig;
import horizont.com.pmart.horizon.PushActivity;
import horizont.com.pmart.horizon.fragment.ClFavorite;
import horizont.com.pmart.horizon.fragment.ClHome;
import horizont.com.pmart.horizon.fragment.ClLocation;
import horizont.com.pmart.horizon.fragment.ClProfile;
import horizont.com.pmart.horizon.fragment.ClPromotion;
import horizont.com.pmart.horizon.R;
import me.leolin.shortcutbadger.ShortcutBadger;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar myToolbar;
    private TextView versionName;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Button myButton;
    String title;
    String message;
    PushActivity pushActivity = new PushActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        setToolbar();
        setHamburgerButton();

        // Set Text version name
        versionName = (TextView) findViewById(R.id.txt_version);
        getPackageName();
        versionName.setText("Version : " + BuildConfig.VERSION_NAME);

        findViewById(R.id.txt_nav_profile).setOnClickListener(this);
        findViewById(R.id.txt_nav_cart).setOnClickListener(this);
        findViewById(R.id.txt_nav_store).setOnClickListener(this);

        pushActivity.get_notification();

        myButton = (Button) findViewById(R.id.btn_noti);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_notification();
                int badgeCount = 1;
                ShortcutBadger.applyCount(MainActivity.this, badgeCount);
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
        startActivity(new Intent(MainActivity.this, PopInfo.class));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, ClHome.newInstance());
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.txt_nav_profile) {
            openRegis();
        }
        if (id == R.id.txt_nav_cart) {
            openCart();
        }
        if (id == R.id.txt_nav_store) {

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

    private void setToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        ImageView imageView = (ImageView)findViewById(R.id.img_logo);
        imageView.setImageResource(R.drawable.logohor);
        FrameLayout busket = (FrameLayout)findViewById(R.id.img_basket);
    /*    busket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ClCateActivity.class);
                startActivity(intent);
            }
        });*/
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /*Set HamburgerButton
        drawerLayout
    */
    @SuppressLint("ResourceAsColor")
    private void setHamburgerButton() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this
                , drawerLayout
                , R.string.navigation_drawer_open
                , R.string.navigation_drawer_close);
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
        SearchView searchView = (SearchView) item.getActionView();
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openRegis() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void openCart() {
        Intent intent = new Intent(this, ClCartShopping.class);
        startActivity(intent);
    }

    // set Onclick On xml file
    public void get_notification() {
        Intent intent = new Intent(this, PushActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("message", message);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(PushActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification
                .Builder(this)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.icon1)
                .setContentTitle(pushActivity.title)
                .setContentText(pushActivity.message)
                .setVibrate(new long[]{1000, 300})
                .setLights(Color.GREEN, 3000, 3000)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setNumber(1)
                .build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1000, notification);
    }
}
