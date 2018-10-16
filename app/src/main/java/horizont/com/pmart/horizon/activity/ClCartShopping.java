package horizont.com.pmart.horizon.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import horizont.com.pmart.horizon.R;

public class ClCartShopping extends AppCompatActivity {
    private ImageView img_back;
    private Toolbar myToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cart);
        FrameLayout basket = (FrameLayout)findViewById(R.id.img_basket);
        basket.setVisibility(View.INVISIBLE);
        setToolbar();
    }
    private void setToolbar(){
        myToolbar = (Toolbar) findViewById(R.id.custom_detail_toolbar);
        img_back = findViewById(R.id.img_back);
        TextView title = (TextView)findViewById(R.id.txt_tile);
        title.setText(R.string.nav_cart);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setPressed(true);
                finish();
            }
        });
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
