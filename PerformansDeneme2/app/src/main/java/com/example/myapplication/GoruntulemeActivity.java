package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GoruntulemeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    NotAdapter notAdapter;
    DatabaseHelper db;
    ArrayList<Not> notList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goruntuleme);

        recyclerView = findViewById(R.id.recyclerView);
        db = new DatabaseHelper(this);
        notList = new ArrayList<>();
        veriYukle();


        notAdapter = new NotAdapter(this, notList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(notAdapter);


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Not silinecekNot = notList.get(position);

                boolean silindi = db.veriSil(String.valueOf(silinecekNot.getId()));
                if (silindi) {
                    notList.remove(position);
                    notAdapter.notifyItemRemoved(position);
                    Toast.makeText(GoruntulemeActivity.this, "Not silindi", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(GoruntulemeActivity.this, "Silme başarısız", Toast.LENGTH_SHORT).show();
                    notAdapter.notifyItemChanged(position);
                }
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void veriYukle() {
        Cursor res = db.verileriAl();
        if (res != null) {
            while (res.moveToNext()) {
                int id = res.getInt(0);
                String baslik = res.getString(1);
                String icerik = res.getString(2);
                notList.add(new Not(id, baslik, icerik));
            }
            res.close();
        }
    }
}
