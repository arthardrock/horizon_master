package horizont.com.pmart.horizon.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ybq.android.spinkit.SpinKitView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import horizont.com.pmart.horizon.ClCustomAdapter;
import horizont.com.pmart.horizon.activity.ClDataItem;
import horizont.com.pmart.horizon.OnLoadMoreListener;
import horizont.com.pmart.horizon.R;

import static android.widget.Toast.LENGTH_LONG;
import static horizont.com.pmart.horizon.activity.ClDialogBox.gerErrorDialog;
import static horizont.com.pmart.horizon.activity.ClDialogBox.getHttpLoading;
import static horizont.com.pmart.horizon.ClHttpReq.fnPreparingSynDataPdt;
import static horizont.com.pmart.horizon.ClHttpReq.getDataPosPdtLocal;

public class ClHome extends Fragment {
    public String name;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private ClCustomAdapter adapter;
    private List<ClDataItem> dataList;
    private SpinKitView progressBarData;

    ProgressBar progress;

    public String item;
    public String image;
    public String price;

    int page = 1;

    public static ClHome newInstance() {
        ClHome fragment = new ClHome();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rcy_item);
        dataList = new ArrayList<>();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new ClCustomAdapter(recyclerView, getContext(), dataList, getActivity());
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
        get_itemMore(getContext());
        recyclerView.setAdapter(adapter);
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
                        for (JsonNode nodeMember : data) {

                            ClDataItem dataAr = new ClDataItem(nodeMember.path("item_name").asText(), nodeMember.path("item_image").asText(),
                                    nodeMember.path("price").asText());

                            item = nodeMember.path("item_name").asText();
                            image = nodeMember.path("item_image").asText();
                            price = nodeMember.path("price").asText();

                            dataList.add(dataAr);

                            System.out.println("ITEM :" + dataAr);
                            adapter.notifyDataSetChanged();
                            adapter.setLoaded();
                            }
                    }
                        else{
                            Toast.makeText(getActivity(),"สิ้นสุดการค้นหา",LENGTH_LONG).show();
                            System.out.println("else");
                    }
                }
            }
        }.execute();
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
