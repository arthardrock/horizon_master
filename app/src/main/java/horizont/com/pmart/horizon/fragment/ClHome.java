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
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ybq.android.spinkit.SpinKitView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import horizont.com.pmart.horizon.AdViewPagerAdapter;
import horizont.com.pmart.horizon.ClCustomAdapter;
import horizont.com.pmart.horizon.ClDetectConnection;
import horizont.com.pmart.horizon.activity.ClDataItem;
import horizont.com.pmart.horizon.OnLoadMoreListener;
import horizont.com.pmart.horizon.R;
import horizont.com.pmart.horizon.activity.MainActivity;

import static android.widget.Toast.LENGTH_LONG;
import static horizont.com.pmart.horizon.activity.ClDialogBox.gerErrorDialog;
import static horizont.com.pmart.horizon.activity.ClDialogBox.getHttpLoading;
import static horizont.com.pmart.horizon.ClHttpReq.fnPreparingSynDataPdt;
import static horizont.com.pmart.horizon.ClHttpReq.getDataPosPdtLocal;

public class ClHome extends Fragment {
    public String name;
    private RecyclerView recyclerView;
    private ClCustomAdapter adapter;
    private List<ClDataItem> dataList;

    ProgressBar progress;

    public String item;
    public String image;
    public String price;
    public String promo_price;

    ViewPager viewAdPager;
    LinearLayout linearLayoutHead;
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new ClCustomAdapter(recyclerView, getContext(), dataList, getActivity());
        recyclerView.setAdapter(adapter);
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
        }
        else {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rcy_item);
        dataList = new ArrayList<>();
        linearLayoutHead = view.findViewById(R.id.liner_head);
        viewAdPager = (ViewPager)view.findViewById(R.id.viewAdPager);
        //slideDots = (LinearLayout)findViewById(R.id.sliderDot);
        AdViewPagerAdapter adViewPagerAdapter = new AdViewPagerAdapter(getActivity());
        viewAdPager.setAdapter(adViewPagerAdapter);

        LinearLayout cateid = view.findViewById(R.id.cateid);
        for (int i = 0; i < 6; i++){
            View v = inflater.inflate(R.layout.item_cate,cateid,false);
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
                            ClDataItem dataAr = new ClDataItem(nodeItem.path("item_name").asText(), nodeItem.path("item_image").asText(),
                                    nodeItem.path("price").asText(),nodeItem.path("promo_price").asText());
                            item = nodeItem.path("item_name").asText();
                            image = nodeItem.path("item_image").asText();
                            price = nodeItem.path("price").asText();
                            promo_price = nodeItem.path("promo_price").asText();

                            dataList.add(dataAr);
                            System.out.println("ITEM :" + dataAr);
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
                    }else {
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
                        get_itemMore(getContext());
                        adapter.notifyDataSetChanged();
                    }
                }, 3000);
                adapter.setLoaded();
            }
        });
    }
}
