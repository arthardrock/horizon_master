package horizont.com.pmart.horizon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Information extends AppCompatActivity {
    private ImageView comsoon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_information);
        comsoon = (ImageView)findViewById(R.id.img_soon);
    }
}
