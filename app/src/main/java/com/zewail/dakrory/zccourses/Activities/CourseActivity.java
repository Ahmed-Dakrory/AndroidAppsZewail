package com.zewail.dakrory.zccourses.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zewail.dakrory.zccourses.Models.Course;
import com.zewail.dakrory.zccourses.Models.CourseReg;
import com.zewail.dakrory.zccourses.R;
import com.zewail.dakrory.zccourses.Utils.Parameters;
import com.zewail.dakrory.zccourses.Utils.Tools;
import com.zewail.dakrory.zccourses.WebServices.APIClient;
import com.zewail.dakrory.zccourses.WebServices.APIInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseActivity extends Activity {


    APIInterface apiInterface;

    int idOfThisCourse;
    Course thisCourseData;

    @BindView(R.id.collapsingToolbarlayout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.main_content)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.backdrop)
    ImageView displayCourseImage;

    @BindView(R.id.courseName)
    TextView displayCourseName;

    @BindView(R.id.courseDescrip)
    TextView displayCourseDescrip;

    @BindView(R.id.courseCateg)
    TextView displayCourseCateg;

    @BindView(R.id.enrollMe)
    Button enrollMeButton;

    @BindView(R.id.youAreEnrolled)
    AppCompatImageView youAreEnrolledDisplay;


    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @BindView(R.id.pbHeaderProgress)
    ProgressBar progressBar;

    String TAG="CourseActivityZcCourses";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        ButterKnife.bind(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        idOfThisCourse = getIntent().getIntExtra(Parameters.Course_id,0);
        coordinatorLayout.setVisibility(View.GONE);
        getCourseById(idOfThisCourse);

        if(Tools.isLoggedIn) {

            checkEnrollment(Tools.thisUserAccount.id, idOfThisCourse);
        }
        enrollMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Tools.isLoggedIn){
                    RegisterThisCourse(Tools.thisUserAccount.id,idOfThisCourse);
                }else{
                    Login();
                }
            }
        });


    }

    private void checkEnrollment(Integer id, int idOfThisCourse) {
        Call<CourseReg> call = apiInterface.getcourseRegData(id,idOfThisCourse);
        call.enqueue(new Callback<CourseReg>() {
            @Override
            public void onResponse(Call<CourseReg> call, Response<CourseReg> response) {
                Log.v(TAG,response.code()+"");

                if(response.code()==201) {
                    CourseReg courseReg=response.body();
                    Log.v(TAG, String.valueOf(courseReg.id));
                    if(courseReg.id!=null) {
                        enrollMeButton.setVisibility(View.GONE);
                        youAreEnrolledDisplay.setVisibility(View.VISIBLE);
                    }else{

                        enrollMeButton.setVisibility(View.VISIBLE);
                        youAreEnrolledDisplay.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<CourseReg> call, Throwable t) {
                Log.v(TAG,"Failed");
                call.cancel();
                enrollMeButton.setVisibility(View.VISIBLE);
                youAreEnrolledDisplay.setVisibility(View.GONE);
            }
        });
    }

    private void RegisterThisCourse(Integer id, int idOfThisCourse) {
        Call<CourseReg> call = apiInterface.setcourseRegData(id,idOfThisCourse);
        call.enqueue(new Callback<CourseReg>() {
            @Override
            public void onResponse(Call<CourseReg> call, Response<CourseReg> response) {
                Log.v(TAG,response.code()+"");

                if(response.code()==201) {
                   enrollMeButton.setVisibility(View.GONE);
                   youAreEnrolledDisplay.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<CourseReg> call, Throwable t) {
                Log.v(TAG,"Failed");
                call.cancel();
            }
        });
    }

    private void Login() {
        Intent login=new Intent(CourseActivity.this,LoginActivity.class);
        startActivity(login);
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
            finish();
    }

    private void getCourseById(int i) {
        Call<Course> call = apiInterface.getCourseById(i);
        call.enqueue(new Callback<Course>() {
            @Override
            public void onResponse(Call<Course> call, Response<Course> response) {
                Log.v(TAG,response.code()+"");

                if(response.code()==201) {
                    thisCourseData = response.body();
                    Log.v(TAG,"CourseId: "+thisCourseData.getId());

                    progressBar.setVisibility(View.GONE);
                    coordinatorLayout.setVisibility(View.VISIBLE);
                    addTitleToCollapseToolbar(thisCourseData.getName());
                    setImageToImageView();

                }
            }

            @Override
            public void onFailure(Call<Course> call, Throwable t) {
                Log.v(TAG,"Failed");
                call.cancel();
            }
        });

    }

    private void setImageToImageView() {
        Tools.SetImageToImageView(thisCourseData.getImage(),displayCourseImage);
        displayCourseName.setText(thisCourseData.getName());
        displayCourseDescrip.setText(thisCourseData.getDescription());
        displayCourseCateg.setText(thisCourseData.getCategories());
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
}
