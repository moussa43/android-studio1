package com.example.dice_throw

data class DiceCollection(var Dices: MutableList<Int>, var nbDice: Int)
{
    override fun toString(): String
    {
        var result : String = ""

        result += nbDice
        result += ": "

        for (item in Dices)
        {
            result += item
            result += " "
        }

        return result
    }
}