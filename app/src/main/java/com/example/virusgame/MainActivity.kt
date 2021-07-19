package com.example.virusgame

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.virusgame.customview.OutlineTextView
import com.example.virusgame.databinding.ActivityMainBinding
import com.example.virusgame.dialog.DialogType
import com.example.virusgame.dialog.SharingDialog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.random.Random.Default.nextInt


class MainActivity : BaseActivity() {


    lateinit var binding: ActivityMainBinding

    private var randomlist = ArrayList<String>()
    private var randomlist2 = ArrayList<Int>()
    private var arraylist = ArrayList<String>()
    private var count = 1
    private var level = 8

    private var lastNum = 10
    private var num = ""
    private var num1 = ""
    private var num2 = ""
    private var num3 = ""
    private var num4 = ""
    private var num5 = ""
    private var num6 = ""
    var choiceNum = 0
    var choiceNum1 = 0
    var choiceNum2 = 0
    private lateinit var choiceView1: View
    private lateinit var choiceView2: View
    private var sign = "+"
    private var sign2 = "-"
    private var cnt = 0
    private var finishCnt = 0
    private var numquestLayout = ArrayList<VirusLayout>()
    var timerTextView: OutlineTextView? = null
    var timeLeft = 0L
    var isCorrect: Int = 0
    var scene: Int = 0
    var sound: Int = 0
    var calculationNum1 = 0
    var calculationNum2 = 0
    var mCompositeDisposable: CompositeDisposable? = null
    private var mainTimerDisposable: Disposable? = null
    private var mainSeconds = 0
    var timeLimit = 0
    var seconds = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        timeLimit = DEFAULT_TIME_LIMIT
        timeLeft = timeLimit * DEFAULT_SECOND
        initGame()
        timerTextView = binding.timerTxtView
        setTimerText(timerTextView!!, timeLeft)
        mainTimer(timeLimit)
    }

    private fun initGame() {
        mCompositeDisposable = CompositeDisposable()
        binding.levelImgView.text = ("${level}단계")

        arraylist = ArrayList()
        CoroutineScope(mainDispatcher).launch {
            delay(500L)
            Log.e("level", "$level")
            genNum(level)
            genQuest()
        }
    }

    fun rightAnswer() {
        isCorrect = CORRECT
        scene = R.drawable.img_game_o
    }

    fun wrongAnswer() {
        isCorrect = FAILED
        scene = R.drawable.img_game_x
    }

    private fun setTimerText(textView: OutlineTextView, remainedTime: Long) {
        val time = remainedTime / 1000
        val minute = time / 60
        val second = if (time % 60 >= 10) time % 60 else "0${(time % 60)}"

        textView.text = (" $minute : $second ")
    }


    private fun mainTimer(limit: Int) {
        val duration = Observable.interval(1, TimeUnit.SECONDS)
        mainTimerDisposable = duration.observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                mainSeconds++
                timeLeft -= 1000

                setTimerText(timerTextView!!, timeLeft)
                if (timeLeft <= 10000) timerTextView!!.startAnimation()
                if (mainSeconds == limit) {
                    mainSeconds--
                    setTimerText(timerTextView!!, 0)
                }

            }
        mCompositeDisposable!!.add(mainTimerDisposable!!)
    }


    fun showToast(scene: Int) {
        val dialog = showOxDialog(scene)
        CoroutineScope(mainDispatcher).launch {
            delay(600L)
            dialog.dismiss()
            delay(100L)
            if (binding.questAreaLayout.childCount != 0) {
                binding.questAreaLayout.removeAllViews()
                binding.questAreaLayout.requestLayout()
            }
            delay(800L)
            initGame()
        }
    }


    fun showOxDialog(img: Int): SharingDialog {
        val oxDialog = SharingDialog(this)
        oxDialog
            .setType(DialogType.GAME_OX)
            .setOxIcon(img)
            .window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        oxDialog
            .window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        /*.window!!.attributes.windowAnimations = R.anim.alpha_500*/
        oxDialog.setOwnerActivity(this)
        oxDialog.show()
        return oxDialog
    }

    private fun genNum(level: Int) {

        when (level) {
            1, 2 -> initNum(9)
            3, 4, 5 -> initNum(19)
            6, 7, 8 -> initNum(29)
        }


    }

    private fun initNum(max: Int) {
        val rand = Random()
        val randNum1 = (2 until max).random()
        val randNum2 = (2 until max).random()
        val randNum3 = (2 until max).random()
        when (level) {
            1 -> {
                num1 = randNum1.toString()
                num2 = num1
                num3 = randNum2.toString()
                do {
                    randomlist = ArrayList()
                    for (i in 2 until lastNum) {
                        num = i.toString()
                        randomlist.add(num)
                    }
                    randomlist.shuffle()
                    Log.e("randomList", "${randomlist}")
                    num3 = randomlist[0]
                } while(num1.toInt() == num3.toInt())
                val randNum3 = (1 until num3.toInt()).random()
                val calculationNum = num3.toInt() - randNum3
                num4 = randNum3.toString().plus(sign).plus(calculationNum)
            }
            2 -> {
                num1 = randNum1.toString()
                val randNum =(1 until num1.toInt() ).random()
                val calculationNum2 = num1.toInt() - randNum
                num2 = randNum.toString().plus(sign).plus(calculationNum2)
                num3 = randNum2.toString()
                do {
                    randomlist = ArrayList()
                    for (i in 2 until lastNum) {
                        num = i.toString()
                        randomlist.add(num)
                    }
                    randomlist.shuffle()
                    num3 = randomlist[0]
                    Log.e("randomlist", "$randomlist")
                } while(num1.toInt() == num3.toInt())
                val randNum4 = (1 until num3.toInt()).random()
                val calculationNum = num3.toInt() - randNum4
                num4 = randNum4.toString().plus(sign).plus(calculationNum)

            }
            3 -> {
                num1 = randNum1.toString()
                val randNum =(1 until num1.toInt() ).random()
                val calculationNum2 = num1.toInt() - randNum
                num2 = randNum.toString().plus(sign).plus(calculationNum2)
                num3 = randNum2.toString()
                num5 = randNum3.toString()
                do {
                    randomlist = ArrayList()
                    for (i in 2 until 20) {
                        num = i.toString()
                        randomlist.add(num)
                    }
                    randomlist.shuffle()
                    num3 = randomlist[0]
                    num5 = randomlist[1]
                    Log.e("randomlist", "$randomlist")
                } while(num1 == num3 || num3 == num5 || num1 == num5)
                val randNum4 = (1 until num3.toInt()).random()
                val calculationNum = num3.toInt() - randNum4
                val randNum5 = (1 until num5.toInt()).random()
                val calculationNum5 = num5.toInt() - randNum5
                num4 = randNum4.toString().plus(sign).plus(calculationNum)
                num6 = randNum5.toString().plus(sign).plus(calculationNum5)


            }
            4 -> {
                num1 = randNum1.toString()
                val randNum =(1 until num1.toInt() ).random()
                val calculationNum2 = num1.toInt() - randNum
                num2 = randNum.toString().plus(sign).plus(calculationNum2)
                num3 = randNum2.toString()
                num5 = (2 until 15).random().toString()
                do {
                    randomlist = ArrayList()
                    for (i in 2 until 20) {
                        num = i.toString()
                        randomlist.add(num)
                    }
                    val randomlistSec = ArrayList<String>()
                    for (i in 2 until 15) {
                        randomlistSec.add(i.toString())
                    }
                    randomlist.shuffle()
                    randomlistSec.shuffle()
                    num3 = randomlist[0]
                    num5 = randomlistSec[0]
                    Log.e("randomlist2", "$randomlistSec")
                    Log.e("randomlist", "$randomlist")
                } while(num1 == num3 || num3 == num5 || num1 == num5)
                val randNum4 = (1 until num3.toInt()).random()
                val calculationNum = num3.toInt() - randNum4
                val randNum5 = (1 until num5.toInt()).random()
                val calculationNum5 = num5.toInt() - randNum5
                num4 = randNum4.toString().plus(sign).plus(calculationNum)
                num6 = randNum5.toString().plus(sign).plus(calculationNum5)
                val randNum6 = (1 until num5.toInt()).random()
                val calculationNum6 = num5.toInt() + randNum6
                Log.e("calcul", "$calculationNum6")
                num6 = calculationNum6.toString().plus(sign2).plus(randNum6)
            }

            5 -> {
                num1 = randNum1.toString()
                var rand1 = (1 until num1.toInt() ).random()
                Log.e("rand1", "$rand1")
                var rand2 = rand.nextInt(rand1)
                Log.e("rand2", "$rand2")
                calculationNum1 = num1.toInt() - (rand1 - rand2)
                Log.e("calculationNum1", "$calculationNum1")
                num2 = rand1.toString().plus(sign2).plus(rand2).plus(sign)
                    .plus(calculationNum1)

                num3 = randNum2.toString()
                num5 = randNum3.toString()
                do {
                    randomlist = ArrayList()
                    for (i in 2 until 20) {
                        num = i.toString()
                        randomlist.add(num)
                    }
                    randomlist.shuffle()
                    num3 = randomlist[0]
                    num5 = randomlist[1]
                    Log.e("randomlist", "$randomlist")
                } while(num1 == num3 || num3 == num5 || num1 == num5)


                rand1 = (1 until num3.toInt() ).random()
                Log.e("rand1", "$rand1")
                rand2 = rand.nextInt(rand1)
                Log.e("rand2", "$rand2")
                calculationNum1 = num3.toInt() - (rand1 - rand2)
                Log.e("calculationNum1", "$calculationNum1")
                num4 = rand1.toString().plus(sign2).plus(rand2).plus(sign)
                    .plus(calculationNum1)

                var rand3 = (1 until max).random()
                Log.e("rand3", "$rand3")
                var rand4 = (1 until max).random()
                Log.e("rand4", "$rand4")
                do{
                    randomlist = ArrayList()
                    for (i in 2 until 20) {
                        num = i.toString()
                        randomlist.add(num)
                    }
                    randomlist.shuffle()
                    rand3 = randomlist[0].toInt()
                    rand4 = randomlist[1].toInt()
                    Log.e("rand3", "${rand3}")
                    Log.e("rand4", "${rand4}")
                }while ((rand3+rand4)<num5.toInt())
                calculationNum1 = (rand3 + rand4) - num5.toInt()
                Log.e("calculationNum1", "$calculationNum1")
                num6 = rand3.toString().plus(sign).plus(rand4).plus(sign2)
                    .plus(calculationNum1)

            }
            6 -> {
                num1 = randNum1.toString()
                var rand1 = (1 until max).random()
                var rand2 = (1 until max).random()
                var rand3 = (1 until max).random()

                do{
                    randomlist = ArrayList()
                    for (i in 2 until 30) {
                        num = i.toString()
                        randomlist.add(num)
                    }
                    randomlist.shuffle()
                    rand1 = randomlist[0].toInt()
                    rand2 = randomlist[1].toInt()
                    rand3 = randomlist[2].toInt()
                    Log.e("rand1", "${rand1}")
                    Log.e("rand2", "${rand2}")
                    Log.e("rand3", "${rand3}")
                }while (rand3 + (rand1 * rand2) < num1.toInt())
                calculationNum1 = rand3 + (rand1 * rand2)
                calculationNum2 = calculationNum1 - num1.toInt()
                Log.e("calculationNum1", "$calculationNum1")
                num2 = rand3.toString().plus(sign).plus("(")
                    .plus(rand1.toString()).plus("×").plus(rand2).plus(")").plus(sign2)
                    .plus(calculationNum2)


                num3 = randNum2.toString()
                num5 = randNum3.toString()
                do {
                    randomlist = ArrayList()
                    for (i in 2 until 30) {
                        num = i.toString()
                        randomlist.add(num)
                    }
                    randomlist.shuffle()
                    num3 = randomlist[0]
                    num5 = randomlist[1]
                    Log.e("randomlist", "$randomlist")
                } while(num1 == num3 || num3 == num5 || num1 == num5)

                rand1 = (1 until max).random()
                rand2 = (1 until max).random()
                rand3 = (1 until max).random()
                do{
                    randomlist = ArrayList()
                    for (i in 2 until 30) {
                        num = i.toString()
                        randomlist.add(num)
                    }
                    randomlist.shuffle()
                    rand1 = randomlist[0].toInt()
                    rand2 = randomlist[1].toInt()
                    rand3 = randomlist[2].toInt()
                    Log.e("rand1", "${rand1}")
                    Log.e("rand2", "${rand2}")
                    Log.e("rand3", "${rand3}")
                }while (rand3 + (rand1 * rand2) < num3.toInt())
                calculationNum1 = rand3 + (rand1 * rand2)
                calculationNum2 = calculationNum1 - num3.toInt()
                Log.e("calculationNum1", "$calculationNum1")
                num4 = rand3.toString().plus(sign).plus("(")
                    .plus(rand1.toString()).plus("×").plus(rand2).plus(")").plus(sign2)
                    .plus(calculationNum2)


                rand1 = (1 until max).random()
                rand2 = (1 until max).random()
                rand3 = (1 until max).random()
                do{
                    randomlist = ArrayList()
                    for (i in 2 until 30) {
                        num = i.toString()
                        randomlist.add(num)
                    }
                    randomlist.shuffle()
                    rand1 = randomlist[0].toInt()
                    rand2 = randomlist[1].toInt()
                    rand3 = randomlist[2].toInt()
                }while (((rand1 * rand2) + rand3) < num5.toInt())
                Log.e("rand1", "$rand1")
                Log.e("rand2", "$rand2")
                Log.e("rand3", "$rand3")

                calculationNum1 = ((rand1 * rand2) + rand3)
                calculationNum2 = calculationNum1 - num5.toInt()
                Log.e("calculationNum1", "$calculationNum1")
                num6 = "(".plus(rand1.toString()).plus("×").plus(rand2).plus(")").plus(sign)
                    .plus(rand3).plus(sign2).plus(calculationNum2)
            }
            7 -> {
                randomlist2 = ArrayList()
                num1 = randNum1.toString()
                Log.e("num1", num1)
                var rand1 = (1 until 10).random()
                Log.e("rand1", "${rand1}")
                calculationNum1 = num1.toInt() * rand1//24*5=120 -> 10,12
                Log.e("calculationNum1", "${calculationNum1}")

                for (i in 1..calculationNum1) {
                    if (calculationNum1 % i == 0) {
                        randomlist2.add(i)
                    }
                }
                randomlist2.shuffle()
                Log.e("Rand1", "$randomlist2")
                var rand3 = rand.nextInt(calculationNum1 / randomlist2[0])
                Log.e("rand3", "$rand3")
                num2 =
                    "(".plus(rand3).plus(sign).plus(calculationNum1 / randomlist2[0] - rand3)
                        .plus(")").plus("×").plus(randomlist2[0])
                        .plus("÷")
                        .plus(rand1)

                randomlist2 = ArrayList()
                var randNum = (1 until max).random()
                num5 = randNum2.toString()
                do {
                    randomlist = ArrayList()
                    for (i in 2 until 30) {
                        num = i.toString()
                        randomlist.add(num)
                    }
                    randomlist.shuffle()
                    randNum = randomlist[0].toInt()
                    num5 = randomlist[1]
                    Log.e("randomlist", "$randomlist")
                } while(num1.toInt() == randNum || num1 == num5 || randNum == num5.toInt())
                rand1 = (1 until 10).random()
                calculationNum1 = randNum * rand1

                for (i in 1..calculationNum1) {
                    if (calculationNum1 % i == 0) {
                        randomlist2.add(i)
                    }
                }
                randomlist2.shuffle()
                Log.e("Rand2", "$randomlist2")
                rand3 = rand.nextInt(calculationNum1 / randomlist2[0])
                val secondRand3 = rand.nextInt(calculationNum1 / randomlist2[1])
                num3 = "(".plus(secondRand3).plus(sign)
                    .plus(calculationNum1 / randomlist2[1] - secondRand3).plus(")").plus("×")
                    .plus(randomlist2[1])
                    .plus("÷")
                    .plus(rand1)
                num4 =
                    "(".plus(rand3).plus(sign).plus(calculationNum1 / randomlist2[0] - rand3)
                        .plus(")").plus("×").plus(randomlist2[0])
                        .plus("÷")
                        .plus(rand1)


                randomlist2 = ArrayList()
                Log.e("num5", num5)
                rand1 = (2 until 15).random()
                calculationNum1 = num5.toInt() * rand1
                Log.e("C", "$calculationNum1")
                for (i in 1..randNum2) {
                    if (randNum2 % i == 0) {
                        randomlist2.add(i)
                    }
                }
                randomlist2.shuffle()
                Log.e("Rand3", "$randomlist2")
                rand3 = (1 until 5).random()

                do {
                    randomlist = ArrayList()
                    for (i in 1 until 5) {
                        num = i.toString()
                        randomlist.add(num)
                    }
                    randomlist.shuffle()
                    rand3 = randomlist[0].toInt()
                    Log.e("randomlist", "$randomlist")
                } while((calculationNum1 / randomlist2[0]) * rand3 < num5.toInt())
                calculationNum2 = (calculationNum1 / randomlist2[0]) * rand3 - num5.toInt()
                num6 =
                    calculationNum1.toString().plus("÷").plus(randomlist2[0]).plus("×").plus(rand3)
                        .plus(sign2)
                        .plus(calculationNum2)
            }
            8 -> {
                randomlist2 = ArrayList()
                var rNum1 = (2 until max).random()
                var rand1 = (1 until 5).random()
                var secRand1 = (1 until 5).random()
                Log.e("rand1", "${rand1}")
                calculationNum1 = rNum1 * rand1
                Log.e("calculationNum1", "${calculationNum1}")

                for (i in 1..rNum1) {
                    if (rNum1 % i == 0) {
                        randomlist2.add(i)
                    }
                }
                randomlist2.shuffle()
                Log.e("Rand1", "$randomlist2")

                var rand2 = (30 until 60).random()
                var secRand2 = (30 until 60).random()
                calculationNum2 =
                    ((calculationNum1 * randomlist2[0]) / randomlist2[1]) + rand2 - rNum1
                var secCalNum2 =
                    (((rNum1 * secRand1) * randomlist2[1]) / randomlist2[0]) + secRand2 - rNum1
                num1 = (rNum1 * secRand1).toString().plus("×").plus(randomlist2[1]).plus("÷")
                    .plus(randomlist2[0])
                    .plus(sign).plus(secRand2).plus(sign2).plus(secCalNum2)
                num2 =
                    calculationNum1.toString().plus("×").plus(randomlist2[0]).plus("÷")
                        .plus(randomlist2[1])
                        .plus(sign).plus(rand2).plus(sign2).plus(calculationNum2)

                randomlist2 = ArrayList()
                var rNum2 = (2 until max).random()
                num5 = randNum3.toString()
                do {
                    randomlist = ArrayList()
                    for (i in 2 until 30) {
                        num = i.toString()
                        randomlist.add(num)
                    }
                    randomlist.shuffle()
                    rNum2 = randomlist[0].toInt()
                    num5 = randomlist[1]
                    Log.e("randomlist", "$randomlist")
                } while(rNum1 == rNum2 || rNum1 == num5.toInt() || rNum2 == num5.toInt())
                rand1 = (1 until 5).random()
                secRand1 = (1 until 5).random()
                Log.e("rand1", "${rand1}")
                calculationNum1 = rNum2 * rand1
                Log.e("calculationNum1", "${calculationNum1}")

                for (i in 1..rNum2) {
                    if (rNum2 % i == 0) {
                        randomlist2.add(i)
                    }
                }
                randomlist2.shuffle()
                Log.e("Rand1", "$randomlist2")
                rand2 = (30 until 60).random()
                secRand2 = (30 until 60).random()
                calculationNum2 =
                    ((calculationNum1 * randomlist2[0]) / randomlist2[1]) + rand2 - rNum2
                secCalNum2 =
                    (((rNum2 * secRand1) * randomlist2[1]) / randomlist2[0]) + secRand2 - rNum2
                num3 = (rNum2 * secRand1).toString().plus("×").plus(randomlist2[1]).plus("÷")
                    .plus(randomlist2[0])
                    .plus(sign).plus(secRand2).plus(sign2).plus(secCalNum2)
                num4 =
                    calculationNum1.toString().plus("×").plus(randomlist2[0]).plus("÷")
                        .plus(randomlist2[1])
                        .plus(sign).plus(rand2).plus(sign2).plus(calculationNum2)

                randomlist2 = ArrayList()
                rand1 = (30 until 60).random()
                rand2 = (1 until 5).random()
                Log.e("rand1", "${rand1}")
                calculationNum1 = randNum3 * rand2
                Log.e("calculationNum1", "${calculationNum1}")

                for (i in 1..randNum3) {
                    if (randNum3 % i == 0) {
                        randomlist2.add(i)
                    }
                }
                randomlist2.shuffle()
                Log.e("Rand1", "$randomlist2")

                calculationNum2 =
                    rand1 + (calculationNum1 * randomlist2[0]) / randomlist2[1] - num5.toInt()
                num6 = rand1.toString().plus(sign).plus("(").plus(calculationNum1).plus("×")
                    .plus(randomlist2[0]).plus(")")
                    .plus("÷").plus(randomlist2[1]).plus(sign2).plus(calculationNum2)

            }
        }
        when (level) {
            1, 2 -> {
                finishCnt = 2
                arraylist.add(num1)
                arraylist.add(num2)
                arraylist.add(num3)
                arraylist.add(num4)
                Log.e("arraylist", "$arraylist")
            }
            else -> {
                finishCnt = 3
                arraylist.add(num1)
                arraylist.add(num2)
                arraylist.add(num3)
                arraylist.add(num4)
                arraylist.add(num5)
                arraylist.add(num6)
                Log.e("arraylist", "$arraylist")
            }
        }

    }

    private fun genRandom(num: Int): Int {
        return Random().nextInt(num - 10) + 10
    }


    private fun genQuest() {
        cnt = 0
        count = 1
        val areaWidth = binding.questAreaLayout.width
        val areaHeight = binding.questAreaLayout.height
        var i = 0
        while (i in 0 until arraylist.size) {
            val questLayout = VirusLayout(this)
            val length = questLayout.mWidth
            val mGenCoordX = genRandom(areaWidth - length - 60)
            val mGenCoordY = genRandom(areaHeight - length - 60)
            questLayout.setQuestNum(arraylist[i]).setXY(mGenCoordX.toFloat(), mGenCoordY.toFloat())
                .setVisible(true)
            questLayout.setFinally()
            questLayout.tag = i
            Log.e("ii", "$i")

            binding.questAreaLayout.addView(questLayout)
            numquestLayout.add(questLayout)
            questLayout.setOnClickListener(onClickListener)
            if (i != 0) {
                for (j in 0 until i) {
                    val prevX = binding.questAreaLayout.getChildAt(j).x.toInt()
                    val prevY = binding.questAreaLayout.getChildAt(j).y.toInt()
                    val currX = binding.questAreaLayout.getChildAt(i).x.toInt()
                    val currY = binding.questAreaLayout.getChildAt(i).y.toInt()
                    if (currX >= prevX - length &&
                        currX <= prevX + length &&
                        currY >= prevY - length &&
                        currY <= prevY + length
                    ) {
                        binding.questAreaLayout.removeViewAt(i)
                        i--
                        break
                    }
                }
            }
            i++
        }


    }

    fun setOnClick(view: View) {
        view.setOnClickListener(onClickListener)
    }

    private var onClickListener = object : View.OnClickListener {
        override fun onClick(view: View?) {
            Log.e("view", "${view!!.tag}")
            val tag = view.tag
            Log.e("tag", "$tag")
            val questLayout = binding.questAreaLayout.getChildAt(tag as Int) as VirusLayout
            questLayout.setVisible(true)
            val background: ConstraintLayout = questLayout.findViewById(R.id.cardLayout)
            background.setBackgroundResource(R.drawable.img_game_memory_002)
            if (count == 1) {
                val num1 = arraylist[tag]
                choiceView1 = view
                choiceView1.setOnClickListener(null)
                if (num1.contains(sign) || num1.contains(sign2)) {
                    splitNum(num1)
                    choiceNum1 = choiceNum

                } else {
                    choiceNum1 = num1.toInt()
                    Log.e("choiceNum1", "$choiceNum1")
                }
                count++
            } else {
                val num2 = arraylist[tag]
                choiceView2 = view
                choiceView2.setOnClickListener(null)
                if (num2.contains(sign) || num2.contains(sign2)) {
                    splitNum(num2)
                    choiceNum2 = choiceNum
                } else {
                    choiceNum2 = num2.toInt()
                    Log.e("choiceNum2", "$choiceNum2")
                }
                if (choiceNum1 == choiceNum2) {
                    cnt++
                    count = 1
                    background.setBackgroundResource(R.drawable.img_game_memory_002)
                    window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    CoroutineScope(mainDispatcher).launch {
                        delay(500L)
                        choiceView1.visibility = View.INVISIBLE
                        choiceView2.visibility = View.INVISIBLE
                        for (j in 0 until numquestLayout.size) {
                            numquestLayout[j].setQuestImg()
                        }
                        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        setOnClick(choiceView1)
                        setOnClick(choiceView2)
                    }
                    if (cnt == finishCnt) {
                        rightAnswer()
                        showToast(scene)
                    }

                } else {
                    wrongAnswer()
                    showToast(scene)
                }
            }

        }
    }

    private fun splitNum(num: String): Int {
        val spiltNum = num.split(sign, sign2, "×", "÷", "(", ")")
        Log.e("spiltNum", "$spiltNum")
        when (level) {
            1, 2, 3 -> {
                choiceNum = spiltNum[0].toInt() + spiltNum[1].toInt()
            }
            4 -> {
                choiceNum = if (arraylist.indexOf(num) == arraylist.lastIndex) {
                    spiltNum[0].toInt() - spiltNum[1].toInt()
                } else {
                    spiltNum[0].toInt() + spiltNum[1].toInt()
                }
            }
            5 -> {
                choiceNum = if (arraylist.indexOf(num) == arraylist.lastIndex) {
                    spiltNum[0].toInt() + spiltNum[1].toInt() - spiltNum[2].toInt()
                } else {
                    spiltNum[0].toInt() - spiltNum[1].toInt() + spiltNum[2].toInt()
                }
            }
            6 -> {
                choiceNum = if (arraylist.indexOf(num) == arraylist.lastIndex) {
                    (spiltNum[1].toInt() * spiltNum[2].toInt()) + spiltNum[4].toInt() - spiltNum[5].toInt()
                } else {
                    spiltNum[0].toInt() + (spiltNum[2].toInt() * spiltNum[3].toInt()) - spiltNum[5].toInt()
                }
            }
            7 -> {
                choiceNum = if (arraylist.indexOf(num) == arraylist.lastIndex) {
                    (spiltNum[0].toInt() / spiltNum[1].toInt()) * spiltNum[2].toInt() - spiltNum[3].toInt()
                } else {
                    (spiltNum[1].toInt() + (spiltNum[2].toInt())) * spiltNum[4].toInt() / spiltNum[5].toInt()
                }
            }
            8 -> {
                choiceNum = if (arraylist.indexOf(num) == arraylist.lastIndex) {
                    spiltNum[0].toInt() + ((spiltNum[2].toInt() * spiltNum[3].toInt()) / spiltNum[5].toInt()) - spiltNum[6].toInt()
                } else {
                    ((spiltNum[0].toInt() * spiltNum[1].toInt()) / spiltNum[2].toInt()) + spiltNum[3].toInt() - spiltNum[4].toInt()
                }

            }
        }
        Log.e("choiceNum", "$choiceNum")
        return choiceNum
    }

    companion object {

        private const val DEFAULT_TIME_LIMIT = 120
        private const val DEFAULT_LEVEL = 1
        private const val DEFAULT_SECOND = 1000L
        private const val DEFAULT_PROGRESS = 0
        private const val DEFAULT_PROGRESS_FINISH = 100

        private const val CORRECT = 1
        private const val FAILED = 0

    }

}