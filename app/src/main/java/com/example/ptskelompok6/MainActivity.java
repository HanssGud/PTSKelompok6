package com.example.ptskelompok6;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Layar pertama (Project 1 - User Interface).
 * Menampilkan judul kelompok dan form input nama sebelum
 * berpindah ke layar interaksi (FormActivity).
 */
public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_NAMA = "extra_nama";

    EditText editTextNama;

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

        editTextNama = findViewById(R.id.editTextNama);
    }

    /** Dipanggil saat tombol "Lanjut" ditekan. */
    public void lanjutkan(View view) {
        String nama = editTextNama.getText().toString().trim();
        if (TextUtils.isEmpty(nama)) {
            nama = "Kelompok Enam";
        }

        Toast.makeText(this, "Selamat datang, " + nama + "!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, FormActivity.class);
        intent.putExtra(EXTRA_NAMA, nama);
        startActivity(intent);
    }
}
