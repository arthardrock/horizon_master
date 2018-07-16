package horizont.com.pmart.horizon;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ybq.android.spinkit.SpinKitView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    SpinKitView progress;

    public String item;
    public String image;
    public String price;
    public static ClHomeFragment newInstance() {
        ClHomeFragment fragment = new ClHomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        progress = (SpinKitView)view.findViewById(R.id.spin_kit);
        recyclerView = (RecyclerView) view.findViewById(R.id.rcy_item);
        dataList = new ArrayList<>();
        get_item(getContext());

        gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new ClCustomAdapter(getContext(),dataList);
        recyclerView.setAdapter(adapter);
        return view;
    }


    public void get_item(final Context context) {
        new AsyncTask<Void, Void, String>() {

            ProgressDialog progressDialog = getHttpLoading(context);
            AlertDialog.Builder errorDialog = gerErrorDialog(context);
            @Override

            protected String doInBackground(Void... voids) {
                return getDataPosPdt("api/apitestitem.jsp", fnPreparingSynDataPdt(""));//api/menu/1
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                progressDialog.dismiss();
                if(s.equals("error")){
                    errorDialog.show();
                }
                else{
                    super.onPostExecute(s);
                    ObjectMapper rootMapper = new ObjectMapper();
                    JsonNode obj = null;
                    try {
                        obj = rootMapper.readTree(s);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    JsonNode data = obj.path("menu");
                    for (JsonNode nodeMember : data) {

                        ClDataItem dataAr = new ClDataItem (nodeMember.path("item_name").asText(),nodeMember.path("item_image").asText(),
                                nodeMember.path("price").asText());

                        item = nodeMember.path("item_name").asText();
                        image = nodeMember.path("item_image").asText();
                        price = nodeMember.path("price").asText();

                        dataList.add(dataAr);

                        System.out.println("ITEM :" + dataAr);
                        adapter.notifyDataSetChanged();
                    }
//                    System.out.println("ITEM :" + item);
//                    System.out.println("image :" + image);
//                    System.out.println("price :" + price);
                }
            }
        }.execute();

    }
}