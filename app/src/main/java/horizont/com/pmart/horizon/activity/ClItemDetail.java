package horizont.com.pmart.horizon.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.math.BigInteger;
import java.util.Hashtable;

import horizont.com.pmart.horizon.EncryptMD5;
import horizont.com.pmart.horizon.R;
import me.leolin.shortcutbadger.ShortcutBadger;

public class ClItemDetail extends AppCompatActivity {
    private TextView txt_item, txt_price, displayInteger, count_notif,btn_add,txt_promotion;
    private ImageView img_item, img_back, btn_pricelist, myImgTitle;
    private Toolbar myToolbar;
    private LinearLayout  decrease;
    public int number  ;
    int minteger = 0;
    private Context context;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    public final static int QRcodeWidth = 500 ;
    Bitmap bitmap ;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_detailitem);
        myImgTitle = (ImageView) findViewById(R.id.img_title);
        setToolbar();
        txt_item = findViewById(R.id.txt_item);
        img_item = findViewById(R.id.img_item);
        txt_price = findViewById(R.id.txt_price);
        btn_pricelist = findViewById(R.id.btn_pricelist);
        btn_add = findViewById(R.id.btn_add);
        txt_promotion = findViewById(R.id.txt_promotion);
        btn_pricelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClItemDetail.this, PopPriceList.class);
                view.setPressed(true);
                startActivity(intent);
            }
        });
        Context context = getApplicationContext();
  /*      sp = getSharedPreferences("PREF_NAMBER", context.MODE_PRIVATE);

        editor = sp.edit();
        editor.putInt("", number);
        editor.commit();*/
        // remove IconApp
        ShortcutBadger.removeCount(context);

        //number = sp.getInt("number",-1);
        decrease = (LinearLayout) findViewById(R.id.btn_decrease);
        btn_add.setText(R.string.btn_selectItem);
        displayInteger = (TextView) findViewById(R.id.qty_number);

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            txt_item.setText(mBundle.getString("item"));
            txt_price.setText(mBundle.getString("promo_price"));
            txt_promotion.setText(mBundle.getString("item_promodesc"));
            String image = getIntent().getExtras().getString("image");
            Glide.with(this)
                    .load(image)
                    .placeholder(R.color.colorBlack_Trap)
                    .into(img_item);
        }
        decrease.setVisibility(View.INVISIBLE);
        //display(0);
    }
    public void increaseInteger(View view) {
        minteger = minteger + 1;
        display(minteger);
    }

    public void decreaseInteger(View view) {
        minteger = minteger - 1;
        display(minteger);
        if (minteger == 0) {
            decrease.setVisibility(View.INVISIBLE);
            btn_add.setText(R.string.btn_selectItem);
        }
    }
    private void display(final int number) {
        displayInteger.setText("" + number);

        if (displayInteger.toString().length() == 0) {
            decrease.setVisibility(View.INVISIBLE);
        } else {
            btn_add.setText(R.string.btn_addBasket);
            decrease.setVisibility(View.VISIBLE);
            count_notif.setText("" + number);
        }
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (number == 0 ) {
                    Log.d("Count",""+number);
                    Toast.makeText(ClItemDetail.this, "เพิ่มจำนวนสินค้า",
                            Toast.LENGTH_LONG).show();
                } else {
                    JsonObject jsonObj = new JsonObject();
                    JsonObject  jaOrder = new JsonObject();
                    String price = txt_price.getText().toString();
                    jaOrder.add("Order",jsonObj);
                    jsonObj.addProperty("Qty",String.valueOf(number));
                    jsonObj.addProperty("Name",txt_item.getText().toString());
                    jsonObj.addProperty("Price",price);
                    jsonObj.addProperty("Total",(number*Integer.parseInt(price)));

                    //String to Json format
                    jaOrder.toString();
                    Log.d("JSON",""+jaOrder);
                    Toast.makeText(ClItemDetail.this,  jaOrder.toString(),
                            Toast.LENGTH_LONG).show();

                    //Encypt Json to Md5
                    byte[] md5Text = jaOrder.toString().getBytes();
                    BigInteger md5Data = null;
                    try {
                        md5Data = new BigInteger(1, EncryptMD5.encryptMD5(md5Text));
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    String md5Str = md5Data.toString(16);
                    Log.d("MD5",""+md5Str);
                    try {
                        bitmap = TextToImageEncode(md5Str);
                        ImageView img_QRCode = (ImageView)findViewById(R.id.img_QRCode);
                        img_QRCode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private void setToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.custom_detail_toolbar);
        FrameLayout basket = (FrameLayout)findViewById(R.id.img_basket);
        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClItemDetail.this,ClCartShopping.class);
                startActivity(intent);
            }
        });
        count_notif = findViewById(R.id.count_notif);
        myImgTitle.setImageResource(R.drawable.logohor);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setPressed(true);
                finish();
            }
        });
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, hints
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.colorBlack):getResources().getColor(R.color.colorWhite);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.RGB_565 );
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}
