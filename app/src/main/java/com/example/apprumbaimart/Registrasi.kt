package com.example.apprumbaimart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern
import com.example.apprumbaimart.databinding.LayoutRegistrasiBinding

class Registrasi : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: LayoutRegistrasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LayoutRegistrasiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.btnRegistrasi.setOnClickListener {

            val mUsername = binding.edtUname.text.toString()
            val mPassword = binding.edtPass.text.toString()

            val passwordRegex = Pattern.compile("^" +
                    ".{6,}" +                // Minimal 6 karakter
                    "$")

            if(mUsername.isEmpty()) {
                Toast.makeText(this, "Isi Email Anda Dengan Benar!",
                    Toast.LENGTH_SHORT).show()
            } else if (mPassword.isEmpty()){
                Toast.makeText(this, "Isi Password Anda Dengan Benar!",
                    Toast.LENGTH_SHORT).show()
            }else {
                createAccount(mUsername, mPassword)
            }
        }

        binding.gtLogin.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            this.startActivity(intent)
        }

    }

//    public override fun onStart() {
//        super.onStart()
//        val currentUser = auth.currentUser
//        if(currentUser != null){
//            if(currentUser.isEmailVerified){
//                val intent = Intent(this, Login::class.java)
//                this.startActivity(intent)
//            } else {
//                val intent = Intent(this, Registrasi::class.java)
//                this.startActivity(intent)
//            }
//        }
//    }

    private fun createAccount(mUsername: String, mPassword: String) {
        auth.createUserWithEmailAndPassword(mUsername, mPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, Login::class.java)
                    this.startActivity(intent)
                } else {
                    Toast.makeText(this, "Tidak Dapat Membuat Akun. Password minimal 6 digit",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}