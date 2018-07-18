package horizont.com.pmart.horizon;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import static android.content.Context.LOCATION_SERVICE;
import static android.support.v4.content.ContextCompat.getSystemService;

public class ClLocationFragment extends Fragment {

    private LocationManager locationManager;
    private LocationListener locationListener;

    public static ClLocationFragment newInstance() {
        ClLocationFragment fragment = new ClLocationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_location, container, false);
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
