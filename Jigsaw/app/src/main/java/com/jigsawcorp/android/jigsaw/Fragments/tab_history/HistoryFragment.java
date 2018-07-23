package com.jigsawcorp.android.jigsaw.Fragments.tab_history;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jigsawcorp.android.jigsaw.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {;
    private static final String TAG = "HistoryFragment";
    private ViewPager mViewPager;
    @Override
    public void onCreate(Bundle savecInstanceState) {
        super.onCreate(savecInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onSavedInstanceState) {
        Log.i(TAG,"onCreateView()");
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        // Setup view Pager
        mViewPager = (ViewPager) v.findViewById(R.id.fragment_history_view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new HistoryCalendarTab(), "Calendar");
        adapter.addFragment(new HistoryRecentTab(), "Recent");
        mViewPager.setAdapter(adapter);

        TabLayout tabLayout = v.findViewById(R.id.fragment_history_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.setupWithViewPager(mViewPager);


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");
        getActivity().setTitle("History");
    }

    // Adapter for the viewpager using FragmentPagerAdapter
    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Log.i(TAG,"getItem" + mFragmentList.get(position).toString());
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
    }
}
