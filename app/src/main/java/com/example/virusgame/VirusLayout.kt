package com.example.virusgame

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout

class VirusLayout(context: Context, attrs: AttributeSet? = null) : ConstraintLayout(context, attrs) {
    private var mTxtView: AppCompatTextView
    private var mCardLayout: ConstraintLayout
    private var mQuestNum = 0
    private var mX = 0.0f
    private var mY = 0.0f
    var mWidth = 0

    init {
        val inflaterService = Context.LAYOUT_INFLATER_SERVICE
        val inflater = context.getSystemService(inflaterService) as LayoutInflater
        val view = inflater.inflate(R.layout.item_virusimage, this, false)
        addView(view)

        mCardLayout = view.findViewById(R.id.cardLayout)
        mTxtView = view.findViewById(R.id.numberTxtView)
        mWidth = view.layoutParams.width
    }

    fun setQuestNum(questNum: Int): VirusLayout {
        this.mQuestNum = questNum

        return this
    }

    fun setQuestImg(questNum: Bitmap, difficulty: Boolean): VirusLayout{
        if (!difficulty){
            this.mCardLayout.background = BitmapDrawable(resources, questNum)
        }
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
        if (this.mQuestNum !=0){
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




    fun cardVisible(){
        if (this.mQuestNum !=0){
            mCardLayout.visibility = View.VISIBLE
        }else{
            mCardLayout.visibility = View.INVISIBLE
        }
    }


    fun setVisible(visible: Boolean) {
        mTxtView.visibility = if(visible) View.VISIBLE else View.GONE
    }

}