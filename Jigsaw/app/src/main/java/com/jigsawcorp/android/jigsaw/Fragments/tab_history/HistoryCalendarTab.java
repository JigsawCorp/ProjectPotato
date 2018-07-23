package com.jigsawcorp.android.jigsaw.Fragments.tab_history;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jigsawcorp.android.jigsaw.R;
import com.roomorama.caldroid.CaldroidFragment;

import java.util.Calendar;

public class HistoryCalendarTab extends Fragment {
    private static final String TAG = "HistoryCalendarTab";
    @Override
    public void onCreate(Bundle savecInstanceState) {
        super.onCreate(savecInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onSavedInstanceState) {
        Log.i(TAG,"onCreateView");
        View v = inflater.inflate(R.layout.fragment_history_calendar_tab, container, false);
        CaldroidFragment caldroidFragment = new CaldroidCustomFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);

        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.replace(R.id.fragment_history_calebdar_tab_calendar_container, caldroidFragment);
        t.addToBackStack(null);
        t.commit();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");
    }
}
