package com.github.florent37.androidallocineapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.florent37.allocineapi.model.Movie.Movie;
import com.github.florent37.allocineapi.service.AllocineApi;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class MainActivity extends AppCompatActivity {

    protected TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
        execute();
    }

    public void execute(){
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        final AllocineApi allocineApi = new AllocineApi(okHttpClient);

        allocineApi.movielist(AllocineApi.MovieListFilter.NOW_SHOWING, AllocineApi.MovieProfile.SMALL, AllocineApi.MovieListOrder.TOPRANK, 20, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {
                        textView.setText(movies.toString());
                        Log.d("MainActivity", movies.toString());
                    }
                });
    }
}
