package horizont.com.pmart.horizon.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;

import horizont.com.pmart.horizon.Information;
import horizont.com.pmart.horizon.R;

public class SplashScreen extends AppCompatActivity {
    private Handler handler;
    private Runnable runnable;
    private TextView welcome;
    long delay_time;
    long time = 2500L;
    private SpinKitView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_splash_screen);
        welcome = (TextView)findViewById(R.id.txt_welcome);
//        welcome.setText(R.string.txt_wel_EN);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                openMainAct();
            }
        };
    }
    public void openMainAct() {
        welcome.setText(R.string.txt_wel_EN);
        Intent intent = new Intent(this,Information.class);
        startActivity(intent);
        finish();
    }
    public void onResume() {
        super.onResume();
        delay_time = time;
        handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
        welcome.setText(R.string.txt_wel_TH);
    }
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis()-time);
    }
}
