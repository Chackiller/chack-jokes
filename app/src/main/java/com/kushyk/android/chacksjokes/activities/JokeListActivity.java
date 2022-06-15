package com.kushyk.android.chacksjokes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.widget.Toast;

import com.kushyk.android.chacksjokes.R;
import com.kushyk.android.chacksjokes.adapters.JokesRecycleViewAdapter;
import com.kushyk.android.chacksjokes.retrofit.JokesJSON;
import com.kushyk.android.chacksjokes.retrofit.NetworkService;

import java.util.ArrayList;

import static com.kushyk.android.chacksjokes.activities.CategoriesListActivity.getActivity;
import static com.kushyk.android.chacksjokes.activities.CategoriesListActivity.makeFullScreen;
import static com.kushyk.android.chacksjokes.adapters.CategoriesRecycleViewAdapter.CATEGORY_KEY;

public class JokeListActivity extends AppCompatActivity {
    private final ArrayList<JokesJSON> jokesArray = new ArrayList<>();
    private RecyclerView jokesList;
    private boolean wasToast = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeFullScreen(getActivity(this));
        setContentView(R.layout.activity_joke_list);
        jokesList = findViewById(R.id.categoriesRecycleView);
        setJokesArray();
    }

    private void setJokesArray() {
        for (int i = 0; i < 15; i++) {
            getJoke();
        }
    }

    private void getJoke() {
        NetworkService.getInstance()
                .getJSONApi()
                .getRandomJokesJSON(getIntent().getExtras().get(CATEGORY_KEY).toString())
                .enqueue(new Callback<JokesJSON>() {
                    @Override
                    public void onResponse(@NonNull Call<JokesJSON> call, @NonNull Response<JokesJSON> response) {
                        JokesJSON jokesJSON = response.body();
                        jokesArray.add(jokesJSON);
                        System.out.println(jokesArray.size());
                        if (jokesArray.size() == 15) {
                            JokesRecycleViewAdapter jokesRecycleViewAdapter
                                    = new JokesRecycleViewAdapter(JokeListActivity.this, jokesArray);

                            jokesList.setAdapter(jokesRecycleViewAdapter);
                            jokesList.setLayoutManager(new LinearLayoutManager(JokeListActivity.this));
                            jokesList.setHasFixedSize(true);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<JokesJSON> call, @NonNull Throwable t) {
                        if (!wasToast) {
                            Toast.makeText(getApplicationContext(),
                                    "Error occurred while getting request!", Toast.LENGTH_SHORT).show();
                            wasToast = true;
                        }
                        t.printStackTrace();
                    }
                });
    }
}