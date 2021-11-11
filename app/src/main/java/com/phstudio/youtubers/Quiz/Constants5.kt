package com.phstudio.youtubers.Quiz

import android.annotation.SuppressLint
import com.phstudio.youtubers.R

object Constants5 {

    @SuppressLint("NewApi")
    fun getQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()

        val matem = "MaTTem"
        val morry = "Morry"
        val vetus = "Vetus"
        val smusa = "SmusaGames"
        val marwex = "MarweX"
        val datel = "DATEL"
        val petan = "petangames"
        val jmenujisemartin = "Jmenuju Se Martin"
        val vidrail = "Vidrail"
        val attack = "Attack"

        // 1
        val que1 = Question(
            1,
            R.drawable.matem_full,
            datel,
            matem,
            attack,
            petan,
            2
        )
        questionsList.add(que1)

        // 2
        val que2 = Question(
            1,
            R.drawable.morry_full,
            petan,
            vetus,
            morry,
            vidrail,
            3
        )
        questionsList.add(que2)

        // 3
        val que3 = Question(
            1,
            R.drawable.vetus_full,
            vetus,
            morry,
            jmenujisemartin,
            attack,
            1
        )
        questionsList.add(que3)

        // 4
        val que4 = Question(
            1,
            R.drawable.smusa_full,
            datel,
            attack,
            smusa,
            marwex,
            3
        )
        questionsList.add(que4)

        // 5
        val que5 = Question(
            1,
            R.drawable.marwex_full,
            marwex,
            vidrail,
            datel,
            smusa,
            1
        )
        questionsList.add(que5)

        // 6
        val que6 = Question(
            1,
            R.drawable.datel_full,
            datel,
            jmenujisemartin,
            morry,
            marwex,
            1
        )
        questionsList.add(que6)

        // 7
        val que7 = Question(
            1,
            R.drawable.petan_full,
            morry,
            vetus,
            smusa,
            petan,
            4
        )
        questionsList.add(que7)

        // 8
        val que8 = Question(
            1,
            R.drawable.jmenujisemartin_full,
            smusa,
            matem,
            jmenujisemartin,
            attack,
            3
        )
        questionsList.add(que8)

        // 9
        val que9 = Question(
            1,
            R.drawable.vidrail_full,
            vetus,
            vidrail,
            petan,
            smusa,
            2
        )
        questionsList.add(que9)

        // 10
        val que10 = Question(
            1,
            R.drawable.attack_full,
            jmenujisemartin,
            smusa,
            petan,
            attack,
            4
        )
        questionsList.add(que10)


        return questionsList.shuffled() as ArrayList<Question>
    }
}