package com.example.newmonage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AddTransaksi : AppCompatActivity() {
    lateinit var addTransaction : Button
    lateinit var labelInput : TextInputEditText
    lateinit var labelLayout : TextInputLayout
    lateinit var amountInput : TextInputEditText
    lateinit var amountLayout : TextInputLayout
    lateinit var tanggalInput: TextInputEditText
    lateinit var tanggalLayout : TextInputLayout
    lateinit var closeBtn : ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaksi)

        addTransaction = findViewById(R.id.addTransactionBtn)
        labelInput= findViewById(R.id.labelInput)
        labelLayout= findViewById(R.id.labelLayout)
        amountInput= findViewById(R.id.amountInput)
        amountLayout= findViewById(R.id.amountLayout)
        tanggalInput= findViewById(R.id.tanggalInput)
        tanggalLayout= findViewById(R.id.labelLayout)
        closeBtn= findViewById(R.id.closeBtn)

        tanggalInput.addTextChangedListener {
            if(it!!.count() > 0)
                tanggalLayout.error = null
        }

        labelInput.addTextChangedListener {
            if(it!!.count() > 0)
                labelLayout.error = null
        }

        amountInput.addTextChangedListener {
            if(it!!.count() > 0)
                amountLayout.error = null
        }

        addTransaction.setOnClickListener {
            val tgl = tanggalInput.text.toString()
            val label = labelInput.text.toString()
            val amount = amountInput.text.toString().toDoubleOrNull()

            if(tgl.isEmpty())
                tanggalLayout.error = "Please enter a valid label"

            if(label.isEmpty())
                labelLayout.error = "Please enter a valid label"

            if(amount == null)
                amountLayout.error = "Please enter a valid amount"
            }

        closeBtn.setOnClickListener {
            finish()
        }
    }
        }