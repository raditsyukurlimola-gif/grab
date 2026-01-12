package com.example.grab

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.grab.R

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnMakanan = view.findViewById<View>(R.id.btnMakanan)
        val btnMotor = view.findViewById<View>(R.id.btnMotor)
        val btnMobil = view.findViewById<View>(R.id.btnMobil)
        val btnExpress = view.findViewById<View>(R.id.btnExpress)

        btnMakanan.setOnClickListener {
            val intent = Intent(requireContext(), MakananActivity::class.java)
            startActivity(intent)
        }

        btnMotor.setOnClickListener {
            Toast.makeText(requireContext(), "Layanan Motor dipilih", Toast.LENGTH_SHORT).show()
        }

        btnMobil.setOnClickListener {
            Toast.makeText(requireContext(), "Layanan Mobil dipilih", Toast.LENGTH_SHORT).show()
        }

        btnExpress.setOnClickListener {
            Toast.makeText(requireContext(), "Layanan Express dipilih", Toast.LENGTH_SHORT).show()
        }
    }
}