package com.phstudio.youtubers.Quiz

import android.annotation.SuppressLint
import com.phstudio.youtubers.R

object Constants3 {

    @SuppressLint("NewApi")
    fun getQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()

        val aik = "Aik & Johanka"
        val baxtrix = "Baxtrix"
        val destro = "Jakub Destro"
        val earth = "EARTH"
        val fatty = "FattyPillowTV"
        val fayne = "Fayne"
        val nejfake = "NejFake"
        val pimps = "Pimps"
        val porty = "Porty"
        val tary = "Tary"

        // 1
        val que1 = Question(
            1,
            R.drawable.aik_full,
            fatty,
            aik,
            nejfake,
            pimps,
            2
        )
        questionsList.add(que1)

        // 2
        val que2 = Question(
            1,
            R.drawable.baxtrix_full,
            baxtrix,
            fayne,
            tary,
            earth,
            1
        )
        questionsList.add(que2)

        // 3
        val que3 = Question(
            1,
            R.drawable.destro_full,
            porty,
            destro,
            fatty,
            fayne,
            2
        )
        questionsList.add(que3)

        // 4
        val que4 = Question(
            1,
            R.drawable.earth_full,
            tary,
            fatty,
            earth,
            pimps,
            3
        )
        questionsList.add(que4)

        // 5
        val que5 = Question(
            1,
            R.drawable.fatty_full,
            fayne,
            fatty,
            baxtrix,
            destro,
            2
        )
        questionsList.add(que5)

        // 6
        val que6 = Question(
            1,
            R.drawable.fayne_full,
            fayne,
            pimps,
            baxtrix,
            fatty,
            1
        )
        questionsList.add(que6)

        // 7
        val que7 = Question(
            1,
            R.drawable.nejfake_full,
            porty,
            tary,
            fayne,
            nejfake,
            4
        )
        questionsList.add(que7)

        // 8
        val que8 = Question(
            1,
            R.drawable.pimps_full,
            aik,
            pimps,
            baxtrix,
            tary,
            2
        )
        questionsList.add(que8)

        // 9
        val que9 = Question(
            1,
            R.drawable.porty_full,
            destro,
            porty,
            earth,
            fayne,
            2
        )
        questionsList.add(que9)

        // 10
        val que10 = Question(
            1,
            R.drawable.tary_full,
            fatty,
            destro,
            tary,
            earth,
            3
        )
        questionsList.add(que10)


        return questionsList.shuffled() as ArrayList<Question>
    }
}