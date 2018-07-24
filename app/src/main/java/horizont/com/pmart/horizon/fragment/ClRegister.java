package horizont.com.pmart.horizon.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import horizont.com.pmart.horizon.R;

public class ClRegister extends Fragment {
    public static ClRegister newInstance() {
        ClRegister fragment = new ClRegister();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_register, container, false);
        return view;
    }
}
