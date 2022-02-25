package com.example.bugbountyhelper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PortRCAdapter extends RecyclerView.Adapter <PortRCAdapter.ViewHolder> {
    Context context;
    ArrayList<PortModel> PMlist;
    PortRCAdapter(Context context, ArrayList<PortModel> PMlist){
        this.context=context;
        this.PMlist=PMlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.port_model_row,parent,false);
        ViewHolder viewHold =new ViewHolder(v);

        return viewHold;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.PortData.setText(PMlist.get(position).PortDetails);
        holder.PortNo.setText(PMlist.get(position).port_no);
        Log.d("TEST=>",PMlist.get(position).IsOpen);
        holder.Banner.setText(PMlist.get(position).Banner);
        holder.Stats.setImageResource(PMlist.get(position).D);

    }

    @Override
    public int getItemCount() {
        return PMlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView Stats;
        TextView PortNo,PortData,Banner;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Stats= itemView.findViewById(R.id.status);
            PortNo= itemView.findViewById(R.id.PortNo);
            Banner=itemView.findViewById(R.id.portBanner);
            PortData= itemView.findViewById(R.id.PortDetails);
        }
    }

}
