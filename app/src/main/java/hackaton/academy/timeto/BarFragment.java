package hackaton.academy.timeto;


import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class BarFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    SwipeController mSwipeController;
    private View mRootView;

    public BarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_bar, container, false);
        initRecyclerView();

        // Inflate the layout for this fragment
        return mRootView;
    }

    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(mRootView.getContext());
        List<PlaceData> dataSource = LoadPlaces();
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

    private List<PlaceData> LoadPlaces() {
        List<PlaceData> places = new ArrayList<>();
        PlaceData place1 = new PlaceData(R.drawable.ic_launcher_background,"first Pab","Nice place...");
        PlaceData place2 = new PlaceData(R.drawable.ic_launcher_background,"second Pab","very Nice place...");
        places.add(place1);
        places.add(place2);

        return places;
    }
}
