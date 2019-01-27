package com.zewail.dakrory.zccourses.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zewail.dakrory.zccourses.Activities.AllCoursesOfProgramActivity;
import com.zewail.dakrory.zccourses.Activities.CourseActivity;
import com.zewail.dakrory.zccourses.Models.Course;
import com.zewail.dakrory.zccourses.Models.Program;
import com.zewail.dakrory.zccourses.R;
import com.zewail.dakrory.zccourses.Utils.Parameters;
import com.zewail.dakrory.zccourses.Utils.Tools;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by A7med Al-Dakrory on 1/17/2019.
 */

public class ProgramListAdapter extends RecyclerView.Adapter<ProgramListAdapter.ProgramViewHolder> {
    private ArrayList<Program> mPrograms = new ArrayList<>();
    private Context mContext;

    public ProgramListAdapter(Context context, ArrayList<Program> programs) {
        mContext = context;
        mPrograms = programs;
    }

    @Override
    public ProgramListAdapter.ProgramViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.program_item, parent, false);
        ProgramViewHolder viewHolder = new ProgramViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProgramListAdapter.ProgramViewHolder holder, int position) {
        holder.bindCourse(mPrograms.get(position));
    }

    @Override
    public int getItemCount() {
        return mPrograms.size();
    }

    public class ProgramViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.programImageView)
        ImageButton mProgramImageView;
        @BindView(R.id.programNameTextView)
        TextView mNameTextView;

        private Context mContext;

        public ProgramViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindCourse(final Program program) {
            Tools.SetImageToImageView(program.getImage(),mProgramImageView);
            mNameTextView.setText(program.getName());
            mProgramImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent openThisCourse=new Intent(mContext, AllCoursesOfProgramActivity.class);
                    openThisCourse.putExtra(Parameters.Program_id,program.getId());
                    mContext.startActivity(openThisCourse);
                }
            });
        }
    }
}