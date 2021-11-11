package com.phstudio.youtubers.Quiz

import android.annotation.SuppressLint
import com.phstudio.youtubers.R

object Constants {

    @SuppressLint("NewApi")
    fun getQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()

        val pedro = "PedrosGame"
        val kovy = "Kovy"
        val ment = "MenT"
        val jirka_kral = "Jirka Král"
        val viralbrothers = "ViralBrothers"
        val fizi = "FIZIstyle"
        val stejk = "Stejk"
        val vlada = "VladaVideos"
        val ati = "AtiShow"
        val tvtvixx = "Danny z TVTwixx"

        // 1
        val que1 = Question(
            1,
            R.drawable.pedro_full,
            kovy,
            pedro,
            stejk,
            tvtvixx,
            2
        )
        questionsList.add(que1)

        // 2
        val que2 = Question(
            1,
            R.drawable.kovy_full,
            ati,
            kovy,
            vlada,
            ment,
            2
        )
        questionsList.add(que2)

        // 3
        val que3 = Question(
            1,
            R.drawable.ment_full,
            stejk,
            jirka_kral,
            ment,
            fizi,
            3
        )
        questionsList.add(que3)

        // 4
        val que4 = Question(
            1,
            R.drawable.jirkakral_full,
            viralbrothers,
            ment,
            jirka_kral,
            kovy,
            3
        )
        questionsList.add(que4)

        // 5
        val que5 = Question(
            1,
            R.drawable.viralbrothers_full,
            viralbrothers,
            tvtvixx,
            pedro,
            ment,
            1
        )
        questionsList.add(que5)

        // 6
        val que6 = Question(
            1,
            R.drawable.fizi_full,
            fizi,
            stejk,
            jirka_kral,
            pedro,
            1
        )
        questionsList.add(que6)

        // 7
        val que7 = Question(
            1,
            R.drawable.stejk_full,
            stejk,
            kovy,
            jirka_kral,
            ati,
            1
        )
        questionsList.add(que7)

        // 8
        val que8 = Question(
            1,
            R.drawable.vlada_full,
            vlada,
            fizi,
            viralbrothers,
            ment, 1
        )
        questionsList.add(que8)

        // 9
        val que9 = Question(
            1,
            R.drawable.ati_full,
            kovy,
            vlada,
            pedro,
            ati,
            4
        )
        questionsList.add(que9)

        // 10
        val que10 = Question(
            1,
            R.drawable.tvtwixx_full,
            kovy,
            vlada,
            ment,
            tvtvixx,
            4
        )
        questionsList.add(que10)


        return questionsList.shuffled() as ArrayList<Question>
    }
}