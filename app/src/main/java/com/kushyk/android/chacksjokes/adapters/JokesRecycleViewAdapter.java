package com.kushyk.android.chacksjokes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.kushyk.android.chacksjokes.retrofit.JokesJSON;
import com.kushyk.android.chacksjokes.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class JokesRecycleViewAdapter extends RecyclerView.Adapter<JokesRecycleViewAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final List<JokesJSON> jokesJSON;

    public JokesRecycleViewAdapter(Context context, List<JokesJSON> jokesJSON) {
        this.inflater = LayoutInflater.from(context);
        this.jokesJSON = jokesJSON;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.joke_list_item, parent, false);
        return new JokesRecycleViewAdapter.ViewHolder(view);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull JokesRecycleViewAdapter.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String s = jokesJSON.get(position).getValue();
        holder.categoryTextView.setText(s);
        setAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return jokesJSON.size();
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
