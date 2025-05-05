package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText editTXTBaslik, editTXTIcerik;
    Button btnKaydet, btnGoruntule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        db = new DatabaseHelper(this);
        editTXTBaslik = findViewById(R.id.editTXTBaslik);
        editTXTIcerik = findViewById(R.id.editTXTIcerik);
        btnKaydet = findViewById(R.id.btnKaydet);
        btnGoruntule = findViewById(R.id.btnGoruntule);


        btnKaydet.setOnClickListener(v -> {
            String baslik = editTXTBaslik.getText().toString().trim();
            String icerik = editTXTIcerik.getText().toString().trim();

            if (baslik.isEmpty() || icerik.isEmpty()) {
                Toast.makeText(this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean kaydedildi = db.veriEkle(baslik, icerik);
            if (kaydedildi) {
                Toast.makeText(this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                editTXTBaslik.setText("");
                editTXTIcerik.setText("");
            } else {
                Toast.makeText(this, "Kayıt Başarısız", Toast.LENGTH_SHORT).show();
            }
        });

        btnGoruntule.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GoruntulemeActivity.class);
            startActivity(intent);
        });
    }
}
