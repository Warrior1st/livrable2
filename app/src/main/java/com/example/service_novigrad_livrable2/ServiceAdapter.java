package com.example.service_novigrad_livrable2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    // creating variables for our list, context, interface and position.
    private ArrayList<ServiceModal> serviceModalArrayList;
    private Context context;
    private ServiceClickInterface serviceClickInterface;
    int lastPos = -1;

    // creating a constructor.
    public ServiceAdapter(ArrayList<ServiceModal> serviceModalArrayList, Context context, ServiceClickInterface serviceClickInterface) {
        this.serviceModalArrayList = serviceModalArrayList;
        this.context = context;
        this.serviceClickInterface = serviceClickInterface;
    }

    @NonNull
    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.service_item, parent, false);
        return new ViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // setting data to our recycler view item on below line.

        ServiceModal serviceModal = serviceModalArrayList.get(holder.getAdapterPosition());
        holder.serviceTV.setText(serviceModal.getServiceName());
        holder.servicePriceTV.setText("Rs. " + serviceModal.getServicePrice());
        // adding animation to recycler view item on below line.
        setAnimation(holder.itemView, holder.getAdapterPosition());

    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPos) {
            // on below line we are setting animation.
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return serviceModalArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variable for our image view and text view on below line.

        private TextView serviceTV, servicePriceTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing all our variables on below line.
            serviceTV = itemView.findViewById(R.id.idTVCOurseName);
            servicePriceTV = itemView.findViewById(R.id.idTVCousePrice);
        }
    }

    // creating a interface for on click
    public interface ServiceClickInterface {
        void onServiceClick(int position);
    }
}