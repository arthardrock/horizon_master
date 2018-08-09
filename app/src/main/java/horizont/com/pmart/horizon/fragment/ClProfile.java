package horizont.com.pmart.horizon.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import horizont.com.pmart.horizon.R;

import static horizont.com.pmart.horizon.activity.ClDialogBox.gerErrorDialog;
import static horizont.com.pmart.horizon.activity.ClDialogBox.getHttpLoading;
import static horizont.com.pmart.horizon.ClHttpReq.fnPreparingSynDataPdt;
import static horizont.com.pmart.horizon.ClHttpReq.getDataPosPdt;

public class ClProfile extends Fragment{
    public String name,sername,company,titlename,address,sub_district,district,province;
    private TextView txt_name,txt_titlename,txt_company,txt_address,
            txt_sername,txt_sub_district,txt_district,txt_province;

    public static ClProfile newInstance() {
        ClProfile fragment = new ClProfile();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_profile, container, false);
        get_item(getContext());

        txt_name = (TextView)view.findViewById(R.id.txt_name);
        txt_titlename = (TextView)view.findViewById(R.id.txt_titlename);
        txt_company = (TextView)view.findViewById(R.id.txt_company);
        txt_address = (TextView)view.findViewById(R.id.txt_address);
        txt_sername = (TextView)view.findViewById(R.id.txt_sername);
        txt_sub_district = (TextView)view.findViewById(R.id.txt_sub_district);
        txt_district = (TextView)view.findViewById(R.id.txt_district);
        txt_province = (TextView)view.findViewById(R.id.txt_province);

        return view;
    }
    @SuppressLint("StaticFieldLeak")
    public void get_item(final Context context) {
        new AsyncTask<Void, Void, String>() {

            ProgressDialog progressDialog = getHttpLoading(context);
            AlertDialog.Builder errorDialog = gerErrorDialog(context);

            @Override

            protected String doInBackground(Void... voids) {
                return getDataPosPdt("apimember?para=1;MjAxOC0wNi0yNyAxNzoyODowNC4zODg4OTk=", fnPreparingSynDataPdt("4445555555"));
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s.equals("error")){
                    errorDialog.show();
                }
                else {
                    progressDialog.dismiss();
                    System.out.println("result :" + s);

                    ObjectMapper rootMapper = new ObjectMapper();
                    JsonNode obj = null;
                    try {
                        obj = rootMapper.readTree(s);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("ROOT : " + obj.get("MCMC_NO").asText());
                    System.out.println("Phone : " + obj.get("MCMA_CELL_PHONE1").asText());

                    JsonNode objmember = obj.path("member");
                    for (JsonNode nodeMember : objmember) {
                        name = nodeMember.path("MKTC_NAME").asText();
                        sername = nodeMember.path("MKTC_SURNAME").asText();
                        company = nodeMember.path("MKTC_COMPANY").asText();
                        titlename = nodeMember.path("MCMI_CODE").asText();

                        System.out.println("Member : " + titlename + name + sername + company);
                    }
                    JsonNode objaddress = obj.path("address");
                    for (JsonNode nodeAddress : objaddress) {
                        address = nodeAddress.path("MCMA_ADDR_ADDR").asText();
                        sub_district = nodeAddress.path("MCMA_ADDR_DISTICT").asText();
                        district = nodeAddress.path("MCMA_ADDR_PREFECTURE").asText();
                        province = nodeAddress.path("MCMA_ADDR_PROVINCE").asText();


                    }
                    System.out.println("Address : " + address + sub_district + district + province);

                    txt_titlename.setText(titlename);
                    txt_name.setText(name);
                    txt_company.setText(company);
                    txt_address.setText(address);
                    txt_sername.setText(sername);
                    txt_sub_district.setText(sub_district);
                    txt_district.setText(district);
                    txt_province.setText(province);

                }
            }

        }.execute();
    }
}
