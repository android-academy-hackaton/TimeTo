package hackaton.academy.timeto.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hackaton.academy.timeto.R;

public class BarFragment extends Fragment {

    private RecyclerView.Adapter mAdapter;

    public BarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rest, container, false);
        initRecyclerView();


    }

    private void initRecyclerView() {
        List<PlacData> dataSource = LoadPlaces();

        mAdapter = new PlaceViewAdapter(this,dataSource);

    }

    private List<PlacData> LoadPlaces() {
        List<PlacData> places = new ArrayList<>();
        return places;
    }
}
