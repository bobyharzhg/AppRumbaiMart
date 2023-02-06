package com.example.apprumbaimart.model

class ModelBarang {

    var key: String? = null
    var nama: String? = null
    var jumlah: String? = null
    var harga: String? = null

    constructor() {}

    constructor(namaBarang: String?, jumlahBarang: String?, hargaBarang: String?) {
        nama = namaBarang
        jumlah = jumlahBarang
        harga = hargaBarang
    }
}