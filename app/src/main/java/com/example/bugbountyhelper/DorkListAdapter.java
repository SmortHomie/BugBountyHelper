package com.example.bugbountyhelper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DorkListAdapter extends RecyclerView.Adapter <DorkListAdapter.ViewHolder> {
        Context context;
        List<String> DorkList;
        DorkListAdapter(Context context, List<String> DorkList){
            this.context=context;
            this.DorkList=DorkList;
        }

        @NonNull
        @Override
        public DorkListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View v= LayoutInflater.from(context).inflate(R.layout.dork_link_model,parent,false);
            DorkListAdapter.ViewHolder viewHold =new DorkListAdapter.ViewHolder(v);

            return viewHold;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         holder.Link.setText(DorkList.get(position));
         holder.LinkButt.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Uri uri = Uri.parse(DorkList.get(position).toString());
                 context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
             }
         });

        }

        @Override
        public int getItemCount() {
            return DorkList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            ImageView LinkButt;
            TextView Link;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                Link=itemView.findViewById(R.id.Link);
                LinkButt=itemView.findViewById(R.id.Linkbutt);
            }
        }

    }
