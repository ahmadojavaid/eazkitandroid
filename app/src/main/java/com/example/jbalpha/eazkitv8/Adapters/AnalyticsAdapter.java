package com.example.jbalpha.eazkitv8.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jbalpha.eazkitv8.Models.AllSessionsModel;
import com.example.jbalpha.eazkitv8.Models.Utils;
import com.example.jbalpha.eazkitv8.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnalyticsAdapter extends RecyclerView.Adapter<AnalyticsAdapter.MyViewHolder> {

    private List<AllSessionsModel> ListData;
    private Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        ImageView image;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            image = (ImageView) view.findViewById(R.id.imageView);

        }
    }


    public AnalyticsAdapter(List<AllSessionsModel> ListData, Activity activity) {
        this.ListData = ListData;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_analytics, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AllSessionsModel movie = ListData.get(position);

        int pos=position+1;
        holder.title.setText("Session"+pos);


        Picasso.with(activity).load(Utils.BASE_URL_IMAGE + movie.getSessionImage()).fit().centerCrop().into(holder.image);

    }

    @Override
    public int getItemCount() {
        return ListData.size();
    }
}