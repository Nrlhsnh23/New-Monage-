package com.example.newmonage

import android.content.Intent
import android.database.sqlite.SQLiteTransactionListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var transactions: ArrayList<Transaksi>
    private lateinit var transaksiAdapter: AdapterTransaksi
    private lateinit var linearlayoutManager: LinearLayoutManager
    lateinit var AddBtn: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AddBtn = findViewById(R.id.Btn)

        transactions = arrayListOf(
            Transaksi("10 September", "makan", 400.00),
            Transaksi("11 September", "makan", 500.00),
            Transaksi("12 September", "makan", -200.00),
            Transaksi("13 September", "makan", 200.00)
        )
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        transaksiAdapter = AdapterTransaksi(transactions)
        linearlayoutManager = LinearLayoutManager(this)

        recyclerview.apply {
            adapter = transaksiAdapter
            layoutManager = linearlayoutManager
        }

        updateDashboard()

        AddBtn.setOnClickListener{
            val intent = Intent(this, AddTransaksi::class.java)
            startActivity(intent)
        }
    }
    private fun updateDashboard(){

        val saldo = findViewById<TextView>(R.id.saldo)
        val pemasukan= findViewById<TextView>(R.id.pemasukan)
        val pengeluaran = findViewById<TextView>(R.id.pengeluaran)

        val totalAmount = transactions.map { it.jumlah }.sum()
        val budgetAmount = transactions.filter { it.jumlah>0 }.map { it.jumlah }.sum()
        val expenseAmount = totalAmount-budgetAmount

        saldo.text = "Rp %.2f".format(totalAmount)
        pemasukan.text = "Rp %.2f".format(budgetAmount)
        pengeluaran.text = "Rp %.2f".format(expenseAmount)
    }
}