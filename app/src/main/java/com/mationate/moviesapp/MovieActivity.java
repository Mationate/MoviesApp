package com.mationate.moviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mationate.moviesapp.models.Movie;

public class MovieActivity extends AppCompatActivity {

    private CheckBox watchedCb;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

         watchedCb = findViewById(R.id.watchedCb);

        Intent intent = getIntent();
        long movieid = intent.getLongExtra(MainActivity.MOVIE_ID,0);
        Log.e("TAG", String.valueOf(movieid));

        movie = Movie.findById(Movie.class,movieid);
        Log.e("TAG",movie.getName());

        getSupportActionBar().setTitle(movie.getName());

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (watchedCb.isChecked()){
            movie.setWatched(true);
            movie.save();
        }

    }
}
