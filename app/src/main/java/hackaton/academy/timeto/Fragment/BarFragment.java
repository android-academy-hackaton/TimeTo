package hackaton.academy.timeto.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hackaton.academy.timeto.Constants;
import hackaton.academy.timeto.PlaceData;
import hackaton.academy.timeto.PlaceViewAdapter;
import hackaton.academy.timeto.R;
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
    private List<PlaceData> places = new ArrayList<>();

    public BarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_bar, container, false);
        // Inflate the layout for this fragment

        PlacesService moviesService = RestManager.getPlaceServiceInstance();
        moviesService.getPlaces("-33.8670522,151.1957362", 1500, "restaurant", Constants.API_KEY).enqueue(new Callback<Place>() {
            @Override
            public void onResponse(Call<Place> call, Response<Place> response) {

                Place body = response.body();
                List<Result> results = null;
                if (body != null) {
                    results = body.getResults();
                }
                if(results != null) {
                    for (int i = 0; i < results.size(); i++) {
                        places.add(new PlaceData(R.drawable.ic_launcher_background, results.get(i).getName(), "Awsome place"));
                    }
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<Place> call, Throwable t) {

            }
        });

        initRecyclerView();

        return mRootView;


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
    }

}
