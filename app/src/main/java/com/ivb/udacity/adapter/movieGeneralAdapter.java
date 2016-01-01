package com.ivb.udacity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivb.udacity.R;
import com.ivb.udacity.modal.movieGeneralModal;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by S.Shivasurya on 1/1/2016 - androidStudio.
 */
public class movieGeneralAdapter extends RecyclerView.Adapter<movieGeneralHolder> {
    private List<movieGeneralModal> mMovieGeneralModal;
    private Context context;

    public movieGeneralAdapter(Context context, List<movieGeneralModal> itemList) {
        this.mMovieGeneralModal = itemList;
        this.context = context;
    }

    @Override
    public movieGeneralHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_cards, null);
        movieGeneralHolder rcv = new movieGeneralHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(movieGeneralHolder holder, int position) {
        holder.movieName.setText(mMovieGeneralModal.get(position).getTitle());
        holder.movieAvg.setText(mMovieGeneralModal.get(position).getmVote());
        //picasso loading here
        Picasso.with(context)
                .load(mMovieGeneralModal.get(position).getThumbnail())
                .into(holder.moviePhoto);
    }

    @Override
    public int getItemCount() {
        return this.mMovieGeneralModal.size();
    }
}


