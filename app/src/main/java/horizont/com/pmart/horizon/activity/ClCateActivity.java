package horizont.com.pmart.horizon.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import horizont.com.pmart.horizon.R;

public class ClCateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_categoly_scrollview);

        LinearLayout cateid = findViewById(R.id.cateid);
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < 5; i++){
            View view = inflater.inflate(R.layout.ly_item_cate,cateid,false);

            ImageView imageView = view.findViewById(R.id.img_cate);
            imageView.setImageResource(R.drawable.cate_beef);
            cateid.addView(view);
        }
    }
}
