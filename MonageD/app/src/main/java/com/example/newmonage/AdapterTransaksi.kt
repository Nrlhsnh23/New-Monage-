package com.example.newmonage

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat

class AdapterTransaksi(private val transactions: ArrayList<Transaksi>) : RecyclerView.Adapter<AdapterTransaksi.TransactionHolder>() {

    class TransactionHolder(view: View) : RecyclerView.ViewHolder(view){

        val tanggal : TextView = view.findViewById(R.id.tanggal)
        val label : TextView = view.findViewById(R.id.label)
        val jumlah : TextView = view.findViewById(R.id.jumlah)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaksi_layout, parent, false)
        return TransactionHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {
        val transaksi = transactions[position]
        val context = holder.jumlah.context

        if (transaksi.jumlah >= 0) {
            holder.jumlah.text = "+ Rp%.2f".format(transaksi.jumlah)
            holder.jumlah.setTextColor(ContextCompat.getColor(context, R.color.green))
        } else {
            holder.jumlah.text = "- Rp%.2f".format(Math.abs(transaksi.jumlah))
            holder.jumlah.setTextColor(ContextCompat.getColor(context, R.color.red))
        }
        holder.label.text = transaksi.label
        holder.tanggal.text = transaksi.tanggal
    }
    override fun getItemCount(): Int {
        return transactions.size
    }
}


