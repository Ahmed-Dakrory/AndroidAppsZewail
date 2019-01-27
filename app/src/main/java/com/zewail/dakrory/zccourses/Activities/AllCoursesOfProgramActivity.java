package com.zewail.dakrory.zccourses.Activities;

import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zewail.dakrory.zccourses.Adapters.CoursesListAdapter;
import com.zewail.dakrory.zccourses.Models.Course;
import com.zewail.dakrory.zccourses.Models.Program;
import com.zewail.dakrory.zccourses.R;
import com.zewail.dakrory.zccourses.Utils.Parameters;
import com.zewail.dakrory.zccourses.Utils.Tools;
import com.zewail.dakrory.zccourses.WebServices.APIClient;
import com.zewail.dakrory.zccourses.WebServices.APIInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCoursesOfProgramActivity extends Activity {

    APIInterface apiInterface;

    int idOfThisProgram;
    Program thisProgramData;

    @BindView(R.id.collapsingToolbarlayout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.main_content)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.backdrop)
    ImageView displayProgramImage;

    @BindView(R.id.programName)
    TextView displayProgramName;

    @BindView(R.id.programDescrip)
    TextView displayProgramDescrip;





    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @BindView(R.id.pbHeaderProgress)
    ProgressBar progressBar;

    @BindView(R.id.pbHeaderProgress2)
    ProgressBar progressBarRecyLoading;


    @BindView(R.id.recyclerViewCourses)
    RecyclerView recyclerCoursesView;
    ArrayList<Course> courseArrayList;
    private CoursesListAdapter mcoursesAdapter;

    String TAG="ProgramActivityZcCourses";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_courses_of_program);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        ButterKnife.bind(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        idOfThisProgram = getIntent().getIntExtra(Parameters.Program_id,0);
         courseArrayList=new ArrayList<Course>();
        coordinatorLayout.setVisibility(View.GONE);
        getProgrambyId(idOfThisProgram);

    }

    private void getProgrambyId(int i) {
        Call<Program> call = apiInterface.getProgrambyId(i);
        call.enqueue(new Callback<Program>() {
            @Override
            public void onResponse(Call<Program> call, Response<Program> response) {
                Log.v(TAG,response.code()+"");

                if(response.code()==201) {
                    thisProgramData = response.body();
                    Log.v(TAG,"CourseId: "+thisProgramData.getId());

                    progressBar.setVisibility(View.GONE);
                    coordinatorLayout.setVisibility(View.VISIBLE);
                    addTitleToCollapseToolbar(thisProgramData.getName());
                    setImageToImageView();
                    addElementToRecyclerViewOfCourses(idOfThisProgram);

                }
            }

            @Override
            public void onFailure(Call<Program> call, Throwable t) {
                Log.v(TAG,"Failed");
                call.cancel();
            }
        });

    }

    private void setImageToImageView() {
        Tools.SetImageToImageView(thisProgramData.getImage(),displayProgramImage);
        displayProgramName.setText(thisProgramData.getName());
        displayProgramDescrip.setText(thisProgramData.getDescription());
    }

    private void addTitleToCollapseToolbar(final String name) {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(name);
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
    }

    private void addElementToRecyclerViewOfCourses(int i) {
        Call<List<Course>> call = apiInterface.getCoursesbyProgramId(i);
        call.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                Log.v(TAG,response.code()+"");

                if(response.code()==201) {
                    List<Course> course = response.body();
                    ArrayList<Course> coursesArrayList=new ArrayList<Course>(course);
                    setAdapterToCoursesListView(coursesArrayList);
                    progressBarRecyLoading.setVisibility(View.GONE);
                    recyclerCoursesView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Log.v(TAG,"Failed");
                call.cancel();
            }
        });

    }

    private void setAdapterToCoursesListView(ArrayList<Course> courses) {
        mcoursesAdapter = new CoursesListAdapter(this, courses);
        recyclerCoursesView.setAdapter(mcoursesAdapter);
        recyclerCoursesView.setLayoutManager(new GridLayoutManager(this, 2));
    }
}
