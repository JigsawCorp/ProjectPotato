package com.jigsawcorp.android.jigsaw.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.jigsawcorp.android.jigsaw.Database.Program.ProgramLab;
import com.jigsawcorp.android.jigsaw.Fragments.tab_programs.EditProgramFragment;
import com.jigsawcorp.android.jigsaw.Fragments.tab_programs.ProgramHistoryTabFragment;
import com.jigsawcorp.android.jigsaw.Fragments.tab_programs.ProgramScheduleTabFragment;
import com.jigsawcorp.android.jigsaw.Fragments.tab_programs.ProgramWorkoutsTabFragment;
import com.jigsawcorp.android.jigsaw.Model.Program;
import com.jigsawcorp.android.jigsaw.R;
import com.jigsawcorp.android.jigsaw.Util.RequestCodes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DetailedProgramActivity extends AppCompatActivity {
    private static final String EXTRA_PROGRAM_MODIFIED = "program_modified";
    private static final String EXTRA_PROGRAM_ID = "program";
    private static final String TAB_HISTORY_TITLE = "ProgramHistoryFragment";
    private static final String TAB_SCHEDULE_TITLE = "ProgramScheduleFragment";
    private static final String TAB_WORKOUTS_TITLE = "ProgramWorkoutsFragment";


    private FloatingActionMenu mMenuCreate;
    private FloatingActionButton mCreateWorkoutFab;
    private FloatingActionButton mAddWorkoutFab;

    private BottomNavigationView mBottomNavigationView;
    private ViewPager mViewPager;
    private ViewPagerAdapter mPagerAdapter;
    private MenuItem mPrevMenuItem;

    public static Intent newIntent(Context packageContext, UUID uuid) {
        Intent intent = new Intent(packageContext, DetailedProgramActivity.class);
        intent.putExtra(EXTRA_PROGRAM_ID, uuid.toString());
        return intent;
    }


    private EditProgramFragment mEditProgramFragment;
    private Program mProgram;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_program);
        mProgram = ProgramLab.get(this).getProgram(UUID.fromString(getIntent().getStringExtra(EXTRA_PROGRAM_ID)));

        mBottomNavigationView = findViewById(R.id.activity_detailed_program_bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bottom_navigation_menu_activity_detailed_program_history:
                        mPagerAdapter.changeTab(TAB_HISTORY_TITLE);
                        break;
                    case R.id.bottom_navigation_menu_activity_detailed_program_schedule:
                        mPagerAdapter.changeTab(TAB_SCHEDULE_TITLE);
                        break;
                    case R.id.bottom_navigation_menu_activity_detailed_program_workouts:
                        mPagerAdapter.changeTab(TAB_WORKOUTS_TITLE);
                        break;
                }
                return true;
            }
        });

        mViewPager = (ViewPager) findViewById(R.id.activity_detailed_program_view_pager);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (mPrevMenuItem != null) {
                    mPrevMenuItem.setChecked(false);
                }
                else
                {
                    mBottomNavigationView.getMenu().getItem(0).setChecked(false);
                }

                mBottomNavigationView.getMenu().getItem(position).setChecked(true);
                mPrevMenuItem = mBottomNavigationView.getMenu().getItem(position);

            }

        });
        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mPagerAdapter.addFragment(new ProgramScheduleTabFragment(), "ProgramScheduleFragment");
        mPagerAdapter.addFragment(new ProgramWorkoutsTabFragment(), "ProgramWorkoutsFragment");
        mPagerAdapter.addFragment(new ProgramHistoryTabFragment(), "ProgramHistoryFragment");
        mViewPager.setAdapter(mPagerAdapter);

        mMenuCreate = (FloatingActionMenu) findViewById(R.id.activity_detailed_program_fam);

        mCreateWorkoutFab = (FloatingActionButton) findViewById(R.id.activity_detailed_program_fab_create_workout);
        mCreateWorkoutFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(CreateProgramWorkoutActivity.newIntent(DetailedProgramActivity.this, mProgram.getId()), RequestCodes.REQUEST_CODE_CREATE_PROGRAM_WORKOUT);
            }
        });

        mAddWorkoutFab = (FloatingActionButton) findViewById(R.id.activity_detailed_program_fab_add_to_schedule);
        mAddWorkoutFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivityForResult(ExerciseListActivity.newIntent(getContext(), true), RequestCodes.REQUEST_CODE_ADD_EXERCISE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(mProgram.getName());
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.activity_detailed_program_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.i("DetailedProgram", "onHomeClick()");
                setProgramModifiedResult(false);
                finish();
                return true;
            default:
                Log.i("DetailedProgram", "onDefaultClick()");
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if(requestCode == RequestCodes.REQUEST_CODE_CREATE_PROGRAM_WORKOUT) {
            if (data == null) {
                return;
            }
            else {
                if (CreateProgramActivity.wasProgramCreated(data)) {
                    //((ProgramsAdapter) mProgramsRecyclerView.getAdapter()).setPrograms(ProgramLab.get(getContext()).getPrograms());
                }
            }
            mPagerAdapter.changeTab(TAB_WORKOUTS_TITLE);
        }
    }


    private void setProgramModifiedResult(boolean isModified) {
        Intent data = new Intent();
        data.putExtra(EXTRA_PROGRAM_MODIFIED, isModified);
        setResult(RESULT_OK, data);
    }

    public static boolean wasProgramModified(Intent result) {
        return result.getBooleanExtra(EXTRA_PROGRAM_MODIFIED, false);
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }


        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        public void changeTab(String tabTitle) {
            mViewPager.setCurrentItem(mFragmentTitleList.indexOf(tabTitle));
        }
    }
}
