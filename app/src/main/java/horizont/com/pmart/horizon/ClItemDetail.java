package horizont.com.pmart.horizon;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import static android.app.PendingIntent.getActivity;
import static horizont.com.pmart.horizon.ClDialogBox.getHttpLoading;

public class ClItemDetail extends AppCompatActivity {
 private TextView txt_item,myTextView;
 private ImageView img_item ,img_back;
 private Toolbar myToolbar;
 private LinearLayout btn_add;


    ProgressDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_detailactivity);
        myTextView = (TextView)findViewById(R.id.txt_title);
        setToolbar();
        txt_item = findViewById(R.id.txt_item);
        img_item = findViewById(R.id.img_item);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null){
            txt_item.setText(mBundle.getString("item"));
            myTextView.setText(mBundle.getString("item"));
            String image = getIntent().getExtras().getString("image");
            Glide.with(this)
                    .load(image)
                    .placeholder(R.drawable.iconhorizon)
                    .into(img_item);
        }
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ClItemDetail.this, "ยังไม่พร้อมใช้งาน",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
    private void setToolbar(){
        myToolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        img_back = findViewById(R.id.img_back);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
