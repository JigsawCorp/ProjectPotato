package com.jigsawcorp.android.jigsaw.Fragments.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.jigsawcorp.android.jigsaw.Activities.EditWorkoutActivity;
import com.jigsawcorp.android.jigsaw.Database.Workout.WorkoutLab;
import com.jigsawcorp.android.jigsaw.Model.Workout;
import com.jigsawcorp.android.jigsaw.R;
import com.jigsawcorp.android.jigsaw.Util.ConfigurationHelper;
import com.jigsawcorp.android.jigsaw.Util.RequestCodes;
import com.jigsawcorp.android.jigsaw.View.CustomHolders.HistoryCalendarCaseWorkoutHolder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class NewWorkoutHistoryDialog extends DialogFragment {
    private static final String ARGUMENT_DIALOG_TITLE = "dialog_title";
    private static final String ARGUMENT_START_DATE = "start_date";

    private TimePicker mStartTimePicker, mEndTimePicker;

    private HistoryCalendarCaseDialog.HistoryCalendarCaseDialogListener mListener;


    public static NewWorkoutHistoryDialog newInstance(String dialogTitle, String startDay) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_DIALOG_TITLE, dialogTitle);
        bundle.putString(ARGUMENT_START_DATE, startDay);
        NewWorkoutHistoryDialog fragment = new NewWorkoutHistoryDialog();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Bundle bundle = getArguments();

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dialog_new_workout_history, null);
        mStartTimePicker = v.findViewById(R.id.dialog_new_workout_history_timePicker_start);
        mEndTimePicker = v.findViewById(R.id.dialog_new_workout_history_timePicker_end);

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(bundle.getString(ARGUMENT_DIALOG_TITLE))
                .setView(v)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Workout workout = new Workout();
                        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", ConfigurationHelper.getCurrentLocale(getContext()));
                        try {
                            workout.setStartDate(format.parse(bundle.getString(ARGUMENT_START_DATE)));
                            Date startDate = format.parse(bundle.getString(ARGUMENT_START_DATE));
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(startDate);
                            cal.set(Calendar.HOUR_OF_DAY, mStartTimePicker.getCurrentHour());
                            cal.set(Calendar.MINUTE, mStartTimePicker.getCurrentMinute());
                            workout.setStartDate(cal.getTime());

                            cal.setTime(startDate);
                            cal.set(Calendar.HOUR_OF_DAY, mEndTimePicker.getCurrentHour());
                            cal.set(Calendar.MINUTE, mEndTimePicker.getCurrentMinute());
                            workout.setEndDate(cal.getTime());

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        WorkoutLab.get(getContext()).addWorkout(workout);
                        startActivity(EditWorkoutActivity.newIntent(getContext(), workout.getId()));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
}
