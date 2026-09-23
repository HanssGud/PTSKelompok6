package com.example.ptskelompok6;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Layar kedua (Project 2 - User Interaction), dikembangkan untuk PTS:
 * - Menyapa pengguna dengan nama yang dikirim dari MainActivity.
 * - Memvalidasi input nomor telepon sebelum dikirim.
 * - Menampilkan hasil input di layar (bukan hanya Toast).
 * - Menghitung jumlah data yang sudah dikirim (fitur counter).
 * - Menampilkan daftar data yang telah dimasukkan dalam bentuk list yang dapat di-scroll.
 */
public class FormActivity extends AppCompatActivity {

    EditText editTextPhone;
    RadioButton radioRumah;
    RadioButton radioMobile;
    RadioButton radioKantor;
    TextView textGreeting;
    TextView textResult;
    TextView textCounter;
    LinearLayout containerListData;
    ScrollView scrollViewMain;
    TextView textHeaderList;

    int jumlahTerkirim = 0;
    List<String> listData = new ArrayList<>();

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
        containerListData = findViewById(R.id.containerListData);
        scrollViewMain = findViewById(R.id.main);
        textHeaderList = findViewById(R.id.textHeaderList);

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

        String itemStr = "Data ke-" + jumlahTerkirim + ": " + pilih + " - " + phone;
        listData.add(itemStr);

        textResult.setText("Data ke-" + jumlahTerkirim + " berhasil dikirim:\n"
                + pilih + " - " + phone);
        textCounter.setText("Jumlah data terkirim: " + jumlahTerkirim);

        // Tambahkan item baru ke container list di layar
        TextView itemView = new TextView(this);
        itemView.setText(itemStr);
        itemView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        itemView.setTextColor(Color.BLACK);
        itemView.setPadding(24, 20, 24, 20);
        itemView.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 8, 0, 8);
        itemView.setLayoutParams(params);

        containerListData.addView(itemView);

        Toast.makeText(this, pilih + ": " + phone, Toast.LENGTH_SHORT).show();
    }

    /** Dipanggil saat tombol "Lihat List Data" ditekan. */
    public void lihatListData(View view) {
        if (listData.isEmpty()) {
            Toast.makeText(this, "Belum ada data yang dimasukkan.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tampilkan dialog pop-up berisi list data yang bisa di-scroll
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                listData
        );

        new AlertDialog.Builder(this)
                .setTitle("List Data Terkirim (" + listData.size() + ")")
                .setAdapter(adapter, null)
                .setPositiveButton("Tutup", null)
                .show();

        // Scroll otomatis ke bagian list di halaman
        if (textHeaderList != null && scrollViewMain != null) {
            scrollViewMain.post(() -> scrollViewMain.smoothScrollTo(0, textHeaderList.getTop()));
        }
    }
}
