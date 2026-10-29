package com.b2yblaster.groupcoza

import android.os.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var btnInject: Button
    private lateinit var btnScriptHub: Button
    private lateinit var etScript: EditText
    private lateinit var btnExecute: Button
    private lateinit var tvStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnInject    = findViewById(R.id.btnInject)
        btnScriptHub = findViewById(R.id.btnScriptHub)
        etScript     = findViewById(R.id.etScript)
        btnExecute   = findViewById(R.id.btnExecute)
        tvStatus     = findViewById(R.id.tvStatus)

        btnInject.setOnClickListener {
            tvStatus.text = "✓ Ready"
        }

        btnScriptHub.setOnClickListener {
            tvStatus.text = "Script Hub — paste script above"
        }

        btnExecute.setOnClickListener {
            val script = etScript.text.toString().trim()
            if (script.isEmpty()) {
                tvStatus.text = "✗ No script"
                return@setOnClickListener
            }
            tvStatus.text = "✓ Executed"
        }
    }
}
