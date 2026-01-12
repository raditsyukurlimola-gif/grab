package com.example.grab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class AccountFragment : Fragment() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvToLogin: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_account, container, false)

        etEmail = view.findViewById(R.id.etEmailRegister)
        etPassword = view.findViewById(R.id.etPasswordRegister)
        btnRegister = view.findViewById(R.id.btnRegisterAccount)
        tvToLogin = view.findViewById(R.id.tvToLogin)

        btnRegister.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Email dan Password wajib diisi", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Akun berhasil dibuat", Toast.LENGTH_SHORT).show()
            }
        }
        tvToLogin.setOnClickListener {
            Toast.makeText(requireContext(), "Arahkan ke halaman login", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}