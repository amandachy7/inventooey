import android.util.Log
import android.content.Context
import com.android.volley.NetworkError
import com.android.volley.NoConnectionError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.net.UnknownHostException

object ApiRequest {
    // Initialize the request queue with the proper context
    fun makeRequest(context: Context) {
        val queue: RequestQueue = Volley.newRequestQueue(context)
        val url: String = "http://192.168.1.11:8000/api/data" // Replace with your local IP address or domain

        // Create the StringRequest
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response -> // Handle response
                Log.d("API Response", response)
            },
            { error -> // Handle error
                when (error) {
                    is NoConnectionError -> {
                        Log.e("API Error", "No connection to the server")
                    }
                    is NetworkError -> {
                        Log.e("API Error", "Network error occurred")
                    }
                    is UnknownHostException -> {
                        Log.e("API Error", "UnknownHostException: Unable to resolve the host")
                    }
                    else -> {
                        Log.e("API Error", error.toString())
                    }
                }
            }
        )

        // Add the request to the Volley request queue
        queue.add(stringRequest)
    }
}
