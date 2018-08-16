package horizont.com.pmart.horizon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import horizont.com.pmart.horizon.R;

public class ClToolBarHome extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tb_home_custom);
    }
    public void ToolBarHome(){
        FrameLayout basket = (FrameLayout)findViewById(R.id.img_basket);
        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClToolBarHome.this,ClCateActivity.class);
                startActivity(intent);
            }
        });
        TextView count = (TextView)findViewById(R.id.count_notif);
    }

}
