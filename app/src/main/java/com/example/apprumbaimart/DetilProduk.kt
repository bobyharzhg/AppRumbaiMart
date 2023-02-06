package com.example.apprumbaimart

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.apprumbaimart.databinding.DetilProdukBinding
import com.example.apprumbaimart.databinding.LayoutKeranjangBinding
import com.example.apprumbaimart.model.ModelBarang
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.content.Intent
import com.example.apprumbaimart.activities.Keranjang

class DetilProduk : AppCompatActivity() {
    private lateinit var binding: DetilProdukBinding
    private lateinit var bindingss: LayoutKeranjangBinding
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mNama: TextView
    private lateinit var mHarga: TextView
    private lateinit var mJumlah: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetilProdukBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)
        if (intent.hasExtra("namanya")){
            val nama: String = this.intent.getStringExtra("namanya").toString()
            val foto: String = this.intent.getStringExtra("fotonya").toString()
            val harga: String = this.intent.getStringExtra("harganya").toString()
            setDetil(foto,nama,harga)
        }

        mNama = findViewById(R.id.nama_detil_produk)
        mHarga = findViewById(R.id.harga_detil_produk)
        mJumlah = findViewById(R.id.edt_pesan)

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("barang")

        val email = intent.getStringExtra("email")

        binding.btnPesan.setOnClickListener{
//            if (email.equals(null)) {
//                val intent = Intent(this, Login::class.java)
//                intent.putExtra("email",email)
//                this.startActivity(intent)
//            } else {
                 submitDataBarang()
//            }
        }
    }

    private fun submitDataBarang() {
        val mNama = mNama.text.toString()
        val mJumlah = mJumlah.text.toString()
        val mHarga = mHarga.text.toString()
        val total = mJumlah.toInt() * mHarga.toInt()

        val email = intent.getStringExtra("email")

        val barang = ModelBarang(mNama, mJumlah, total.toString())
        mDatabaseReference.child("data_barang").push().setValue(barang)
            .addOnCompleteListener{
                Toast.makeText(this,"Produk berhasil diinputkan ke keranjang", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Keranjang::class.java)
                intent.putExtra("email",email)
                this.startActivity(intent)
            }.addOnFailureListener{ err ->
                Toast.makeText(this,"Produk gagal diinputkan ke keranjang", Toast.LENGTH_SHORT).show()
            }
    }

    fun setDetil(foto: String, nama: String, harga: String){
        val requestOp = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        binding.namaDetilProduk.text = nama
        Glide.with(this)
            .load(foto)
            .apply(requestOp)
            .centerCrop()
            .into(binding.fotoDetil)
        binding.hargaDetilProduk.text = harga
    }
}