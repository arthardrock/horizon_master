package horizont.com.pmart.horizon.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import horizont.com.pmart.horizon.AdViewPagerAdapter;
import horizont.com.pmart.horizon.ClCustomAdapter;
import horizont.com.pmart.horizon.model.ClDataItem;
import horizont.com.pmart.horizon.model.ClSlideUnit;
import horizont.com.pmart.horizon.model.OnLoadMoreListener;
import horizont.com.pmart.horizon.R;

import static horizont.com.pmart.horizon.activity.ClDialogBox.gerErrorDialog;
import static horizont.com.pmart.horizon.activity.ClDialogBox.getHttpLoading;
import static horizont.com.pmart.horizon.ClHttpReq.fnPreparingSynDataPdt;
import static horizont.com.pmart.horizon.ClHttpReq.getDataPosPdtLocal;

public class ClHome extends Fragment {
    public String name;
    private RecyclerView recyclerView;
    private ClCustomAdapter adapter;
    private List<ClDataItem> dataList;
    private ImageView[] dots;
    private int dotcount;

    RequestQueue rq;
    List<ClSlideUnit> sliderImg;
    AdViewPagerAdapter adViewPagerAdapter;


    ProgressBar progress;

    public String item;
    public String image;
    public String price;
    public String promo_price;

    ViewPager viewAdPager;
    LinearLayout SliderDots;
    int page = 1;

    public static ClHome newInstance() {
        ClHome fragment = new ClHome();
        return fragment;
    }
    private Handler mHandler = new Handler(Looper.getMainLooper());


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimeTask(),2000,4000);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new ClCustomAdapter(recyclerView, getActivity(), dataList, getActivity());
        recyclerView.setAdapter(adapter);

        rq = Volley.newRequestQueue(getActivity());
        sliderImg = new ArrayList<>();

        sendRequest();

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(adapter.getItemViewType(position)){
                    case 0:
                        return 1;
                    case 1:
                        return 2;
                    default:
                        return -1;
                }
            }
        });
        if (savedInstanceState == null) {
            get_itemMore(getContext());
            recyclerView.setAdapter(adapter);
        }
        else {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, container, false);

        recyclerView = view.findViewById(R.id.rcy_item);
        dataList = new ArrayList<>();
        SliderDots = view.findViewById(R.id.sliderDots);
        viewAdPager = view.findViewById(R.id.viewAdPager);
        LinearLayout cateid = view.findViewById(R.id.cateid);


        viewAdPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotcount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.noactive_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        for (int i = 0; i < 6; i++){
            View v = inflater.inflate(R.layout.ly_item_cate,cateid,false);
            TextView textView = v.findViewById(R.id.text_cate);
            textView.setText("Category : "+i);
            ImageView imageView = v.findViewById(R.id.img_cate);
            imageView.setImageResource(R.drawable.cate_beef);
            cateid.addView(v);
        }
        return view;
    }
    @SuppressLint("StaticFieldLeak")
    public void get_itemMore(final Context context) {
        new AsyncTask<Void, Void, String>() {
            ProgressDialog progressDialog = getHttpLoading(context);
            AlertDialog.Builder errorDialog = gerErrorDialog(context);

            @Override
            protected String doInBackground(Void... voids) {
                     //config Class HttpReq
                return getDataPosPdtLocal( "api/menu/"+page, fnPreparingSynDataPdt(""));//"api/menu/"+page ,api/apitestitem.jsp
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
            }
            @Override
            protected void onPostExecute(String s) {
                progressDialog.dismiss();
                if (s.equals("error")) {
                    errorDialog.show();
                } else {
                    loaddata();
                    super.onPostExecute(s);
                    ObjectMapper rootMapper = new ObjectMapper();
                    JsonNode obj = null;
                    try {
                        obj = rootMapper.readTree(s);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    JsonNode data = obj.path("menu");
                    System.out.println("data size is"+data.size());
                    if(data.size()>0){
                        for (JsonNode nodeItem : data) {
                            ClDataItem dataItem = new ClDataItem(nodeItem.path("item_name").asText(), nodeItem.path("item_image").asText(),
                                    nodeItem.path("price").asText(),nodeItem.path("promo_price").asText());
                            item = nodeItem.path("item_name").asText();
                            image = nodeItem.path("item_image").asText();
                            price = nodeItem.path("price").asText();
                            promo_price = nodeItem.path("promo_price").asText();
                            dataList.add(dataItem);
                            adapter.notifyDataSetChanged();
                            adapter.setLoaded();
                            }
                         }
                        else{
                            Toast.makeText(getActivity(),"สิ้นสุดการค้นหา",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }.execute();
    }
    public class MyTimeTask extends TimerTask {
        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if(viewAdPager.getCurrentItem() == 0){
                        viewAdPager.setCurrentItem(1);

                    } else if (viewAdPager.getCurrentItem() == 1){
                        viewAdPager.setCurrentItem(2);

                    } else{
                        viewAdPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
    public void loaddata() {
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                dataList.add(null);
                adapter.notifyItemInserted(dataList.size() - 1);
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dataList.remove(dataList.size() - 1);
                        adapter.notifyItemRemoved(dataList.size());
                        get_itemMore(getActivity());
                        adapter.notifyDataSetChanged();
                    }
                }, 3000);
                adapter.setLoaded();
            }
        });
    }

    public void sendRequest(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://172.17.8.147:3000/api/promotiononlimit",
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
                adViewPagerAdapter = new AdViewPagerAdapter(sliderImg,getActivity());
                viewAdPager.setAdapter(adViewPagerAdapter);

                dotcount = adViewPagerAdapter.getCount();
                dots = new ImageView[dotcount];

                for(int i = 0; i < dotcount; i++){

                    dots[i] = new ImageView(getActivity());
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.noactive_dot));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(8, 0, 8, 0);

                    SliderDots.addView(dots[i], params);

                }

                dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.active_dot));


            }
            }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){

            }
        });
        rq.add(jsonArrayRequest);
    }
}
