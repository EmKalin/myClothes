package com.example.mytesting;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdaptCreations extends RecyclerView.Adapter<AdaptCreations.CreationsViewHolder> {

    private Context cContext;
    private List<MyCreations> myCreations;

    public AdaptCreations(Context context, List<MyCreations> creations){
        myCreations = creations;
        cContext = context;
    }

    @NonNull
    @Override
    public CreationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(cContext).inflate(R.layout.imagecreation_item, parent, false);
        return new CreationsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CreationsViewHolder holder, int position) {
        MyCreations currentCreation = myCreations.get(position);
        holder.textVName.setText(currentCreation.getName());
        holder.textVDate.setText("Date: "+currentCreation.getDate());

        Picasso.with(cContext).load(currentCreation.getFirstUrl()).fit().into(holder.viewFirst);
        Picasso.with(cContext).load(currentCreation.getSecondUrl()).fit().into(holder.viewSecond);
    }

    @Override
    public int getItemCount() {
        return myCreations.size();
    }


    public class CreationsViewHolder extends RecyclerView.ViewHolder{
        public TextView textVName;
        public TextView textVDate;
        public ImageView viewFirst;
        public ImageView viewSecond;

        public CreationsViewHolder(View itemVie){
            super(itemVie);

            textVName = itemVie.findViewById(R.id.text_view_creationName);
            textVDate = itemVie.findViewById(R.id.textView_date);
            viewFirst = itemVie.findViewById(R.id.image_view_CreationFirst);
            viewSecond = itemVie.findViewById(R.id.image_view_CreationSecond);
        }
    }


}
