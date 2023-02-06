package com.example.apprumbaimart.holder

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.apprumbaimart.R

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @JvmField
    var namaBarang: TextView

    @JvmField
    var jumlahBarang: TextView

    @JvmField
    var hargaBarang: TextView

    @JvmField
    var view: CardView

    init {
        namaBarang = itemView.findViewById(R.id.nama_barang)
        jumlahBarang = itemView.findViewById(R.id.jumlah_barang)
        hargaBarang = itemView.findViewById(R.id.harga_barang)
        view = itemView.findViewById(R.id.cvMain)
    }
}