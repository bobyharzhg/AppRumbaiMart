package com.example.apprumbaimart

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.apprumbaimart.activities.Keranjang
import com.example.apprumbaimart.databinding.ActivityMainBinding
import com.example.apprumbaimart.databinding.LayoutSuksesBinding
import com.example.apprumbaimart.model.ModelBarang


class Sukses : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bindings: LayoutSuksesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val email = intent.getStringExtra("email")

            bindings = LayoutSuksesBinding.inflate(layoutInflater)
            setContentView(bindings.root)

            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("email",email)
                this.startActivity(intent)
                supportActionBar?.hide()
            }, 3000)
    }
}