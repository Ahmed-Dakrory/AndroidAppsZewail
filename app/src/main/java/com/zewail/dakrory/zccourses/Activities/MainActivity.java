package com.zewail.dakrory.zccourses.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zewail.dakrory.zccourses.Adapters.CoursesListAdapter;
import com.zewail.dakrory.zccourses.Fragments.MainPage;
import com.zewail.dakrory.zccourses.Models.Course;
import com.zewail.dakrory.zccourses.Models.Program;
import com.zewail.dakrory.zccourses.Models.UserData;
import com.zewail.dakrory.zccourses.R;
import com.zewail.dakrory.zccourses.Utils.Parameters;
import com.zewail.dakrory.zccourses.Utils.Tools;
import com.zewail.dakrory.zccourses.WebServices.APIClient;
import com.zewail.dakrory.zccourses.WebServices.APIInterface;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    Menu menu;


    @BindView(R.id.drawer_layout)
    DrawerLayout drawer ;


    ImageView UserImage ;

    TextView UserName ;

    TextView UserEmail ;



    APIInterface apiInterface;

    // Begin the transaction
    FragmentTransaction ft;

    Fragment mainPage;

    String TAG="ZcCoursesMain";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        ButterKnife.bind(this);
        View navHeaderView = navigationView.getHeaderView(0);
        UserImage=(ImageView)navHeaderView.findViewById(R.id.imageView);
        UserName=(TextView)navHeaderView.findViewById(R.id.NameOfUser);
        UserEmail=(TextView)navHeaderView.findViewById(R.id.emailOfUser);

        setSupportActionBar(toolbar);
        mainPage=new MainPage().getInstance(apiInterface);

        ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.MainActivity,mainPage );
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();

        menu = navigationView.getMenu();

        Tools.getUser(this);
        if(Tools.isLoggedIn){
            getUserData();
        }



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void hideLogin() {
        menu.findItem(R.id.login).setVisible(false);
        menu.findItem(R.id.logout).setVisible(true);
        Tools.SetImageToImageView(Tools.thisUserAccount.image,UserImage,Tools.Circular);
        UserName.setText(Tools.thisUserAccount.fullName);
        UserEmail.setText(Tools.thisUserAccount.email);
    }

    private void getUserData() {
        Call<UserData> call = apiInterface.getUserbyEmailAPassword(Tools.UserEmail,Tools.UserPassword);
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                Log.v(TAG,response.code()+"");

                if(response.code()==201) {
                    Tools.thisUserAccount=response.body();
                    Log.v(TAG,"Main: "+Tools.thisUserAccount.fullName);
                    hideLogin();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                Log.v(TAG,"Failed");
                call.cancel();
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Programs) {
            // Handle the camera action

        } else if (id == R.id.login) {
            Intent openLogin=new Intent(MainActivity.this,LoginActivity.class);
            startActivityForResult(openLogin, Parameters.RequestCodeForLogin);

        }else if (id == R.id.logout) {
            Logout();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void Logout() {
        Tools.LogOut(MainActivity.this);
        showNav();
    }

    private void showNav() {
        menu.findItem(R.id.login).setVisible(true);
        menu.findItem(R.id.logout).setVisible(false);
        //Tools.SetImageToImageView(Tools.thisUserAccount.image,UserImage);
        UserImage.setVisibility(View.GONE);
        UserName.setText("Courses");
        UserEmail.setText("learningtechnology@zewailcity.edu.eg");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Parameters.RequestCodeForLogin){
            if(Tools.isLoggedIn){
                hideLogin();
            }
        }
    }
}
