package com.example.dice_throw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity()
{
    lateinit var nbOfDice : TextView
    lateinit var minus_buton : Button
    lateinit var plus_buton : Button

    var imagesArray : MutableList<ImageView> = mutableListOf()

    var nbOfCurrentDices : Int = 0


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nbOfDice = findViewById<TextView>(R.id.Nub_Dice)
        minus_buton = findViewById<Button>(R.id.minus_button)
        plus_buton = findViewById<Button>(R.id.plus_button)

        imagesArray.add(findViewById<ImageView>(R.id.First_Dice))
        imagesArray.add(findViewById<ImageView>(R.id.Second_Dice))
        imagesArray.add(findViewById<ImageView>(R.id.Third_Dice))
        imagesArray.add(findViewById<ImageView>(R.id.Forth_Dice))
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
        for (i in 0 until nbOfDice.text.toString().toInt())
        {
            imagesArray[i].setImageResource(newDiceImage())
            imagesArray[i].isVisible = true
        }

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

        imagesArray[nbOfCurrentDices].setImageResource(newDiceImage())
        imagesArray[nbOfCurrentDices].isVisible = true

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


    private fun newDiceImage(): Int
    {
        when((1..6).random())
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