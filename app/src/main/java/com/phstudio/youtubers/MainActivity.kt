package com.phstudio.youtubers

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.phstudio.youtubers.Quiz.QuizActivity
import com.phstudio.youtubers.R.*


private var animation_visible: Animation? = null
private var animation_visiblegone: Animation? = null

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        setupNavigation()
        quiz()

        MobileAds.initialize(this@MainActivity) {}
    }

    private fun setupNavigation() {
        val navView: BottomNavigationView = findViewById(id.nav_view)
        val cl_quiz = findViewById<ConstraintLayout>(id.cl_quiz)
        val cl_catchphrases = findViewById<ConstraintLayout>(id.cl_catchphrases)
        val cl_info = findViewById<ConstraintLayout>(id.cl_info)
        val cl_settings = findViewById<ConstraintLayout>(id.cl_settings)
        val tv_money = findViewById<TextView>(id.tv_money)

        animation_visible = AnimationUtils.loadAnimation(
            applicationContext,
            anim.visible
        )
        animation_visiblegone = AnimationUtils.loadAnimation(
            applicationContext,
            anim.visiblegone
        )

        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                id.quiz -> {
                    cl_quiz.visibility = View.VISIBLE
                    cl_quiz.startAnimation(animation_visible)
                    cl_catchphrases.visibility = View.GONE
                    cl_info.visibility = View.GONE
                    cl_settings.visibility = View.GONE
                    tv_money.visibility = View.VISIBLE
                    quiz()
                    true
                }
                id.catchphrases -> {
                    cl_quiz.visibility = View.GONE
                    cl_catchphrases.visibility = View.VISIBLE
                    cl_catchphrases.startAnimation(animation_visible)
                    cl_info.visibility = View.GONE
                    cl_settings.visibility = View.GONE
                    tv_money.visibility = View.GONE
                    catchphrases()
                    true
                }
                id.info -> {
                    cl_quiz.visibility = View.GONE
                    cl_catchphrases.visibility = View.GONE
                    cl_info.visibility = View.VISIBLE
                    cl_info.startAnimation(animation_visible)
                    cl_settings.visibility = View.GONE
                    tv_money.visibility = View.GONE
                    info()
                    true
                }
                id.settings -> {
                    cl_quiz.visibility = View.GONE
                    cl_catchphrases.visibility = View.GONE
                    cl_info.visibility = View.GONE
                    cl_settings.visibility = View.VISIBLE
                    cl_settings.startAnimation(animation_visible)
                    tv_money.visibility = View.GONE
                    settings()
                    true
                }
                else -> true
            }
        }
    }

    @SuppressLint("CommitPrefEdits", "NewApi")
    private fun quiz() {
        val sharedPreferences = getSharedPreferences(
            resources.getString(string.app_package),
            Context.MODE_PRIVATE
        )
        val editor = getSharedPreferences(
            resources.getString(string.app_package),
            Context.MODE_PRIVATE
        ).edit()
        val total = sharedPreferences.getInt("money", 0)
        val tv_money = findViewById<TextView>(id.tv_money)
        if (total != 0) {
            tv_money.text = total.toString()
        } else {
            tv_money.text = ("0")
        }


        //Level1
        val cl_level1 = findViewById<ConstraintLayout>(id.cl_level1)
        val tv_score_bt1 = findViewById<TextView>(id.tv_score_bt1)
        val scorelevel1 = sharedPreferences.getInt("score1", 0)
        tv_score_bt1.text = ("$scorelevel1/10")
        cl_level1.setOnClickListener {
            val intent = Intent(this@MainActivity, QuizActivity::class.java)
            intent.putExtra("level", 1)
            startActivity(intent)
            finish()
        }

        //Level 2
        val level2 = sharedPreferences.getBoolean("level2", false)
        val cl_level2 = findViewById<ConstraintLayout>(id.cl_level2)
        val iv_score_bt2 = findViewById<ImageView>(id.iv_score_bt2)
        val tv_score_bt2 = findViewById<TextView>(id.tv_score_bt2)
        val scorelevel2 = sharedPreferences.getInt("score2", 0)
        tv_score_bt2.text = ("$scorelevel2/10")
        val tv_money_bt2 = findViewById<TextView>(id.tv_money_bt2)
        val iv_money_bt2 = findViewById<ImageView>(id.iv_money_bt2)

        if (level2) {
            iv_score_bt2.visibility = View.INVISIBLE
            tv_score_bt2.visibility = View.VISIBLE
            tv_money_bt2.visibility = View.INVISIBLE
            iv_money_bt2.visibility = View.INVISIBLE
            cl_level2.setBackgroundColor(Color.parseColor("#43A047"))
        }
        cl_level2.setOnClickListener {
            if (level2) {
                val intent = Intent(this@MainActivity, QuizActivity::class.java)
                intent.putExtra("level", 2)
                startActivity(intent)
                finish()
            } else {
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setIcon(drawable.unlock)
                dialogBuilder.setMessage(resources.getString(string.level_2_buy))
                    .setCancelable(false)
                    .setPositiveButton(
                        resources.getString(string.Yes)
                    ) { dialog, _ ->
                        if (total >= 10) {
                            val totalmoney = total - 10
                            iv_score_bt2.visibility = View.INVISIBLE
                            tv_score_bt2.visibility = View.VISIBLE
                            tv_money_bt2.visibility = View.INVISIBLE
                            iv_money_bt2.visibility = View.INVISIBLE
                            cl_level2.setBackgroundColor(Color.parseColor("#43A047"))
                            editor.putInt("money", totalmoney).apply()
                            editor.putBoolean("level2", true).apply()
                            dialog.cancel()
                            quiz()
                        } else {
                            Toast.makeText(this, getString(string.no_money), Toast.LENGTH_SHORT)
                                .show()
                            dialog.cancel()
                        }
                    }
                    .setNegativeButton(
                        resources.getString(string.No)
                    ) { dialog, _ ->
                        dialog.cancel()
                    }
                val alert = dialogBuilder.create()
                alert.setTitle(resources.getString(string.buy))
                alert.show()
            }
        }

        //Level 3
        val level3 = sharedPreferences.getBoolean("level3", false)
        val cl_level3 = findViewById<ConstraintLayout>(id.cl_level3)
        val iv_score_bt3 = findViewById<ImageView>(id.iv_score_bt3)
        val tv_score_bt3 = findViewById<TextView>(id.tv_score_bt3)
        val scorelevel3 = sharedPreferences.getInt("score3", 0)
        tv_score_bt3.text = ("$scorelevel3/10")
        val tv_money_bt3 = findViewById<TextView>(id.tv_money_bt3)
        val iv_money_bt3 = findViewById<ImageView>(id.iv_money_bt3)

        if (level3) {
            iv_score_bt3.visibility = View.INVISIBLE
            tv_money_bt3.visibility = View.INVISIBLE
            iv_money_bt3.visibility = View.INVISIBLE
            tv_score_bt3.visibility = View.VISIBLE
            cl_level3.setBackgroundColor(Color.parseColor("#43A047"))
        }
        cl_level3.setOnClickListener {
            if (level3) {
                val intent = Intent(this@MainActivity, QuizActivity::class.java)
                intent.putExtra("level", 3)
                startActivity(intent)
                finish()
            } else {
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setIcon(drawable.unlock)
                dialogBuilder.setMessage(resources.getString(string.level_3_buy))
                    .setCancelable(false)
                    .setPositiveButton(
                        resources.getString(string.Yes)
                    ) { dialog, _ ->
                        if (total >= 25) {
                            val totalmoney = total - 25
                            iv_score_bt3.visibility = View.INVISIBLE
                            tv_score_bt3.visibility = View.VISIBLE
                            tv_money_bt3.visibility = View.INVISIBLE
                            iv_money_bt3.visibility = View.INVISIBLE
                            cl_level3.setBackgroundColor(Color.parseColor("#43A047"))
                            editor.putInt("money", totalmoney).apply()
                            editor.putBoolean("level3", true).apply()
                            dialog.cancel()
                            quiz()
                        } else {
                            Toast.makeText(this, getString(string.no_money), Toast.LENGTH_SHORT)
                                .show()
                            dialog.cancel()
                        }
                    }
                    .setNegativeButton(
                        resources.getString(string.No)
                    ) { dialog, _ ->
                        dialog.cancel()
                    }
                val alert = dialogBuilder.create()
                alert.setTitle(resources.getString(string.buy))
                alert.show()
            }
        }

        //Level 4
        val level4 = sharedPreferences.getBoolean("level4", false)
        val cl_level4 = findViewById<ConstraintLayout>(id.cl_level4)
        val iv_score_bt4 = findViewById<ImageView>(id.iv_score_bt4)
        val tv_score_bt4 = findViewById<TextView>(id.tv_score_bt4)
        val scorelevel4 = sharedPreferences.getInt("score4", 0)
        tv_score_bt4.text = ("$scorelevel4/10")
        val tv_money_bt4 = findViewById<TextView>(id.tv_money_bt4)
        val iv_money_bt4 = findViewById<ImageView>(id.iv_money_bt4)

        if (level4) {
            iv_score_bt4.visibility = View.INVISIBLE
            tv_score_bt4.visibility = View.VISIBLE
            tv_money_bt4.visibility = View.INVISIBLE
            iv_money_bt4.visibility = View.INVISIBLE
            cl_level4.setBackgroundColor(Color.parseColor("#43A047"))
        }
        cl_level4.setOnClickListener {
            if (level4) {
                val intent = Intent(this@MainActivity, QuizActivity::class.java)
                intent.putExtra("level", 4)
                startActivity(intent)
                finish()
            } else {
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setIcon(drawable.unlock)
                dialogBuilder.setMessage(resources.getString(string.level_4_buy))
                    .setCancelable(false)
                    .setPositiveButton(
                        resources.getString(string.Yes)
                    ) { dialog, _ ->
                        if (total >= 50) {
                            val totalmoney = total - 50
                            iv_score_bt4.visibility = View.INVISIBLE
                            tv_score_bt4.visibility = View.VISIBLE
                            tv_money_bt4.visibility = View.INVISIBLE
                            iv_money_bt4.visibility = View.INVISIBLE
                            cl_level4.setBackgroundColor(Color.parseColor("#43A047"))
                            editor.putInt("money", totalmoney).apply()
                            editor.putBoolean("level4", true).apply()
                            dialog.cancel()
                            quiz()
                        } else {
                            Toast.makeText(this, getString(string.no_money), Toast.LENGTH_SHORT)
                                .show()
                            dialog.cancel()
                        }
                    }
                    .setNegativeButton(
                        resources.getString(string.No)
                    ) { dialog, _ ->
                        dialog.cancel()
                    }
                val alert = dialogBuilder.create()
                alert.setTitle(resources.getString(string.buy))
                alert.show()
            }
        }

        //Level 5
        val level5 = sharedPreferences.getBoolean("level5", false)
        val cl_level5 = findViewById<ConstraintLayout>(id.cl_level5)
        val iv_score_bt5 = findViewById<ImageView>(id.iv_score_bt5)
        val tv_score_bt5 = findViewById<TextView>(id.tv_score_bt5)
        val scorelevel5 = sharedPreferences.getInt("score5", 0)
        tv_score_bt5.text = ("$scorelevel5/10")
        val tv_money_bt5 = findViewById<TextView>(id.tv_money_bt5)
        val iv_money_bt5 = findViewById<ImageView>(id.iv_money_bt5)

        if (level5) {
            iv_score_bt5.visibility = View.INVISIBLE
            tv_score_bt5.visibility = View.VISIBLE
            tv_money_bt5.visibility = View.INVISIBLE
            iv_money_bt5.visibility = View.INVISIBLE
            cl_level5.setBackgroundColor(Color.parseColor("#43A047"))
        }
        cl_level5.setOnClickListener {
            if (level5) {
                val intent = Intent(this@MainActivity, QuizActivity::class.java)
                intent.putExtra("level", 5)
                startActivity(intent)
                finish()
            } else {
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setIcon(drawable.unlock)
                dialogBuilder.setMessage(resources.getString(string.level_5_buy))
                    .setCancelable(false)
                    .setPositiveButton(
                        resources.getString(string.Yes)
                    ) { dialog, _ ->
                        if (total >= 70) {
                            val totalmoney = total - 70
                            iv_score_bt5.visibility = View.INVISIBLE
                            tv_score_bt5.visibility = View.VISIBLE
                            tv_money_bt5.visibility = View.INVISIBLE
                            iv_money_bt5.visibility = View.INVISIBLE
                            cl_level5.setBackgroundColor(Color.parseColor("#43A047"))
                            editor.putInt("money", totalmoney).apply()
                            editor.putBoolean("level5", true).apply()
                            dialog.cancel()
                            quiz()
                        } else {
                            Toast.makeText(this, getString(string.no_money), Toast.LENGTH_SHORT)
                                .show()
                            dialog.cancel()
                        }
                    }
                    .setNegativeButton(
                        resources.getString(string.No)
                    ) { dialog, _ ->
                        dialog.cancel()
                    }
                val alert = dialogBuilder.create()
                alert.setTitle(resources.getString(string.buy))
                alert.show()
            }
        }
    }

    private fun info() {
        val sv_info = findViewById<ScrollView>(id.sv_info)

        //viralbrothers
        val bt_info_viralbrothers = findViewById<Button>(id.bt_info_viralbrothers)
        val cl_info_viralbrothers = findViewById<ConstraintLayout>(id.cl_info_viralbrothers)
        val bt_info_text_viralbrothers = findViewById<Button>(id.bt_info_text_viralbrothers)
        bt_info_viralbrothers.setOnClickListener {
            sv_info.visibility = View.GONE
            cl_info_viralbrothers.visibility = View.VISIBLE
        }
        bt_info_text_viralbrothers.setOnClickListener {
            sv_info.visibility = View.VISIBLE
            cl_info_viralbrothers.visibility = View.GONE
        }

        //ment
        val bt_info_ment = findViewById<Button>(id.bt_info_ment)
        val cl_info_ment = findViewById<ConstraintLayout>(id.cl_info_ment)
        val bt_info_text_ment = findViewById<Button>(id.bt_info_text_ment)
        bt_info_ment.setOnClickListener {
            sv_info.visibility = View.GONE
            cl_info_ment.visibility = View.VISIBLE
        }
        bt_info_text_ment.setOnClickListener {
            sv_info.visibility = View.VISIBLE
            cl_info_ment.visibility = View.GONE
        }

        //gejmr
        val bt_info_gejmr = findViewById<Button>(id.bt_info_gejmr)
        val cl_info_gejmr = findViewById<ConstraintLayout>(id.cl_info_gejmr)
        val bt_info_text_gejmr = findViewById<Button>(id.bt_info_text_gejmr)
        bt_info_gejmr.setOnClickListener {
            sv_info.visibility = View.GONE
            cl_info_gejmr.visibility = View.VISIBLE
        }
        bt_info_text_gejmr.setOnClickListener {
            sv_info.visibility = View.VISIBLE
            cl_info_gejmr.visibility = View.GONE
        }

        //pedro
        val bt_info_pedro = findViewById<Button>(id.bt_info_pedro)
        val cl_info_pedro = findViewById<ConstraintLayout>(id.cl_info_pedro)
        val bt_info_text_pedro = findViewById<Button>(id.bt_info_text_pedro)
        bt_info_pedro.setOnClickListener {
            sv_info.visibility = View.GONE
            cl_info_pedro.visibility = View.VISIBLE
        }
        bt_info_text_pedro.setOnClickListener {
            sv_info.visibility = View.VISIBLE
            cl_info_pedro.visibility = View.GONE
        }

        //stejk
        val bt_info_stejk = findViewById<Button>(id.bt_info_stejk)
        val cl_info_stejk = findViewById<ConstraintLayout>(id.cl_info_stejk)
        val bt_info_text_stejk = findViewById<Button>(id.bt_info_text_stejk)
        bt_info_stejk.setOnClickListener {
            sv_info.visibility = View.GONE
            cl_info_stejk.visibility = View.VISIBLE
        }
        bt_info_text_stejk.setOnClickListener {
            sv_info.visibility = View.VISIBLE
            cl_info_stejk.visibility = View.GONE
        }

        //jirkakral
        val bt_info_jirkakral = findViewById<Button>(id.bt_info_jirkakral)
        val cl_info_jirkakral = findViewById<ConstraintLayout>(id.cl_info_jirkakral)
        val bt_info_text_jirkakral = findViewById<Button>(id.bt_info_text_jirkakral)
        bt_info_jirkakral.setOnClickListener {
            sv_info.visibility = View.GONE
            cl_info_jirkakral.visibility = View.VISIBLE
        }
        bt_info_text_jirkakral.setOnClickListener {
            sv_info.visibility = View.VISIBLE
            cl_info_jirkakral.visibility = View.GONE
        }

        //house
        val bt_info_house = findViewById<Button>(id.bt_info_house)
        val cl_info_house = findViewById<ConstraintLayout>(id.cl_info_house)
        val bt_info_text_house = findViewById<Button>(id.bt_info_text_house)
        bt_info_house.setOnClickListener {
            sv_info.visibility = View.GONE
            cl_info_house.visibility = View.VISIBLE
        }
        bt_info_text_house.setOnClickListener {
            sv_info.visibility = View.VISIBLE
            cl_info_house.visibility = View.GONE
        }

        //kovy
        val bt_info_kovy = findViewById<Button>(id.bt_info_kovy)
        val cl_info_kovy = findViewById<ConstraintLayout>(id.cl_info_kovy)
        val bt_info_text_kovy = findViewById<Button>(id.bt_info_text_kovy)
        bt_info_kovy.setOnClickListener {
            sv_info.visibility = View.GONE
            cl_info_kovy.visibility = View.VISIBLE
        }
        bt_info_text_kovy.setOnClickListener {
            sv_info.visibility = View.VISIBLE
            cl_info_kovy.visibility = View.GONE
        }

    }

    private fun catchphrases() {
        val sv_catchphrases = findViewById<ScrollView>(id.sv_catchphrases)

        //ment
        val bt_catchphrases_ment = findViewById<Button>(id.bt_catchphrases_ment)
        val cl_catchphrases_ment = findViewById<ConstraintLayout>(id.cl_catchphrases_ment)
        val bt_catchphrases_text_ment = findViewById<Button>(id.bt_catchphrases_text_ment)
        bt_catchphrases_ment.setOnClickListener {
            sv_catchphrases.visibility = View.GONE
            cl_catchphrases_ment.visibility = View.VISIBLE
        }
        bt_catchphrases_text_ment.setOnClickListener {
            sv_catchphrases.visibility = View.VISIBLE
            cl_catchphrases_ment.visibility = View.GONE
        }
        val tv_catchphrases_ment1 = findViewById<Button>(id.tv_catchphrases_ment1)
        tv_catchphrases_ment1.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.ment1)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_ment2 = findViewById<Button>(id.tv_catchphrases_ment2)
        tv_catchphrases_ment2.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.ment2)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_ment3 = findViewById<Button>(id.tv_catchphrases_ment3)
        tv_catchphrases_ment3.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.ment3)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_ment4 = findViewById<Button>(id.tv_catchphrases_ment4)
        tv_catchphrases_ment4.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.ment4)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_ment5 = findViewById<Button>(id.tv_catchphrases_ment5)
        tv_catchphrases_ment5.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.ment5)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_ment6 = findViewById<Button>(id.tv_catchphrases_ment6)
        tv_catchphrases_ment6.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.ment6)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_ment7 = findViewById<Button>(id.tv_catchphrases_ment7)
        tv_catchphrases_ment7.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.ment7)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_ment8 = findViewById<Button>(id.tv_catchphrases_ment8)
        tv_catchphrases_ment8.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.ment8)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_ment9 = findViewById<Button>(id.tv_catchphrases_ment9)
        tv_catchphrases_ment9.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.ment9)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_ment10 = findViewById<Button>(id.tv_catchphrases_ment10)
        tv_catchphrases_ment10.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.ment10)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_ment11 = findViewById<Button>(id.tv_catchphrases_ment11)
        tv_catchphrases_ment11.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.ment11)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_ment12 = findViewById<Button>(id.tv_catchphrases_ment12)
        tv_catchphrases_ment12.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.ment12)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_ment13 = findViewById<Button>(id.tv_catchphrases_ment13)
        tv_catchphrases_ment13.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.ment13)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }

        //gejmr
        val bt_catchphrases_gejmr = findViewById<Button>(id.bt_catchphrases_gejmr)
        val cl_catchphrases_gejmr = findViewById<ConstraintLayout>(id.cl_catchphrases_gejmr)
        val bt_catchphrases_text_gejmr = findViewById<Button>(id.bt_catchphrases_text_gejmr)
        bt_catchphrases_gejmr.setOnClickListener {
            sv_catchphrases.visibility = View.GONE
            cl_catchphrases_gejmr.visibility = View.VISIBLE
        }
        bt_catchphrases_text_gejmr.setOnClickListener {
            sv_catchphrases.visibility = View.VISIBLE
            cl_catchphrases_gejmr.visibility = View.GONE
        }
        val tv_catchphrases_gejmr1 = findViewById<Button>(id.tv_catchphrases_gejmr1)
        tv_catchphrases_gejmr1.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.gejmr1)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_gejmr2 = findViewById<Button>(id.tv_catchphrases_gejmr2)
        tv_catchphrases_gejmr2.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.gejmr2)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_gejmr3 = findViewById<Button>(id.tv_catchphrases_gejmr3)
        tv_catchphrases_gejmr3.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.gejmr3)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_gejmr4 = findViewById<Button>(id.tv_catchphrases_gejmr4)
        tv_catchphrases_gejmr4.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.gejmr4)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_gejmr5 = findViewById<Button>(id.tv_catchphrases_gejmr5)
        tv_catchphrases_gejmr5.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.gejmr5)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_gejmr6 = findViewById<Button>(id.tv_catchphrases_gejmr6)
        tv_catchphrases_gejmr6.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.gejmr6)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_gejmr7 = findViewById<Button>(id.tv_catchphrases_gejmr7)
        tv_catchphrases_gejmr7.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.gejmr7)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_gejmr8 = findViewById<Button>(id.tv_catchphrases_gejmr8)
        tv_catchphrases_gejmr8.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.gejmr8)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_gejmr9 = findViewById<Button>(id.tv_catchphrases_gejmr9)
        tv_catchphrases_gejmr9.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.gejmr9)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_gejmr10 = findViewById<Button>(id.tv_catchphrases_gejmr10)
        tv_catchphrases_gejmr10.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.gejmr10)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_gejmr11 = findViewById<Button>(id.tv_catchphrases_gejmr11)
        tv_catchphrases_gejmr11.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.gejmr11)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_gejmr12 = findViewById<Button>(id.tv_catchphrases_gejmr12)
        tv_catchphrases_gejmr12.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.gejmr12)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_gejmr13 = findViewById<Button>(id.tv_catchphrases_gejmr13)
        tv_catchphrases_gejmr13.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.gejmr13)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_gejmr14 = findViewById<Button>(id.tv_catchphrases_gejmr14)
        tv_catchphrases_gejmr14.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.gejmr14)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_gejmr15 = findViewById<Button>(id.tv_catchphrases_gejmr15)
        tv_catchphrases_gejmr15.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.gejmr15)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_gejmr16 = findViewById<Button>(id.tv_catchphrases_gejmr16)
        tv_catchphrases_gejmr16.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.gejmr16)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_gejmr17 = findViewById<Button>(id.tv_catchphrases_gejmr17)
        tv_catchphrases_gejmr17.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.gejmr17)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_gejmr18 = findViewById<Button>(id.tv_catchphrases_gejmr18)
        tv_catchphrases_gejmr18.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.gejmr18)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_gejmr19 = findViewById<Button>(id.tv_catchphrases_gejmr19)
        tv_catchphrases_gejmr19.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.gejmr19)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_gejmr20 = findViewById<Button>(id.tv_catchphrases_gejmr20)
        tv_catchphrases_gejmr20.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.gejmr20)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }

        //pedro
        val bt_catchphrases_pedro = findViewById<Button>(id.bt_catchphrases_pedro)
        val cl_catchphrases_pedro = findViewById<ConstraintLayout>(id.cl_catchphrases_pedro)
        val bt_catchphrases_text_pedro = findViewById<Button>(id.bt_catchphrases_text_pedro)
        bt_catchphrases_pedro.setOnClickListener {
            sv_catchphrases.visibility = View.GONE
            cl_catchphrases_pedro.visibility = View.VISIBLE
        }
        bt_catchphrases_text_pedro.setOnClickListener {
            sv_catchphrases.visibility = View.VISIBLE
            cl_catchphrases_pedro.visibility = View.GONE
        }
        val tv_catchphrases_pedro1 = findViewById<Button>(id.tv_catchphrases_pedro1)
        tv_catchphrases_pedro1.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.pedro1)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_pedro2 = findViewById<Button>(id.tv_catchphrases_pedro2)
        tv_catchphrases_pedro2.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.pedro2)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_pedro3 = findViewById<Button>(id.tv_catchphrases_pedro3)
        tv_catchphrases_pedro3.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.pedro3)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_pedro4 = findViewById<Button>(id.tv_catchphrases_pedro4)
        tv_catchphrases_pedro4.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.pedro4)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_pedro5 = findViewById<Button>(id.tv_catchphrases_pedro5)
        tv_catchphrases_pedro5.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.pedro5)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_pedro6 = findViewById<Button>(id.tv_catchphrases_pedro6)
        tv_catchphrases_pedro6.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.pedro6)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_pedro7 = findViewById<Button>(id.tv_catchphrases_pedro7)
        tv_catchphrases_pedro7.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.pedro7)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_pedro8 = findViewById<Button>(id.tv_catchphrases_pedro8)
        tv_catchphrases_pedro8.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.pedro8)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_pedro9 = findViewById<Button>(id.tv_catchphrases_pedro9)
        tv_catchphrases_pedro9.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.pedro9)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_pedro10 = findViewById<Button>(id.tv_catchphrases_pedro10)
        tv_catchphrases_pedro10.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.pedro10)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_pedro11 = findViewById<Button>(id.tv_catchphrases_pedro11)
        tv_catchphrases_pedro11.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.pedro11)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_pedro12 = findViewById<Button>(id.tv_catchphrases_pedro12)
        tv_catchphrases_pedro12.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.pedro12)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_pedro13 = findViewById<Button>(id.tv_catchphrases_pedro13)
        tv_catchphrases_pedro13.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.pedro13)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }

        //jirkakral
        val bt_catchphrases_jirkakral = findViewById<Button>(id.bt_catchphrases_jirkakral)
        val cl_catchphrases_jirkakral = findViewById<ConstraintLayout>(id.cl_catchphrases_jirkakral)
        val bt_catchphrases_text_jirkakral = findViewById<Button>(id.bt_catchphrases_text_jirkakral)
        bt_catchphrases_jirkakral.setOnClickListener {
            sv_catchphrases.visibility = View.GONE
            cl_catchphrases_jirkakral.visibility = View.VISIBLE
        }
        bt_catchphrases_text_jirkakral.setOnClickListener {
            sv_catchphrases.visibility = View.VISIBLE
            cl_catchphrases_jirkakral.visibility = View.GONE
        }
        val tv_catchphrases_jirkakral1 = findViewById<Button>(id.tv_catchphrases_jirkakral1)
        tv_catchphrases_jirkakral1.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.jirkakral1)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_jirkakral2 = findViewById<Button>(id.tv_catchphrases_jirkakral2)
        tv_catchphrases_jirkakral2.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.jirkakral2)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_jirkakral3 = findViewById<Button>(id.tv_catchphrases_jirkakral3)
        tv_catchphrases_jirkakral3.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.jirkakral3)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_jirkakral4 = findViewById<Button>(id.tv_catchphrases_jirkakral4)
        tv_catchphrases_jirkakral4.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.jirkakral4)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_jirkakral5 = findViewById<Button>(id.tv_catchphrases_jirkakral5)
        tv_catchphrases_jirkakral5.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.jirkakral5)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_jirkakral6 = findViewById<Button>(id.tv_catchphrases_jirkakral6)
        tv_catchphrases_jirkakral6.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.jirkakral6)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_jirkakral7 = findViewById<Button>(id.tv_catchphrases_jirkakral7)
        tv_catchphrases_jirkakral7.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.jirkakral7)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_jirkakral8 = findViewById<Button>(id.tv_catchphrases_jirkakral8)
        tv_catchphrases_jirkakral8.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.jirkakral8)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_jirkakral9 = findViewById<Button>(id.tv_catchphrases_jirkakral9)
        tv_catchphrases_jirkakral9.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.jirkakral9)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_jirkakral10 = findViewById<Button>(id.tv_catchphrases_jirkakral10)
        tv_catchphrases_jirkakral10.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.jirkakral10)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_jirkakral11 = findViewById<Button>(id.tv_catchphrases_jirkakral11)
        tv_catchphrases_jirkakral11.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.jirkakral11)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_jirkakral12 = findViewById<Button>(id.tv_catchphrases_jirkakral12)
        tv_catchphrases_jirkakral12.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.jirkakral12)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_jirkakral13 = findViewById<Button>(id.tv_catchphrases_jirkakral13)
        tv_catchphrases_jirkakral13.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.jirkakral13)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }

