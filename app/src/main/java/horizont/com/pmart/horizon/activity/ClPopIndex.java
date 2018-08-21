package horizont.com.pmart.horizon.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.io.InputStream;

import horizont.com.pmart.horizon.R;

public class ClPopIndex extends AppCompatActivity {
    public String promotion;
    private ImageView img_promotion;
    //
    Bitmap bitmap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_popup_info);

        img_promotion = (ImageView) findViewById(R.id.img_promotion);
        img_promotion.setImageResource(R.drawable.mom);

        get_promotion Get_promotion = new get_promotion();
        Get_promotion.get_promotion(img_promotion,ClPopIndex.this);

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
    public class get_promotion extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public void get_promotion(ImageView img_promotion, Context context){
            this.imageView = img_promotion;
            Glide.with(context)
                    .load("http://gallery.devpmm.tech/main.php?g2_view=core.DownloadItem&g2_itemId=414&g2_serialNumber=1")
                    .into(img_promotion);
            //img_promotion.setImageBitmap(bitmap);
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

        }
    }
}
