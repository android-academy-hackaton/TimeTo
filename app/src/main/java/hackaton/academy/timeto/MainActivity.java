package hackaton.academy.timeto;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import hackaton.academy.timeto.Fragment.BarFragment;
import hackaton.academy.timeto.Fragment.ClubFragment;
import hackaton.academy.timeto.Fragment.RestFragment;

public class MainActivity extends AppCompatActivity {



    //This is our viewPager
    private ViewPager viewPager;

    //Fragments
    RestFragment chatFragment;
    BarFragment callsFragment;
    ClubFragment contactsFragment;
    MenuItem prevMenuItem;
    Button orderPlaceButton;
    Button viewContactsButton;

    ImageButton restaurantImageButton;
    ImageButton barImageButton;
    ImageButton clubImageButton;
    SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing viewPager


        orderPlaceButton = findViewById(R.id.orderPlaceButton);
        viewPager = findViewById(R.id.viewpager);
        restaurantImageButton = findViewById(R.id.restrant_btn);
        barImageButton = findViewById(R.id.bar_btn);
        clubImageButton = findViewById(R.id.club_btn);
        seekBar = findViewById(R.id.simpleSeekBar);



        orderPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        final Dialog fbDialogue = new Dialog(MainActivity.this, android.R.style.Theme_Black_NoTitleBar);
                        fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                        fbDialogue.setContentView( R.layout.order_place_dialogue);

                        viewContactsButton =  fbDialogue.findViewById(R.id.viewContactsButton);
                        viewContactsButton.setOnClickListener(new View.OnClickListener() {
                         @Override
                        public void onClick(View view) {
                             Intent myIntent = new Intent(MainActivity.this,FindContactsActivity.class);
//                          myIntent.putExtra("key", value); //Optional parameters
                             startActivity(myIntent);
                        }
                });
                        fbDialogue.setCancelable(true);
                        fbDialogue.show();
                    }
                });

        SeekBar seekBar = findViewById(R.id.simpleSeekBar);

        final TextView seekBarValue = findViewById(R.id.milles);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                seekBarValue.setText(String.valueOf(progress*50) + " Meters");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        restaurantImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
                restaurantImageButton.setImageResource(R.drawable.rest);
                barImageButton.setImageResource(R.drawable.bar_non_selected);
                clubImageButton.setImageResource(R.drawable.club_non_selected);
            }
        });

        barImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
                restaurantImageButton.setImageResource(R.drawable.rest_non_selected);
                barImageButton.setImageResource(R.drawable.bar);
                clubImageButton.setImageResource(R.drawable.club_non_selected);
            }
        });

        clubImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
                restaurantImageButton.setImageResource(R.drawable.rest_non_selected);
                barImageButton.setImageResource(R.drawable.bar_non_selected);
                clubImageButton.setImageResource(R.drawable.club);
            }
        });

       /*  //Disable ViewPager Swipe

       viewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });

        */

        setupViewPager(viewPager);

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        callsFragment = new BarFragment();
        chatFragment = new RestFragment();
        contactsFragment = new ClubFragment();
        adapter.addFragment(callsFragment);
        adapter.addFragment(chatFragment);
        adapter.addFragment(contactsFragment);
        viewPager.setAdapter(adapter);
    }
}
