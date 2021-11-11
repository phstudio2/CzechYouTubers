package com.phstudio.youtubers.Quiz

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.phstudio.youtubers.MainActivity
import com.phstudio.youtubers.R.*

class QuizActivity : AppCompatActivity(), View.OnClickListener {

    private var PositionOfList: Int = 1
    private var QuestionsList: ArrayList<Question>? = null
    private var SelectedOptionPosition: Int = 0
    private var CorrectAnswers: Int = 0
    private var AnswersChecker: Int = 0
    private var mRewardedAd: RewardedAd? = null
    private var TAG = "YouTubersLOG"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.layout_quiz_level1)
        fullscreen()
        val level = intent.getIntExtra("level", 1)
        fullscreen()
        val bt_tryagain = findViewById<Button>(id.bt_tryagain)
        val btn_back = findViewById<Button>(id.btn_back)
        val btn_double = findViewById<Button>(id.btn_double)

        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(this,
            "ca-app-pub-9970129930390851/8360507727",
            //"ca-app-pub-3940256099942544/5224354917", //test
            adRequest, object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError.message)
                    mRewardedAd = null
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    Log.d(TAG, "Ad was loaded.")
                    mRewardedAd = rewardedAd
                    btn_double.visibility = View.VISIBLE
                }
            })

        mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad was shown.")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                // Called when ad fails to show.
                Log.d(TAG, "Ad failed to show.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Log.d(TAG, "Ad was dismissed.")
                mRewardedAd = null
            }
        }

        val sharedPreferences = getSharedPreferences(
            resources.getString(string.app_package),
            Context.MODE_PRIVATE
        )
        val editor = getSharedPreferences(
            resources.getString(string.app_package),
            Context.MODE_PRIVATE
        ).edit()
        val total = sharedPreferences.getInt("money", 0)
        var scorelevel = 0
        when (level) {
            1 -> {
                scorelevel = sharedPreferences.getInt("score1", 0)
            }
            2 -> {
                scorelevel = sharedPreferences.getInt("score2", 0)
            }
            3 -> {
                scorelevel = sharedPreferences.getInt("score3", 0)
            }
            4 -> {
                scorelevel = sharedPreferences.getInt("score4", 0)
            }
            5 -> {
                scorelevel = sharedPreferences.getInt("score5", 0)
            }

        }


        btn_double.setOnClickListener {

            if (mRewardedAd != null) {

                mRewardedAd?.show(this, OnUserEarnedRewardListener {
                    fun onUserEarnedReward(rewardItem: RewardItem) {
                        val rewardAmount = rewardItem.amount
                        val rewardType = rewardItem.type
                        editor.putInt("money", total + (CorrectAnswers * 2)).apply()
                        if (CorrectAnswers > scorelevel) {
                            when (level) {
                                1 -> {
                                    editor.putInt("score1", CorrectAnswers).apply()
                                }
                                2 -> {
                                    editor.putInt("score2", CorrectAnswers).apply()
                                }
                                3 -> {
                                    editor.putInt("score3", CorrectAnswers).apply()
                                }
                                4 -> {
                                    editor.putInt("score4", CorrectAnswers).apply()
                                }
                                5 -> {
                                    editor.putInt("score5", CorrectAnswers).apply()
                                }

                            }
                        }
                        startActivity(Intent(this@QuizActivity, MainActivity::class.java))
                        Log.d(
                            TAG,
                            "User earned the reward. TYPE -  $rewardType AMOUNT - $rewardAmount"
                        )
                    }
                    onUserEarnedReward(it)
                })
            } else {
                Log.d(TAG, "The rewarded ad wasn't ready yet.")
                Toast.makeText(
                    this@QuizActivity,
                    "The rewarded ad wasn't ready yet.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        bt_tryagain.setOnClickListener {
            editor.putInt("money", total + CorrectAnswers).apply()
            if (CorrectAnswers > scorelevel) {
                when (level) {
                    1 -> {
                        editor.putInt("score1", CorrectAnswers).apply()
                    }
                    2 -> {
                        editor.putInt("score2", CorrectAnswers).apply()
                    }
                    3 -> {
                        editor.putInt("score3", CorrectAnswers).apply()
                    }
                    4 -> {
                        editor.putInt("score4", CorrectAnswers).apply()
                    }
                    5 -> {
                        editor.putInt("score5", CorrectAnswers).apply()
                    }

                }
            }
            val intent = Intent(this@QuizActivity, QuizActivity::class.java)
            intent.putExtra("level", level)
            startActivity(intent)
        }

        btn_back.setOnClickListener {
            editor.putInt("money", total + CorrectAnswers).apply()
            if (CorrectAnswers > scorelevel) {
                when (level) {
                    1 -> {
                        editor.putInt("score1", CorrectAnswers).apply()
                    }
                    2 -> {
                        editor.putInt("score2", CorrectAnswers).apply()
                    }
                    3 -> {
                        editor.putInt("score3", CorrectAnswers).apply()
                    }
                    4 -> {
                        editor.putInt("score4", CorrectAnswers).apply()
                    }
                    5 -> {
                        editor.putInt("score5", CorrectAnswers).apply()
                    }

                }
            }
            startActivity(Intent(this@QuizActivity, MainActivity::class.java))
        }
        when {
            (level == 1) -> {
                QuestionsList = Constants.getQuestions()
            }
            (level == 2) -> {
                QuestionsList = Constants2.getQuestions()
            }
            (level == 3) -> {
                QuestionsList = Constants3.getQuestions()
            }
            (level == 4) -> {
                QuestionsList = Constants4.getQuestions()
            }
            (level == 5) -> {
                QuestionsList = Constants5.getQuestions()
            }

        }

        LoadQuestion()

        val tv_option_one = findViewById<TextView>(id.tv_option_one)
        val tv_option_two = findViewById<TextView>(id.tv_option_two)
        val tv_option_three = findViewById<TextView>(id.tv_option_three)
        val tv_option_four = findViewById<TextView>(id.tv_option_four)
        val btn_submit = findViewById<Button>(id.btn_submit)

        tv_option_one.setOnClickListener(this)
        tv_option_two.setOnClickListener(this)
        tv_option_three.setOnClickListener(this)
        tv_option_four.setOnClickListener(this)
        btn_submit.setOnClickListener(this)
    }

    fun fullscreen() {
        if (window.decorView.systemUiVisibility == View.SYSTEM_UI_FLAG_VISIBLE) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN
            }
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
    }

    override fun onClick(v: View?) {
        val tv_option_one = findViewById<TextView>(id.tv_option_one)
        val tv_option_two = findViewById<TextView>(id.tv_option_two)
        val tv_option_three = findViewById<TextView>(id.tv_option_three)
        val tv_option_four = findViewById<TextView>(id.tv_option_four)
        val btn_submit = findViewById<Button>(id.btn_submit)


        when (v?.id) {
            id.tv_option_one -> {
                SelectedView(tv_option_one, 1)
            }
            id.tv_option_two -> {
                SelectedView(tv_option_two, 2)
            }
            id.tv_option_three -> {
                SelectedView(tv_option_three, 3)
            }
            id.tv_option_four -> {
                SelectedView(tv_option_four, 4)
            }
            id.btn_submit -> {
                if (SelectedOptionPosition == 0) {
                    PositionOfList++
                    when {
                        PositionOfList <= QuestionsList!!.size -> {
                            LoadQuestion()
                        }
                        else -> {
                            val sl_quiz = findViewById<ScrollView>(id.sl_quiz)
                            val cl_result = findViewById<ConstraintLayout>(id.cl_result)
                            val tv_score = findViewById<TextView>(id.tv_score)
                            sl_quiz.visibility = View.GONE
                            cl_result.visibility = View.VISIBLE
                            tv_score.text =
                                (getString(string.score) + " $CorrectAnswers " + getString(string.of) + " ${QuestionsList!!.size}")
                            //finish()
                        }
                    }
                } else {
                    val question = QuestionsList?.get(PositionOfList - 1)
                    if (question!!.correctAnswer != SelectedOptionPosition) {
                        AnswerView(SelectedOptionPosition, drawable.wrong_option_border_bg)
                    } else {
                        if (AnswersChecker == 0) {
                            CorrectAnswers++
                            /*val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.good)
                             mp.start()
                             mp.setOnCompletionListener { mp.release() }*/
                        } else {
                            Toast.makeText(this, getString(string.answered), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    AnswerView(question.correctAnswer, drawable.correct_option_border_bg)
                    if (PositionOfList == QuestionsList!!.size) {
                        btn_submit.text = getString(string.finish)
                    } else {
                        btn_submit.text = getString(string.next)
                    }
                    SelectedOptionPosition = 0
                    AnswersChecker++
                }
            }
        }
    }

    private fun LoadQuestion() {
        val tv_option_one = findViewById<TextView>(id.tv_option_one)
        val tv_option_two = findViewById<TextView>(id.tv_option_two)
        val tv_option_three = findViewById<TextView>(id.tv_option_three)
        val tv_option_four = findViewById<TextView>(id.tv_option_four)
        val progressBar = findViewById<ProgressBar>(id.progressBar)
        val tv_progress = findViewById<TextView>(id.tv_progress)
        val btn_submit = findViewById<Button>(id.btn_submit)
        val iv_image = findViewById<ImageView>(id.iv_image)
        AnswersChecker = 0
        val question = QuestionsList!!.get(PositionOfList - 1)
        ResetView()
        if (PositionOfList == QuestionsList!!.size) {
            btn_submit.text = getString(string.finish)
        } else {
            btn_submit.text = getString(string.submit)
        }
        progressBar.progress = PositionOfList
        tv_progress.text = ("$PositionOfList" + "/" + progressBar.max)
        iv_image.setImageResource(question.image)
        tv_option_one.text = question.optionOne
        tv_option_two.text = question.optionTwo
        tv_option_three.text = question.optionThree
        tv_option_four.text = question.optionFour
    }

    private fun SelectedView(tv: TextView, selectedOptionNum: Int) {
        ResetView()
        SelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this@QuizActivity,
            drawable.selected_option_border_bg
        )
    }

    private fun ResetView() {
        val tv_option_one = findViewById<TextView>(id.tv_option_one)
        val tv_option_two = findViewById<TextView>(id.tv_option_two)
        val tv_option_three = findViewById<TextView>(id.tv_option_three)
        val tv_option_four = findViewById<TextView>(id.tv_option_four)

        val options = ArrayList<TextView>()
        options.add(0, tv_option_one)
        options.add(1, tv_option_two)
        options.add(2, tv_option_three)
        options.add(3, tv_option_four)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this@QuizActivity,
                drawable.default_option_border_bg
            )
        }
    }

    private fun AnswerView(answer: Int, drawableView: Int) {
        val tv_option_one = findViewById<TextView>(id.tv_option_one)
        val tv_option_two = findViewById<TextView>(id.tv_option_two)
        val tv_option_three = findViewById<TextView>(id.tv_option_three)
        val tv_option_four = findViewById<TextView>(id.tv_option_four)

        when (answer) {
            1 -> {
                tv_option_one.background = ContextCompat.getDrawable(
                    this@QuizActivity,
                    drawableView
                )
            }
            2 -> {
                tv_option_two.background = ContextCompat.getDrawable(
                    this@QuizActivity,
                    drawableView
                )
            }
            3 -> {
                tv_option_three.background = ContextCompat.getDrawable(
                    this@QuizActivity,
                    drawableView
                )
            }
            4 -> {
                tv_option_four.background = ContextCompat.getDrawable(
                    this@QuizActivity,
                    drawableView
                )
            }
        }
    }

    override fun onBackPressed() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setIcon(drawable.back)
        dialogBuilder.setMessage(resources.getString(string.end_quiz))
            .setCancelable(false)
            .setPositiveButton(
                resources.getString(string.Yes)
            ) { _, _ ->
                val sharedPreferences = getSharedPreferences(
                    resources.getString(string.app_package),
                    Context.MODE_PRIVATE
                )
                val editor = getSharedPreferences(
                    resources.getString(string.app_package),
                    Context.MODE_PRIVATE
                ).edit()
                val total = sharedPreferences.getInt("money", 0)
                var scorelevel = 0
                editor.putInt("money", total + CorrectAnswers).apply()
                val level = intent.getIntExtra("level", 1)
                when (level) {
                    1 -> {
                        scorelevel = sharedPreferences.getInt("score1", 0)
                    }
                    2 -> {
                        scorelevel = sharedPreferences.getInt("score2", 0)
                    }
                    3 -> {
                        scorelevel = sharedPreferences.getInt("score3", 0)
                    }
                    4 -> {
                        scorelevel = sharedPreferences.getInt("score4", 0)
                    }
                    5 -> {
                        scorelevel = sharedPreferences.getInt("score5", 0)
                    }

                }
                if (CorrectAnswers > scorelevel) {
                    when (level) {
                        1 -> {
                            editor.putInt("score1", CorrectAnswers).apply()
                        }
                        2 -> {
                            editor.putInt("score2", CorrectAnswers).apply()
                        }
                        3 -> {
                            editor.putInt("score3", CorrectAnswers).apply()
                        }
                        4 -> {
                            editor.putInt("score4", CorrectAnswers).apply()
                        }
                        5 -> {
                            editor.putInt("score5", CorrectAnswers).apply()
                        }

                    }
                }
                startActivity(Intent(this@QuizActivity, MainActivity::class.java))
            }
            .setNegativeButton(
                resources.getString(string.No)
            ) { dialog, _ ->
                dialog.cancel()
            }
        val alert = dialogBuilder.create()
        alert.setTitle(resources.getString(string.Exit_quiz))
        alert.show()
    }
}