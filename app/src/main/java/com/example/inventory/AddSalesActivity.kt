package com.example.inventory

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class AddSalesActivity : AppCompatActivity() {

    private lateinit var etTanggal: EditText
    private lateinit var spinnerKasir: Spinner
    private lateinit var btnSaveSales: Button
    private lateinit var requestQueue: RequestQueue
    private lateinit var users: List<User> // Menyimpan daftar kasir

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sales)

        etTanggal = findViewById(R.id.et_tanggal)
        spinnerKasir = findViewById(R.id.spinner_kasir)
        btnSaveSales = findViewById(R.id.btn_save_sales)

        requestQueue = Volley.newRequestQueue(this)

        // Mengambil daftar kasir dari API
        fetchUsersData()

        // Menangani klik tombol Simpan
        btnSaveSales.setOnClickListener {
            val tanggal = etTanggal.text.toString()
            val kasirId = (spinnerKasir.selectedItem as User).id

            if (tanggal.isNotEmpty()) {
                addSales(tanggal, kasirId)
            } else {
                Toast.makeText(this, "Tanggal harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchUsersData() {
        val url = "https://your-laravel-api-url.com/api/users" // Ganti dengan URL API Laravel untuk mengambil data pengguna

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                parseUsersData(response)
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(jsonObjectRequest)
    }

    private fun parseUsersData(response: JSONObject) {
        try {
            val usersArray = response.getJSONArray("users")
            val usersList = mutableListOf<User>()

            for (i in 0 until usersArray.length()) {
                val userObject = usersArray.getJSONObject(i)
                val user = User(
                    id = userObject.getInt("id"),
                    name = userObject.getString("name")
                )
                usersList.add(user)
            }

            users = usersList

            // Menampilkan daftar kasir dalam Spinner
            val kasirNames = usersList.map { it.name }
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, kasirNames)
            spinnerKasir.adapter = adapter

        } catch (e: Exception) {
            Toast.makeText(this, "Parsing error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addSales(tanggal: String, kasirId: Int) {
        val url = "https://your-laravel-api-url.com/api/sales" // Ganti dengan URL API Laravel untuk menambah penjualan

        val jsonObject = JSONObject().apply {
            put("tanggal", tanggal)
            put("kasir", kasirId)
        }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            Response.Listener { response ->
                Toast.makeText(this, "Penjualan berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                finish() // Kembali ke activity sebelumnya
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(jsonObjectRequest)
    }
}

data class User(
    val id: Int,
    val name: String
)
