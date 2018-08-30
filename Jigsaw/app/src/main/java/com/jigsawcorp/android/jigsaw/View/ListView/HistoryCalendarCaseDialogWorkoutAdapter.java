package com.jigsawcorp.android.jigsaw.View.ListView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jigsawcorp.android.jigsaw.Model.Workout;
import com.jigsawcorp.android.jigsaw.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class HistoryCalendarCaseDialogWorkoutAdapter extends ArrayAdapter<Workout> {
    private Context mContext;
    private List<Workout> workoutList = new ArrayList<>();

    public HistoryCalendarCaseDialogWorkoutAdapter(Context context,ArrayList<Workout> workouts) {
        super(context, 0 , workouts);
        mContext = context;
        workoutList = workouts;
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item_dialog_history_calendar_case_workout,parent,false);

        Workout currentWorkout = workoutList.get(position);

        ImageView viewWorkoutImageView = (ImageView)listItem.findViewById(R.id.list_item_dialog_history_calendar_case_workout_imageView_view_workout);
        viewWorkoutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        TextView workoutTypeTextView = (TextView) listItem.findViewById(R.id.list_item_dialog_history_calendar_case_workout_textView_type);
        workoutTypeTextView.setText("Type PlaceHolder");

        TextView workoutStartTimeTextView = (TextView) listItem.findViewById(R.id.list_item_dialog_history_calendar_case_workout_textView_date);
        workoutStartTimeTextView.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(currentWorkout.getStartDate()));

        return listItem;
    }
}
