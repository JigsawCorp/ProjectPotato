package com.jigsawcorp.android.jigsaw.View.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jigsawcorp.android.jigsaw.Model.Program;
import com.jigsawcorp.android.jigsaw.R;

import java.util.ArrayList;
import java.util.List;

public class ProgramsAdapter extends RecyclerView.Adapter<ProgramsAdapter.ProgramHolder> {
    private List<Program> mPrograms = new ArrayList<>();
    private Context mContext;

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

    public List<Program> getPrograms() {
        return mPrograms;
    }

    class ProgramHolder extends RecyclerView.ViewHolder{
        protected Program mProgram;
        protected TextView mProgramNameTextView, mProgramDescriptionTextView, mProgramTrainingTypeTextView, mProgramLengthTextView, mProgramSplitTextView;

        public ProgramHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_program, parent, false));

            mProgramNameTextView = (TextView) itemView.findViewById(R.id.list_item_program_textView_program_name);
            mProgramDescriptionTextView = (TextView) itemView.findViewById(R.id.list_item_program_textView_description);
            mProgramTrainingTypeTextView = (TextView) itemView.findViewById(R.id.list_item_program_textView_training_type);
            mProgramLengthTextView = (TextView) itemView.findViewById(R.id.list_item_program_textView_is_weekly);
            mProgramSplitTextView = (TextView) itemView.findViewById(R.id.list_item_program_textView_split);
        }


        public void bind(Program program){
            mProgram = program;
            mProgramNameTextView.setText(mProgram.getName());
            mProgramDescriptionTextView.setText(mProgram.getDescription());
            //mProgramTrainingTypeTextView.setText(mProgram.getTrainingType().name());
            mProgramTrainingTypeTextView.setText("HAHAHAHAHAHAHAHAHAHHA");
        }

    }
}
