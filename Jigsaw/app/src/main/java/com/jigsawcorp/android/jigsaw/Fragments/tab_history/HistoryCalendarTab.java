package com.jigsawcorp.android.jigsaw.Fragments.tab_history;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jigsawcorp.android.jigsaw.Database.Workout.WorkoutLab;
import com.jigsawcorp.android.jigsaw.Fragments.Dialogs.HistoryCalendarCaseDialog;
import com.jigsawcorp.android.jigsaw.Model.Workout;
import com.jigsawcorp.android.jigsaw.R;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

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
        args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, false);
        args.putInt(CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.MONDAY);
        caldroidFragment.setArguments(args);

        caldroidFragment.setCaldroidListener(new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                List<UUID> workouts = new ArrayList<>();
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.setTime(date);
                workouts = WorkoutLab.get(getContext()).getWorkoutIds(calendar);
                FragmentManager manager = getFragmentManager();
                HistoryCalendarCaseDialog dialog = HistoryCalendarCaseDialog.newInstance(DateFormat.getDateInstance(DateFormat.LONG).format(date), workouts);
                dialog.setHistoryCalendarCaseDialogListener(new HistoryCalendarCaseDialog.HistoryCalendarCaseDialogListener() {
                    @Override
                    public void onElementSelected(int pos) {

                    }
                });
                dialog.show(manager, "HistoryCalendarCaseDiag");
            }
        });

        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.replace(R.id.fragment_history_calebdar_tab_calendar_container, caldroidFragment);
        t.commit();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
