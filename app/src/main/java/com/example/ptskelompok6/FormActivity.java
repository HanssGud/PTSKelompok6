package com.example.ptskelompok6;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Layar kedua (Project 2 - User Interaction), dikembangkan untuk PTS:
 * - Menyapa pengguna dengan nama yang dikirim dari MainActivity.
 * - Memvalidasi input nomor telepon sebelum dikirim.
 * - Menampilkan hasil input di layar (bukan hanya Toast).
 * - Menghitung jumlah data yang sudah dikirim (fitur counter,
 *   dikembangkan dari konsep counter pada Project 1).
 */
public class FormActivity extends AppCompatActivity {

    EditText editTextPhone;
    RadioButton radioRumah;
    RadioButton radioMobile;
    RadioButton radioKantor;
    TextView textGreeting;
    TextView textResult;
    TextView textCounter;

    int jumlahTerkirim = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextPhone = findViewById(R.id.editTextPhone);
        radioRumah = findViewById(R.id.radioRumah);
        radioMobile = findViewById(R.id.radioMobile);
        radioKantor = findViewById(R.id.radioKantor);
        textGreeting = findViewById(R.id.textGreeting);
        textResult = findViewById(R.id.textResult);
        textCounter = findViewById(R.id.textCounter);

        String nama = getIntent().getStringExtra(MainActivity.EXTRA_NAMA);
        if (TextUtils.isEmpty(nama)) {
            nama = "Kelompok Enam";
        }
        textGreeting.setText("Halo, " + nama + "! Silahkan lengkapi data kontak Anda.");
    }

    /** Dipanggil saat tombol "Kirim" ditekan. */
    public void showText(View view) {
        String phone = editTextPhone.getText().toString().trim();

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Nomor telepon wajib diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        String pilih;
        if (radioRumah.isChecked()) {
            pilih = "Telp Rumah";
        } else if (radioMobile.isChecked()) {
            pilih = "Mobile";
        } else if (radioKantor.isChecked()) {
            pilih = "Telp Kantor";
        } else {
            pilih = "Belum memilih jenis telepon";
        }

        jumlahTerkirim++;

        textResult.setText("Data ke-" + jumlahTerkirim + " berhasil dikirim:\n"
                + pilih + " - " + phone);
        textCounter.setText("Jumlah data terkirim: " + jumlahTerkirim);

        Toast.makeText(this, pilih + ": " + phone, Toast.LENGTH_SHORT).show();
    }
}
