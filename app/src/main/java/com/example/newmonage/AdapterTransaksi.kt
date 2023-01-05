package com.example.newmonage


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class AdapterTransaksi(val onPress: (transaction: Transaksi) -> Unit) : RecyclerView.Adapter<AdapterTransaksi.ViewHolder>() {
    private var transactions = arrayListOf<Transaksi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaksi_layout, parent, false)
        return ViewHolder(view)
    }

    // need to append the data to the transactions
    fun addTransactions(data: ArrayList<Transaksi>) {
        // clear list before adding so no data duplicated
        transactions.clear()
        transactions.addAll(data)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaksi = transactions[position]
        val context = holder.jumlah.context


        holder.setupView(transaksi.tanggal, transaksi.label, transaksi.amount)
        holder.itemView.setOnClickListener { _ ->
            onPress(transaksi)
        }

        if (transaksi.amount >= 0) {
            holder.jumlah.text = "+ Rp. %.0f".format(transaksi.amount)
            holder.jumlah.setTextColor(ContextCompat.getColor(context, R.color.green))
        } else {
            holder.jumlah.text = "- Rp. %.0f".format(Math.abs(transaksi.amount))
            holder.jumlah.setTextColor(ContextCompat.getColor(context, R.color.red))
        }
        holder.label.text = transaksi.label
        holder.tanggal.text = transaksi.tanggal


    }
    override fun getItemCount(): Int {
        return transactions.size
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tanggal : TextView
        val label : TextView
        val jumlah : TextView

        init {
            tanggal = view.findViewById(R.id.tanggal)
            label = view.findViewById(R.id.label)
            jumlah = view.findViewById(R.id.jumlah)

        }

        fun setupView(tanggalData: String, labelData: String, jumlahData: Double ) {
            tanggal.text = tanggalData
            label.text = labelData
            jumlah.text = jumlahData.toString()


        }
    }

}


