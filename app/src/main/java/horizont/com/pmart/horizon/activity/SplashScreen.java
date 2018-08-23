package horizont.com.pmart.horizon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;

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
        getAndroidVersion();
        welcome = (TextView)findViewById(R.id.txt_welcome);
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
        Intent intent = new Intent(this,MainActivity.class);
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
    public String getAndroidVersion() {
        String release = Build.VERSION.RELEASE;
        int sdkVersion = Build.VERSION.SDK_INT;
        Log.d("SDKVersion","Android SDK: "+ sdkVersion +"(" + release +")");
        return "Android SDK: " + sdkVersion + " (" + release +")";
    }
}
