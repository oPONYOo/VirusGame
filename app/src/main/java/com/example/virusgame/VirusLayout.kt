package com.example.virusgame

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout

class VirusLayout(context: MainActivity, attrs: AttributeSet? = null) : ConstraintLayout(context, attrs) {
    private var mTxtView: AppCompatTextView
    private var mCardLayout: ConstraintLayout
    private var mQuestNum =" 0"
    private var mX = 0.0f
    private var mY = 0.0f
    var mWidth = 0
    private var check = 0
    private lateinit var tag: String

    init {
        val inflaterService = Context.LAYOUT_INFLATER_SERVICE
        val inflater = context.getSystemService(inflaterService) as LayoutInflater
        val view = inflater.inflate(R.layout.item_virusimage, this, false)
        addView(view)

        mCardLayout = view.findViewById(R.id.cardLayout)
        mTxtView = view.findViewById(R.id.numberTxtView)
        mWidth = view.layoutParams.width
    }

    fun setQuestNum(questNum: String): VirusLayout {
        this.mQuestNum = questNum

        return this
    }



    fun setQuestImg(): VirusLayout{
        this.mCardLayout.setBackgroundResource(R.drawable.img_game_memory_001)
        return this

    }



    fun setXY(x: Float, y: Float): VirusLayout {
        this.mX = x
        this.mY = y
        return this
    }

    fun setFinally() {
        x = this.mX
        y = this.mY
        if (this.mQuestNum != ""){
            mTxtView.text = this.mQuestNum.toString()
        }else{
            mTxtView.text = "  "
        }
    }

    fun setTxtSize(txtSize: Float) {
        mTxtView.textSize = txtSize
    }

    fun currentNumber(currentNum: Int, difficulty: Boolean) {
        Log.e("currentNum", "${currentNum}")
        if (mTxtView.text!=""&&currentNum == mTxtView.text.toString().toInt() && difficulty){

            mTxtView.setTextColor(Color.parseColor("#3FB5B5"))
        }else{
            mTxtView.setTextColor(Color.parseColor("#B2ADDDB9"))
        }

    }




//    fun cardVisible(){
//        if (this.mQuestNum !=0){
//            mCardLayout.visibility = View.VISIBLE
//        }else{
//            mCardLayout.visibility = View.INVISIBLE
//        }
//    }


    fun setVisible(visible: Boolean) {
        mCardLayout.visibility = if(visible) View.VISIBLE else View.GONE
        mTxtView.visibility = if (visible) View.VISIBLE else View.GONE
    }

}