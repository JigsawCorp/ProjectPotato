package com.jigsawcorp.android.jigsaw.View.RecyclerView;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jigsawcorp.android.jigsaw.Activities.ProgramWorkoutActivity;
import com.jigsawcorp.android.jigsaw.Database.Exercise.ExerciseLab;
import com.jigsawcorp.android.jigsaw.Fragments.tab_current_workout.CurrentWorkoutFragment;
import com.jigsawcorp.android.jigsaw.Fragments.tab_programs.ProgramWorkoutsTabFragment;
import com.jigsawcorp.android.jigsaw.Model.PerformedExercise;
import com.jigsawcorp.android.jigsaw.Model.PerformedSet;
import com.jigsawcorp.android.jigsaw.Model.ProgramExercise;
import com.jigsawcorp.android.jigsaw.Model.ProgramSet;
import com.jigsawcorp.android.jigsaw.R;
import com.jigsawcorp.android.jigsaw.View.ProgramSetHolder;
import com.jigsawcorp.android.jigsaw.View.SetHolder;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class WorkoutExerciseAdapter extends RecyclerView.Adapter<WorkoutExerciseAdapter.ProgramExerciseHolder> implements ProgramWorkoutActivity.ActionCompletionContract{
    private List<AbstractMap.SimpleEntry<ProgramExercise, Boolean>> mProgramExercises = new ArrayList<>();
    private Context mContext;
    private WorkoutExerciseAdapterEventListener mListener;
    private View mSelectedSetView;
    private ProgramSet mSelectedProgramSet;

    public interface WorkoutExerciseAdapterEventListener {
        void onExerciseClicked(ProgramExercise programExercise);
        void onSetClicked(ProgramSet set, Boolean sameSet);
    }

    public WorkoutExerciseAdapter(List<ProgramExercise> programExercises, Context context) {
        for (ProgramExercise exercise : programExercises) {
            mProgramExercises.add(new AbstractMap.SimpleEntry<ProgramExercise, Boolean>(exercise, true));
        }
        mContext = context;
    }

    @Override
    public ProgramExerciseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        return new ProgramExerciseHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(ProgramExerciseHolder holder, int position) {
        holder.bind(mProgramExercises.get(position));
    }

    @Override
    public int getItemCount() {
        return mProgramExercises.size();
    }

    @Override
    public void onViewMoved(int oldPosition, int newPosition) {
        AbstractMap.SimpleEntry programExercise = mProgramExercises.get(oldPosition);
        mProgramExercises.remove(oldPosition);
        mProgramExercises.add(newPosition, programExercise);
        notifyItemMoved(oldPosition, newPosition);
    }

    public void setProgramExercises(List<ProgramExercise> exercises) {
        mProgramExercises.clear();
        for (ProgramExercise exercise : exercises) {
            mProgramExercises.add(new AbstractMap.SimpleEntry<ProgramExercise, Boolean>(exercise, true));
        }
        notifyDataSetChanged();
    }

    public List<ProgramExercise> getProgramExercises() {
        List<ProgramExercise> programExercises = new ArrayList<>();
        for (AbstractMap.SimpleEntry<ProgramExercise, Boolean> exercise : mProgramExercises) {
            programExercises.add(exercise.getKey());
        }
        return programExercises;
    }

    public void setWorkoutExercisesListEventListener(WorkoutExerciseAdapterEventListener listener) {
        mListener = listener;
    }


    public void updateSelectedSet(ProgramSet programSet) {
        ProgramSetHolder.replaceSetHolder(mSelectedSetView, mContext, programSet);
        mSelectedProgramSet.updateSet(programSet);
    }

    class ProgramExerciseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected AbstractMap.SimpleEntry<ProgramExercise, Boolean> mProgramExercise;

        protected TextView mTitleTextView, mPositionNumberingTextView, mAddSetTextView;
        protected Button mExpandButton;
        protected LinearLayout mSetsContainer;
        protected Button mAddSetButton;

        public ProgramExerciseHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_program_exercise, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_program_exercise_title);
            mPositionNumberingTextView = (TextView) itemView.findViewById(R.id.list_item_program_exercise_position_indicator);
            mExpandButton = (Button) itemView.findViewById(R.id.list_item_program_exercise_expand_button);
            mSetsContainer = (LinearLayout) itemView.findViewById(R.id.list_item_program_exercise_sets_container);
            mAddSetButton = (Button) itemView.findViewById(R.id.list_item_program_exercise_button_add_set);
            mAddSetTextView = (TextView) itemView.findViewById(R.id.list_item_program_exercise_text_view_add_set);
        }


        public void bind(final AbstractMap.SimpleEntry<ProgramExercise, Boolean> programExercise){
            mProgramExercise = programExercise;
            mTitleTextView.setText((ExerciseLab.get(mContext).getExercise(programExercise.getKey().getExercise()).getName()));
            mPositionNumberingTextView.setText(String.valueOf(mProgramExercises.indexOf(programExercise) + 1));
            mSetsContainer.setVisibility(programExercise.getValue() ? View.VISIBLE : View.GONE);
            mSetsContainer.removeAllViews();

            for (int i = 0; i < mProgramExercise.getKey().getSets().size(); ++i) {
                addSet(mProgramExercise.getKey().getSets().get(i), i);
            }
            mExpandButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mProgramExercise.getValue()) {
                        toggleExpandSetContainer(false);
                        mProgramExercise.setValue(false);
                    }
                    else {toggleExpandSetContainer(true);
                        mProgramExercise.setValue(true);
                    }
                }
            });

            mAddSetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProgramSet newProgramSet;
                    if (mProgramExercise.getKey().getSets() == null || mProgramExercise.getKey().getSets().size() == 0) {
                        // Check to see if any sets were performed
                        // if so, return that last set
                        newProgramSet = new ProgramSet(8,12, false, false);
                    }
                    else {
                        newProgramSet = mProgramExercise.getKey().getSets().get(mProgramExercise.getKey().getSets().size() - 1);
                    }
                    mProgramExercise.getKey().addSet(newProgramSet);
                    selectSetHolder(addSet(newProgramSet, (mProgramExercise.getKey().getSets().size() - 1)), newProgramSet);
                    // mListener.onNewSetClicked(newSet);
                }
            });

        }

        @Override
        public void onClick(View view) {
            Toast.makeText(mContext, "Hello", Toast.LENGTH_LONG).show();
            mListener.onExerciseClicked(mProgramExercise.getKey());
        }

        private View addSet(final ProgramSet programSet, int index) {
            View programSetHolder = com.jigsawcorp.android.jigsaw.View.ProgramSetHolder.getViewFromSet(LayoutInflater.from(mContext), mContext, programSet, index);
            programSetHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectSetHolder(view, programSet);
                }
            });
            mSetsContainer.addView(programSetHolder);
            return programSetHolder;
        }

        private void selectSetHolder(View view, ProgramSet programSet) {
            if (view == mSelectedSetView) {
                view.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                mListener.onSetClicked(programSet, true);
                mSelectedSetView = null;
                mSelectedProgramSet = null;
            }
            else {
                if (mSelectedSetView != null) {
                    mSelectedSetView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }
                view.setBackgroundColor(mContext.getResources().getColor(R.color.caldroid_sky_blue));
                mSelectedSetView = view;
                mSelectedProgramSet = programSet;
                mListener.onSetClicked(programSet, false);
            }
        }

        private void toggleExpandSetContainer(boolean toExpand) {
            if (toExpand) {
                mSetsContainer.setVisibility(View.VISIBLE);
                mAddSetButton.setVisibility(View.VISIBLE);
                mAddSetTextView.setVisibility(View.VISIBLE);
            }
            else {
                mSetsContainer.setVisibility(View.GONE);
                mAddSetButton.setVisibility(View.GONE);
                mAddSetTextView.setVisibility(View.GONE);
            }
        }
    }
}
