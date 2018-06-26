package com.mationate.moviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mationate.moviesapp.models.Movie;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Movie> movieList;
    private EditText movieEt;
    private Button saveBtn;
    public static final String MOVIE_ID = "com.mationate.moviesapp.KEY.MOVIE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieEt = findViewById(R.id.movieEt);
        saveBtn = findViewById(R.id.saveBtn);
        Button lastMovieBtn = findViewById(R.id.lastMovieBtn);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleMovie = movieEt.getText().toString();


                if (titleMovie.trim().length() > 0) {

                    List<Movie> checkMovies = Movie.find(Movie.class, "name = ?", titleMovie);

                    if (checkMovies.size() > 0) {

                        Toast.makeText(MainActivity.this, "Existe pelicula con ese nombre", Toast.LENGTH_SHORT).show();

                    } else {

                        Movie movie = new Movie();
                        movie.setName(titleMovie);
                        movie.setWatched(false);
                        movie.save();

                        movieList = getMovies();
                        movieEt.setText("");
                        Toast.makeText(MainActivity.this, "Película guardada con éxito", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(MainActivity.this, "Debes escribir un nombre", Toast.LENGTH_SHORT).show();

                }

            }
        });

        lastMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = movieList.size();

                if (count > 0) {
                    long movieId = movieList.get(count - 1).getId();
                    /*Movie lastMovie = movieList.get(count-1);
                    lastMovie.setWatched(true);
                    lastMovie.save();*/

                    Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                    intent.putExtra(MOVIE_ID, movieId);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "No hay peliculas", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private List<Movie> getMovies() {
        /*movieList = Movie.listAll(Movie.class);*/
        return Movie.find(Movie.class, "watched = ?", "0");

    }

    @Override
    protected void onResume() {
        super.onResume();
        movieList = getMovies();

    }

}
