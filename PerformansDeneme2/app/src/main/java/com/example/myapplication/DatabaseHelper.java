package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Veritabanı ve tablo bilgileri
    public static final String DATABASE_NAME = "NotDefteri.db";
    public static final String TABLE_NAME = "notlar";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "BASLIK";
    public static final String COL_3 = "ICERIK";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    // Veritabanı oluşturulunca çağrılır
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                "(" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT, " +
                COL_3 + " TEXT)");
    }

    // Versiyon güncellendiğinde tabloyu sıfırla
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Veri ekleme
    public boolean veriEkle(String baslik, String icerik) {
        if (baslik == null || icerik == null || baslik.isEmpty() || icerik.isEmpty())
            return false;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2, baslik);
        cv.put(COL_3, icerik);
        long sonuc = db.insert(TABLE_NAME, null, cv);
        return sonuc != -1;
    }

    // Tüm verileri çek
    public Cursor verileriAl() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    // Veri silme
    public boolean veriSil(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int satirSayisi = db.delete(TABLE_NAME, "ID = ?", new String[]{id});
        return satirSayisi > 0;
    }

    // Veri güncelleme (Quizde çıkabilir!)
    public boolean veriGuncelle(String id, String yeniBaslik, String yeniIcerik) {
        if (id == null || yeniBaslik == null || yeniIcerik == null)
            return false;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2, yeniBaslik);
        cv.put(COL_3, yeniIcerik);

        int satirSayisi = db.update(TABLE_NAME, cv, "ID = ?", new String[]{id});
        return satirSayisi > 0;
    }
}
