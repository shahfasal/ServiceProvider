package fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.HashMap;

import asyncTasks.ComplaintUploadAyncTask;
import quadvision.serviceprovider.GPSTracker;
import quadvision.serviceprovider.R;

public class ComplaintRegisterFragment extends Fragment implements
        AdapterView.OnItemSelectedListener {

    Activity activity;
    HashMap<String,String> userData=new HashMap<String,String>();
    private Bitmap bp;
    private GoogleMap mMap;
    private LinearLayout complaintImageContainer;

    public ComplaintRegisterFragment() {

    }

    private Spinner spinner1;
    Spinner spinnerOsversions;
    TextView selVersion;
    private ImageView complaintImage;
    private String[] state = {"Crime and safety",
            "Defacements of buildings and public properties",
            "Electricity and power supply", "Fire safety",
            "Garbage and unsanitary practice", "Lakes", "Pollution",
            "Land and property encroachment",
            "Maintenance of road and footpaths",
            "Sewage,Drainage and storm water drains",
            "Stray animals and insects", "Traffic and road safety",
            "Parks and playgrounds", "Water supply and services", "Tress",
            "Street lights", "Snake rescue", "Dead animals", "Law and order",
            "Silt remova", "Empty site cleaning", "Parking issues",
            "Accidents", "Illegal advertisements", "Theft and Robbery",
            "Trespassing", "Neighbour conflicts", "Eve teasing", "Pothole"};

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            // mMap=((SupportMapFragment)(CommunityActivity.fm.findFragmentById(R.id.map))).getMap();
            mMap = ((SupportMapFragment) (getChildFragmentManager().findFragmentById(R.id.map))).getMap();
            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {

                }
            });
            if (mMap != null) {
                //mMap
                //setUpMap();
            }
        }
    }


    private void setUpMap(double lat, double lng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("Marker"));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View v = inflater.inflate(R.layout.complaint_register_layout,
                container, false);
        // ListView profileItems=(ListView)

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        this.activity = activity;

    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);



        /*
        Toolbar :: start
         */
//        Toolbar toolbar
//                = (Toolbar) activity.findViewById(R.id.app_bar);
//
//        ((ActionBarActivity) activity).setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_action_back);
//        toolbar.setTitleTextColor(Color.WHITE);
//        toolbar.setTitle("Describe Complaint");
//
//
//        toolbar.setNavigationOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        /*
        Tooalbar end
         */


        setUpMapIfNeeded();

        spinnerOsversions = (Spinner) activity.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(
                activity, android.R.layout.simple_spinner_item, state);
        adapter_state
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOsversions.setAdapter(adapter_state);
        spinnerOsversions
                .setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        complaintImage = (ImageView) activity.findViewById(R.id.complaint_image);
        complaintImageContainer = (LinearLayout) activity.findViewById(R.id.complaint_image_container);


		/*
         * playing with te GPS
		 */
      String address = null;
        GPSTracker gps = new GPSTracker(
                activity);

        if (gps.canGetLocation()) {
            double lat = gps.getLatitude(); // returns latitude
            double lon = gps.getLongitude(); // returns longitude
            Location loc = gps.location;
            address = gps.getAddress(lat, lon);
            gps.stopUsingGPS();
            Log.d("google maps", "lat::" + lat + " lon::" + lon);

            setUpMap(lat, lon);


        } else {
            gps.showSettingsAlert();
            double lat = gps.getLatitude(); // returns latitude
            double lon = gps.getLongitude(); // returns longitude
            address = gps.getAddress(lat, lon);
            gps.stopUsingGPS();
            setUpMap(lat, lon);
        }

        SharedPreferences prefs=activity.getSharedPreferences("cache", Context.MODE_PRIVATE);
        String profileJson=prefs.getString("profile", null);
        try
        {
            JSONObject profile=new JSONObject(profileJson);
            userData.put("profile_user",profileJson.replace("\"", "\\\""));
            userData.put("profile_id",profile.getString("profile_id"));
            userData.put("zone",profile.getString("zone"));
            userData.put("locality",profile.getString("locality"));
            ((TextView) activity.findViewById(R.id.etComplaintAddress))
                    .setText(profile.getString("address"));
            userData.put("address", profile.getString("address"));
            ((TextView) activity.findViewById(R.id.etName)).setText(profile.getString("name"));
            userData.put("name",profile.getString("name"));
            ((TextView) activity.findViewById(R.id.etComplaintEmail))
                    .setText(profile.getString("email"));
            userData.put("email",profile.getString("email"));
            ((TextView) activity.findViewById(R.id.etComplaintMobile))
                    .setText(profile.getString("mobile"));
            userData.put("mobile",profile.getString("mobile"));
            Button b = ((Button) activity
                    .findViewById(R.id.reister_in_complaint_view_button));
            b.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    String title = ((EditText) activity
                            .findViewById(R.id.etComplaintTitle)).getText()
                            .toString();
                    userData.put("complaint_title", title);

                    String description = ((EditText) activity
                            .findViewById(R.id.editComplaintProblem)).getText()
                            .toString();
                    userData.put("complaint_decription", description);
                    String landmark = ((EditText) activity
                            .findViewById(R.id.etComplaintLandmark)).getText()
                            .toString();
                    userData.put("complaint_landmark", landmark);
                    String address = ((EditText) activity
                            .findViewById(R.id.etComplaintAddress)).getText()
                            .toString();
                    userData.put("complaint_address", address);
                    if (bp != null) {
                        new ComplaintUploadAyncTask(activity,bp).execute(userData);
                    } else {
                        new ComplaintUploadAyncTask(activity).execute(userData);
                    }
                }
            });
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }

    public void open() {
        Intent intent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        // Log.d("result",resultCode+"");
        if (resultCode != 0) {
            bp = (Bitmap) data.getExtras().get("data");
            Drawable drawable = new BitmapDrawable(getResources(), bp);
            complaintImageContainer.removeAllViews();
            complaintImageContainer.setBackground(drawable);
            //complaintImage.setImageBitmap(bp);
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        spinnerOsversions.setSelection(position);
        String selState = (String) spinnerOsversions.getSelectedItem();
        // Toast.makeText(MainActivity.applicationContext, "selected" +
        // selState,
        // Toast.LENGTH_SHORT).show();
        userData.put("complaint_type", selState);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
