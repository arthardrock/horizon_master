package horizont.com.pmart.horizon.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import horizont.com.pmart.horizon.R;

public class ClLocation extends Fragment {


    public static ClLocation newInstance() {
        ClLocation fragment = new ClLocation();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.frag_location, container, false);

       return view;

    }
}
