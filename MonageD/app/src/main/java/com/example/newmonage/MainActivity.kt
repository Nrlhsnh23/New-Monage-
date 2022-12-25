package com.example.newmonage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newmonage.api.MonageApi
import com.example.newmonage.api.RetrofitHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var transactions: ArrayList<Transaksi>
    lateinit var AddBtn: FloatingActionButton
    lateinit var listTransaksi: RecyclerView

    //
    val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImljYXBwaW1nY2FzZ3hjeGd0aGJpIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NzA1NzUxOTYsImV4cCI6MTk4NjE1MTE5Nn0.XxDLVw5GRojK4emEVUuTMmJt6RaXQzJoy5DLMoXH7Bw"
    val token = "Bearer $apiKey"

    val Items = ArrayList<Transaksi>()
    val MonageApi = RetrofitHelper.getInstance().create(MonageApi::class.java)
    //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AddBtn = findViewById(R.id.Btn)
        listTransaksi = findViewById(R.id.recyclerview)

        //ini pak
        CoroutineScope(Dispatchers.Main).launch {
            val response = MonageApi.get(token=token, apiKey=apiKey)

            response.body()?.forEach {
                Items.add(
                    Transaksi(
                        id=it.id,
                        tanggal=it.tanggal,
                        label=it.label,
                        jumlah=it.jumlah,
                    )
                )
            }

            setList(Items)
        }
        updateDashboard()

        AddBtn.setOnClickListener{
            val intent = Intent(this, AddTransaksi::class.java)
            startActivity(intent)
        }
    }
    fun setList(Items: ArrayList<Transaksi>) {
        val adapter = AdapterTransaksi(Items)
        listTransaksi.adapter = adapter
    }

         fun updateDashboard(){

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


    // sampe sini
}


