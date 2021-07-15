package com.example.virusgame

import android.annotation.SuppressLint
import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * @since 2021.02.01.Mon
 * @author Thomas Park in ROWAN INC.
 */

private val TAG = BaseActivity::class.java.simpleName
open class BaseActivity: RxAppCompatActivity() {

    val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
    var isWifi = false
    var registered = false

    private var soundPool: SoundPool? = null
    var assetManager: AssetManager? = null
    var assetFileDescriptor: AssetFileDescriptor? = null


    private val effects = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        instance = this
        actionBar?.hide()
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()
//        if(networkChecker == null) {
//            networkChecker = NetworkConnectionStateMonitor(this)
//            networkChecker!!.register()
//            registered = true
//        }
    }

    override fun onPause() {
//        if (networkChecker != null && registered) {
//            networkChecker!!.unregister()
//            networkChecker = null
//            registered = false
//        }
        super.onPause()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus)  {
            hideSystemUI()
        }
    }

    @Suppress("DEPRECATION")
    private fun hideSystemUI() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Log.e(TAG, "hide System UI")
            window.setDecorFitsSystemWindows(false)
            window.insetsController!!.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            window.insetsController!!.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                    // Set the content to appear under the system bars so that the
                    // content doesn't resize when the system bars hide and show.
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }

    }

    open fun playSoundPoolEffect(effect: Int, loop: Int? = 0) {
        runOnUiThread {
            if (soundPool == null) {
                soundPool = SoundPool.Builder().build()
            }
            if (effects == effect) {
                soundPool!!.autoResume()
            } else {
                soundPool!!.load(this, effect, 1)
                soundPool!!.setOnLoadCompleteListener { _: SoundPool?, sampleId: Int, _: Int -> soundPool!!.play(
                    sampleId,
                    1.0f,
                    1.0f,
                    1,
                    loop!!,
                    1.0f
                ) }
            }
        }
    }

    open fun stopSoundPoolEffect() {
        if (soundPool != null) {
            soundPool!!.release()
            soundPool = null
        }
    }

    /**
     * EditText 있는 페이지에서 EditText Box 이외의 영역 터치 시 가상 키보드 내려감.
     */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val view = currentFocus
        if (view != null && (ev!!.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) && view is EditText && !view.javaClass.name.startsWith(
                "android.webkit."
            )
        ) {
            val scrcoords = IntArray(2)
            view.getLocationOnScreen(scrcoords)
            val x = ev.rawX + view.getLeft() - scrcoords[0]
            val y = ev.rawY + view.getTop() - scrcoords[1]
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) (this.getSystemService(
                INPUT_METHOD_SERVICE
            ) as InputMethodManager).hideSoftInputFromWindow(
                this.window.decorView.applicationWindowToken, 0
            )
        }
        return super.dispatchTouchEvent(ev)
    }

    open fun nextPage(delay: Int? = 500) {
    }

    open fun getAssetFileDescriptor(mp3Path: String?): AssetFileDescriptor? {
        return try {
            Log.e(TAG, "mp3Path = $mp3Path")
            assetManager =
                createPackageContext("kr.rowan.superbrain_b2b", 0).assets
            assetFileDescriptor = assetManager!!.openFd(mp3Path!!)
            assetFileDescriptor
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getMp3Path(fileName: String) = "audio/$fileName.mp3"

    fun getMp4Path(fileName: String) = "tutorial/$fileName.mp4"

    @SuppressLint("ClickableViewAccessibility")
    fun setScreenTouchEffect(screenView: ConstraintLayout) {
        /*screenView.setOnTouchListener { _: View?, evt: MotionEvent ->
            if (screenView.childCount >= 1) {
                screenView.removeAllViews()
                screenView.requestLayout()
            }
            runOnUiThread {
                screenView.addView(
                    ScreenTouchEffectView(this).init(
                        evt.x,
                        evt.y,
                        POINTER_SIZE,
                        POINTER_SIZE
                    ).build(this), POINTER_SIZE, POINTER_SIZE
                )
            }
            false
        }*/
    }


    companion object {
        private const val POINTER_SIZE = 100
        lateinit var instance: BaseActivity
            private set
    }
}