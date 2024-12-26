package com.example.inventory

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class MasterFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var salesAdapter: SalesAdapter
    private lateinit var requestQueue: RequestQueue
    private lateinit var btnAddSales: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_master, container, false)

        recyclerView = view.findViewById(R.id.recycler_view_sales)
        btnAddSales = view.findViewById(R.id.btn_add_sales)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        salesAdapter = SalesAdapter(listOf())
        recyclerView.adapter = salesAdapter

        requestQueue = Volley.newRequestQueue(requireContext())
        fetchSalesData()

        btnAddSales.setOnClickListener {
            val intent = Intent(requireContext(), AddSalesActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun fetchSalesData() {
        val url = "http://192.168.1.11:8000/api/sales" // Ganti dengan URL API Laravel

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                parseSalesData(response)
            },
            Response.ErrorListener { error ->
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(jsonObjectRequest)
    }

    private fun parseSalesData(response: JSONObject) {
        try {
            val salesArray: JSONArray = response.getJSONArray("sales")
            val salesList = mutableListOf<Sales>()

            for (i in 0 until salesArray.length()) {
                val saleObject = salesArray.getJSONObject(i)
                val sales = Sales(
                    id = saleObject.getInt("id_penjualan"),
                    tanggal = saleObject.getString("tanggal"),
                    kasir = saleObject.getString("kasir")
                )
                salesList.add(sales)
            }

            salesAdapter.updateSales(salesList)

        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Parsing error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}

data class Sales(
    val id: Int,
    val tanggal: String,
    val kasir: String
)
