package com.example.apprumbaimart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
//import com.amulyakhare.textdrawable.util.ColorGenerator
import com.example.apprumbaimart.R
import com.example.apprumbaimart.holder.MainViewHolder
import com.example.apprumbaimart.model.ModelBarang
import java.util.*

class MainAdapter(private val context: Context,
                  private val daftarBarang: ArrayList<ModelBarang?>?) : RecyclerView.Adapter<MainViewHolder>() {
    private val listener: FirebaseDataListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_barang, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.namaBarang.text = "Nama   : " + (daftarBarang?.get(position)?.nama)
        holder.jumlahBarang.text = "Merk     : " + daftarBarang?.get(position)?.jumlah
        holder.hargaBarang.text = "Harga   : " + daftarBarang?.get(position)?.harga
        holder.view.setOnClickListener { listener.onDataClick(daftarBarang?.get(position), position) }
    }

    override fun getItemCount(): Int {

        return daftarBarang?.size!!
    }

    //interface data listener
    interface FirebaseDataListener {
        fun onDataClick(barang: ModelBarang?, position: Int)
    }

    init {
        listener = context as FirebaseDataListener
    }
}