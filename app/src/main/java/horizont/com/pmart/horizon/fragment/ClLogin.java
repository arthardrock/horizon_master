package horizont.com.pmart.horizon.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import horizont.com.pmart.horizon.R;

public class ClLogin extends Fragment {
    private Button buttonConfirm;
    private EditText editTextPhone,editTextPassword;

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
        checkLogin();
        return view;
    }
    public void checkLogin(){
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextPhone.getText().toString().trim().length()!=0) {
                    if(isValidPhone(editTextPhone.getText().toString())){
//                        Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        editTextPhone.setError("รูปแบบไม่ถูกต้อง");
                    }
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
    private boolean isValidPhone(String phone)
    {
     //   phone = "0"+phone;
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
