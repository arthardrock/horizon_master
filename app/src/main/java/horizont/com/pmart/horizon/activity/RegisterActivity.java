package horizont.com.pmart.horizon.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import horizont.com.pmart.horizon.ClPageAdapter;
import horizont.com.pmart.horizon.R;
import horizont.com.pmart.horizon.fragment.ClLogin;
import horizont.com.pmart.horizon.fragment.ClRegister;

public class RegisterActivity extends AppCompatActivity {
    private TextView myTextView;
    private ImageView img_back,img_busket;
    private Toolbar myToolbar;
    private AppBarLayout appBarLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register);
        myTextView = (TextView)findViewById(R.id.txt_title);
        myTextView.setText(R.string.title_register);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        tabLayout = (TabLayout) findViewById(R.id.tabs_layout);

        ClPageAdapter pageAdapter = new ClPageAdapter(getSupportFragmentManager());
        // Adding Fragments
        pageAdapter.AddFragment(new ClLogin(),"เข้าสู่ระบบ");
        pageAdapter.AddFragment(new ClRegister(),"สมัครสมาชิก");
        //Set Adapter
        viewPager.setAdapter(pageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setToolbar();
    }
    private void setToolbar(){
        myToolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        img_busket = findViewById(R.id.img_busket);
        img_busket.setVisibility(View.INVISIBLE);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
