package com.example.dice_throw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class HistoryActivity : AppCompatActivity()
{
    lateinit var linearLayoutHistory : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        linearLayoutHistory = findViewById<LinearLayout>(R.id.linear_layouy_history)

        val all_dices = HistoryDB.get_all()

        get()

        Log.d("list: ", all_dices.toString())

        show(all_dices)

    }

    private fun get()
    {
        val list: MutableList<DiceCollection> = mutableListOf()

        val queue : RequestQueue = Volley.newRequestQueue(MainActivity.context)
        val url = "https://615b221c4a360f0017a814ca.mockapi.io/results"


        val postRequest = JsonArrayRequest(
            Request.Method.GET, url,
            null,
            {
                response -> Log.d("moussa", response.toString())
                // val response = JSONArray(response2)

                for (i in 0 until response.length() - HistoryDB.size())
                {
                    val jsonObject = response.getJSONObject(i)
                    val diceArray: JSONArray = jsonObject.getJSONArray("dice_throw")
                    val diceMutableList: MutableList<Int> = mutableListOf()
                    for (j in 0 until diceArray.length())
                    {
                        diceMutableList.add(diceArray.get(j) as Int)
                    }
                    list.add(DiceCollection(diceMutableList, diceMutableList.size))
                }

                show(list)
            },
            {
                    error -> Log.d("ferzli", error.toString())
            })

        queue.add(postRequest)
    }

    private fun show(list: MutableList<DiceCollection>)
    {
        for (dices in list)
        {
            val text = TextView(this)
            text.textSize = 40f
            text.text = dices.toString()

            val card = CardView(this)
            card.addView(text)
            card.setCardBackgroundColor(getColor(R.color.white))
            card.radius = 20F
            card.useCompatPadding = true

            linearLayoutHistory.addView(card)
        }
    }

    fun on_back_press(sender: View)
    {
        onBackPressed()
    }

    fun delete_All(sender: View)
    {
        HistoryDB.remove_all()
        linearLayoutHistory.removeAllViews()
    }
}