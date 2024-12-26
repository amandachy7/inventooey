package com.example.inventory;

public class Db_Contract {

        // Ganti dengan IP yang sesuai dengan server yang akan diakses
        public static String ip = "http://192.168.1.11"; // Gantilah dengan IP dari server Anda
        public static String baseUrl = ip + "/api"; // Menambahkan /api jika API Anda diakses dari path /api

        // API endpoints
        public static String getSales = baseUrl + "/sales";
        public static String getProducts = baseUrl + "/products";
        public static String createSales = baseUrl + "/sales/create";
        public static String urlLogin;

        // Contoh URL lengkap untuk API lainnya
}
