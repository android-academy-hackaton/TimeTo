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
import android.widget.SeekBar;
import android.widget.TextView;

import hackaton.academy.timeto.Fragment.BarFragment;
import hackaton.academy.timeto.Fragment.ClubFragment;
import hackaton.academy.timeto.Fragment.RestFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;


    //This is our viewPager
    private ViewPager viewPager;

    //Fragments
    RestFragment chatFragment;
    BarFragment callsFragment;
    ClubFragment contactsFragment;
    MenuItem prevMenuItem;
    Button orderPlaceButton;
    Button viewContactsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        //Initializing the bottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        orderPlaceButton = findViewById(R.id.orderPlaceButton);

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






        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_pabs:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.menu_restaurants:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.menu_club:
                                viewPager.setCurrentItem(2);
                                break;
                        }
                        return false;
                    }
                });

        SeekBar seekBar = findViewById(R.id.simpleSeekBar);
        final TextView seekBarValue = findViewById(R.id.milles);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                seekBarValue.setText(String.valueOf(progress*10) + "Meters");
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


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
