package com.b2yblaster.groupcoza

import android.os.*
import android.widget.*
import android.content.Intent
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

        btnInject.setOnClickListener { doInject() }
        btnExecute.setOnClickListener { doExecute() }
        btnScriptHub.setOnClickListener {
            startActivity(Intent(this, ScriptHubActivity::class.java))
        }
    }

    private fun doInject() {
        tvStatus.text = "Attaching..."
        CoroutineScope(Dispatchers.IO).launch {
            val ok = ExecutorService.attach()
            withContext(Dispatchers.Main) {
                tvStatus.text = if (ok) "✓ Attached" else "✗ Failed — open Roblox first"
            }
        }
    }

    private fun doExecute() {
        val script = etScript.text.toString().trim()
        if (script.isEmpty()) return
        CoroutineScope(Dispatchers.IO).launch {
            val ok = ExecutorService.execute(script)
            withContext(Dispatchers.Main) {
                tvStatus.text = if (ok) "✓ Executed" else "✗ Execution failed"
            }
        }
    }
}
