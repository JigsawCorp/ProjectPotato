package com.jigsawcorp.android.jigsaw.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.jigsawcorp.android.jigsaw.Fragments.CurrentWorkoutFragment;
import com.jigsawcorp.android.jigsaw.Fragments.HistoryFragment;
import com.jigsawcorp.android.jigsaw.Fragments.HomeFragment;
import com.jigsawcorp.android.jigsaw.Fragments.PlanFragment;
import com.jigsawcorp.android.jigsaw.Model.Exercise;
import com.jigsawcorp.android.jigsaw.Model.User;
import com.jigsawcorp.android.jigsaw.Fragments.WorkoutLogFragment;
import com.jigsawcorp.android.jigsaw.Fragments.ProgressFragment;
import com.jigsawcorp.android.jigsaw.R;
import com.jigsawcorp.android.jigsaw.Fragments.RoutinesFragment;
import com.jigsawcorp.android.jigsaw.Util.SourceCodeHelp;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Util Attributes
    private static final String TAG = "MainActivity";

    // Model Attributes
    private User mUser;

    // View Attributes
    private DrawerLayout mDrawerLayout;
    private ViewPager mViewPager;
    private int mAdapterPosition;
    private static final int NUMBER_OF_MENUS = 6;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUser = new User(getApplicationContext());

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);


        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        switch (menuItem.getItemId()) {
                            case R.id.nav_home:
                                mViewPager.setCurrentItem(0);
                                break;
                            case R.id.nav_current_workout:
                                mViewPager.setCurrentItem(1);
                                break;
                            case R.id.nav_routines:
                                mViewPager.setCurrentItem(2);
                                break;
                            case R.id.nav_plan:
                                mViewPager.setCurrentItem(3);
                                break;
                            case R.id.nav_progress:
                                mViewPager.setCurrentItem(4);
                                break;
                            case R.id.nav_history:
                                mViewPager.setCurrentItem(5);
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
                //mAdapterPosition = position;
                //if (mPrevMenuItem != null) {
               //     mPrevMenuItem.setChecked(false);
               // } else {
               //     mBottomNavigationView.getMenu().getItem(0).setChecked(false);
               // }
                Log.d("page", "onPageSelected: " + position);
                //mBottomNavigationView.getMenu().getItem(position).setChecked(true);
                //mPrevMenuItem = mBottomNavigationView.getMenu().getItem(position);
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
                        return new HomeFragment();
                    case 1:
                        return new CurrentWorkoutFragment();
                    case 2:
                        return new RoutinesFragment();
                    case 3:
                        return new PlanFragment();
                    case 4:
                        return new ProgressFragment();
                    case 5:
                        return new HistoryFragment();
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setToolbarTitle(int fragmentPos) {
        switch (fragmentPos) {
            case 0:
                setTitle(R.string.drawer_home_title);
                break;
            case 1:
                setTitle(R.string.drawer_current_workout_title);
                break;
            case 2:
                setTitle(R.string.drawer_routines_title);
                break;
            case 3:
                setTitle(R.string.drawer_plan_title);
                break;
            case 4:
                setTitle(R.string.drawer_progress_title);
                break;
            case 5:
                setTitle(R.string.drawer_history_title);
                break;
            default:
        }
    }



}
