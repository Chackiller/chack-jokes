package com.kushyk.android.chacksjokes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.kushyk.android.chacksjokes.adapters.CategoriesRecycleViewAdapter;
import com.kushyk.android.chacksjokes.R;
import com.kushyk.android.chacksjokes.retrofit.NetworkService;

import java.util.ArrayList;
import java.util.List;

public class CategoriesListActivity extends AppCompatActivity {
    private boolean wasToast = false;
    private RecyclerView categoriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeFullScreen(getActivity(this));
        setContentView(R.layout.activity_categories_list);
        categoriesList = findViewById(R.id.categoriesRecycleView);
        getCategories();
    }

    private void getCategories() {
        NetworkService.getInstance()
                .getJSONApi()
                .getCategoriesJSON()
                .enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                        ArrayList<String> jsonArray = (ArrayList<String>) response.body();

                        CategoriesRecycleViewAdapter categoriesRecycleViewAdapter
                                = new CategoriesRecycleViewAdapter(CategoriesListActivity.this, jsonArray);

                        categoriesList.setAdapter(categoriesRecycleViewAdapter);
                        categoriesList.setLayoutManager(new LinearLayoutManager(CategoriesListActivity.this));
                        categoriesList.setHasFixedSize(true);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
                        if (!wasToast) {
                            Toast.makeText(getApplicationContext(),
                                    "Error occurred while getting request!", Toast.LENGTH_SHORT).show();
                            wasToast = true;
                        }
                        t.printStackTrace();
                    }
                });
    }

    public static void makeFullScreen(Activity myActivityReference) {
        myActivityReference.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            myActivityReference.getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
    }

    public static Activity getActivity(Context context) {
        if (context == null) return null;
        if (context instanceof Activity) return (Activity) context;
        if (context instanceof ContextWrapper)
            return getActivity(((ContextWrapper) context).getBaseContext());
        return null;
    }
}