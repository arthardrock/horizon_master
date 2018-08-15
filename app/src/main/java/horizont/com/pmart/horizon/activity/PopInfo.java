package horizont.com.pmart.horizon.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import horizont.com.pmart.horizon.R;

public class PopInfo extends AppCompatActivity {
    public String promotion;
    private ImageView img_promotion;
    //Bitmap bitmap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_popup_info);

        img_promotion = (ImageView) findViewById(R.id.img_promotion);
        img_promotion.setImageResource(R.drawable.mom);

        String URLIMAGE = "http://172.17.9.238:3000/api/promotion/2";
        //new get_promotion(img_promotion).execute(URLIMAGE);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        LinearLayout btn_close = (LinearLayout) findViewById(R.id.btn_close);
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
   /* @SuppressLint("StaticFieldLeak")
    public class get_promotion extends AsyncTask<String, Void, Bitmap>{
        ImageView imageView;

        public get_promotion(ImageView img_promotion){
            this.imageView = img_promotion;
        }
        @Override
        protected Bitmap doInBackground(String... url) {
            String urldisplay = url [0];

            try{
                InputStream srt = new java.net.URL(urldisplay).openStream();
                bitmap = BitmapFactory.decodeStream(srt);
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            img_promotion.setImageBitmap(bitmap);
        }
    }*/
}
