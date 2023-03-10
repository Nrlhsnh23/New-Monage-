package com.example.newmonage.api

data class MonageItem (
    val id: String,
    val tanggal: String,
    val label: String,
    // salah namanya, na. harusnya 'amount' bukan 'jumlah'
    val amount: Double,
    val description: String? = null,
    val done_at: String? = null,
    val created_at: String
)