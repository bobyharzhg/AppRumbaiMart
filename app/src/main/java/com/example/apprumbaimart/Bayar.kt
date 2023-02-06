package com.example.apprumbaimart

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.apprumbaimart.activities.Keranjang
import com.example.apprumbaimart.databinding.LayoutPembayaranBinding
import com.example.apprumbaimart.model.ModelPemesanan
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class Bayar : AppCompatActivity() {
    private lateinit var binding: LayoutPembayaranBinding
    lateinit var mDatabaseReference: DatabaseReference
    lateinit var mDatabaseReferences: DatabaseReference
    lateinit var mDatabaseReferencesss: DatabaseReference
    lateinit var mStorage: FirebaseStorage
    lateinit var ImageUri : Uri
    private lateinit var mEmail: TextView
    private lateinit var mPesan: TextView
    private lateinit var mTotal: TextView
    private lateinit var mAlamat: EditText
    var mMetode: RadioGroup? = null
    lateinit var radioButton: RadioButton
    private lateinit var mMetode2:TextView
    private lateinit var btnKirim : Button

    private lateinit var image: ImageView
    private lateinit var uri: Uri
    private var storageRef = Firebase.storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutPembayaranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = intent.getStringExtra("email")
        val totals = intent.getStringExtra("totals")
        val pesans = intent.getStringExtra("pesans")

        binding.totals.text = totals
        binding.txtTest.text = pesans
        binding.emails.text = email

        image = findViewById(R.id.firebaseImage)


        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                image.setImageURI(it)
                if (it != null) {
                    uri = it
                }
            }
        )

        binding.btnBukti.setOnClickListener{
            getImage.launch("image/*")
        }

        storageRef = FirebaseStorage.getInstance()
        btnKirim = findViewById(R.id.btn_kirim)

        binding.btnKirim.setOnClickListener{
            if (email.equals(null)) {
                val intent = Intent(this, Login::class.java)
                this.startActivity(intent)
            } else {
                mDatabaseReference = FirebaseDatabase.getInstance().getReference("barang")
                mDatabaseReference.child("data_barang").removeValue()
                submitDataBarang()

                storageRef.getReference("images").child(System.currentTimeMillis().toString())
                    .putFile(uri)
            }
        }

        binding.btnCart.setOnClickListener{
            if (email.equals(null)) {
                val intent = Intent(this, Login::class.java)
                this.startActivity(intent)
            } else {
                val intent = Intent(this, Keranjang::class.java)
                intent.putExtra("email",email)
                this.startActivity(intent)
            }
        }

        mEmail = findViewById(R.id.emails)
        mMetode = findViewById(R.id.radio_group)
        mPesan = findViewById(R.id.txt_test)
        mTotal = findViewById(R.id.totals)
        mAlamat = findViewById(R.id.edt_alamat)
        mMetode2 = findViewById(R.id.metode2)
    }

    private fun submitDataBarang() {
        val selectedOption: Int = mMetode!!.checkedRadioButtonId
        radioButton = findViewById(selectedOption)
        mMetode2.text = radioButton.text

        val mEmail = mEmail.text.toString()
        val mMetode = mMetode2.text.toString()
        val mPesan = mPesan.text.toString()
        val mTotal = mTotal.text.toString()
        val mAlamat = mAlamat.text.toString()

        val email = intent.getStringExtra("email")

        val pesanan = ModelPemesanan(mEmail, mMetode, mPesan, mTotal, mAlamat)
        mDatabaseReferences = FirebaseDatabase.getInstance().getReference("pemesanan")
        mDatabaseReferences.child("data_pemesanan").push().setValue(pesanan)
            .addOnCompleteListener{
                Toast.makeText(this,"Pesanan berhasil diinputkan ke database", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Sukses::class.java)
                intent.putExtra("email",email)
                this.startActivity(intent)
            }.addOnFailureListener{ err ->
                Toast.makeText(this,"Pesanan gagal diinputkan ke database", Toast.LENGTH_SHORT).show()
            }
    }
}