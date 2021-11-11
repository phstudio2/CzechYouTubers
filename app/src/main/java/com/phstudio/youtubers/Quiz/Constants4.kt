package com.phstudio.youtubers.Quiz

import android.annotation.SuppressLint
import com.phstudio.youtubers.R

object Constants4 {

    @SuppressLint("NewApi")
    fun getQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()

        val dejzr = "Dejzr"
        val freescoot = "FreescootOfficial"
        val herdyn = "Herdyn"
        val jdemezrat = "JdemeŽrát"
        val petrmara = "Petr Mára"
        val praza = "PRÁŽA - original"
        val roth = "Roth Wellden"
        val olchic = "OLCHIČ"
        val tomaviky = "Tom & Viky"
        val klucizprahy = "Kluci z Prahy - (HONEST GUIDE)"

        // 1
        val que1 = Question(
            1,
            R.drawable.dejzr_full,
            dejzr,
            olchic,
            petrmara,
            freescoot,
            1
        )
        questionsList.add(que1)

        // 2
        val que2 = Question(
            1,
            R.drawable.freescoot_full,
            praza,
            freescoot,
            roth,
            olchic,
            2
        )
        questionsList.add(que2)

        // 3
        val que3 = Question(
            1,
            R.drawable.herdyn_full,
            dejzr,
            olchic,
            herdyn,
            roth,
            3
        )
        questionsList.add(que3)

        // 4
        val que4 = Question(
            1,
            R.drawable.jdemezrat_full,
            olchic,
            petrmara,
            jdemezrat,
            roth,
            3
        )
        questionsList.add(que4)

        // 5
        val que5 = Question(
            1,
            R.drawable.praza_full,
            praza,
            herdyn,
            freescoot,
            olchic,
            1
        )
        questionsList.add(que5)

        // 6
        val que6 = Question(
            1,
            R.drawable.roth_full,
            jdemezrat,
            praza,
            roth,
            petrmara,
            3
        )
        questionsList.add(que6)

        // 7
        val que7 = Question(
            1,
            R.drawable.olchic_full,
            olchic,
            herdyn,
            roth,
            petrmara,
            1
        )
        questionsList.add(que7)

        // 8
        val que8 = Question(
            1,
            R.drawable.tomaviky_full,
            roth,
            klucizprahy,
            jdemezrat,
            tomaviky,
            4
        )
        questionsList.add(que8)

        // 9
        val que9 = Question(
            1,
            R.drawable.klucizprahy_full,
            roth,
            klucizprahy,
            jdemezrat,
            tomaviky,
            2
        )
        questionsList.add(que9)

        // 10
        val que10 = Question(
            1,
            R.drawable.petrmara_full,
            herdyn,
            petrmara,
            jdemezrat,
            roth,
            2
        )
        questionsList.add(que10)


        return questionsList.shuffled() as ArrayList<Question>
    }
}