package com.phstudio.youtubers.Quiz

import android.annotation.SuppressLint
import com.phstudio.youtubers.R

object Constants2 {

    @SuppressLint("NewApi")
    fun getQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()

        val house = "HouseBox"
        val gejmr = "GEJMR"
        val artix = "Artix"
        val natyla = "Natyla"
        val wedry = "Wedry"
        val shopaholic = "Shopaholic Nicol"
        val carrie = "Carrie Kirsten"
        val lukefry = "Lukefry"
        val geekboy = "GeekBoy"
        val cupofstyle = "A Cup of Style"

        // 1
        val que1 = Question(
            1,
            R.drawable.house_full,
            gejmr,
            artix,
            house,
            geekboy,
            3
        )
        questionsList.add(que1)

        // 2
        val que2 = Question(
            1,
            R.drawable.gejmr_full,
            gejmr,
            wedry,
            geekboy,
            lukefry,
            1
        )
        questionsList.add(que2)

        // 3
        val que3 = Question(
            1,
            R.drawable.artix_full,
            artix,
            geekboy,
            gejmr,
            house,
            1
        )
        questionsList.add(que3)

        // 4
        val que4 = Question(
            1,
            R.drawable.natyla_full,
            carrie,
            cupofstyle,
            natyla,
            shopaholic,
            3
        )
        questionsList.add(que4)

        // 5
        val que5 = Question(
            1,
            R.drawable.wedry_full,
            artix,
            wedry,
            house,
            geekboy,
            2
        )
        questionsList.add(que5)

        // 6
        val que6 = Question(
            1,
            R.drawable.shopaholic_full,
            shopaholic,
            cupofstyle,
            carrie,
            natyla,
            1
        )
        questionsList.add(que6)

        // 7
        val que7 = Question(
            1,
            R.drawable.lukefry_full,
            gejmr,
            artix,
            lukefry,
            house,
            3
        )
        questionsList.add(que7)

        // 8
        val que8 = Question(
            1,
            R.drawable.geekboy_full,
            artix,
            gejmr,
            house,
            geekboy,
            4
        )
        questionsList.add(que8)

        // 9
        val que9 = Question(
            1,
            R.drawable.carrie_full,
            shopaholic,
            cupofstyle,
            carrie,
            natyla,
            3
        )
        questionsList.add(que9)

        // 10
        val que10 = Question(
            1,
            R.drawable.cupofstyle_full,
            natyla,
            cupofstyle,
            carrie,
            shopaholic,
            2
        )
        questionsList.add(que10)


        return questionsList.shuffled() as ArrayList<Question>
    }
}