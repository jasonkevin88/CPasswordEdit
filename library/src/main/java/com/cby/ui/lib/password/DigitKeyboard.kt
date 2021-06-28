package com.cby.ui.lib.password

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Description:DigitKeyboard
 *
 * @author 陈宝阳
 * @create 2020/4/11 10: 21
 */
class DigitKeyboard @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr), View.OnClickListener {
    private var mListener: DigitKeyboardClickListener? = null

    /**
     * dip 转 px
     */
    private fun dip2px(dip: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dip.toFloat(), resources.displayMetrics).toInt()
    }

    /**
     * 创建按键key (1-9), 隐藏键，0，删除键
     */
    private fun createChildView(context: Context) {
        //添加1-到9
        for (i in 1..3) {
            val linearLayout = LinearLayout(context)
            val lineParam = LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            lineParam.topMargin = dip2px(1)
            linearLayout.layoutParams = lineParam
            for (j in 1..3) {
                val textView = TextView(context)
                textView.text = (i * j).toString()
                val params = LayoutParams(
                        0, ViewGroup.LayoutParams.WRAP_CONTENT, 1F)
                params.rightMargin = dip2px(1)
                textView.layoutParams = params
                textView.setPadding(dip2px(20), dip2px(20), dip2px(20), dip2px(20))
                textView.gravity = Gravity.CENTER
                textView.setBackgroundColor(Color.parseColor("#FFFFFF"))
                linearLayout.addView(textView)
            }
            addView(linearLayout)
        }

        //添加 数字0按键 和 删除键
        val linearLayout = LinearLayout(context)
        val lineParam = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lineParam.topMargin = dip2px(1)
        linearLayout.layoutParams = lineParam
        linearLayout.addView(createHideView(context))
        linearLayout.addView(createZeroView(context))
        linearLayout.addView(createDeleteView(context))
        addView(linearLayout)
    }

    /**
     * 创建空白的按键view
     */
    private fun createHideView(context: Context): TextView {
        val textView = TextView(context)
        val params = LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT, 1F)
        params.rightMargin = dip2px(1)
        textView.layoutParams = params
        textView.setPadding(dip2px(20), dip2px(20), dip2px(20), dip2px(20))
        textView.gravity = Gravity.CENTER
        textView.text = HIDE
        return textView
    }

    /**
     * 创建0的按键view
     */
    private fun createZeroView(context: Context): TextView {
        val textView = TextView(context)
        textView.text = "0"
        val params = LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1F)
        params.rightMargin = dip2px(1)
        textView.layoutParams = params
        textView.gravity = Gravity.CENTER
        textView.setPadding(dip2px(20), dip2px(20), dip2px(20), dip2px(20))
        textView.setBackgroundColor(Color.parseColor("#FFFFFF"))
        return textView
    }

    /**
     * 创建删除的按键view
     */
    private fun createDeleteView(context: Context): ImageView {
        val imageView = ImageView(context)
        val params = LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1F)
        params.gravity = Gravity.CENTER_VERTICAL
        imageView.layoutParams = params
        imageView.setPadding(0, dip2px(15), dip2px(5), dip2px(15))
        imageView.setImageResource(R.drawable.cby_keyboard_delete)
        return imageView
    }

    /**
     * 设置键盘子View的点击事件
     */
    private fun setChildViewOnclick(parent: ViewGroup) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            // 不断的递归设置点击事件
            val view = parent.getChildAt(i)
            if (view is ViewGroup) {
                setChildViewOnclick(view)
                continue
            }
            view.setOnClickListener(this)
        }
    }

    override fun onClick(v: View) {
        if (v is TextView) {
            // 如果点击的是TextView
            val number = v.text.toString()
            if (!TextUtils.isEmpty(number)&& HIDE != number) {
                if (mListener != null) {
                    // 回调
                    mListener!!.click(number)
                }
            } else {
                visibility = View.GONE
            }
        } else if (v is ImageView) {
            // 如果是图片那肯定点击的是删除
            if (mListener != null) {
                mListener!!.delete()
            }
        }
    }

    /**
     * 拦截back键的响应事件
     *
     * @param event 系统按键事件
     */
    fun dispatchKeyEventInFullScreen(event: KeyEvent?): Boolean {
        return if (event == null) {
            false
        } else when (event.keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                if (isShown) {
                    visibility = GONE
                    return true
                }
                false
            }
            else -> false
        }
    }

    /**
     * 设置键盘的点击回调监听
     */
    fun setOnDigitKeyboardClickListener(listener: DigitKeyboardClickListener?) {
        mListener = listener
    }

    /**
     * 点击键盘的回调监听
     */
    interface DigitKeyboardClickListener {
        fun click(number: String?)
        fun delete()
    }

    init {
        setBackgroundColor(Color.parseColor("#EBEBEB"))
        orientation = VERTICAL
        createChildView(context)
        setChildViewOnclick(this)
    }

    companion object {
        private const val HIDE = "收起"
    }
}