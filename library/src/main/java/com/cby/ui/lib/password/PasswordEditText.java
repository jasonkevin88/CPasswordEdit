package com.cby.ui.lib.password;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

/**
 * Description:自定义密码输入框
 *
 * @author 陈宝阳
 * @create 2020/4/11 10: 18
 */
public class PasswordEditText extends EditText {

  /** 默认的密码颜色 */
  private static final int DEFAULT_PASSWORD_COLOR = Color.parseColor("#333333");

  /** 默认边框的颜色 */
  private static final int DEFAULT_BORDER_COLOR = Color.parseColor("#d1d2d6");

  /** 默认密码下划线的颜色 */
  private static final int DEFAULT_UNDERLINE_COLOR = Color.parseColor("#666666");

  private static final int BACKGROUND_STYLE_BORDER = 0;

  private static final int BACKGROUND_STYLE_UNDERLINE = 1;

  /** 密码的画笔 */
  private Paint mPasswordPaint;
  /** 密码圆点的颜色 */
  private int mPasswordColor = DEFAULT_PASSWORD_COLOR;
  /** 一个密码所占的宽度 */
  private int mPasswordItemWidth;
  /** 密码的个数,默认为4位数 */
  private int mPasswordNumber = 4;
  /** 密码圆点的半径大小，m默认为4像素 */
  private int mPasswordRadius = 4;

  /** 下划线的画笔 */
  private Paint mUnderlinePaint;
  /** 密码底部下划线的宽度 */
  private int mUnderlineWidth;
  /** 密码底部下划线的厚度 */
  private int mUnderlineSize = 1;
  /** 密码底部下划线的宽度 */
  private int mUnderlineColor = DEFAULT_UNDERLINE_COLOR;

  /** 边框的画笔 */
  private Paint mBorderPaint;
  /** 背景边框颜色 */
  private int mBorderColor = DEFAULT_BORDER_COLOR;
  /** 背景边框厚度大小 */
  private int mBorderStrokeSize = 1;
  /** 背景边框圆角大小 */
  private int mBorderCorner = 0;

  /** 分隔线的画笔 */
  private Paint mDivisionLinePaint;
  /** 分割线的颜色，默认跟边框同个颜色 */
  private int mDivisionLineColor = mBorderColor;
  /** 分割线的大小 */
  private int mDivisionLineSize = 1;

  /** 样式类型 */
  private int mBgStyle = 0;

  public PasswordEditText(Context context) {
    this(context, null);
  }

