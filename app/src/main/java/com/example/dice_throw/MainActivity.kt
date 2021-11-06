package com.example.dice_throw

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity()
{
    companion object
    {
        @SuppressLint("StaticFieldLeak")
        @JvmStatic
        lateinit var context: Context
    }

    lateinit var nbOfDice : TextView
    lateinit var minus_buton : Button
    lateinit var plus_buton : Button

    var imagesArray : MutableList<ImageView> = mutableListOf()

    var nbOfCurrentDices : Int = 0


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = applicationContext

        nbOfDice = findViewById<TextView>(R.id.Nub_Dice)
        minus_buton = findViewById<Button>(R.id.minus_button)
        plus_buton = findViewById<Button>(R.id.plus_button)

        imagesArray.add(findViewById<ImageView>(R.id.First_Dice))
        imagesArray.add(findViewById<ImageView>(R.id.Second_Dice))
        imagesArray.add(findViewById<ImageView>(R.id.Third_Dice))
        imagesArray.add(findViewById<ImageView>(R.id.Forth_Dice))

        val button = findViewById<Button>(R.id.galleryBtn)
        button.setOnClickListener {
            val goToActivity2 = Intent(applicationContext, GalleryActivity::class.java)
            startActivity(goToActivity2)
        }

        val button2 = findViewById<Button>(R.id.mapBtn)
        button2.setOnClickListener {
            val goToActivity2 = Intent(applicationContext, MapsActivity::class.java)
            startActivity(goToActivity2)
        }
    }


    fun minus(sender: View)
    {
        var int_button = nbOfDice.text.toString().toInt()
        int_button -= 1
        nbOfDice.text = int_button.toString()


        minus_buton.isEnabled = nbOfDice.text != "0"
        plus_buton.isEnabled = nbOfDice.text != "4"
    }


    fun plus(sender: View)
    {
        var int_button = nbOfDice.text.toString().toInt()
        int_button += 1
        nbOfDice.text = int_button.toString()


        minus_buton.isEnabled = nbOfDice.text != "0"
        plus_buton.isEnabled = nbOfDice.text != "4"
    }

    fun rollAll(sender: View)
    {
        val temp_dice_list: MutableList<Int> = mutableListOf()

        for (i in 0 until nbOfDice.text.toString().toInt())
        {
            val randum_num = (1..6).random()
            temp_dice_list.add(randum_num)

            imagesArray[i].setImageResource(newDiceImage(randum_num))
            imagesArray[i].isVisible = true
        }

        HistoryDB.add_collection(DiceCollection(temp_dice_list, nbOfDice.text.toString().toInt()))

        for(i in nbOfDice.text.toString().toInt() until 4)
        {
            imagesArray[i].isVisible = false
        }
    }


    fun rollOnce(sender: View)
    {
        if (nbOfDice.text.toString().toInt() == 0)
        {
            for(i in 0 until 4)
            {
                imagesArray[i].isVisible = false
            }
            return
        }

        val randum_num = (1..6).random()
        imagesArray[nbOfCurrentDices].setImageResource(newDiceImage(randum_num))
        imagesArray[nbOfCurrentDices].isVisible = true

        HistoryDB.add_partial(randum_num, nbOfDice.text.toString().toInt())

        for(i in nbOfCurrentDices + 1 until 4)
        {
            imagesArray[i].isVisible = false
        }

        nbOfCurrentDices += 1

        if (nbOfCurrentDices >= nbOfDice.text.toString().toInt())
        {
            nbOfCurrentDices = 0
        }
    }

    fun history(sender: View)
    {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }


    private fun newDiceImage(randum_nb: Int): Int
    {
        when(randum_nb)
        {
            1 -> return R.drawable.dice_1
            2 -> return R.drawable.dice_2
            3 -> return R.drawable.dice_3
            4 -> return R.drawable.dice_4
            5 -> return R.drawable.dice_5
            6 -> return R.drawable.dice_6
        }
        return 0
    }

}