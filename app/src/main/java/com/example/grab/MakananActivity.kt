package com.example.grab

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.grab.R
import com.google.firebase.database.FirebaseDatabase

class MakananActivity : AppCompatActivity() {

    private var jmlPizza = 0
    private var jmlAyam = 0
    private val hargaPizza = 50000
    private val hargaAyam = 20000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makanan)

        // Binding UI
        val tvPizza = findViewById<TextView>(R.id.tvCountPizza)
        val tvAyam = findViewById<TextView>(R.id.tvCountAyam)
        val tvTotal = findViewById<TextView>(R.id.tvTotalHarga)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)

        // Firebase
        val database = FirebaseDatabase.getInstance()
        val pesananRef = database.getReference("pesanan")

        // ===== EVENT PIZZA =====
        findViewById<Button>(R.id.btnPlusPizza).setOnClickListener {
            jmlPizza++
            tvPizza.text = jmlPizza.toString()
            updateTotal(tvTotal)
        }

        findViewById<Button>(R.id.btnMinPizza).setOnClickListener {
            if (jmlPizza > 0) jmlPizza--
            tvPizza.text = jmlPizza.toString()
            updateTotal(tvTotal)
        }

        // ===== EVENT AYAM =====
        findViewById<Button>(R.id.btnPlusAyam).setOnClickListener {
            jmlAyam++
            tvAyam.text = jmlAyam.toString()
            updateTotal(tvTotal)
        }

        findViewById<Button>(R.id.btnMinAyam).setOnClickListener {
            if (jmlAyam > 0) jmlAyam--
            tvAyam.text = jmlAyam.toString()
            updateTotal(tvTotal)
        }

        // ===== BUTTON SIMPAN =====
        btnSimpan.setOnClickListener {

            if (jmlPizza == 0 && jmlAyam == 0) {
                Toast.makeText(this, "Pesanan masih kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val idPesanan = pesananRef.push().key!!

            if (jmlPizza > 0) {
                pesananRef.child(idPesanan).child("pizza")
                    .setValue(
                        Pesanan(
                            "Pizza",
                            hargaPizza,
                            jmlPizza,
                            jmlPizza * hargaPizza
                        )
                    )
            }

            if (jmlAyam > 0) {
                pesananRef.child(idPesanan).child("ayam")
                    .setValue(
                        Pesanan(
                            "Ayam Geprek",
                            hargaAyam,
                            jmlAyam,
                            jmlAyam * hargaAyam
                        )
                    )
            }

            val totalBayar = (jmlPizza * hargaPizza) + (jmlAyam * hargaAyam)
            pesananRef.child(idPesanan).child("totalBayar").setValue(totalBayar)

            Toast.makeText(this, "Pesanan berhasil disimpan", Toast.LENGTH_SHORT).show()

            // RESET
            jmlPizza = 0
            jmlAyam = 0
            tvPizza.text = "0"
            tvAyam.text = "0"
            updateTotal(tvTotal)
        }
    }

    private fun updateTotal(textView: TextView) {
        val total = (jmlPizza * hargaPizza) + (jmlAyam * hargaAyam)
        textView.text = "Rp $total"
    }
}