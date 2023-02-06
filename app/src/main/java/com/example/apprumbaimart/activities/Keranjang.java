package com.example.apprumbaimart.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprumbaimart.Bayar;
import com.example.apprumbaimart.MainActivity;
import com.example.apprumbaimart.R;
import com.example.apprumbaimart.adapter.MainAdapter;
import com.example.apprumbaimart.model.ModelBarang;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class Keranjang extends AppCompatActivity implements MainAdapter.FirebaseDataListener {

    private ExtendedFloatingActionButton mFloatingActionButton;
    private TextView mEditNama;
    private EditText mEditJumlah;
    private EditText mEditHarga;
    private RecyclerView mRecyclerView;
    private MainAdapter mAdapter;
    private Button mBtnBayar;
    private ArrayList<ModelBarang> daftarBarang;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mDatabaseReferences;
    private DatabaseReference mDatabaseReferencess;
    private FirebaseDatabase mFirebaseInstance;
    private TextView mTotal;
    private TextView mEmail;
    private TextView mPesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_keranjang);

        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseApp.initializeApp(this);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseInstance.getReference("barang");
        mDatabaseReference.child("data_barang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                daftarBarang = new ArrayList<>();
                for (DataSnapshot mDataSnapshot : dataSnapshot.getChildren()) {
                    ModelBarang barang = mDataSnapshot.getValue(ModelBarang.class);
                    barang.setKey(mDataSnapshot.getKey());
                    daftarBarang.add(barang);
                }

                mAdapter = new MainAdapter(Keranjang.this, daftarBarang);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(Keranjang.this,databaseError.getDetails() + " " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Bundle email = getIntent().getExtras();
        String value = email.getString("email");

        mEmail = (TextView)findViewById(R.id.emailss);
        mEmail.setText(String.valueOf(value));

        mPesan = (TextView)findViewById(R.id.txt_test);
        mPesan.setText(String.valueOf(value));

        mDatabaseReferencess = FirebaseDatabase.getInstance().getReference().child("barang").child("data_barang");
        mDatabaseReferencess.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 1;
                String all = "";
                for(DataSnapshot data: dataSnapshot.getChildren()) {
                    String value1 = data.child("nama").getValue(String.class);
                    String value2 = data.child("jumlah").getValue(String.class);
                    String value3 = data.child("harga").getValue(String.class);

                    String pesan = "Data : \n- "+value1+"\n- "+value2+"\n - "+value3+"\n\n || ";

                    all = all.concat(pesan);

                    mPesan.setText(all.toString());
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mBtnBayar = findViewById(R.id.btn_bayar);
        mBtnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Intent launchActivity1 = new Intent(Keranjang.this, Bayar.class);
                launchActivity1.putExtra("email",mEmail.getText());
                launchActivity1.putExtra("totals",mTotal.getText());
                launchActivity1.putExtra("pesans",mPesan.getText());
                startActivity(launchActivity1);
            }
        });

        mTotal = (TextView)findViewById(R.id.total_harga);
        mDatabaseReferences = FirebaseDatabase.getInstance().getReference().child("barang").child("data_barang");
        mDatabaseReferences.addValueEventListener(new ValueEventListener() {
             @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 int sum = 0;
                 for(DataSnapshot data: dataSnapshot.getChildren()) {
                    String value = data.child("harga").getValue(String.class);
                    int total = Integer.parseInt(value);

                    sum = sum + total;

                    mTotal.setText(String.valueOf(sum));
                 }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void onDataClick(final ModelBarang barang, int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Aksi");

        builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialogUpdateBarang(barang);
            }
        });

        builder.setNegativeButton("HAPUS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                hapusDataBarang(barang);
            }
        });

        builder.setNeutralButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void dialogUpdateBarang(final ModelBarang barang) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Data ModelBarang");
        View view = getLayoutInflater().inflate(R.layout.layout_edit_barang, null);
        View view2 = getLayoutInflater().inflate(R.layout.layout_keranjang, null);

        mEditNama = view.findViewById(R.id.nama_barang);
        mEditJumlah = view.findViewById(R.id.jumlah_barang);
        mEditHarga = view.findViewById(R.id.harga_barang);

        mEditNama.setText(barang.getNama());
        mEditJumlah.setText(barang.getJumlah());
        mEditHarga.setText(barang.getHarga());

        String jumlahBarang = mEditJumlah.getText().toString();
        String hargaBarang = mEditHarga.getText().toString();

        Integer jumlahAwal = Integer.parseInt(jumlahBarang);
        Integer hargaAwal = Integer.parseInt(hargaBarang);

        Integer hargaSatuan = hargaAwal / jumlahAwal;

        builder.setView(view);

        if (barang != null) {
            builder.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    barang.setNama(mEditNama.getText().toString());
                    barang.setJumlah(mEditJumlah.getText().toString());

                    String jumlahBarangs = mEditJumlah.getText().toString();
                    Integer jumlahAwal = Integer.parseInt(jumlahBarangs);
                    Integer totalHarga = jumlahAwal * hargaSatuan;
                    mEditHarga.setText(totalHarga.toString());
                    barang.setHarga(mEditHarga.getText().toString());
                    updateDataBarang(barang);
                }
            });
        }

        builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();

    }

    private void updateDataBarang(ModelBarang barang) {
        mDatabaseReference.child("data_barang").child(barang.getKey())
                .setValue(barang).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void mVoid) {
                Toast.makeText(Keranjang.this, "Data berhasil di update !", Toast.LENGTH_LONG).show();
            }
        });
        //fungsi matematika
    }

    private void hapusDataBarang(ModelBarang barang) {
        if (mDatabaseReference != null) {
            mDatabaseReference.child("data_barang").child(barang.getKey())
                    .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void mVoid) {
                    Toast.makeText(Keranjang.this, "Data berhasil di hapus !", Toast.LENGTH_LONG).show();
                }
            });

            mTotal = (TextView)findViewById(R.id.total_harga);
            mDatabaseReferences = FirebaseDatabase.getInstance().getReference().child("barang").child("data_barang");
            mDatabaseReferences.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int sum = 0;
                    for(DataSnapshot data: dataSnapshot.getChildren()) {
                        String value = data.child("harga").getValue(String.class);
                        int total = Integer.parseInt(value);

                        sum = sum + total;

                        mTotal.setText(String.valueOf(sum));
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
