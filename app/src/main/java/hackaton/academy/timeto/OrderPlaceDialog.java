package hackaton.academy.timeto;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class OrderPlaceDialog  extends Fragment  {

    private Button viewContactsButton;


    private View mRootView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.order_place_dialogue, container, false);
        // Inflate the layout for this fragment

        Toast.makeText(getActivity(), "onCreateView", Toast.LENGTH_SHORT).show();
        viewContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog fbDialogue = new Dialog(mRootView.getContext(), android.R.style.Theme_Black_NoTitleBar);
                fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                fbDialogue.setContentView( R.layout.view_contacts_dialogue);
                fbDialogue.setCancelable(true);
                fbDialogue.show();
            }
        });
        return mRootView;


    }

}
