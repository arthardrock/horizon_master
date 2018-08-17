package horizont.com.pmart.horizon.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import horizont.com.pmart.horizon.R;
import me.leolin.shortcutbadger.ShortcutBadger;

public class ClItemDetail extends AppCompatActivity {
    private TextView txt_item, txt_price, displayInteger, count_notif;
    private ImageView img_item, img_back, btn_pricelist, myImgTitle;
    private Toolbar myToolbar;
    private LinearLayout btn_add, decrease;
    private RecyclerView carditem;
    public int number ;
    int minteger = 0;
    private Context context;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_detailitem);
        myImgTitle = (ImageView) findViewById(R.id.img_title);
        setToolbar();
        txt_item = findViewById(R.id.txt_item);
        img_item = findViewById(R.id.img_item);
        txt_price = findViewById(R.id.txt_price);
        btn_pricelist = findViewById(R.id.btn_pricelist);
        btn_pricelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClItemDetail.this, PopPriceList.class);
                startActivity(intent);
            }
        });
        Context context = getApplicationContext();
        sp = getSharedPreferences("PREF_NAMBER", context.MODE_PRIVATE);

        editor = sp.edit();
        editor.putInt("", number);
        editor.commit();
        // remove IconApp
        ShortcutBadger.removeCount(context);

        number = sp.getInt("number",-1);
        decrease = (LinearLayout) findViewById(R.id.btn_decrease);

        displayInteger = (TextView) findViewById(R.id.qty_number);

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            txt_item.setText(mBundle.getString("item"));
            txt_price.setText(mBundle.getString("promo_price"));
            String image = getIntent().getExtras().getString("image");
            Glide.with(this)
                    .load(image)
                    .placeholder(R.drawable.iconhorizon)
                    .into(img_item);
        }
        btn_add = findViewById(R.id.btn_add);
        decrease.setVisibility(View.INVISIBLE);
    }
    public void increaseInteger(View view) {
        minteger = minteger + 1;
        display(minteger);
    }

    public void decreaseInteger(View view) {
        minteger = minteger - 1;
        display(minteger);
        if (minteger == 0) {
            decrease.setVisibility(View.INVISIBLE);

        }
    }
    private void display(final int number) {
        displayInteger.setText("" + number);

        if (displayInteger.toString().length() == 0) {
            decrease.setVisibility(View.INVISIBLE);
        } else {
            decrease.setVisibility(View.VISIBLE);
            count_notif.setText("" + number);

        }
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (number == 0) {
                    Toast.makeText(ClItemDetail.this, "เพิ่มจำนวนสินค้า",
                            Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(ClItemDetail.this, "จำนวน : " + number,
                            Toast.LENGTH_LONG).show();

                SharedPreferences.Editor editor = sp.edit();
                editor.commit();
            }
        });
    }

    private void setToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.custom_detail_toolbar);
        FrameLayout basket = (FrameLayout)findViewById(R.id.img_basket);
        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClItemDetail.this,ClCartShopping.class);
                startActivity(intent);
            }
        });
        count_notif = findViewById(R.id.count_notif);
        myImgTitle.setImageResource(R.drawable.logohor);
        img_back = findViewById(R.id.img_back);
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
