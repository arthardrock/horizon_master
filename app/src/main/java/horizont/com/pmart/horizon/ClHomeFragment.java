package horizont.com.pmart.horizon;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import static android.widget.Toast.LENGTH_LONG;
import static horizont.com.pmart.horizon.ClDialogBox.gerErrorDialog;
import static horizont.com.pmart.horizon.ClDialogBox.getHttpLoading;
import static horizont.com.pmart.horizon.ClHttpReq.fnPreparingSynDataPdt;
import static horizont.com.pmart.horizon.ClHttpReq.getDataPosPdt;
import static horizont.com.pmart.horizon.ClHttpReq.getDataPosPdtLocal;

public class ClHomeFragment extends Fragment {
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

    public static ClHomeFragment newInstance() {
        ClHomeFragment fragment = new ClHomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rcy_item);
        dataList = new ArrayList<>();

        //ttt();
        get_itemMore(getContext());

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
        loaddata();
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
//                    loaddata();
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

                        }
                    }
                    else{
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
//                        dataList.remove(dataList.size() - 1);
//                        adapter.notifyItemRemoved(dataList.size());

                        get_itemMore(getContext());
//                            for (int j = page; page < 2; page++) {
//                                Log.d("page", "" + (page + 1));
//                                continue;
//                            }
                        adapter.setLoaded();
                        adapter.notifyDataSetChanged();
                    }
                }, 3000);
            }
        });
//                dataList.add(null);
//                adapter.notifyItemInserted(dataList.size() - 1);
//                get_itemMore(getActivity());
//                if (dataList.size() == 20) {
//                    dataList.add(null);
//                    adapter.notifyItemInserted(dataList.size() - 1);
//                    recyclerView.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            dataList.remove(dataList.size() - 1);
//                            adapter.notifyItemRemoved(dataList.size());
//
//                            int index = dataList.size();
//                            int end = index + 20;
//
//                            for (int i = index; i < end; i++) {
//                                //progress.setVisibility(View.GONE);
//                                ClDataItem dataItem = new ClDataItem("name", "image", "price");
//                                dataItem.getItem_name();
//                                dataItem.getItem_image();
//                                dataItem.getPrice();
//
//                            }
//                            for (int j = page; page < 2; page++) {
//                                get_itemMore(getContext());
//                                Log.d("page", "" + (page + 1));
//                                continue;
//                            }
//                            adapter.notifyDataSetChanged();
//                            adapter.setLoaded();
//                        }
//                    }, 3000);
//                }
//                else if (dataList.size() == 40){
//                    dataList.add(null);
//                    adapter.notifyItemInserted(dataList.size() - 1);
//                    recyclerView.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            dataList.remove(dataList.size() - 1);
//                            adapter.notifyItemRemoved(dataList.size());
//
//                            int index = dataList.size();
//                            int end = index + 20;
//
//                            for (int i = index; i < end; i++) {
//                                //progress.setVisibility(View.GONE);
//                                ClDataItem dataItem = new ClDataItem("name", "image", "price");
//                                dataItem.getItem_name();
//                                dataItem.getItem_image();
//                                dataItem.getPrice();
//
//                                for (int j = page; page < 3; page++) {
//                                    get_itemMore(getContext());
//                                    Log.d("page", "" + (page + 1));
//                                    continue;
//                                }
//
//                            }
//                            adapter.notifyDataSetChanged();
//                            adapter.setLoaded();
//                        }
//                    }, 3000);
//                    //progressBarData.setVisibility(View.GONE);
//                }
//                else {
//                    Toast.makeText(getActivity(),"สิ้นสุดการค้นหา",LENGTH_LONG).show();
//                }
//            }
//        });
    }
}