  public PasswordEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
    initAttributeSet(context, attrs);
    initPaint();
    // 默认只能够设置数字
    setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
  }

  /**
   * 初始化画笔
   */
  private void initPaint() {
    // 初始化密码边框的画笔
    mBorderPaint = new Paint();
    // 抗锯齿
    mBorderPaint.setAntiAlias(true);
    // 防抖动
    mBorderPaint.setDither(true);
    // 给画笔设置大小
    mBorderPaint.setStrokeWidth(mBorderStrokeSize);
    // 设置背景的颜色
    mBorderPaint.setColor(mBorderColor);
    // 画空心
    mBorderPaint.setStyle(Paint.Style.STROKE);

    // 初始化分隔线的画笔
    mDivisionLinePaint = new Paint();
    // 抗锯齿
    mDivisionLinePaint.setAntiAlias(true);
    // 防抖动
    mDivisionLinePaint.setDither(true);
    // 分割线画笔设置大小
    mDivisionLinePaint.setStrokeWidth(mDivisionLineSize);
    // 设置分割线的颜色
    mDivisionLinePaint.setColor(mDivisionLineColor);

    //初始化密码的画笔
    mPasswordPaint = new Paint();
    // 抗锯齿
    mPasswordPaint.setAntiAlias(true);
    // 防抖动
    mPasswordPaint.setDither(true);
    // 密码绘制是实心
    mPasswordPaint.setStyle(Paint.Style.FILL);
    // 设置密码的颜色
    mPasswordPaint.setColor(mPasswordColor);

    //初始化下划线的画笔
    mUnderlinePaint = new Paint();
    // 抗锯齿
    mUnderlinePaint.setAntiAlias(true);
    // 防抖动
    mUnderlinePaint.setDither(true);
    // 设置颜色
    mUnderlinePaint.setColor(mUnderlineColor);
    // 设置画笔的大小
    mUnderlinePaint.setStrokeWidth(mUnderlineSize);
  }

  /**
   * 初始化属性
   */
  private void initAttributeSet(Context context, AttributeSet attrs) {
    TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PasswordEditText);
    // 密码的颜色
    mPasswordColor = array.getColor(R.styleable.PasswordEditText_passwordColor, mPasswordColor);
    // 密码圆点的半径
    mPasswordRadius = (int) array.getDimension(R.styleable.PasswordEditText_passwordRadius,
        dip2px(mPasswordRadius));
    // 密码的个数
    mPasswordNumber = array.getInteger(R.styleable.PasswordEditText_passwordNumber,
        mPasswordNumber);

    // 间隔线大小
    mDivisionLineSize = (int) array.getDimension(R.styleable.PasswordEditText_divisionLineSize,
        dip2px(mDivisionLineSize));
    // 间隔线的颜色
    mDivisionLineColor = array.getColor(R.styleable.PasswordEditText_divisionLineColor,
        mDivisionLineColor);

    // 边框的厚度
    mBorderStrokeSize = (int) array.getDimension(R.styleable.PasswordEditText_bgSize, dip2px(
        mBorderStrokeSize));
    // 边框的圆角
    mBorderCorner = (int) array.getDimension(R.styleable.PasswordEditText_bgCorner, 0);
    // 获取边框的颜色
    mBorderColor = array.getColor(R.styleable.PasswordEditText_bgColor, mBorderColor);

    // 下划线的颜色
    mUnderlineColor = array.getColor(R.styleable.PasswordEditText_underlineColor, mUnderlineColor);
    // 下划线的厚度
    mUnderlineSize = (int) array.getDimension(R.styleable.PasswordEditText_underlineSize,
        dip2px(mUnderlineSize));

    // 样式类型
    mBgStyle = array.getInteger(R.styleable.PasswordEditText_bgStyle, BACKGROUND_STYLE_BORDER);
    array.recycle();
  }

  /**
   * dip 转 px
   */
  private float dip2px(int dip) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        dip, getResources().getDisplayMetrics());
  }

  @Override
  protected void onDraw(Canvas canvas) {
    // 一个密码的宽度
    mPasswordItemWidth =
        (getWidth() - 2 * mBorderStrokeSize - (mPasswordNumber - 1) * mDivisionLineSize)
            / mPasswordNumber;
    mUnderlineWidth = mPasswordItemWidth - 8 * mBorderStrokeSize;

    if(mBgStyle == BACKGROUND_STYLE_UNDERLINE) {
      //绘制下划线
      drawUnderLine(canvas);
    }else {
      // 画背景
      drawBg(canvas);
      // 画分割线
      drawDivisionLine(canvas);
    }
    // 画密码
    drawPassword(canvas);

    // 当前密码是不是满了
    if (mListener != null) {
      String password = getText().toString().trim();
      if (password.length() >= mPasswordNumber) {
        mListener.passwordFull(password);
      } else {
        mListener.passwordChanged(password);
      }
    }
  }

  /**
   * 绘制密码
   */
  private void drawPassword(Canvas canvas) {
    // 获取当前text
    String text = getText().toString().trim();
    // 获取密码的长度
    int passwordLength = text.length();
    // 不断的绘制密码
    for (int i = 0; i < passwordLength; i++) {
      int cy = getHeight() / 2;
      int cx = mBorderStrokeSize
          + i * mPasswordItemWidth + i * mDivisionLineSize + mPasswordItemWidth / 2;
      canvas.drawCircle(cx, cy, mPasswordRadius, mPasswordPaint);
    }
  }

  /**
   * 绘制分割线
   */
  private void drawDivisionLine(Canvas canvas) {

    for (int i = 0; i < mPasswordNumber - 1; i++) {
      int startX = mBorderStrokeSize + (i + 1) * mPasswordItemWidth + i * mDivisionLineSize;
      int startY = mBorderStrokeSize;
      int endX = startX;
      int endY = getHeight() - mBorderStrokeSize;
      canvas.drawLine(startX, startY, endX, endY, mDivisionLinePaint);
    }
  }

  /**
   * 绘制背景
   */
  private void drawBg(Canvas canvas) {
    RectF rect = new RectF(mBorderStrokeSize,
        mBorderStrokeSize, getWidth() - mBorderStrokeSize, getHeight() - mBorderStrokeSize);
    // 绘制背景  drawRect , drawRoundRect  ,
    // 如果有圆角那么就绘制drawRoundRect，否则绘制drawRect
    if (mBorderCorner == 0) {
      canvas.drawRect(rect, mBorderPaint);
    } else {
      canvas.drawRoundRect(rect, mBorderCorner, mBorderCorner, mBorderPaint);
    }
  }

  /**
   * 绘制每个密码项的底部下划线
   */
  private void drawUnderLine(Canvas canvas) {
    for (int i = 0; i < mPasswordNumber; i++) {
      int startX = mBorderStrokeSize * 4 + i * mPasswordItemWidth;
      int startY = getHeight() - mBorderStrokeSize;
      int endX = startX + mUnderlineWidth;
      int endY = getHeight() - mBorderStrokeSize;
      canvas.drawLine(startX, startY, endX, endY, mUnderlinePaint);
    }
  }

  /**
   * 添加一个密码
   */
  public void addPassword(String number) {
    // 把之前的密码取出来
    String password = getText().toString().trim();
    if (password.length() >= mPasswordNumber) {
      // 密码不能超过当前密码个输
      return;
    }
    // 密码叠加
    password += number;
    setText(password);
  }

  /**
   * 删除最后一位密码
   */
  public void deleteLastPassword() {
    String password = getText().toString().trim();
    // 判断当前密码是不是空
    if (TextUtils.isEmpty(password)) {
      return;
    }
    password = password.substring(0, password.length() - 1);
    setText(password);
  }

  // 设置当前密码是否已满的接口回掉
  private PasswordFullListener mListener;

  public void setOnPasswordFullListener(PasswordFullListener listener) {
    this.mListener = listener;
  }

  /**
   * 密码已经全部填满
   */
  public interface PasswordFullListener {
    void passwordFull(String password);
    void passwordChanged(String password);
  }
}
