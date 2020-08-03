package com.example.foodpanda.fragment.about;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.foodpanda.AboutActivity;
import com.example.foodpanda.R;
import com.example.foodpanda.util.MyApplication;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MapFragment extends Fragment implements OnMapReadyCallback {
    private Context mContext;
    private View view;
    private TextView tvAddress, tvTime;
    private GoogleMap mGoogleMap;
    private String address, date, shopName;

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_map, container, false);
            init(view);
            initData();
            initListener();
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        }
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 保证容器Activity实现了回调接口 否则抛出异常警告
        try {
            MyApplication.fragment = this;
            String[] info = ((AboutActivity) context).getIntentParams();
            address = info[0];
            date = info[1];
            shopName = info[2];
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement Fragment1CallBack");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        MyApplication.fragment = this;

    }

    void init(View view) {
        tvAddress = (TextView) view.findViewById(R.id.tv_address);
        tvTime = (TextView) view.findViewById(R.id.tv_time);
    }

    void initData() {
        tvAddress.setText(address.trim());
        tvTime.setText(date.trim());
    }

    void initListener() {

    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Geocoder geoCoder = new Geocoder(mContext, Locale.getDefault());
        List<Address> addressLocation = null;
        try {
            addressLocation = geoCoder.getFromLocationName(address.trim(), 1);
            double latitude = addressLocation.get(0).getLatitude();
            double longitude = addressLocation.get(0).getLongitude();
            Log.e("latitude",latitude+"");
            Log.e("longitude",longitude+"");
            mGoogleMap = googleMap;
            // Add a marker in Sydney and move the camera
            LatLng Tapei = new LatLng(latitude, longitude);
            CameraPosition cameraPosition = new CameraPosition.Builder().target(Tapei).zoom(17).build();
            mGoogleMap.addMarker(new MarkerOptions().position(Tapei).title(shopName));
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
