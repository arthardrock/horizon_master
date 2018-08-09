package horizont.com.pmart.horizon;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class PushActivity extends AppCompatActivity{
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

        String message = bundle.getString("message");

        TextView textView = (TextView)findViewById(R.id.txt_message);
        textView.setText(message);
    }
}
