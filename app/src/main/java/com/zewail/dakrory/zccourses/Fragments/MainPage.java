package com.zewail.dakrory.zccourses.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.zewail.dakrory.zccourses.Adapters.CoursesListAdapter;
import com.zewail.dakrory.zccourses.Adapters.ProgramListAdapter;
import com.zewail.dakrory.zccourses.Models.Course;
import com.zewail.dakrory.zccourses.Models.Program;
import com.zewail.dakrory.zccourses.R;
import com.zewail.dakrory.zccourses.WebServices.APIInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPage extends Fragment {



    @BindView(R.id.recyclerViewMain)
    RecyclerView recyclerProgramsView;
    ArrayList<Program> programsArrayList;
    private ProgramListAdapter mprogramsAdapter;


    @BindView(R.id.pbHeaderProgress)
    ProgressBar progressBar;

    String TAG ="MainPageZcCourse";
    APIInterface apiInterface;

    View view;

    public MainPage() {
        // Required empty public constructor
    }

    public MainPage getInstance(APIInterface apiInterface){
        this.apiInterface=apiInterface;
        return this;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main_page, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        programsArrayList=new ArrayList<Program>();

        addElementToRecyclerViewOfCourses();

    }


    private void addElementToRecyclerViewOfCourses() {

        Call<List<Program>> call = apiInterface.getAllPrograms();
        call.enqueue(new Callback<List<Program>>() {
            @Override
            public void onResponse(Call<List<Program>> call, Response<List<Program>> response) {
                Log.v(TAG,response.code()+"");

                if(response.code()==201) {
                    List<Program> programsList = response.body();
                    ArrayList<Program> programArrayList=new ArrayList<Program>(programsList);
                    setAdapterToProgramsListView(programArrayList);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Program>> call, Throwable t) {
                Log.v(TAG,"Failed");
                call.cancel();
            }
        });

    }

    private void setAdapterToProgramsListView(ArrayList<Program> programs) {
        mprogramsAdapter = new ProgramListAdapter(getActivity(), programs);
        recyclerProgramsView.setAdapter(mprogramsAdapter);
        recyclerProgramsView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
    }

}
