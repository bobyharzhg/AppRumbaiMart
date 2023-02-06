package com.example.apprumbaimart

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.apprumbaimart.databinding.LayoutListProdukBinding

class ProdukRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var items: List<ListObjProduk> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdukViewHolder {
        val binding = LayoutListProdukBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return ProdukViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is ProdukViewHolder -> {
                holder.bind(items.get(position))
                holder.klik.setOnClickListener {
                    holder.kalau_diklik(items.get(position))
                }
            }
        }
    }

    fun submitList(listProduk: List<ListObjProduk>){
        items = listProduk
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ProdukViewHolder constructor(val binding: LayoutListProdukBinding)
        : RecyclerView.ViewHolder(binding.root){
        val foto_produk: ImageView = binding.gambarProduk
        val nama_produk: TextView = binding.namaProduk
        val harga_produk: TextView = binding.hargaProduk
        val klik: RelativeLayout = binding.rlKlik

        fun bind(ListObjProduk: ListObjProduk){
            nama_produk.setText(ListObjProduk.namaProduk)
            harga_produk.setText(ListObjProduk.harga)

            val requestOp = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOp)
                .load(ListObjProduk.gambar)
                .into(foto_produk)
        }

        fun kalau_diklik(get: ListObjProduk){
            Toast.makeText(itemView.context, "Kamu memilih : ${get.namaProduk}",
                Toast.LENGTH_SHORT)
                .show()
            val intent = Intent(itemView.context, DetilProduk::class.java)
            val email = intent.getStringExtra("email")

            intent.putExtra("namanya", get.namaProduk)
            intent.putExtra("harganya", get.harga)
            intent.putExtra("fotonya", get.gambar)
            intent.putExtra("email",email)
            itemView.context.startActivity(intent)
        }
    }
}