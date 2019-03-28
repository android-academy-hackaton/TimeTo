package hackaton.academy.timeto.Fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
            Toast.makeText(getActivity(), "Requesting permission...", Toast.LENGTH_SHORT).show();
        }
        // MY_PERMISSION_REQUEST_READ_FINE_LOCATION is an
        // app-defined int constant. The callback method gets the
        // result of the request.
        else {
            Toast.makeText(getActivity(), "Calling get data!", Toast.LENGTH_SHORT).show();
            getData();
        }
        initRecyclerView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
//        List<PlaceData> dataSource = places;
        mAdapter = new PlaceViewAdapter(places);
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

    private void getData() {


        PlacesService placesService = RestManager.getPlaceServiceInstance();
        placesService.getPlaces("-33.8670522,151.1957362", 1500, "restaurant", Constants.API_KEY).enqueue(new Callback<Place>() {
            @Override
            public void onResponse(Call<Place> call, Response<Place> response) {

                Place body = response.body(); // TODO Create loading inside the fragment
                List<Result> results = null;
                if (body != null) {
                    results = body.getResults();
                }
                String photoRefrence = "";
                if (results != null) {
                    for (int i = 0; i < results.size(); i++) {
                        List<Photo> photoList = results.get(i).getPhotos();
                        if(photoList.size() > 0) {
                          photoRefrence = photoList.get(0).getPhotoReference();
                        }
                        places.add(new PlaceData(photoRefrence, results.get(i).getName(), "Awsome place"));
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
