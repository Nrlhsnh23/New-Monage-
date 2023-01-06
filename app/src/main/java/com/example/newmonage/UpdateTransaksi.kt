package com.example.newmonage

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.newmonage.api.MonageData
import com.example.newmonage.api.RetrofitHelper
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class UpdateTransaksi : AppCompatActivity() {
    lateinit var btnUpdate: Button
    lateinit var labelInput: TextInputEditText
    lateinit var labelLayout: TextInputLayout
    lateinit var amountInput: TextInputEditText
    lateinit var amountLayout: TextInputLayout
    lateinit var tanggalInput: TextInputEditText
    lateinit var tanggalLayout: TextInputLayout
    lateinit var descriptionInput: TextInputEditText
    lateinit var descriptionLayout: TextInputLayout
    lateinit var closeBtn: ImageButton

    private lateinit var transaksi: Transaksi

    //    lateinit var id : String
    val apiKey =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImljYXBwaW1nY2FzZ3hjeGd0aGJpIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NzA1NzUxOTYsImV4cCI6MTk4NjE1MTE5Nn0.XxDLVw5GRojK4emEVUuTMmJt6RaXQzJoy5DLMoXH7Bw"
    val token = "Bearer $apiKey"

    val MonageApi =
        RetrofitHelper.getInstance().create(com.example.newmonage.api.MonageApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_transaksi)

        btnUpdate = findViewById(R.id.upTransactionBtn)
        labelInput = findViewById(R.id.labelInput)
        labelLayout = findViewById(R.id.labelLayout)
        amountInput = findViewById(R.id.amountInput)
        amountLayout = findViewById(R.id.amountLayout)
        tanggalInput = findViewById(R.id.tanggalInput)
        tanggalLayout = findViewById(R.id.tanggalLayout)
        descriptionInput = findViewById(R.id.descriptionInput)
        descriptionLayout = findViewById(R.id.descriptionLayout)
        closeBtn = findViewById(R.id.closeBtn)

        transaksi = intent.getSerializableExtra("transaksi") as Transaksi

        tanggalInput.setText(transaksi.tanggal)
        labelInput.setText(transaksi.label)
        amountInput.setText(transaksi.amount.toString())
        descriptionInput.setText(transaksi.description)

        tanggalInput.addTextChangedListener {
            btnUpdate.visibility = View.VISIBLE
            if (it!!.count() > 0)
                tanggalLayout.error = null
        }

        labelInput.addTextChangedListener {
            btnUpdate.visibility = View.VISIBLE
            if (it!!.count() > 0)
                labelLayout.error = null
        }

        amountInput.addTextChangedListener {
            btnUpdate.visibility = View.VISIBLE
            if (it!!.count() > 0)
                amountLayout.error = null
        }

        descriptionInput.addTextChangedListener {
            btnUpdate.visibility = View.VISIBLE
        }

        btnUpdate.setOnClickListener {
            val tgl = tanggalInput.text.toString()
            val label = labelInput.text.toString()
            val amount = amountInput.text.toString().toDoubleOrNull()
            val desc = descriptionInput.text.toString()

            if (tgl.isEmpty()) {
                tanggalLayout.error = "Please enter a valid label"
                return@setOnClickListener
            }

            if (label.isEmpty()) {
                labelLayout.error = "Please enter a valid label"
                return@setOnClickListener
            }

            if (amount == null) {
                amountLayout.error = "Please enter a valid amount"
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.Main).launch {
                try {
                    var data = MonageData(
                        id = transaksi.id.toInt(), // must provide id, otherwise it will be error
                        tanggal = tgl,
                        label = label,
                        amount = amount,
                        description = desc
                    )
                    // use current transaction id to update
                    MonageApi.update(token = token, apiKey = apiKey, monageData = data, idQuery = "eq.${transaksi.id}")

                    Toast.makeText(
                        applicationContext,
                        "Berhasil merubah transaksi",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(
                        applicationContext,
                        "Gagal merubah transaksi",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        closeBtn.setOnClickListener {
            finish()
        }
    }

}

