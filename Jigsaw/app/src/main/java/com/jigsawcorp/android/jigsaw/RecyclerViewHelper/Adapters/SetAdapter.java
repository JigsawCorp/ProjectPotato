package com.jigsawcorp.android.jigsaw.RecyclerViewHelper.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jigsawcorp.android.jigsaw.Model.PerformedSet;
import com.jigsawcorp.android.jigsaw.R;

import java.util.List;

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.SetHolder> {
    private List<PerformedSet> mPerformedSets;
    public int mSelectedPosition;
    private  OnItemClickListener mListener;
    private Context mContext;

    public interface OnItemClickListener {
        void onItemSelected(PerformedSet performedSet);
    }

    public SetAdapter(Context context, List<PerformedSet> performedSets, OnItemClickListener listener) {
        mPerformedSets = performedSets;
        mSelectedPosition = -1;
        mListener = listener;
        mContext = context;
    }

    @Override
    public SetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new SetHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(SetHolder holder, int position) {
        PerformedSet performedSet = mPerformedSets.get(position);
        holder.bind(performedSet, position, mListener);
    }

    @Override
    public int getItemCount() {
        return mPerformedSets.size();
    }

    public void setPerformedSets(List<PerformedSet> performedSets) {
        mPerformedSets = performedSets;
    }

    public void addSet(PerformedSet performedSet) {
        mPerformedSets.add(performedSet);
        notifyDataSetChanged();
    }

    public void updateSet(PerformedSet performedSet) {
        mPerformedSets.set(mPerformedSets.indexOf(performedSet), performedSet);
        notifyItemChanged(mPerformedSets.indexOf(performedSet));
    }

    public class SetHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private PerformedSet mPerformedSet;

        private TextView mPositionTextView;
        private TextView mWeightTextView;
        private TextView mRepsTextView;

        public SetHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_set, parent, false));
            itemView.setOnClickListener(this);

            mPositionTextView = (TextView) itemView.findViewById(R.id.list_item_set_position_indicator);
            mWeightTextView = (TextView) itemView.findViewById(R.id.list_item_set_weight);
            mRepsTextView = (TextView) itemView.findViewById(R.id.list_item_set_reps);
        }

        public void bind(PerformedSet performedSet, int position, OnItemClickListener listener){
            mPerformedSet = performedSet;
            mPositionTextView.setText(String.valueOf(position + 1));
            mWeightTextView.setText(String.valueOf(performedSet.getWeight()));
            mRepsTextView.setText(String.valueOf(performedSet.getReps()));

            if (position == mSelectedPosition) {
                itemView.setBackgroundColor(mContext.getResources().getColor(R.color.caldroid_sky_blue));
            }
            else {
                itemView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
        }

        @Override
        public void onClick(View view) {
            if (getAdapterPosition() == mSelectedPosition) {
                mSelectedPosition = -1;
                itemView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                mListener.onItemSelected(null);
            }
            else {
                itemView.setBackgroundColor(mContext.getResources().getColor(R.color.caldroid_sky_blue));
                mSelectedPosition = getAdapterPosition();
                notifyDataSetChanged();
                mListener.onItemSelected(mPerformedSet);
            }
        }
    }


}
