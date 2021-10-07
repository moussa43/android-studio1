package com.example.dice_throw

import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

object HistoryDB
{
    private val All_DiceCollection : MutableList<DiceCollection> = mutableListOf()

    fun add_collection(x: DiceCollection)
    {
        All_DiceCollection.add(x)
        post(x.Dices.toString())
    }

    fun add_partial(dice: Int, nbDice: Int)
    {
        if (All_DiceCollection.size == 0)
        {
            val tmp_list : MutableList<Int> = mutableListOf(dice)
            All_DiceCollection.add(DiceCollection(tmp_list, nbDice))
            return
        }

        val last_element = All_DiceCollection.removeLast()

        if (last_element.Dices.size == last_element.nbDice)
        {
            All_DiceCollection.add(last_element)

            val tmp_list : MutableList<Int> = mutableListOf(dice)
            All_DiceCollection.add(DiceCollection(tmp_list, nbDice))
        }
        else
        {
            last_element.Dices.add(dice)
            last_element.nbDice = nbDice
            All_DiceCollection.add(last_element)

            if (last_element.Dices.size == last_element.nbDice)
            {
                post(last_element.Dices.toString())
            }

        }
    }

    fun remove_all()
    {
        All_DiceCollection.clear()
    }

    fun get_all() : MutableList<DiceCollection>
    {
        return All_DiceCollection;
    }

    fun size(): Int
    {
        return All_DiceCollection.size
    }


    private fun post(data: String)
    {
        val queue : RequestQueue = Volley.newRequestQueue(MainActivity.context)
        val url = "https://615b221c4a360f0017a814ca.mockapi.io/results"

        Log.d("mouss", data)

        val postRequest = JsonObjectRequest(
            Request.Method.POST, url,
            JSONObject("{ dice_throw : $data }"),
            {
                    response -> Log.d("moussa", response.toString())
            },
            {
                    error -> Log.d("ferzli", error.toString())
            })

        queue.add(postRequest)
    }

}