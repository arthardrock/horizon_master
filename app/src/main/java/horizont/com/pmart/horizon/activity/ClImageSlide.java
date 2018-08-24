package horizont.com.pmart.horizon.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import horizont.com.pmart.horizon.AdViewPagerAdapter;
import horizont.com.pmart.horizon.R;
import horizont.com.pmart.horizon.model.ClSlideUnit;


public class ClImageSlide extends AppCompatActivity {

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private TextView skip;
    ViewPager viewad;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dott;

    String requrl = "http://172.17.8.147:3000/api/promotiononlimit";

    RequestQueue rq;
    List<ClSlideUnit> sliderImg;
    AdViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_image_slider);
        viewad = (ViewPager) findViewById(R.id.view_slide);
        sliderDotspanel = findViewById(R.id.sliderDots);
        /*Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimeTask(),3000,4000);*/

        rq = Volley.newRequestQueue(this);
        sliderImg = new ArrayList<>();
        sendRequest();

        viewad.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dott[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.noactive_dot));
                }

                dott[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public class MyTimeTask extends TimerTask {
        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if(viewad.getCurrentItem() == 0){
                        viewad.setCurrentItem(1);

                    } else if (viewad.getCurrentItem() == 1){
                        viewad.setCurrentItem(2);

                    } else{
                        viewad.setCurrentItem(0);
                    }
                }
            });
        }
    }
    public void sendRequest(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://172.17.8.147:3000/api/promotiononlimit/slide",
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length();i++){

                    ClSlideUnit clSlideUnit = new ClSlideUnit();
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        clSlideUnit.setSliderImgUrl(jsonObject.getString("promo_images"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    sliderImg.add(clSlideUnit);
                }
                viewPagerAdapter = new AdViewPagerAdapter(sliderImg,ClImageSlide.this);
                viewad.setAdapter(viewPagerAdapter);

                dotscount = viewPagerAdapter.getCount();
                dott = new ImageView[dotscount];

                for(int i = 0; i < dotscount; i++){

                    dott[i] = new ImageView(ClImageSlide.this);
                    dott[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.noactive_dot));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(8, 0, 8, 0);

                    sliderDotspanel.addView(dott[i], params);

                }

                dott[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));


            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){

            }
        });
        rq.add(jsonArrayRequest);
    }

}
