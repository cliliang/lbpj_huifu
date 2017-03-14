package com.baluobo.home.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.tools.SharePopupWindow;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.home.actions.DotAction;
import com.umeng.socialize.UMShareAPI;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/8.
 */
public class RecalculateActivity extends BaseToolBarActivity implements View.OnClickListener{
    private TextView totalView, removeView, remainView;
    private String formerString = "";
    private String calculateString = "";
    private String resultString = "";
    private boolean finishCalculate = false;
    private boolean appendCalculate = true;
    private final char symbolAC = 'a';
    private final char symbolDelete = 'd';
    private final char symbolJia = '+';
    private final char symbolJian = '-';
    private final char symbolCheng = '×';
    private final char symbolChu = '÷';
    private final char symbolDot = '.';
    private final char symbolEqual = '=';
    private final char symbolZero = '0';
    private final char symbolOne = '1';
    private final char symbolTwo = '2';
    private final char symbolThree = '3';
    private final char symbolFour = '4';
    private final char symbolFive = '5';
    private final char symbolSix = '6';
    private final char symbolSeven = '7';
    private final char symbolEight = '8';
    private final char symbolNine = '9';
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recalculate_activity_layout);
        setTitle(getString(R.string.luobo_calculate));
        setRightTextMenu(getString(R.string.share), new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (getSharePermissionSize(mSharePermissionList) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(RecalculateActivity.this, mSharePermissionList, 100);
                }else {
                    showShareWindow();
                }
            }
        });
        setupViews();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String boundString = bundle.getString(DotAction.RECALCULATE_STRING);
            formerString = boundString;
            totalView.setText(boundString);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showShareWindow();
            } else {
                UIHelper.ToastMessage(this, getString(R.string.share_state_refuse));
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showShareWindow(){
        SharePopupWindow shareWindow = new SharePopupWindow(this);
        if (!TextUtils.isEmpty(resultString)){
            shareWindow.setShareCalculate(2, resultString);
            shareWindow.showAtLocation(findViewById(R.id.recalculate_share_line), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        }else {
            UIHelper.ToastMessage(this, getString(R.string.share_result_is_empty));
        }
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        totalView = (TextView) findViewById(R.id.recalculate_total_number_view);
        removeView = (TextView) findViewById(R.id.recalculate_remove_number_view);
        remainView = (TextView) findViewById(R.id.recalculate_remain_number_view);
        findViewById(R.id.recalculate_number_zero_view).setOnClickListener(this);
        findViewById(R.id.recalculate_number_one_view).setOnClickListener(this);
        findViewById(R.id.recalculate_number_two_view).setOnClickListener(this);
        findViewById(R.id.recalculate_number_three_view).setOnClickListener(this);
        findViewById(R.id.recalculate_number_four_view).setOnClickListener(this);
        findViewById(R.id.recalculate_number_five_view).setOnClickListener(this);
        findViewById(R.id.recalculate_number_six_view).setOnClickListener(this);
        findViewById(R.id.recalculate_number_seven_view).setOnClickListener(this);
        findViewById(R.id.recalculate_number_eight_view).setOnClickListener(this);
        findViewById(R.id.recalculate_number_nine_view).setOnClickListener(this);

        findViewById(R.id.recalculate_clear_all_button).setOnClickListener(this);
        findViewById(R.id.recalculate_clear_one_button).setOnClickListener(this);
        findViewById(R.id.recalculate_float_view).setOnClickListener(this);
        findViewById(R.id.recalculate_equal_view).setOnClickListener(this);
        findViewById(R.id.recalculate_addition_view).setOnClickListener(this);
        findViewById(R.id.recalculate_subtraction_view).setOnClickListener(this);
        findViewById(R.id.recalculate_multiplication_view).setOnClickListener(this);
        findViewById(R.id.recalculate_division_view).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.recalculate_number_zero_view:
                appendString(symbolZero);
                break;
            case R.id.recalculate_number_one_view:
                appendString(symbolOne);
                break;
            case R.id.recalculate_number_two_view:
                appendString(symbolTwo);
                break;
            case R.id.recalculate_number_three_view:
                appendString(symbolThree);
                break;
            case R.id.recalculate_number_four_view:
                appendString(symbolFour);
                break;
            case R.id.recalculate_number_five_view:
                appendString(symbolFive);
                break;
            case R.id.recalculate_number_six_view:
                appendString(symbolSix);
                break;
            case R.id.recalculate_number_seven_view:
                appendString(symbolSeven);
                break;
            case R.id.recalculate_number_eight_view:
                appendString(symbolEight);
                break;
            case R.id.recalculate_number_nine_view:
                appendString(symbolNine);
                break;
            //清除所有
            case R.id.recalculate_clear_all_button:
                appendString(symbolAC);
                break;
            //清除1个
            case R.id.recalculate_clear_one_button:
                appendString(symbolDelete);
                break;
            //小数点
            case R.id.recalculate_float_view:
                appendString(symbolDot);
                break;
            //=
            case R.id.recalculate_equal_view:
                appendString(symbolEqual);
                break;
            //+
            case R.id.recalculate_addition_view:
                appendString(symbolJia);
                break;
            //-
            case R.id.recalculate_subtraction_view:
                appendString(symbolJian);
                break;
            //×
            case R.id.recalculate_multiplication_view:
                appendString(symbolCheng);
                break;
            //÷
            case R.id.recalculate_division_view:
                appendString(symbolChu);
                break;
        }
    }

    private void appendString(char symbol){
        if (symbolAC == symbol){
            formerString = "";
            calculateString = "";
            resultString = "";
            totalView.setText("");
            removeView.setText("");
            remainView.setText("");
            appendCalculate = false;
            return;
        }
        if (finishCalculate){
            finishCalculate = false;
//            appendCalculate = true;
            formerString = resultString;
            calculateString = "";
            resultString = "";
            totalView.setText(formerString);
            remainView.setText("");
            removeView.setText("");
        }
        //作用于第二行
        if (appendCalculate){
            if (symbolDelete == symbol){
                if (calculateString.length() > 0){
                    calculateString = calculateString.substring(0, calculateString.length() - 1);
                }
            }else if (symbolDot == symbol){
                if (TextUtils.isEmpty(calculateString)){
                    calculateString = "0" + symbol;
                }else {
                    if (calculateString.contains(Character.toString(symbol))){
                    }else {
                        calculateString = calculateString + symbol;
                    }
                }
            }else if (symbolJia == symbol || symbolJian == symbol || symbolCheng == symbol || symbolChu == symbol){
                if (TextUtils.isEmpty(calculateString)){
                    calculateString = String.valueOf(symbol);
                }else {
                    char flag = calculateString.charAt(0);
                    if (flag == symbolJia || flag == symbolJian || flag == symbolCheng || flag == symbolChu){
                        calculateString = calculateString.replace(flag, symbol);
                    } else {
                        calculateString = symbol + calculateString;
                    }
                }
            }else if (symbolEqual == symbol){
                calculate();
            }else {
                String temp = calculateString;
                if (!TextUtils.isEmpty(temp)){
                    temp = temp.replace(Character.toString(symbolJia), "");
                    temp = temp.replace(Character.toString(symbolJian), "");
                    temp = temp.replace(Character.toString(symbolCheng), "");
                    temp = temp.replace(Character.toString(symbolChu), "");
                    if (Character.toString(symbolZero).equals(temp)){
                        if (symbol != symbolDot){
                            return;
                        }
                    }
                }
                calculateString = calculateString + symbol;
            }
            removeView.setText(calculateString);
        }else {
            //作用于第一行
            if (symbolDelete == symbol){
                if (formerString.length() > 0){
                    formerString = formerString.substring(0, formerString.length() - 1);
                }
            }else if (symbolDot == symbol){
                if (TextUtils.isEmpty(formerString)){
                    formerString = "0" + symbol;
                }else {
                    if (formerString.contains(Character.toString(symbol))){
                    }else {
                        formerString = formerString + symbol;
                    }
                }
            }else if (symbolJia == symbol || symbolJian == symbol || symbolCheng == symbol || symbolChu == symbol){
                appendCalculate = true;
                appendString(symbol);
            }else if (symbolEqual == symbol){
                UIHelper.ToastMessage(this, getString(R.string.input_calculate_symbol));
            }else {
                if (Character.toString(symbolZero).equals(formerString)){
                    if (symbol != symbolDot){
                        return;
                    }
                }
                formerString = formerString + symbol;
            }
            totalView.setText(formerString);
        }
    }

    private void calculate(){
        try {
            BigDecimal floatFormer = new BigDecimal(formerString);
            BigDecimal floatCalculate;
            char symbol;
            if (calculateString.length() > 1){
                floatCalculate = new BigDecimal(calculateString.substring(1, calculateString.length()));
                symbol = calculateString.charAt(0);
            }else {
                UIHelper.ToastMessage(this, getString(R.string.input_calculate_symbol));
                return;
            }
            if (symbolJia != symbol && symbolJian != symbol && symbolCheng != symbol && symbolChu != symbol){
                UIHelper.ToastMessage(this, getString(R.string.input_calculate_symbol));
                return;
            }

            BigDecimal resultFloat;
            if (symbolJia == symbol){
                resultFloat = floatFormer.add(floatCalculate);
            }else if (symbolJian == symbol){
                resultFloat = floatFormer.subtract(floatCalculate);
            }else if (symbolCheng == symbol){
                resultFloat = floatFormer.multiply(floatCalculate);
            }else if (symbolChu == symbol){
                resultFloat = floatFormer.divide(floatCalculate, 2, BigDecimal.ROUND_HALF_UP);
            }else {
                UIHelper.ToastMessage(this, getString(R.string.input_calculate_symbol));
                return;
            }
            DecimalFormat format = new DecimalFormat("#.00");
            resultString = format.format(resultFloat);

            remainView.setText(resultString);
            finishCalculate = true;
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult( requestCode, resultCode, data);
    }

}
