package com.example.inventory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SalesAdapter(private var salesList: List<Sales>) :
    RecyclerView.Adapter<SalesAdapter.SalesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sales, parent, false)
        return SalesViewHolder(view)
    }

    override fun onBindViewHolder(holder: SalesViewHolder, position: Int) {
        val sale = salesList[position]
        holder.tvTanggal.text = sale.tanggal
        holder.tvKasir.text = sale.kasir
    }

    override fun getItemCount(): Int {
        return salesList.size
    }

    fun updateSales(newSalesList: List<Sales>) {
        salesList = newSalesList
        notifyDataSetChanged()
    }

    class SalesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTanggal: TextView = view.findViewById(R.id.tv_tanggal)
        val tvKasir: TextView = view.findViewById(R.id.tv_kasir)
    }
}

