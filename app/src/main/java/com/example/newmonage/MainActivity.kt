package com.example.newmonage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newmonage.api.MonageApi
import com.example.newmonage.api.RetrofitHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var deletedTransaction : Transaksi
    private var transactions: ArrayList<Transaksi> = arrayListOf()
    private var oldTransactions: ArrayList<Transaksi> = arrayListOf()
    lateinit var AddBtn: FloatingActionButton
    lateinit var listTransaksi: RecyclerView

    //
    val apiKey =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImljYXBwaW1nY2FzZ3hjeGd0aGJpIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NzA1NzUxOTYsImV4cCI6MTk4NjE1MTE5Nn0.XxDLVw5GRojK4emEVUuTMmJt6RaXQzJoy5DLMoXH7Bw"
    val token = "Bearer $apiKey"

    val MonageApi = RetrofitHelper.getInstance().create(MonageApi::class.java)
    //

    private var adapter : AdapterTransaksi = AdapterTransaksi{
        transactionOnPress(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AddBtn = findViewById(R.id.Btn)
        listTransaksi = findViewById(R.id.recyclerview)
        // need to add layoutManager to the recycleView
        listTransaksi.layoutManager = LinearLayoutManager(this)
        listTransaksi.adapter = adapter
        //

        //swipe to remove
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               deleteTransaction(transactions[viewHolder.adapterPosition])
            }

        }
        val swipeHelper = ItemTouchHelper(itemTouchHelper)
        swipeHelper.attachToRecyclerView(listTransaksi)

        AddBtn.setOnClickListener {
            val intent = Intent(this, AddTransaksi::class.java)
            startActivity(intent)
        }

    }

    fun setList() {
        adapter.addTransactions(transactions)
        adapter.notifyDataSetChanged()
    }

    fun getItem() {
        CoroutineScope(Dispatchers.Main).launch {
            val response = MonageApi.get(token = token, apiKey = apiKey)
            // clear list before adding so no data duplicated
            transactions.clear()
            response.body()?.forEach {
                transactions.add(
                    Transaksi(
                        id = it.id,
                        tanggal = it.tanggal,
                        label = it.label,
                        amount = it.amount,
                        description = it.description.toString())
                )
            }

            setList()
            updateDashboard()
        }
    }

    fun updateDashboard() {
        val saldo = findViewById<TextView>(R.id.saldo)
        val pemasukan = findViewById<TextView>(R.id.pemasukan)
        val pengeluaran = findViewById<TextView>(R.id.pengeluaran)

        val totalAmount = transactions.map { it.amount }.sum()
        val budgetAmount = transactions.filter { it.amount > 0 }.map { it.amount }.sum()
        val expenseAmount = totalAmount - budgetAmount

        saldo.text = "Rp %.2f".format(totalAmount)
        pemasukan.text = "Rp %.2f".format(budgetAmount)
        pengeluaran.text = "Rp %.2f".format(expenseAmount)
    }

    private fun transactionOnPress(transaction: Transaksi) {
        // create bundle to store the extra data
        val bundle = Bundle()
        // put the data as serializable
        bundle.putSerializable("transaksi", transaction)
        val intent = Intent(this, UpdateTransaksi::class.java)
        // put bundle to the intent as extras
        intent.putExtras(bundle)
        startActivity(intent)
        getItem()
    }

    private fun deleteTransaction(transaction: Transaksi){
        deletedTransaction = transaction
        oldTransactions = transactions

        CoroutineScope(Dispatchers.Main).launch{
            try {
                // use deletedTransaction's id as the id
                MonageApi.delete(token = token, apiKey = apiKey, idQuery = "eq.${deletedTransaction.id}" )
                Toast.makeText(
                    applicationContext,
                    "Berhasil menghapus transaksi",
                    Toast.LENGTH_SHORT
                ).show()
                getItem()
            } catch (e: Exception) {
                Toast.makeText(
                    applicationContext,
                    "Gagal menghapus transaksi",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }



    override fun onResume() {
        super.onResume()
        getItem()
    }

}






