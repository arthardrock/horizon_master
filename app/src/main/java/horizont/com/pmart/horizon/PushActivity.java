package horizont.com.pmart.horizon;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import horizont.com.pmart.horizon.activity.ClDataNotifi;

import static horizont.com.pmart.horizon.ClHttpReq.fnPreparingNotification;
import static horizont.com.pmart.horizon.ClHttpReq.getDataPosPdtLocal;


public class PushActivity extends AppCompatActivity {

    public String title, message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);

        Bundle bundle = getIntent().getExtras();

        ImageView myButtonClose = (ImageView) findViewById(R.id.btn_close);
        myButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //String message = bundle.getString("message");
        get_notification();

        TextView textView = (TextView) findViewById(R.id.txt_message);
        textView.setText(message);
    }
    @SuppressLint("StaticFieldLeak")
    public void get_notification() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                return getDataPosPdtLocal("api/notification/1", fnPreparingNotification());
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                Log.d("sss",s);
                super.onPostExecute(s);

                ObjectMapper rootMapper = new ObjectMapper();
                JsonNode obj = null;
                try {
                    obj = rootMapper.readTree(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JsonNode objnotifi = obj.path("notification");
                for (JsonNode nodeNotifi : objnotifi) {

                    ClDataNotifi clDataNotifi = new ClDataNotifi(nodeNotifi.path("title_notifi").asText(), nodeNotifi.path("message_notifi").asText(),
                            nodeNotifi.path("img_noti").asText());
                    title = nodeNotifi.path("title_notifi").asText();
                    message = nodeNotifi.path("message_notifi").asText();
                    System.out.println("Title : " + clDataNotifi);

                    clDataNotifi.setTitle(title);
                    clDataNotifi.setMessage(message);
                }

                System.out.println("Title : " + title);
                System.out.println("Message : " + message);
                //Log.d("Test","notification : "+s);
            }
        }.execute();
    }
}

