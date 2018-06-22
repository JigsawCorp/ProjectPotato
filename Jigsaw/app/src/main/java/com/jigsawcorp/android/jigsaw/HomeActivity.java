package com.jigsawcorp.android.jigsaw;

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

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    private BottomNavigationView mBottomNavigationView;
    private ViewPager mViewPager;
    private int mAdapterPosition;
    private MenuItem mPrevMenuItem;
    private static final int NUMBER_OF_MENUS = 4;
    private FloatingActionMenu menuYellow;
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        menuYellow = (FloatingActionMenu) findViewById(R.id.menu_yellow);
        fab1 = (FloatingActionButton) findViewById(R.id.fab12);
        fab2 = (FloatingActionButton) findViewById(R.id.fab22);
        fab3 = (FloatingActionButton) findViewById(R.id.fab32);

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bottom_navigation_workout_log:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.bottom_navigation_profile:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.bottom_navigation_routine:
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
                mAdapterPosition = position;
                if (mPrevMenuItem != null) {
                    mPrevMenuItem.setChecked(false);
                } else {
                    mBottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + position);
                mBottomNavigationView.getMenu().getItem(position).setChecked(true);
                mPrevMenuItem = mBottomNavigationView.getMenu().getItem(position);

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
                        return new ProfileFragment();
                    case 2:
                        return new RoutinesFragment();
                    case 3:
                        return new ProgressFragment();
                        default:
                            return new HomeFragment();
                }
            }

            @Override
            public int getCount() {
                return NUMBER_OF_MENUS;
            }

        });

        mViewPager.setCurrentItem(0);
    }




}
