package horizont.com.pmart.horizon.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import horizont.com.pmart.horizon.R;

public class PopPriceList extends AppCompatActivity {
    Spinner spinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_pricelist);
        spinner = findViewById(R.id.sp_branch);

        LinearLayout btn_close = findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        String[] price = getResources().getStringArray(R.array.club);
        ArrayAdapter<String> adapterEnglish  = new ArrayAdapter<String>(this,R.layout.simple_dropdown,price);
        spinner.setAdapter(adapterEnglish);

        int width = dm.widthPixels;
        int hight = dm.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(hight*.7));
    }
}
