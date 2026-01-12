package com.example.grab

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.database.*

class ActivityFragment : Fragment() {

    private lateinit var listView: ListView
    private lateinit var btnUpdate: Button
    private lateinit var btnHapus: Button

    private lateinit var database: DatabaseReference
    private lateinit var adapter: ArrayAdapter<String>

    private val listNama = ArrayList<String>()
    private val listId = ArrayList<String>()

    private var selectedPosition = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_aktivitas, container, false)

        // ================= INIT VIEW =================
        listView = view.findViewById(R.id.listViewData)
        btnUpdate = view.findViewById(R.id.btnUpdate)
        btnHapus = view.findViewById(R.id.btnHapus)

        database = FirebaseDatabase.getInstance().getReference("pesanan")

        adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_single_choice,
            listNama
        )

        listView.adapter = adapter
        listView.choiceMode = ListView.CHOICE_MODE_SINGLE

        listView.setOnItemClickListener { _, _, position, _ ->
            selectedPosition = position
        }

        // ================= AMBIL DATA FIREBASE =================
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                listNama.clear()
                listId.clear()

                if (!snapshot.exists()) {
                    adapter.notifyDataSetChanged()
                    return
                }

                for (dataSnap in snapshot.children) {

                    // AMAN TANPA MODEL (ANTI NULL)
                    val nama = dataSnap.child("nama_makanan").value?.toString() ?: "-"
                    val layanan = dataSnap.child("layanan").value?.toString() ?: "-"
                    val jumlah = dataSnap.child("jumlah").value?.toString() ?: "0"
                    val harga = dataSnap.child("harga").value?.toString() ?: "0"
                    val status = dataSnap.child("status").value?.toString() ?: "-"

                    listNama.add(
                        "$nama | $layanan | $jumlah pcs | Rp$harga | $status"
                    )

                    listId.add(dataSnap.key!!)
                }

                // RESET PILIHAN (WAJIB)
                selectedPosition = -1
                listView.clearChoices()
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Gagal memuat data", Toast.LENGTH_SHORT).show()
            }
        })

        // ================= UPDATE STATUS =================
        btnUpdate.setOnClickListener {
            if (selectedPosition == -1) {
                Toast.makeText(requireContext(), "Pilih pesanan dulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val id = listId[selectedPosition]

            database.child(id).child("status").setValue("Selesai")
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Status diperbarui", Toast.LENGTH_SHORT).show()
                }
        }

        // ================= HAPUS PESANAN =================
        btnHapus.setOnClickListener {
            if (selectedPosition == -1) {
                Toast.makeText(requireContext(), "Pilih pesanan dulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val id = listId[selectedPosition]

            AlertDialog.Builder(requireContext())
                .setTitle("Hapus Pesanan")
                .setMessage("Yakin ingin menghapus pesanan ini?")
                .setPositiveButton("Ya") { _, _ ->
                    database.child(id).removeValue()
                    selectedPosition = -1
                    listView.clearChoices()
                }
                .setNegativeButton("Batal", null)
                .show()
        }

        return view
    }
}