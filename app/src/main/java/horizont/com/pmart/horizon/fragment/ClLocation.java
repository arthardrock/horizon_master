package horizont.com.pmart.horizon.fragment;

import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import horizont.com.pmart.horizon.R;

public class ClLocation extends Fragment {

    private LocationManager locationManager;
    private LocationListener locationListener;

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
       /*locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
       locationListener = new LocationListener() {
          /@Override
           public void onLocationChanged(Location location) {

           }

           @Override
           public void onStatusChanged(String s, int i, Bundle bundle) {

           }

           @Override
           public void onProviderEnabled(String s) {

           }

           @Override
           public void onProviderDisabled(String s) {

           }
       };*/

       return view;

    }

}
