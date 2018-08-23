package horizont.com.pmart.horizon.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import horizont.com.pmart.horizon.R;

import static horizont.com.pmart.horizon.ClHttpReq.fnPreparingSynDataPdt;
import static horizont.com.pmart.horizon.ClHttpReq.getDataPosPdtLocal;
import static horizont.com.pmart.horizon.activity.ClDialogBox.gerErrorDialog;
import static horizont.com.pmart.horizon.activity.ClDialogBox.getHttpLoading;

public class ClPromotion extends Fragment{

    ImageView [] IMGS = new ImageView[3];
    int page =1;

    public String promoimg;

    public static ClPromotion newInstance() {
        ClPromotion fragment = new ClPromotion();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_promotion, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        IMGS[0] = view.findViewById(R.id.img_pro1);
        IMGS[1] = view.findViewById(R.id.img_pro2);
        IMGS[2] = view.findViewById(R.id.img_pro3);

        if (savedInstanceState == null) {
            getImagePromotion(getContext());
        }
        else {

        }
    }
    @SuppressLint("StaticFieldLeak")
    public void getImagePromotion(final Context context){
        new AsyncTask<Void,Void,String>(){
            ProgressDialog progressDialog = getHttpLoading(context);
            AlertDialog.Builder errorDialog = gerErrorDialog(context);
            @Override
            protected String doInBackground(Void... voids) {
                return getDataPosPdtLocal("api/promotiononlimit",fnPreparingSynDataPdt(""));
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                if (s.equals("error")) {
                    errorDialog.show();
                } else {
                    //String arJson =
                    ObjectMapper rootMapper = new ObjectMapper();
                    JsonNode obj = null;
                    try {
                        obj = rootMapper.readTree(s);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    JsonNode data = obj.path("promo");
                    for (JsonNode nodePromo : data) {
                        CldataPromo cldataPromo = new CldataPromo(nodePromo.path("promo_images").asText(), nodePromo.path("name_pm").asText());
                        promoimg = nodePromo.path("promo_images").asText();

                        for(int i = 0; i < 3; i++){
                            Glide.with(context)
                                    .load(""+promoimg)
                                    .into(IMGS[i]);
                            Log.d("IMG","Img : "+IMGS[i]);
                        }
                        //Log.d("IMG","Img : "+IMGS[page]);
                    }
                }
            }
        }.execute();
    }
    public class CldataPromo {
        private String promo_images;
        private String name_pm;

        public CldataPromo(String promo_images, String name_pm) {
            this.promo_images = promo_images;
            this.name_pm = name_pm;
        }

        public String getPromo_images() {
            return promo_images;
        }

        public void setPromo_images(String promo_images) {
            this.promo_images = promo_images;
        }

        public String getName_pm() {
            return name_pm;
        }

        public void setName_pm(String name_pm) {
            this.name_pm = name_pm;
        }
    }
}
