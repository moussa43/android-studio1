package com.example.dice_throw

object HistoryDB
{
    private val All_DiceCollection : MutableList<DiceCollection> = mutableListOf()

    fun add_collection(x: DiceCollection)
    {
        All_DiceCollection.add(x)
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
}