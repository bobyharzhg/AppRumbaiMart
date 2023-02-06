package com.example.apprumbaimart.model

class ModelPemesanan {

    var key: String? = null
    var email: String? = null
    var metode: String? = null
    var pesanan: String? = null
    var total: String? = null
    var alamat: String? = null

    constructor() {}

    constructor(emailPesan: String?, metodePesan: String?, pesananPesan: String?, totalPesan: String?, alamatPesan: String?) {
        email = emailPesan
        metode = metodePesan
        pesanan = pesananPesan
        total = totalPesan
        alamat = alamatPesan
    }
}