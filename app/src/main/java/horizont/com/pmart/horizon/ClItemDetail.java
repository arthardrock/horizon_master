package horizont.com.pmart.horizon;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static horizont.com.pmart.horizon.ClDialogBox.getHttpLoading;

public class ClItemDetail extends AppCompatActivity {
 TextView txt_item;
 ImageView img_item;

    ProgressDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_detailactivity);
        txt_item = findViewById(R.id.txt_item);
        img_item = findViewById(R.id.img_item);

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null){
            txt_item.setText(mBundle.getString("item"));
            String image = getIntent().getExtras().getString("image");
            Glide.with(this)
                    .load(image)
                    .placeholder(R.drawable.ic_sync)
                    .into(img_item);
        }
    }
}
