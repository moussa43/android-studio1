package com.example.dice_throw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView

class HistoryActivity : AppCompatActivity()
{
    lateinit var linearLayoutHistory : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        linearLayoutHistory = findViewById<LinearLayout>(R.id.linear_layouy_history)

        val all_dices = HistoryDB.get_all()

        for (dices in all_dices)
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