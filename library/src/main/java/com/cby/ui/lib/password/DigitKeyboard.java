package com.cby.ui.lib.password;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Description:DigitKeyboard
 *
 * @author 陈宝阳
 * @create 2020/4/11 10: 21
 */
public class DigitKeyboard extends LinearLayout implements View.OnClickListener {
  private DigitKeyboardClickListener mListener;

  public DigitKeyboard(Context context) {
    this(context, null);
  }

  public DigitKeyboard(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public DigitKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setBackgroundColor(Color.parseColor("#EBEBEB"));
    setOrientation(VERTICAL);
    createChildView(context);
    setChildViewOnclick(this);
  }

  /**
   * dip 转 px
   */
  private int dip2px(int dip) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        dip, getResources().getDisplayMetrics());
  }

  /**
   * 创建按键key (1-9), 隐藏键，0，删除键
   */
  private void createChildView(Context context) {
    //添加1-到9
    for (int i = 1; i < 4; i++) {
      LinearLayout linearLayout = new LinearLayout(context);
      LinearLayout.LayoutParams lineParam = new LinearLayout.LayoutParams(
          ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
      lineParam.topMargin = dip2px(1);
      linearLayout.setLayoutParams(lineParam);

      for (int j = 1; j < 4; j++) {
        TextView textView = new TextView(context);
        textView.setText(String.valueOf(i * j));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        params.rightMargin = dip2px(1);
        textView.setLayoutParams(params);
        textView.setPadding(dip2px(20), dip2px(20), dip2px(20), dip2px(20));
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        linearLayout.addView(textView);
      }
      addView(linearLayout);
    }

    //添加 数字0按键 和 删除键
    LinearLayout linearLayout = new LinearLayout(context);
    LinearLayout.LayoutParams lineParam = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    lineParam.topMargin = dip2px(1);
    linearLayout.setLayoutParams(lineParam);

    linearLayout.addView(createBlankView(context));
    linearLayout.addView(createZeroView(context));
    linearLayout.addView(createDeleteView(context));
    addView(linearLayout);
  }

  /**
   * 创建空白的按键view
   */
  private TextView createBlankView(Context context) {
    TextView textView = new TextView(context);
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
        0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
    params.rightMargin = dip2px(1);
    textView.setLayoutParams(params);
    textView.setPadding(dip2px(20), dip2px(20), dip2px(20), dip2px(20));
    textView.setGravity(Gravity.CENTER);
    return textView;
  }

  /**
   * 创建0的按键view
   */
  private TextView createZeroView(Context context) {
    TextView textView = new TextView(context);
    textView.setText("0");
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
        0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
    params.rightMargin = dip2px(1);
    textView.setLayoutParams(params);
    textView.setGravity(Gravity.CENTER);
    textView.setPadding(dip2px(20), dip2px(20), dip2px(20), dip2px(20));
    textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
    return textView;
  }

  /**
   * 创建删除的按键view
   */
  private ImageView createDeleteView(Context context) {
    ImageView imageView = new ImageView(context);
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
        0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
    params.gravity = Gravity.CENTER_VERTICAL;
    imageView.setLayoutParams(params);
    imageView.setPadding(0, dip2px(15), dip2px(5), dip2px(15));
    imageView.setImageResource(R.drawable.cby_keyboard_delete);
    return imageView;
  }

  /**
   * 设置键盘子View的点击事件
   */
  private void setChildViewOnclick(ViewGroup parent) {
    int childCount = parent.getChildCount();
    for (int i = 0; i < childCount; i++) {
      // 不断的递归设置点击事件
      View view = parent.getChildAt(i);
      if (view instanceof ViewGroup) {
        setChildViewOnclick((ViewGroup) view);
        continue;
      }
      view.setOnClickListener(this);
    }
  }

  @Override
  public void onClick(View v) {
    View clickView = v;
    if (clickView instanceof TextView) {
      // 如果点击的是TextView
      String number = ((TextView) clickView).getText().toString();
      if (!TextUtils.isEmpty(number)) {
        if (mListener != null) {
          // 回调
          mListener.click(number);
        }
      }
    } else if (clickView instanceof ImageView) {
      // 如果是图片那肯定点击的是删除
      if (mListener != null) {
        mListener.delete();
      }
    }
  }

  /**
   * 拦截back键的响应事件
   *
   * @param event 系统按键事件
   */
  public boolean dispatchKeyEventInFullScreen(KeyEvent event) {
    if (event == null) {
      return false;
    }
    switch (event.getKeyCode()) {
      case KeyEvent.KEYCODE_BACK:
        if (isShown()) {
          setVisibility(GONE);
          return true;
        }
      default:
        return false;
    }
  }

  /**
   * 设置键盘的点击回调监听
   */
  public void setOnDigitKeyboardClickListener(DigitKeyboardClickListener listener) {
    this.mListener = listener;
  }

  /**
   * 点击键盘的回调监听
   */
  public interface DigitKeyboardClickListener {
    public void click(String number);

    public void delete();
  }
}
