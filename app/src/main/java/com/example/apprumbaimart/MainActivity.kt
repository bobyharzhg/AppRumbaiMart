package com.example.apprumbaimart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apprumbaimart.activities.Keranjang
import com.example.apprumbaimart.databinding.ActivityMainBinding
import com.example.apprumbaimart.databinding.LayoutSuksesBinding
import com.example.apprumbaimart.databinding.LoadBinding

class MainActivity : AppCompatActivity() {
    private lateinit var produkAdapter: ProdukRecyclerAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingload: LoadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        tambahDataSet()
        supportActionBar?.hide()

        val email = intent.getStringExtra("email")
        binding.emails.text = email

        binding.btnCart.setOnClickListener{
            if (email.equals(null)) {
                val intent = Intent(this, Login::class.java)
                this.startActivity(intent)
            } else {
                val intent = Intent(this, Keranjang::class.java)
                intent.putExtra("email",binding.emails.text)
                this.startActivity(intent)
            }
        }
    }

    private fun tambahDataSet(){
        val data = DataProduk.buatSetData()
        produkAdapter.submitList(data)
    }

    private fun initRecyclerView(){
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val spacingAtas = DekorasiSpasiGambar(20)
            addItemDecoration(spacingAtas)
            produkAdapter = ProdukRecyclerAdapter()
            adapter = produkAdapter
        }
    }
}