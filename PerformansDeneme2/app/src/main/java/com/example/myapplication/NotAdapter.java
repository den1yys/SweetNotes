package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotAdapter extends RecyclerView.Adapter<NotAdapter.NotViewHolder> {
    private List<Not> notListesi;
    private Context context;
    private DatabaseHelper db;

    public NotAdapter(Context context, List<Not> notListesi) {
        this.context = context;
        this.notListesi = notListesi;
        this.db = new DatabaseHelper(context);
    }

    @Override
    public NotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.not_item, parent, false);
        return new NotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotViewHolder holder, int position) {
        Not not = notListesi.get(position);
        holder.baslik.setText(not.getBaslik());
        holder.icerik.setText(not.getIcerik());
    }

    @Override
    public int getItemCount() {
        return notListesi.size();
    }

    public class NotViewHolder extends RecyclerView.ViewHolder {
        TextView baslik, icerik;

        public NotViewHolder(View itemView) {
            super(itemView);
            baslik = itemView.findViewById(R.id.baslik);
            icerik = itemView.findViewById(R.id.icerik);
        }
    }


    public void veriSil(int position) {
        Not silinenNot = notListesi.get(position);
        db.veriSil(String.valueOf(silinenNot.getId()));
        notListesi.remove(position);
        notifyItemRemoved(position);
    }
}


