package com.jigsawcorp.android.jigsaw.Fragments.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.jigsawcorp.android.jigsaw.Database.Workout.WorkoutLab;
import com.jigsawcorp.android.jigsaw.Model.Workout;
import com.jigsawcorp.android.jigsaw.R;
import com.jigsawcorp.android.jigsaw.View.ListView.HistoryCalendarCaseDialogWorkoutAdapter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class HistoryCalendarCaseDialog extends DialogFragment {
    private static final String ARGUMENT_DIALOG_TITLE = "dialog_title";
    private static final String ARGUMENT_DIALOG_WORKOUTS = "dialog_workouts";

    private HistoryCalendarCaseDialogListener mListener;

    public interface HistoryCalendarCaseDialogListener {
        void onElementSelected(int pos);
    }

    public static HistoryCalendarCaseDialog newInstance(String dialogTitle, List<UUID> workouts) {
        ArrayList<String>  workoutsStringArray = new ArrayList<>();
        for (int i = 0; i < workouts.size(); ++i) {
            workoutsStringArray.add(workouts.get(i).toString());
        }
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_DIALOG_TITLE, dialogTitle);
        bundle.putStringArrayList(ARGUMENT_DIALOG_WORKOUTS, workoutsStringArray);
        HistoryCalendarCaseDialog fragment = new HistoryCalendarCaseDialog();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        ArrayList<String> workoutsString = bundle.getStringArrayList(ARGUMENT_DIALOG_WORKOUTS);
        ArrayList<Workout> workouts = new ArrayList<>();
        for (int i = 0; i < workoutsString.size(); ++i) {
            workouts.add(WorkoutLab.get(getContext()).getWorkouts(UUID.fromString(workoutsString.get(i))));
        }

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dialog_history_calendar_case, null);
        TextView workoutsAmount = v.findViewById(R.id.dialog_history_calendar_case_textView_workouts_amount);
        workoutsAmount.setText(workouts.size() + " workout found");

        ListView workoutsListView = v.findViewById(R.id.dialog_history_calendar_case_listView_workouts);
        workoutsListView.setAdapter(new HistoryCalendarCaseDialogWorkoutAdapter(getContext(), workouts));
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(bundle.getString(ARGUMENT_DIALOG_TITLE))
                .setView(v)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        Window window = dialog.getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);




        return dialog;
    }

    public void setHistoryCalendarCaseDialogListener(HistoryCalendarCaseDialogListener listener) {
        mListener = listener;
    }
}
