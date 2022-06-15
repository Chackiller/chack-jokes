package com.kushyk.android.chacksjokes.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.kushyk.android.chacksjokes.R;
import com.kushyk.android.chacksjokes.activities.JokeListActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoriesRecycleViewAdapter extends RecyclerView.Adapter<CategoriesRecycleViewAdapter.ViewHolder> {
    public static final String CATEGORY_KEY = "CATEGORY_KEY";
    private final LayoutInflater inflater;
    private final List<String> mCategoriesJSON;

    public CategoriesRecycleViewAdapter(Context context, List<String> categoriesJSON) {
        this.inflater = LayoutInflater.from(context);
        this.mCategoriesJSON = categoriesJSON;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.joke_category_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesRecycleViewAdapter.ViewHolder holder, int position) {
        String s = mCategoriesJSON.get(position);
        holder.categoryTextView.setText(s);
        holder.mView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.mView.getContext(), JokeListActivity.class);
            intent.putExtra(CATEGORY_KEY, s);
            holder.mView.getContext().startActivity(intent);
        });
        setAnimation(holder.itemView);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull CategoriesRecycleViewAdapter.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return mCategoriesJSON.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTextView;
        View mView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            categoryTextView = view.findViewById(R.id.jokeTextView);
        }

        public void clearAnimation() {
            mView.clearAnimation();
        }
    }

    private void setAnimation(View viewToAnimate) {
        Animation animation = AnimationUtils.loadAnimation(inflater.getContext(), R.anim.item_fall_in_animation);
        viewToAnimate.startAnimation(animation);
    }
}
