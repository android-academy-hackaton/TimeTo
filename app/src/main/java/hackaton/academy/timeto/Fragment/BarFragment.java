package hackaton.academy.timeto.Fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import hackaton.academy.timeto.Constants;
import hackaton.academy.timeto.PlaceData;
import hackaton.academy.timeto.PlaceViewAdapter;
import hackaton.academy.timeto.R;
import hackaton.academy.timeto.SwipeController;
import hackaton.academy.timeto.SwipeControllerActions;
import hackaton.academy.timeto.model.Photo;
import hackaton.academy.timeto.model.Place;
import hackaton.academy.timeto.model.Result;
import hackaton.academy.timeto.rest.PlacesService;
import hackaton.academy.timeto.rest.RestManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View mRootView;
    private FusedLocationProviderClient fusedLocationClient;

    SwipeController mSwipeController;

    private List<PlaceData> places = new ArrayList<>();


    public BarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_bar, container, false);
        // Inflate the layout for this fragment

        Toast.makeText(getActivity(), "onCreateView", Toast.LENGTH_SHORT).show();
        return mRootView;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?


            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    100);
        }
        // MY_PERMISSION_REQUEST_READ_FINE_LOCATION is an
        // app-defined int constant. The callback method gets the
        // result of the request.
        else {
            getData();
        }
        initRecyclerView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (getActivity() != null && ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                getData();
            }
        }
    }


    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(mRootView.getContext());
        List<PlaceData> dataSource = places;
        mAdapter = new PlaceViewAdapter(dataSource);
        mRecyclerView = mRootView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //Adding RecyclerView Divider / Separator
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRootView.getContext(), LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mSwipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                //mAdapter.players.remove(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
            }
        });        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(mSwipeController);
        itemTouchhelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration()
        {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                mSwipeController.onDraw(c);
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getData() {

        Toast.makeText(getActivity(), "getData", Toast.LENGTH_SHORT).show();

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(final Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            PlacesService placeService = RestManager.getPlaceServiceInstance();
                            placeService.getPlaces(location.getLatitude() + ","
                                    + location.getLongitude(),
                                    1500, "restaurant",
                                    Constants.API_KEY).enqueue(new Callback<Place>() {
                                @Override
                                public void onResponse(Call<Place> call, Response<Place> response) {

                                    Place body = response.body(); // TODO Create loading inside the fragment
                                    String photoRefernce = "";
                                    List<Result> results = null;
                                    if (body != null) {
                                        results = body.getResults();
                                    }
                                    if (results != null) {
                                        for (int i = 0; i < results.size(); i++) {
                                            double lat = results.get(i).getGeometry().getLocation().getLat();
                                            double lon = results.get(i).getGeometry().getLocation().getLng();
                                            Location placeLocation = new Location("placeLoc");
                                            placeLocation.setLatitude(lat);
                                            placeLocation.setLongitude(lon);
                                            float distance = placeLocation.distanceTo(location);
                                            List<Photo> photos = results.get(i).getPhotos();
                                            if (photos.size() > 0)
                                            {
                                                photoRefernce = photos.get(0).getPhotoReference();
                                            }
                                            places.add(new
                                                    PlaceData(photoRefernce,
                                                    results.get(i).getName(),
                                                    "" + results.get(i).getRating(), distance));
                                        }
                                        mAdapter.notifyDataSetChanged();
                                    }

                                }

                                @Override
                                public void onFailure(Call<Place> call, Throwable t) {

                                }
                            });
                        }
                    }
                });

    }
}
