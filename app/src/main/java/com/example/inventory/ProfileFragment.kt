package com.example.inventory


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class ProfileFragment : Fragment() {

    private lateinit var etName: EditText
    private lateinit var etUsername: EditText
    private lateinit var btnSaveProfile: Button
    private lateinit var requestQueue: RequestQueue
    private var userId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        etName = view.findViewById(R.id.et_name)
        etUsername = view.findViewById(R.id.et_username)
        btnSaveProfile = view.findViewById(R.id.btn_save_profile)

        requestQueue = Volley.newRequestQueue(requireContext())

        // Ambil data profil pengguna dari API
        getUserProfile()

        // Tombol untuk menyimpan perubahan profil
        btnSaveProfile.setOnClickListener {
            val name = etName.text.toString()
            val username = etUsername.text.toString()

            if (name.isNotEmpty() && username.isNotEmpty()) {
                updateProfile(name, username)
            } else {
                Toast.makeText(requireContext(), "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    // Fungsi untuk mengambil data profil pengguna
    private fun getUserProfile() {
        val url = "http://192.168.1.11:8000/api/user-profile" // Ganti dengan URL API Laravel

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val user = response.getJSONObject("user")
                userId = user.getInt("id")
                etName.setText(user.getString("name"))
                etUsername.setText(user.getString("username"))
            },
            Response.ErrorListener { error ->
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(jsonObjectRequest)
    }

    // Fungsi untuk memperbarui profil pengguna
    private fun updateProfile(name: String, username: String) {
        val url = "https://your-laravel-api-url.com/api/update-profile" // Ganti dengan URL API Laravel untuk update profil

        val jsonObject = JSONObject().apply {
            put("name", name)
            put("username", username)
        }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            Response.Listener { response ->
                Toast.makeText(requireContext(), "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(jsonObjectRequest)
    }
}
