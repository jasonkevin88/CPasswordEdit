package com.cby.pwd.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cby.ui.lib.password.DigitKeyboard;
import com.cby.ui.lib.password.PasswordEditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
    implements DigitKeyboard.DigitKeyboardClickListener, PasswordEditText.PasswordFullListener {

  private TextView titleTv;
  private TextView contentTv;
  private TextView sureBtn;
  private TextView getPwdTv;

  private PasswordEditText pwdEdit;
  private DigitKeyboard keyboard;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    viewInit();
  }


  protected void viewInit() {
    titleTv = findViewById(R.id.tv_title);
    contentTv = findViewById(R.id.tv_content);
    sureBtn = findViewById(R.id.btn_sure);
    getPwdTv = findViewById(R.id.tv_get_password);
    pwdEdit = findViewById(R.id.et_password);
    keyboard = findViewById(R.id.custom_key_board);

    titleTv.setText("标题");
    contentTv.setText("描述点什么~");
    sureBtn.setText("确定");

    initListener();
  }

  /**
   * 初始化事件监听
   */
  private void initListener() {
    keyboard.setOnDigitKeyboardClickListener(this);
    pwdEdit.setOnPasswordFullListener(this);

    sureBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String currentPassword = pwdEdit.getText().toString();
        Toast.makeText(MainActivity.this, currentPassword, Toast.LENGTH_SHORT).show();
      }
    });

    pwdEdit.setEnabled(true);
    pwdEdit.setFocusable(false);
    pwdEdit.setFocusableInTouchMode(false);
    pwdEdit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        keyboard.setVisibility(View.VISIBLE);
      }
    });
  }

  @Override
  public void click(String number) {
    pwdEdit.addPassword(number);
  }

  @Override
  public void delete() {
    pwdEdit.deleteLastPassword();
  }

  @Override
  public void passwordFull(String password) {
    setButtonStatus(true);
  }

  @Override
  public void passwordChanged(String password) {
    setButtonStatus(false);
  }

  protected void setButtonStatus(boolean enable) {
    sureBtn.setEnabled(enable);
    sureBtn.setBackgroundResource(enable ? R.drawable.cby_bg_open_button
        : R.drawable.cby_bg_disable_button);
  }
}
