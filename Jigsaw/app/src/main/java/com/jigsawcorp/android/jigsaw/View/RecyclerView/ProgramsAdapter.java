package com.jigsawcorp.android.jigsaw.View.RecyclerView;

import android.content.Context;
import android.os.Bundle;
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
import com.jigsawcorp.android.jigsaw.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProgramsAdapter extends RecyclerView.Adapter<ProgramsAdapter.ProgramHolder> {
    private List<Program> mPrograms = new ArrayList<>();
    private Context mContext;
    private ProgramsAdatperEventListener mListener;

    public interface ProgramsAdatperEventListener {
        void onEditProgramClicked(Program program);
    }

    public ProgramsAdapter(List<Program> programs, Context context) {
        mPrograms = programs;
        mContext = context;
    }

    @Override
    public ProgramHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        return new ProgramHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(ProgramHolder holder, int position) {
        holder.bind(mPrograms.get(position));
    }

    @Override
    public int getItemCount() {
        return mPrograms.size();
    }



    public void setPrograms(List<Program> programs) {
        mPrograms = programs;
        notifyDataSetChanged();
    }

    public void setEventListener(ProgramsAdatperEventListener listener) {
        mListener = listener;
    }

    public List<Program> getPrograms() {
        return mPrograms;
    }

    class ProgramHolder extends RecyclerView.ViewHolder{
        protected Program mProgram;
        protected TextView mProgramNameTextView, mProgramDescriptionTextView, mProgramTrainingTypeTextView, mProgramDaysPerWeekTextView, mProgramSplitTextView;
        protected Button mOverFlowMenuButton;

        public ProgramHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_program, parent, false));

            mProgramNameTextView = (TextView) itemView.findViewById(R.id.list_item_program_textView_program_name);
            mProgramDescriptionTextView = (TextView) itemView.findViewById(R.id.list_item_program_textView_description);
            mProgramTrainingTypeTextView = (TextView) itemView.findViewById(R.id.list_item_program_textView_training_type);
            mProgramDaysPerWeekTextView = (TextView) itemView.findViewById(R.id.list_item_program_textView_days_per_week);
            mProgramSplitTextView = (TextView) itemView.findViewById(R.id.list_item_program_textView_split);
            mOverFlowMenuButton = (Button) itemView.findViewById(R.id.list_item_program_button_overflow);
        }


        public void bind(Program program) {
            final ArrayList<String> trainingTypeStringArray = new ArrayList<>(Arrays.asList(mContext.getResources().getStringArray(R.array.array_training_types)));
            mProgram = program;
            mProgramNameTextView.setText(mProgram.getName());
            mProgramDescriptionTextView.setText(mProgram.getDescription());
            mProgramTrainingTypeTextView.setText(trainingTypeStringArray.get(mProgram.getTrainingType().ordinal()));
            if (mProgram.getDaysPerWeek() == -1) {
                mProgramDaysPerWeekTextView.setText(mContext.getResources().getString(R.string.variable) + " days per week");
            } else {
                mProgramDaysPerWeekTextView.setText(mProgram.getDaysPerWeek() + " days per week");
            }

            mOverFlowMenuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("ProgramsAdapter", "mOverFlowMenuButton onClick()");
                    PopupMenu popup = new PopupMenu(mContext,mOverFlowMenuButton);
                    popup.getMenuInflater().inflate(R.menu.popup_menu_list_item_program_overflow, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.popup_menu_edit_list_item_program_edit:
                                    mListener.onEditProgramClicked(mProgram);
                                    return true;
                                case R.id.popup_menu_edit_list_item_program_delete:
                                    return true;
                            }
                            return false;
                        }
                    });
                    popup.show();
                }
            });
        }

    }
}
