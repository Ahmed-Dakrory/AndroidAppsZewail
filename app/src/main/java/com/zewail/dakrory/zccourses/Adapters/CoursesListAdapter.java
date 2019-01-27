package com.zewail.dakrory.zccourses.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zewail.dakrory.zccourses.Activities.CourseActivity;
import com.zewail.dakrory.zccourses.Models.Course;
import com.zewail.dakrory.zccourses.R;
import com.zewail.dakrory.zccourses.Utils.Parameters;
import com.zewail.dakrory.zccourses.Utils.Tools;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by A7med Al-Dakrory on 1/17/2019.
 */

public class CoursesListAdapter extends RecyclerView.Adapter<CoursesListAdapter.CourseViewHolder> {
    private ArrayList<Course> mCourses = new ArrayList<>();
    private Context mContext;

    public CoursesListAdapter(Context context, ArrayList<Course> courses) {
        mContext = context;
        mCourses = courses;
    }

    @Override
    public CoursesListAdapter.CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        CourseViewHolder viewHolder = new CourseViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CoursesListAdapter.CourseViewHolder holder, int position) {
        holder.bindCourse(mCourses.get(position));
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.courseImageView)
        ImageButton mCourseImageView;
        @BindView(R.id.courseNameTextView)
        TextView mNameTextView;
        @BindView(R.id.categoryTextView) TextView mCategoryTextView;
        @BindView(R.id.priceTextView) TextView mPriceTextView;

        private Context mContext;

        public CourseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindCourse(final Course course) {
            Tools.SetImageToImageView(course.getImage(),mCourseImageView);
            mNameTextView.setText(course.getName());
            mCategoryTextView.setText(course.getCategories());
            mPriceTextView.setText("Price: " + course.getPrice());
            mCourseImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent openThisCourse=new Intent(mContext, CourseActivity.class);
                    openThisCourse.putExtra(Parameters.Course_id,course.getId());
                    mContext.startActivity(openThisCourse);
                }
            });
        }
    }
}