package com.jigsawcorp.android.jigsaw.View.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jigsawcorp.android.jigsaw.Activities.PerformedExerciseEditActivity;
import com.jigsawcorp.android.jigsaw.Database.Exercise.ExerciseLab;
import com.jigsawcorp.android.jigsaw.Fragments.CurrentWorkoutFragment;
import com.jigsawcorp.android.jigsaw.Model.PerformedExercise;
import com.jigsawcorp.android.jigsaw.Model.Set;
import com.jigsawcorp.android.jigsaw.R;
import com.jigsawcorp.android.jigsaw.Util.RequestCodes;
import com.jigsawcorp.android.jigsaw.View.SetHolder;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CheckedOutputStream;

public class PerformedExerciseAdapter extends RecyclerView.Adapter<PerformedExerciseAdapter.PerformedExerciseHolder> implements CurrentWorkoutFragment.ActionCompletionContract {
    private List<AbstractMap.SimpleEntry<PerformedExercise, Boolean>> mPerformedExercises = new ArrayList<>();
    private Context mContext;
    private CurrentWorkoutFragment.OnPerformedExerciseListEventListener mListener;
    private View mSelectedSetView;
    private Set mSelectedSet;

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


    public void updateSelectedSet(Set set) {
        SetHolder.replaceSetHolder(mSelectedSetView, set);
        mSelectedSet.setReps(set.getReps());
        mSelectedSet.setWeight(set.getWeight());
    }

    class PerformedExerciseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected AbstractMap.SimpleEntry<PerformedExercise, Boolean> mPerformedExercise;

        protected TextView mTitleTextView;
        protected TextView mPositionNumberingTextView;
        protected Button mExpandButton;
        protected LinearLayout mSetsContainer;

        public PerformedExerciseHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_performed_exercise, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_performed_exercise_title);
            mPositionNumberingTextView = (TextView) itemView.findViewById(R.id.list_item_performed_exercise_position_indicator);
            mExpandButton = (Button) itemView.findViewById(R.id.list_item_performed_exercise_expand_button);
            mSetsContainer = (LinearLayout) itemView.findViewById(R.id.list_item_performed_exercise_sets_container);
        }


        public void bind(final AbstractMap.SimpleEntry<PerformedExercise, Boolean> performedExercise){
            mPerformedExercise = performedExercise;
            mTitleTextView.setText(ExerciseLab.get(mContext).getExercise(performedExercise.getKey().getExercise()).getName());
            mPositionNumberingTextView.setText(String.valueOf(mPerformedExercises.indexOf(performedExercise) + 1));
            mSetsContainer.setVisibility(performedExercise.getValue() ? View.VISIBLE : View.GONE);
            mSetsContainer.removeAllViews();

            for (final Set set : mPerformedExercise.getKey().getSets()) {
                // View set = getLayoutInflater().inflate(R.layout.list_item_set, null);
                View setHolder = SetHolder.getViewFromSet(LayoutInflater.from(mContext), mContext,set);
                setHolder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (view == mSelectedSetView) {
                            view.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                            mListener.onSetClicked(set, true);
                            mSelectedSetView = null;
                            mSelectedSet = null;
                        }
                        else {
                            if (mSelectedSetView != null) {
                                mSelectedSetView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                            }
                            view.setBackgroundColor(mContext.getResources().getColor(R.color.caldroid_sky_blue));
                            mListener.onSetClicked(set, false);
                            mSelectedSetView = view;
                            mSelectedSet = set;
                        }
                    }
                });
                mSetsContainer.addView(setHolder);

            }
            mExpandButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (performedExercise.getValue()) {
                        mSetsContainer.setVisibility(View.GONE);
                        performedExercise.setValue(false);
                    }
                    else {
                        mSetsContainer.setVisibility(View.VISIBLE);
                        performedExercise.setValue(true);
                    }
                }
            });

        }

        @Override
        public void onClick(View view) {
            Toast.makeText(mContext, "Hello", Toast.LENGTH_LONG).show();
            mListener.onExerciseClicked(mPerformedExercise.getKey());
        }
    }
}
