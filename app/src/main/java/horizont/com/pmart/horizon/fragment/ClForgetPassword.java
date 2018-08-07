package horizont.com.pmart.horizon.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.time.Instant;

import horizont.com.pmart.horizon.R;

public class ClForgetPassword extends Fragment{

    public static ClForgetPassword newInstant(){
        ClForgetPassword fragment = new ClForgetPassword();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_forgetpassword, container, false);


        return view;
    }
}
