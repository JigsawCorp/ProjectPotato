package com.jigsawcorp.android.jigsaw.Activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.jigsawcorp.android.jigsaw.Database.DataBaseHelper;
import com.jigsawcorp.android.jigsaw.Database.Exercise.ExerciseLab;
import com.jigsawcorp.android.jigsaw.Database.PerformedExercise.PerformedExerciseLab;
import com.jigsawcorp.android.jigsaw.Database.User.UserLab;
import com.jigsawcorp.android.jigsaw.Database.Workout.WorkoutLab;
import com.jigsawcorp.android.jigsaw.Fragments.CurrentWorkoutFragment;
import com.jigsawcorp.android.jigsaw.Fragments.tab_history.HistoryFragment;
import com.jigsawcorp.android.jigsaw.Fragments.HomeFragment;
import com.jigsawcorp.android.jigsaw.Fragments.PlanFragment;
import com.jigsawcorp.android.jigsaw.Model.PerformedExercise;
import com.jigsawcorp.android.jigsaw.Model.User;
import com.jigsawcorp.android.jigsaw.Fragments.ProgressFragment;
import com.jigsawcorp.android.jigsaw.R;
import com.jigsawcorp.android.jigsaw.Fragments.tab_programs.ProgramsFragment;

public class MainActivity extends AppCompatActivity implements DataBaseHelper.Callbacks, CurrentWorkoutFragment.Callbacks {

    // Util Attributes
    private static final String TAG = "MainActivity";


    // Model Attributes
    private User mUser;

    // Database labs
    private WorkoutLab mWorkoutLab;
    private PerformedExerciseLab mPerformedExerciseLab;
    private UserLab mUserLab;

    // View Attributes
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private int mCurrentMenuItem;
   // private ViewPager mViewPager;
    private int mAdapterPosition;
    private static final int NUMBER_OF_MENUS = 6;

    //First Run Attributes
    private boolean mDatabaseExists = true;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserLab = UserLab.get(this);
        mPerformedExerciseLab = PerformedExerciseLab.get(this);
        mWorkoutLab = WorkoutLab.get(this);

        mUser = mUserLab.getUser();
        if(!mDatabaseExists) {
            ExerciseLab.get(this).addDefaultExercises();
        }
        mUser = UserLab.get(this).getUser();

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        setTitle("Home");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);


        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int currentId = menuItem.getItemId();
                        if (mCurrentMenuItem == currentId) {
                            mDrawerLayout.closeDrawers();
                            return false;
                        }
                        mCurrentMenuItem = currentId;
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        switch (menuItem.getItemId()) {
                            case R.id.nav_home:
                                changeFragment(new HomeFragment(), "Home");
                                break;
                            case R.id.nav_current_workout:
                                changeFragment(new CurrentWorkoutFragment(), "Current Workout");
                                break;
                            case R.id.nav_programs:
                                changeFragment(new ProgramsFragment(), "Programs");
                                break;
                            case R.id.nav_plan:
                                changeFragment(new PlanFragment(), "Plan");
                                break;
                            case R.id.nav_progress:
                                changeFragment(new ProgressFragment(), "Progress");
                                break;
                            case R.id.nav_history:
                                changeFragment(new HistoryFragment(), "History");
                                break;
                        }
                        return true;
                    }
                });


       /* mViewPager = (ViewPager) findViewById(R.id.main_view_pager);
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
                        return new ProgramsFragment();
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

        });*/
        //setToolbarTitle(0);
//        mViewPager.setCurrentItem(0);
        changeFragment(new HomeFragment(), "Home");
        mNavigationView.setCheckedItem(R.id.nav_home);
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

    // Model Callbacks

    @Override
    public void onPerformedExerciseDeleted(PerformedExercise performedExercise) {
        mPerformedExerciseLab.removePerformedExercise(performedExercise);
        Fragment fragment = (Fragment) getSupportFragmentManager().findFragmentById(R.id.main_activity_fragment_container);
        if (fragment instanceof CurrentWorkoutFragment) {
            ((CurrentWorkoutFragment) fragment).updateUI();
        }
    }

    @Override
    public void onCreateDatabase() {
        mDatabaseExists = false;
    }


    private void changeFragment(Fragment fragment, String toolbarTitle) {
        Log.i(TAG, "changeFragment(" + fragment + ", " + toolbarTitle);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_fragment_container, fragment).addToBackStack(null).commit();
        //setTitle(toolbarTitle);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
           FragmentManager manager = getSupportFragmentManager();
           Fragment currentFragment = (Fragment) manager.findFragmentById(R.id.main_activity_fragment_container);
           if (currentFragment instanceof HomeFragment) {
               manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
               super.onBackPressed();
           }
           else {
               super.onBackPressed();
               currentFragment = (Fragment) manager.findFragmentById(R.id.main_activity_fragment_container);
               if (currentFragment instanceof HomeFragment) {
                   mNavigationView.getMenu().getItem(0).setChecked(true);
                   mCurrentMenuItem = mNavigationView.getMenu().getItem(0).getItemId();
               }
               else if (currentFragment instanceof CurrentWorkoutFragment) {
                   mNavigationView.getMenu().getItem(1).setChecked(true);
                   mCurrentMenuItem = mNavigationView.getMenu().getItem(1).getItemId();
               }
               else if (currentFragment instanceof ProgramsFragment) {
                   mNavigationView.getMenu().getItem(2).setChecked(true);
                   mCurrentMenuItem = mNavigationView.getMenu().getItem(2).getItemId();
               }
               else if (currentFragment instanceof PlanFragment) {
                   mNavigationView.getMenu().getItem(3).setChecked(true);
                   mCurrentMenuItem = mNavigationView.getMenu().getItem(3).getItemId();
               }
               else if (currentFragment instanceof ProgressFragment) {
                   mNavigationView.getMenu().getItem(4).setChecked(true);
                   mCurrentMenuItem = mNavigationView.getMenu().getItem(4).getItemId();
               }
               else if (currentFragment instanceof HistoryFragment) {
                   mNavigationView.getMenu().getItem(5).setChecked(true);
                   mCurrentMenuItem = mNavigationView.getMenu().getItem(5).getItemId();
               }
           }
        }
    }



}
