package com.xelar.legayd.mathchildrengame.utils

import com.xelar.legayd.mathchildrengame.R
import kotlin.random.Random

object GameResultImage {
    private val random = Random

    fun generateWinnerImage(): Int {
        val winnerDrawable = arrayListOf(R.drawable.lev, R.drawable.tigr, R.drawable.slonik)
        val position = random.nextInt(winnerDrawable.size)
        return winnerDrawable[position]
    }

    fun generateLoserImage(): Int {
        val loserDrawable = arrayListOf(R.drawable.begemot, R.drawable.nosorog, R.drawable.leopard)
        val position = random.nextInt(loserDrawable.size)
        return loserDrawable[position]
    }
}