package com.jigsawcorp.android.jigsaw.View.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.jigsawcorp.android.jigsaw.Model.Program;
import com.jigsawcorp.android.jigsaw.Model.ProgramWorkout;
import com.jigsawcorp.android.jigsaw.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProgramWorkoutAdapter extends RecyclerView.Adapter<ProgramWorkoutAdapter.ProgramWorkoutHolder> {
    private List<ProgramWorkout> mProgramWorkouts = new ArrayList<>();
    private Context mContext;
    private ProgramWorkoutAdapterEventListener mListener;


    public interface ProgramWorkoutAdapterEventListener {
        void onEditProgramWorkoutClicked(ProgramWorkout programWorkout);
        void onDeleteProgramWorkoutClicked(ProgramWorkout programWorkout, int position);
        void onProgramWorkoutClicked(ProgramWorkout programWorkout);
    }

    public ProgramWorkoutAdapter(List<ProgramWorkout> programWorkouts, Context context) {
        mProgramWorkouts = programWorkouts;
        Log.i("REcyclerView", "size of array: " + mProgramWorkouts.size());
        mContext = context;
    }

    @Override
    public ProgramWorkoutHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        return new ProgramWorkoutHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(ProgramWorkoutAdapter.ProgramWorkoutHolder holder, int position) {
        holder.bind(mProgramWorkouts.get(position));
    }

    @Override
    public int getItemCount() {
        return mProgramWorkouts.size();
    }



    public void setPrograms(List<ProgramWorkout> programWorkouts) {
        Log.i("Adapter", "size " + programWorkouts.size());
        mProgramWorkouts = programWorkouts;
        notifyDataSetChanged();
    }

    public void setEventListener(ProgramWorkoutAdapterEventListener listener) {
        mListener = listener;
    }

    public List<ProgramWorkout> getProgramWorkouts() {
        return mProgramWorkouts;
    }

    public void removeProgramWorkout(ProgramWorkout programWorkout, int position) {
        mProgramWorkouts.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
        Log.i("RemoveProgramW", "position: " + position);
    }

    class ProgramWorkoutHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ProgramWorkout mProgramWorkout;
        protected TextView mProgramNameTextView;
        protected Button mOverFlowMenuButton;

        public ProgramWorkoutHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_program_workout, parent, false));

            mProgramNameTextView = (TextView) itemView.findViewById(R.id.list_item_program_workout_textView_title);
            mOverFlowMenuButton = (Button) itemView.findViewById(R.id.list_item_program_workout_button_overflow);
        }


        public void bind(ProgramWorkout programWorkout) {
            itemView.setOnClickListener(this);
            mProgramWorkout = programWorkout;
            mProgramNameTextView.setText(mProgramWorkout.getName());

            mOverFlowMenuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(mContext,mOverFlowMenuButton);
                    popup.getMenuInflater().inflate(R.menu.popup_menu_list_item_program_workout_overflow, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.popup_menu_list_item_program_workout_overflow_edit:
                                    mListener.onEditProgramWorkoutClicked(mProgramWorkout);
                                    return true;
                                case R.id.popup_menu_list_item_program_workout_overflow_delete:
                                    mListener.onDeleteProgramWorkoutClicked(mProgramWorkout, getAdapterPosition());
                                    return true;
                            }
                            return false;
                        }
                    });
                    popup.show();
                }
            });
        }

        @Override
        public void onClick(View view ) {
            mListener.onProgramWorkoutClicked(mProgramWorkout);
        }
    }

}
