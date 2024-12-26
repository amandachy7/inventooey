package com.example.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.inventory.R
import org.json.JSONObject

class FragmentDashboard : Fragment() {

    private lateinit var tvTotalPenjualan: TextView
    private lateinit var tvTotalPembelian: TextView
    private lateinit var tvTotalBarangKeluar: TextView
    private lateinit var tvTotalBarangMasuk: TextView
    private lateinit var requestQueue: RequestQueue

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        tvTotalPenjualan = view.findViewById(R.id.tv_total_penjualan)
        tvTotalPembelian = view.findViewById(R.id.tv_total_pembelian)
        tvTotalBarangKeluar = view.findViewById(R.id.tv_total_barang_keluar)
        tvTotalBarangMasuk = view.findViewById(R.id.tv_total_barang_masuk)

        requestQueue = Volley.newRequestQueue(requireContext())
        fetchDashboardData()

        return view
    }

    private fun fetchDashboardData() {
        val url = "http://192.168.1.11:8000/api/dashboard" // Ganti dengan URL API Laravel

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                parseDashboardData(response)
            },
            Response.ErrorListener { error ->
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(jsonObjectRequest)
    }

    private fun parseDashboardData(response: JSONObject) {
        try {
            val totalPenjualan = response.getInt("totalPenjualan")
            val totalPembelian = response.getInt("totalPembelian")
            val totalBarangKeluar = response.getInt("totalBarangKeluar")
            val totalBarangMasuk = response.getInt("totalBarangMasuk")

            tvTotalPenjualan.text = "Total Penjualan: $totalPenjualan"
            tvTotalPembelian.text = "Total Pembelian: $totalPembelian"
            tvTotalBarangKeluar.text = "Total Barang Keluar: $totalBarangKeluar"
            tvTotalBarangMasuk.text = "Total Barang Masuk: $totalBarangMasuk"

        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Parsing error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requestQueue.cancelAll { true }
    }
}

class DashboardFragment : Fragment() {

}
