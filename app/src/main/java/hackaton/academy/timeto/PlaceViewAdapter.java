package hackaton.academy.timeto;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class PlaceViewAdapter extends RecyclerView.Adapter<PlaceViewAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<PlaceData> mDataSource;
    private Context mContext;
    String imageUrl = "https://maps.googleapis.com/maps/api/place/photo?key=AIzaSyAYQCLhhFzj-FWnHnKvvtQ-YJh0QRBolV4&maxwidth=400&&photoreference=";
    public PlaceViewAdapter(List<PlaceData> places) {
        mDataSource = places;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.place_row_screen, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.onBindViewHolder(mDataSource.get(i % mDataSource.size()));
        PageOnClickListener on_click = new PageOnClickListener(mContext,i % mDataSource.size());
        viewHolder.clLayout.setOnClickListener(on_click);
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
        //return Integer.MAX_VALUE;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivImage;
        public TextView tvTitle;
        public TextView tvOverview;
        public ConstraintLayout clLayout;
        public TextView distanceTv;

        public ViewHolder(View view) {
            super(view);
            ivImage = view.findViewById(R.id.singlePlacePic);
            tvTitle = view.findViewById(R.id.singlePlaceTitle);
            tvOverview = view.findViewById(R.id.singlePlaceContext);
            clLayout = view.findViewById(R.id.singlePlaceView);
            distanceTv = view.findViewById(R.id.singlePlaceDist);


        }

        public void onBindViewHolder(PlaceData placeData) {
            String temp = imageUrl+placeData.getImageURL();
            Picasso.get().load(imageUrl+placeData.getImageURL()).into(ivImage);
//            ivImage.setImageResource(placeData.getImageResourceId());
            tvTitle.setText(placeData.getName());
            tvOverview.setText(placeData.getOverview());
            distanceTv.setText((int)placeData.getDistance() + " Meters");
        }
    }

    public static class PageOnClickListener implements View.OnClickListener
    {
        private Context mContext;
        int pos;

        public PageOnClickListener(Context Context, int pos) {
            this.mContext = Context;
            this.pos = pos;
        }

        @Override
        public void onClick(View v)
        {
            //Intent openSecondActivity  = new Intent(mContext, MovieDetailActivity.class);
            //openSecondActivity.putExtra("id", pos);
            //mContext.startActivity(openSecondActivity);
        }
    }
}