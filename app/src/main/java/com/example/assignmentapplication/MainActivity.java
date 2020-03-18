package com.example.assignmentapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.assignmentapplication.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  {

    static public ArrayList<Movie> movies;

    private RecyclerView recyclerView;

    private RecyclerViewAdapter recyclerViewAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting up Recycler View
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        movies = new ArrayList<>();

        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this,movies);

        recyclerView.setAdapter(recyclerViewAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<Movie>> call = api.getMovie();

        call.enqueue(new Callback<List<Movie>>() { // Getting json data using retrofit
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                List<Movie> gotFromRetrofit = response.body();
               if(gotFromRetrofit!=null){
                   for(Movie movie : gotFromRetrofit) {
                       movies.add(movie);
                   }
               }
               recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.i("Info","Failed");
            }
        });



    }
}
