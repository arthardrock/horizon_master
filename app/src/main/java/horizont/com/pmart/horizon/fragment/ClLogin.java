package horizont.com.pmart.horizon.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import horizont.com.pmart.horizon.R;
import horizont.com.pmart.horizon.activity.ClForgetPassword;

public class ClLogin extends Fragment {
    private Button buttonConfirm;
    private EditText editTextPhone,editTextPassword;
    private AppCompatCheckBox checkbox;
    private TextView deviceID,forgetPass;

    private Context context;
    public static ClLogin newInstance() {
        ClLogin fragment = new ClLogin();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_login, container, false);
        buttonConfirm = (Button)view.findViewById(R.id.btn_confirm);
        editTextPhone = (EditText)view.findViewById(R.id.edt_phone);
        editTextPassword = (EditText)view.findViewById(R.id.edt_password);
        forgetPass = (TextView)view.findViewById(R.id.txt_forgotpass);
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgetPass();
            }
        });
        String id = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);

        checkbox = (AppCompatCheckBox) view.findViewById(R.id.checkbox);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    // show password
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        deviceID = (TextView)view.findViewById(R.id.txt_device);

        deviceID.setText("deviceID : "+id);
        checkLogin();
        return view;
    }
    public void checkLogin(){
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextPhone.getText().toString().trim().length()!=0) {
//                    if(isValidPhone(editTextPhone.getText().toString())){
//                        Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        editTextPhone.setError("รูปแบบไม่ถูกต้อง");
//                    }
                }
                else {
                    editTextPhone.setError("กรุณากรอกเบอร์โทร");
                }

                if (editTextPassword.getText().toString().trim().length()!=0) {
                }
                else{
                    editTextPassword.setError("กรุณากรอกรหัสผ่าน");
                    Toast.makeText(getActivity(), "กรอกข้อมูลของท่านให้ครบ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void forgetPass(){
        Intent intent = new Intent(context,ClForgetPassword.class);
        context.startActivity(intent);
    }
    private boolean isValidPhone(String phone)
    {
     // phone = "0"+phone;
        boolean check = false;
        if(!Pattern.matches("[0-9]", phone))
        {
            if(phone.length() < 10 || phone.length() > 10)
            {
                check = false;
            }
            else
            {
                check = true;
            }
        }
        else
        {
            check = false;
        }
        return check;
    }
}
