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

import com.jigsawcorp.android.jigsaw.Database.Exercise.ExerciseLab;
import com.jigsawcorp.android.jigsaw.Fragments.tab_current_workout.CurrentWorkoutFragment;
import com.jigsawcorp.android.jigsaw.Model.PerformedExercise;
import com.jigsawcorp.android.jigsaw.Model.PerformedSet;
import com.jigsawcorp.android.jigsaw.R;
import com.jigsawcorp.android.jigsaw.View.SetHolder;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class PerformedExerciseAdapter extends RecyclerView.Adapter<PerformedExerciseAdapter.PerformedExerciseHolder> implements CurrentWorkoutFragment.ActionCompletionContract {
    private List<AbstractMap.SimpleEntry<PerformedExercise, Boolean>> mPerformedExercises = new ArrayList<>();
    private Context mContext;
    private CurrentWorkoutFragment.OnPerformedExerciseListEventListener mListener;
    private View mSelectedSetView;
    private PerformedSet mSelectedPerformedSet;

    public PerformedExerciseAdapter(List<PerformedExercise> performedExercises, Context context) {
        for (PerformedExercise exercise : performedExercises) {
            mPerformedExercises.add(new AbstractMap.SimpleEntry<PerformedExercise, Boolean>(exercise, true));
        }
        mContext = context;
    }

    @Override
    public PerformedExerciseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        return new PerformedExerciseHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(PerformedExerciseHolder holder, int position) {
        holder.bind(mPerformedExercises.get(position));
    }

    @Override
    public int getItemCount() {
        return mPerformedExercises.size();
    }

    @Override
    public void onViewMoved(int oldPosition, int newPosition) {
        AbstractMap.SimpleEntry performedExercise = mPerformedExercises.get(oldPosition);
        mPerformedExercises.remove(oldPosition);
        mPerformedExercises.add(newPosition, performedExercise);
        notifyItemMoved(oldPosition, newPosition);
    }

    public void setPerformedExercises(List<PerformedExercise> exercises) {
        mPerformedExercises.clear();
        for (PerformedExercise exercise : exercises) {
            mPerformedExercises.add(new AbstractMap.SimpleEntry<PerformedExercise, Boolean>(exercise, true));
        }
    }

    public List<PerformedExercise> getPerformedExercises() {
        List<PerformedExercise> performedExercises = new ArrayList<>();
        for (AbstractMap.SimpleEntry<PerformedExercise, Boolean> exercise : mPerformedExercises) {
            performedExercises.add(exercise.getKey());
        }
        return performedExercises;
    }

    public void setPerformedExerciseListEventListener(CurrentWorkoutFragment.OnPerformedExerciseListEventListener listener) {
        mListener = listener;
    }


    public void updateSelectedSet(PerformedSet performedSet) {
        SetHolder.replaceSetHolder(mSelectedSetView, performedSet);
        mSelectedPerformedSet.setReps(performedSet.getReps());
        mSelectedPerformedSet.setWeight(performedSet.getWeight());
    }

    class PerformedExerciseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected AbstractMap.SimpleEntry<PerformedExercise, Boolean> mPerformedExercise;

        protected TextView mTitleTextView, mPositionNumberingTextView, mAddSetTextView;
        protected Button mExpandButton;
        protected LinearLayout mSetsContainer;
        protected Button mAddSetButton;

        public PerformedExerciseHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_performed_exercise, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_exercise_title);
            mPositionNumberingTextView = (TextView) itemView.findViewById(R.id.list_item_exercise_position_indicator);
            mExpandButton = (Button) itemView.findViewById(R.id.list_item_exercise_expand_button);
            mSetsContainer = (LinearLayout) itemView.findViewById(R.id.list_item_exercise_sets_container);
            mAddSetButton = (Button) itemView.findViewById(R.id.list_item_exercise_button_add_set);
            mAddSetTextView = (TextView) itemView.findViewById(R.id.list_item_exercise_text_view_add_set);
        }


        public void bind(final AbstractMap.SimpleEntry<PerformedExercise, Boolean> performedExercise){
            mPerformedExercise = performedExercise;
            mTitleTextView.setText(ExerciseLab.get(mContext).getExercise(performedExercise.getKey().getExercise()).getName());
            mPositionNumberingTextView.setText(String.valueOf(mPerformedExercises.indexOf(performedExercise) + 1));
            mSetsContainer.setVisibility(performedExercise.getValue() ? View.VISIBLE : View.GONE);
            mSetsContainer.removeAllViews();

            for (int i = 0; i < mPerformedExercise.getKey().getPerformedSets().size(); ++i) {
                addSet(mPerformedExercise.getKey().getPerformedSets().get(i), i);
            }
            mExpandButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (performedExercise.getValue()) {
                        toggleExpandSetContainer(false);
                        performedExercise.setValue(false);
                    }
                    else {toggleExpandSetContainer(true);
                        performedExercise.setValue(true);
                    }
                }
            });

            mAddSetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PerformedSet newPerformedSet;
                    if (mPerformedExercise.getKey().getPerformedSets() == null || mPerformedExercise.getKey().getPerformedSets().size() == 0) {
                        // Check to see if any sets were performed
                        // if so, return that last set
                        newPerformedSet = new PerformedSet(0,0) ;
                    }
                    else {
                        newPerformedSet = new PerformedSet(mPerformedExercise.getKey().getPerformedSets().get(mPerformedExercise.getKey().getPerformedSets().size() - 1));
                    }
                    mPerformedExercise.getKey().addSet(newPerformedSet);
                    selectSetHolder(addSet(newPerformedSet, (mPerformedExercise.getKey().getPerformedSets().size() - 1)), newPerformedSet);
                   // mListener.onNewSetClicked(newSet);
                }
            });

        }

        @Override
        public void onClick(View view) {
            Toast.makeText(mContext, "Hello", Toast.LENGTH_LONG).show();
            mListener.onExerciseClicked(mPerformedExercise.getKey());
        }

        private View addSet(final PerformedSet performedSet, int index) {
            View setHolder = SetHolder.getViewFromSet(LayoutInflater.from(mContext), mContext, performedSet, index);
            setHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectSetHolder(view, performedSet);
                }
            });
            mSetsContainer.addView(setHolder);
            return setHolder;
        }

        private void selectSetHolder(View view, PerformedSet performedSet) {
            if (view == mSelectedSetView) {
                view.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                mListener.onSetClicked(performedSet, true);
                mSelectedSetView = null;
                mSelectedPerformedSet = null;
            }
            else {
                if (mSelectedSetView != null) {
                    mSelectedSetView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }
                view.setBackgroundColor(mContext.getResources().getColor(R.color.caldroid_sky_blue));
                mListener.onSetClicked(performedSet, false);
                mSelectedSetView = view;
                mSelectedPerformedSet = performedSet;
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
