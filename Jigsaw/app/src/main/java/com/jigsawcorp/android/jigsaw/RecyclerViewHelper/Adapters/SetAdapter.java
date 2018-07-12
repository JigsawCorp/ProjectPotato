package com.jigsawcorp.android.jigsaw.RecyclerViewHelper.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jigsawcorp.android.jigsaw.Model.Set;
import com.jigsawcorp.android.jigsaw.R;

import java.util.ArrayList;
import java.util.List;

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.SetHolder> {
    private List<Set> mSets;
    private int mSelectedPosition;
    private  OnItemClickListener mListener;
    private Context mContext;

    public interface OnItemClickListener {
        void onItemSelected(Set set);
    }

    public SetAdapter(Context context, List<Set> sets, OnItemClickListener listener) {
        mSets = sets;
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
        Set set = mSets.get(position);
        holder.bind(set, position, mListener);
    }

    @Override
    public int getItemCount() {
        return mSets.size();
    }

    public void setSets(List<Set> sets) {
        mSets = sets;
    }

    public void addSet(Set set) {
        mSets.add(set);
        notifyDataSetChanged();
        Log.i("SetAdapter", "SetAdapter()");

    }

    public void updateSet(Set set) {
        mSets.set(mSets.indexOf(set), set);
    }

    public class SetHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Set mSet;

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

        public void bind(Set set, int position, OnItemClickListener listener){
            mSet = set;
            mPositionTextView.setText(String.valueOf(position + 1));
            mWeightTextView.setText(String.valueOf(set.getWeight()));
            mRepsTextView.setText(String.valueOf(set.getReps()));

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
                mListener.onItemSelected(mSet);
            }
        }
    }


}
