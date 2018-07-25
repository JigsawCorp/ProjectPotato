package com.jigsawcorp.android.jigsaw.Fragments.tab_history;

import android.util.Log;

import com.jigsawcorp.android.jigsaw.View.Calendar.CaldroidCustomAdapter;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;
import com.roomorama.caldroid.DateGridFragment;

import java.util.ArrayList;

public class CaldroidCustomFragment extends CaldroidFragment {

    @Override
    public ArrayList<DateGridFragment> getFragments() {
        return super.getFragments();
    }

    @Override
    public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
        // TODO Auto-generated method stub
        return new CaldroidCustomAdapter(getActivity(), month, year,
                getCaldroidData(), extraData);
    }
}
