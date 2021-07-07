package com.example.virusgame

import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.virusgame.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.util.*


class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
    private var mCurrentNum = 0
    private var mStimulate = 0
    private var mShowTime = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initGame(1)

    }

    fun initGame(rstGmLevel: Int) {
        when (rstGmLevel) {
            1 -> initSSL(4, 5)
            2 -> initSSL(4, 4)
            3 -> initSSL(6, 6)
            4 -> initSSL(6, 5)
            5 -> initSSL(6, 7)
            6 -> initSSL(6, 6)
            7 -> initSSL(6, 7)
            else -> initSSL(6, 8)
        }


        CoroutineScope(mainDispatcher).launch {
            delay(1000L)
            genQuest()

        }
    }

    private fun genRandom(num: Int): Int {
        return Random().nextInt(num - 10) + 10
    }



    private fun genQuest() {
        mCurrentNum = 1
        val areaWidth = binding.questAreaLayout.width
        val areaHeight = binding.questAreaLayout.height
        var i = 0
        while (i < mStimulate) {
            val questLayout = VirusLayout(this)
            val length = questLayout.mWidth
            val mGenCoordX = genRandom(areaWidth - length - 60)
            val mGenCoordY = genRandom(areaHeight - length - 60)
            questLayout.setQuestNum(i + 1).setXY(mGenCoordX.toFloat(), mGenCoordY.toFloat())
                .setVisible(true)
            questLayout.setFinally()
            Log.e("first", "$i")
            binding.questAreaLayout.addView(questLayout)
            if (i != 0) {
                for (j in 0 until i) {
                    val prevX = binding.questAreaLayout.getChildAt(j).x.toInt()
                    val prevY = binding.questAreaLayout.getChildAt(j).y.toInt()
                    val currX = binding.questAreaLayout.getChildAt(i).x.toInt()
                    val currY = binding.questAreaLayout.getChildAt(i).y.toInt()
                    if (currX >= prevX - length &&
                        currX <= prevX + length &&
                        currY >= prevY - length &&
                        currY <= prevY + length) {
                        binding.questAreaLayout.removeViewAt(i)
                        i--
                        break
                    }
                }
            }
            i++
        }

        CoroutineScope(mainDispatcher).launch {
            delay(mShowTime * 800L)
            for (j in 0 until mStimulate) {
                val questLayout = binding.questAreaLayout.getChildAt(j) as VirusLayout
                val questTxtView: AppCompatTextView = questLayout.findViewById(R.id.numberTxtView)
                val background: ConstraintLayout = questLayout.findViewById(R.id.cardLayout)
                questLayout.setOnClickListener {
                    Log.e("TAG", "mCurrentNum =" + mCurrentNum + " | " + questTxtView.text.toString())
                    questLayout.setOnClickListener(null)
                    var finish = false
                    if (mCurrentNum.toString() == questTxtView.text.toString()) {
                        questLayout.setVisible(true)
                        background.setBackgroundResource(R.drawable.img_game_memory_002)
                        if (mStimulate == mCurrentNum) {
                            finish = true
                        }
                        mCurrentNum++
                    } else {
                        finish = true
                    }
                    if(finish) {

                    }
                }
                background.setBackgroundResource(R.drawable.img_game_memory_001)
            }
        }
    }

    private fun initSSL(vararg cnts: Int) {
        mStimulate = cnts[0]
        mShowTime = cnts[1]
    }

}