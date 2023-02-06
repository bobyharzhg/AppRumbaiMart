package com.example.apprumbaimart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.example.apprumbaimart.activities.Keranjang
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.apprumbaimart.databinding.LayoutLoginBinding
import com.example.apprumbaimart.databinding.LoadBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: LayoutLoginBinding
    private lateinit var bindingload: LoadBinding
    lateinit var mDatabaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingload = LoadBinding.inflate(layoutInflater)
        setContentView(bindingload.root)

        Handler().postDelayed({
        binding = LayoutLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.btnLogin.setOnClickListener {
            val mUsername = binding.edtUname.text.toString()
            val mPassword = binding.edtPass.text.toString()

            when {
                mPassword.isEmpty() || mUsername.isEmpty() -> {
                    Toast.makeText(this, "Inputan salah",
                        Toast.LENGTH_SHORT).show()
                }
                else -> {
                    signIn(mUsername, mPassword)
                }
            }
        }

        binding.gtRegistrasi.setOnClickListener {
            val intent = Intent(this, Registrasi::class.java)
            this.startActivity(intent)
        }
        }, 3000)
    }

        private fun signIn(email: String, password: String) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("TAG", "signInWithEmail:success")
                        reload(email)
                    } else {
                        Log.w("TAG", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Login gagal, silahkan coba lagi",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        private fun reload(email: String) {
            mDatabaseReference = FirebaseDatabase.getInstance().getReference("barang")
            mDatabaseReference.child("data_barang").removeValue()

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("email",email)
            this.startActivity(intent)
        }
}