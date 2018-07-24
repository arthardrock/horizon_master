package horizont.com.pmart.horizon.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import horizont.com.pmart.horizon.R;


public class PopInfo extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_popup_info);

        TextView txt_promotion1 = (TextView)findViewById(R.id.txt_promotion1);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Button btn_close = (Button)findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        int width = dm.widthPixels;
        int hight = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(hight*.6));
    }
}