//house
        val bt_catchphrases_house = findViewById<Button>(id.bt_catchphrases_house)
        val cl_catchphrases_house = findViewById<ConstraintLayout>(id.cl_catchphrases_house)
        val bt_catchphrases_text_house = findViewById<Button>(id.bt_catchphrases_text_house)
        bt_catchphrases_house.setOnClickListener {
            sv_catchphrases.visibility = View.GONE
            cl_catchphrases_house.visibility = View.VISIBLE
        }
        bt_catchphrases_text_house.setOnClickListener {
            sv_catchphrases.visibility = View.VISIBLE
            cl_catchphrases_house.visibility = View.GONE
        }
        val tv_catchphrases_house1 = findViewById<Button>(id.tv_catchphrases_house1)
        tv_catchphrases_house1.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.house1)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_house2 = findViewById<Button>(id.tv_catchphrases_house2)
        tv_catchphrases_house2.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.house2)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_house3 = findViewById<Button>(id.tv_catchphrases_house3)
        tv_catchphrases_house3.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.house3)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_house4 = findViewById<Button>(id.tv_catchphrases_house4)
        tv_catchphrases_house4.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.house4)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_house5 = findViewById<Button>(id.tv_catchphrases_house5)
        tv_catchphrases_house5.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.house5)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_house6 = findViewById<Button>(id.tv_catchphrases_house6)
        tv_catchphrases_house6.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.house6)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_house7 = findViewById<Button>(id.tv_catchphrases_house7)
        tv_catchphrases_house7.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.house7)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_house8 = findViewById<Button>(id.tv_catchphrases_house8)
        tv_catchphrases_house8.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.house8)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_house9 = findViewById<Button>(id.tv_catchphrases_house9)
        tv_catchphrases_house9.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.house9)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_house10 = findViewById<Button>(id.tv_catchphrases_house10)
        tv_catchphrases_house10.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.house10)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }

        //bax
        val bt_catchphrases_bax = findViewById<Button>(id.bt_catchphrases_bax)
        val cl_catchphrases_bax = findViewById<ConstraintLayout>(id.cl_catchphrases_bax)
        val bt_catchphrases_text_bax = findViewById<Button>(id.bt_catchphrases_text_bax)
        bt_catchphrases_bax.setOnClickListener {
            sv_catchphrases.visibility = View.GONE
            cl_catchphrases_bax.visibility = View.VISIBLE
        }
        bt_catchphrases_text_bax.setOnClickListener {
            sv_catchphrases.visibility = View.VISIBLE
            cl_catchphrases_bax.visibility = View.GONE
        }
        val tv_catchphrases_bax1 = findViewById<Button>(id.tv_catchphrases_bax1)
        tv_catchphrases_bax1.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.bax1)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_bax2 = findViewById<Button>(id.tv_catchphrases_bax2)
        tv_catchphrases_bax2.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.bax2)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_bax3 = findViewById<Button>(id.tv_catchphrases_bax3)
        tv_catchphrases_bax3.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.bax3)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_bax4 = findViewById<Button>(id.tv_catchphrases_bax4)
        tv_catchphrases_bax4.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.bax4)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_bax5 = findViewById<Button>(id.tv_catchphrases_bax5)
        tv_catchphrases_bax5.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.bax5)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_bax6 = findViewById<Button>(id.tv_catchphrases_bax6)
        tv_catchphrases_bax6.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.bax6)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_bax7 = findViewById<Button>(id.tv_catchphrases_bax7)
        tv_catchphrases_bax7.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.bax7)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_bax8 = findViewById<Button>(id.tv_catchphrases_bax8)
        tv_catchphrases_bax8.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.bax8)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_bax9 = findViewById<Button>(id.tv_catchphrases_bax9)
        tv_catchphrases_bax9.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.bax9)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_bax10 = findViewById<Button>(id.tv_catchphrases_bax10)
        tv_catchphrases_bax10.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.bax10)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_bax11 = findViewById<Button>(id.tv_catchphrases_bax11)
        tv_catchphrases_bax11.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.bax11)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_bax12 = findViewById<Button>(id.tv_catchphrases_bax12)
        tv_catchphrases_bax12.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.bax12)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }
        val tv_catchphrases_bax13 = findViewById<Button>(id.tv_catchphrases_bax13)
        tv_catchphrases_bax13.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer.create(applicationContext, raw.bax13)
            mp.start()
            mp.setOnCompletionListener { mp.release() }
        }


    }

    private fun settings() {

        val tv_developer_name = findViewById<TextView>(id.tv_developer_name)
        val tv_application_development = findViewById<TextView>(id.tv_application_development)
        val tv_about = findViewById<TextView>(id.tv_about)
        val tv_contact = findViewById<TextView>(id.tv_contact)
        val tv_report_bug = findViewById<TextView>(id.tv_report_bug)
        val tv_share = findViewById<TextView>(id.tv_share)
        val tv_apps = findViewById<TextView>(id.tv_apps)
        val tv_donate = findViewById<TextView>(id.tv_donate)
        val tv_privacy = findViewById<TextView>(id.tv_privacy)
        val tv_terms = findViewById<TextView>(id.tv_terms)
        val tv_settings = findViewById<TextView>(id.tv_settings)

        tv_developer_name.setOnClickListener {
            Toast.makeText(this, getString(string.developer_name), Toast.LENGTH_SHORT).show()
        }

        tv_application_development.setOnClickListener {
            Toast.makeText(this, getString(string.app_version), Toast.LENGTH_SHORT).show()
        }

        tv_about.setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(getString(string.about))
            )
            startActivity(browserIntent)
        }

        tv_contact.setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(getString(string.contact))
            )
            startActivity(browserIntent)
        }

        tv_report_bug.setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(getString(string.report))
            )
            startActivity(browserIntent)
        }

        tv_share.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(string.share_mail))
            intent.putExtra(Intent.EXTRA_TEXT, resources.getString(string.email_text))
            intent.type = "message/rfc822"
            startActivity(Intent.createChooser(intent, resources.getString(string.select_email)))
        }

        tv_apps.setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(getString(string.googleplay))
            )
            startActivity(browserIntent)
        }

        tv_donate.setOnClickListener {
            Toast.makeText(this, getString(string.donateblock), Toast.LENGTH_SHORT).show()
            /*val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(getString(string.donate))
            )
            startActivity(browserIntent)*/
        }

        tv_privacy.setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(getString(string.privacy))
            )
            startActivity(browserIntent)
        }

        tv_terms.setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(getString(string.terms))
            )
            startActivity(browserIntent)
        }

        tv_settings.setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(getString(string.rights))
            )
            startActivity(browserIntent)
        }
    }

    override fun onBackPressed() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setIcon(mipmap.ic_launcher_round)
        dialogBuilder.setMessage(resources.getString(string.close_app))
            .setCancelable(false)
            .setPositiveButton(
                resources.getString(string.Yes)
            ) { _, _ ->
                finishAffinity()
            }
            .setNegativeButton(
                resources.getString(string.No)
            ) { dialog, _ ->
                dialog.cancel()
            }
        val alert = dialogBuilder.create()
        alert.setTitle(resources.getString(string.Exit_app))
        alert.show()
    }
}