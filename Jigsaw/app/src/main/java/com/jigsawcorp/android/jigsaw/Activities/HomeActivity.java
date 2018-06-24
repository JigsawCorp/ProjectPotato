package com.jigsawcorp.android.jigsaw.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.jigsawcorp.android.jigsaw.Fragments.CurrentWorkoutFragment;
import com.jigsawcorp.android.jigsaw.Model.User;
import com.jigsawcorp.android.jigsaw.Fragments.WorkoutLogFragment;
import com.jigsawcorp.android.jigsaw.Fragments.ProgressFragment;
import com.jigsawcorp.android.jigsaw.R;
import com.jigsawcorp.android.jigsaw.Fragments.RoutinesFragment;

public class HomeActivity extends AppCompatActivity {

    // Util Attributes
    private static final String TAG = "HomeActivity";

    // Model Attributes
    private User mUser;

    // View Attributes
    private BottomNavigationView mBottomNavigationView;
    private ViewPager mViewPager;
    private int mAdapterPosition;
    private static final int NUMBER_OF_MENUS = 4;
    private MenuItem mPrevMenuItem;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mUser = new User(getApplicationContext());

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bottom_navigation_current_workout:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.bottom_navigation_workout_log:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.bottom_navigation_routines:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.bottom_navigation_progress:
                        mViewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });

        mViewPager = (ViewPager) findViewById(R.id.main_view_pager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Potato
            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG, "onPageSelected(" + position + ");");
                mAdapterPosition = position;
                if (mPrevMenuItem != null) {
                    mPrevMenuItem.setChecked(false);
                } else {
                    mBottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + position);
                mBottomNavigationView.getMenu().getItem(position).setChecked(true);
                mPrevMenuItem = mBottomNavigationView.getMenu().getItem(position);
                setToolbarTitle(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Blyat
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Log.i(TAG, "getItem: ");
                switch (position) {
                    case 0:
                        return new CurrentWorkoutFragment();
                    case 1:
                        return new WorkoutLogFragment();
                    case 2:
                        return new RoutinesFragment();
                    case 3:
                        return new ProgressFragment();
                        default:
                            return new CurrentWorkoutFragment();
                }
            }

            @Override
            public int getCount() {
                return NUMBER_OF_MENUS;
            }

        });
        setToolbarTitle(0);
        mViewPager.setCurrentItem(0);
    }


    private void setToolbarTitle(int fragmentPos) {
        switch (fragmentPos) {
            case 0:
                setTitle("Current Workout");
                break;
            case 1:
                setTitle("Workout Log");
                break;
            case 2:
                setTitle("Routines");
                break;
            case 3:
                setTitle("Progress");
                break;
            default:
        }
    }



}
