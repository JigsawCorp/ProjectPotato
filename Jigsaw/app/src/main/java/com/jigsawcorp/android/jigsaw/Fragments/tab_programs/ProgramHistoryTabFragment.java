package com.jigsawcorp.android.jigsaw.Fragments.tab_programs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jigsawcorp.android.jigsaw.R;

public class ProgramHistoryTabFragment extends Fragment {
    @Override
    public void onCreate(Bundle savecInstanceState) {
        super.onCreate(savecInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onSavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_program_history_tab, container, false);
        //mTextView = v.findViewById(R.id.fragment_history_recent_tab_text_view);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
