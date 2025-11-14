package com.example.ecopulse.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.ecopulse.R
import com.example.ecopulse.network.ApiClient
import com.example.ecopulse.network.HistoryItem // Folosește modelul tău corect
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private lateinit var historyContainer: LinearLayout // Presupunem că acesta este containerul tău

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Asigură-te că layout-ul este corect
        return inflater.inflate(R.layout.activity_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyContainer = view.findViewById(R.id.history_container) // Presupunem că ID-ul este corect

        // Apelează funcția de rețea imediat după ce vizualizarea e creată
        loadHistoryFromBackend()
    }

    private fun loadHistoryFromBackend() {
        Log.d("API", "Tentativă de a apela /api/v1/history...")

        // Folosește ApiClient-ul și Service-ul tău
        val apiCall = ApiClient.apiService.getHistory()

        apiCall.enqueue(object : Callback<List<HistoryItem>> {

            // Când primești un răspuns de la server
            override fun onResponse(call: Call<List<HistoryItem>>, response: Response<List<HistoryItem>>) {

                if (response.isSuccessful) {
                    // Codul HTTP este 200 OK
                    val historyList = response.body() ?: emptyList() // Dacă e gol, folosește listă goală

                    Log.d("API_SUCCESS", "Istoric primit: ${historyList.size} intrări")

                    historyContainer.removeAllViews() // Curățăm containerul

                    // Afișează fiecare obiect JSON pe ecran
                    for (item in historyList) {
                        addHistoryItem(
                            container = historyContainer,
                            // Folosește câmpurile din modelul tău
                            title = item.question,
                            date = item.timestamp,
                            points = item.answer // Folosești 'answer' pentru 'points' pe UI
                        )
                    }
                } else {
                    // S-A CONECTAT, DAR SERVERUL A RĂSPUNS CU O EROARE (ex: 404 Not Found)
                    Log.e("API_ERROR", "Eroare răspuns (Cod): ${response.code()}")
                }
            }

            // Când nu se poate conecta DELOC (timeout, server oprit, firewall)
            override fun onFailure(call: Call<List<HistoryItem>>, t: Throwable) {
                Log.e("API_FAILURE", "Request FAIL: ${t.message}. Verificați IP-ul/Firewall-ul.")
            }
        })
    }

    // Funcția ta ajutătoare pentru a adăuga elemente în LinearLayout
    private fun addHistoryItem(
        container: LinearLayout,
        title: String,
        date: String,
        points: String
    ) {
        val item = LayoutInflater.from(requireContext())
            .inflate(R.layout.item_history, container, false) // Presupunem că aveți un item_history.xml

        // Aceste ID-uri trebuie să fie definite în item_history.xml
        item.findViewById<TextView>(R.id.tv_history_title).text = title
        item.findViewById<TextView>(R.id.tv_history_date).text = date
        item.findViewById<TextView>(R.id.tv_history_points).text = points

        container.addView(item)
    }
}