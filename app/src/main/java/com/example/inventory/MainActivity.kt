package com.example.inventory

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.inventory.MasterFragment
import com.example.inventory.DashboardFragment
import com.example.inventory.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Optional for full-screen display
        setContentView(R.layout.activity_main)
        // Mengambil data sales dari API
        ApiRequest.makeRequest(this) // 'this' refers to the Activity context
        val navView: BottomNavigationView = findViewById(R.id.navigation)
        navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_table -> {
                    openFragment(MasterFragment())
                    true
                }
                R.id.navigation_dashboard -> {
                    openFragment(DashboardFragment())
                    true
                }
                R.id.navigation_transaksi -> {
                    openFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }

        // Set initial fragment
        navView.selectedItemId = R.id.navigation_table
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

    fun changeBackgroundColor(color: Int) {
        val container: View = findViewById(R.id.container)
        container.setBackgroundColor(color)
    }

}
