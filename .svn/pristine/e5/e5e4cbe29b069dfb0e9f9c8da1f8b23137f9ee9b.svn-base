package com.baluobo.home.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.ui.BaseToolBarActivity;
import com.baluobo.common.tools.UIHelper;
import com.baluobo.home.actions.DotAction;
import com.baluobo.home.router.HomeRouter;
import com.baluobo.home.router.HomeUI;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/8/5.
 */
public class CalculateActivity extends BaseToolBarActivity implements View.OnClickListener{
    private EditText tickMoneyView; //票面金额
    private EditText monthRateView; //月利率
    private EditText yearRateView; //年利率
    private TextView startDateView; //贴现日期
    private TextView endDateView; //到期日期
    private EditText addDayView; //调整天数
    private EditText tickServiceMoneyView; //每十万手续费
    private TextView clearButton; //清除
    private TextView calculateButton; //计算
    private TextView dayCountView; //计算天数
    private TextView tenThousandTieXiMoneyView; //每十万贴息
    private TextView tenThousandTieXiButton; //再计算
    private TextView tieXianRateView; //贴现利息
    private TextView tieXianRateButton; //贴现利息再计算
    private TextView tieXianMoneyView; //贴现金额
    private TextView tieXianMoneyButton; //贴现再计算

    private Calendar startCalendar;
    private Calendar endCalendar;
    private String dateType;
    private final String dateTypeStart = "dateTypeStart";
    private final String dateTypeEnd = "dateTypeEnd";
//    private boolean changeMonth = false;
//    private boolean changeYear = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculate_activity_layout);
        setTitle(getString(R.string.calculate_title));
        setupViews();
        resetView();
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        tickMoneyView = (EditText) findViewById(R.id.calculate_tick_money_view);
        monthRateView = (EditText) findViewById(R.id.calculate_tick_rate_month_view);
        yearRateView = (EditText) findViewById(R.id.calculate_tick_rate_year_view);
        startDateView = (TextView) findViewById(R.id.calculate_tick_start_day_view);
        startDateView.setOnClickListener(this);
        endDateView = (TextView) findViewById(R.id.calculate_tick_end_day_view);
        endDateView.setOnClickListener(this);
        addDayView = (EditText) findViewById(R.id.calculate_tick_add_day_view);
        tickServiceMoneyView = (EditText) findViewById(R.id.calculate_tick_service_money_view);
        clearButton = (TextView) findViewById(R.id.calculate_tick_clear_button);
        clearButton.setOnClickListener(this);
        calculateButton = (TextView) findViewById(R.id.calculate_tick_calculate_button);
        calculateButton.setOnClickListener(this);
        dayCountView = (TextView) findViewById(R.id.calculate_tick_day_count_view);
        tenThousandTieXiMoneyView = (TextView) findViewById(R.id.calculate_tick_rate_each_ten_thousand_money);
        tenThousandTieXiButton = (TextView) findViewById(R.id.calculate_tick_return_each_ten_thousand_calculate_button);
        tenThousandTieXiButton.setOnClickListener(this);
        tieXianRateView = (TextView) findViewById(R.id.calculate_tick_return_money_view);
        tieXianRateButton = (TextView) findViewById(R.id.calculate_tick_rate_return_calculate_button);
        tieXianRateButton.setOnClickListener(this);
        tieXianMoneyView = (TextView) findViewById(R.id.calculate_tick_pay_money_view);
        tieXianMoneyButton = (TextView) findViewById(R.id.calculate_tick_pay_money_each_ten_thousand_return_calculate_button);
        tieXianMoneyButton.setOnClickListener(this);
        monthRateView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                if (TextUtils.isEmpty(input)){
                    yearRateView.setText("");
                }else {
                    try{
                        float monthRate = Float.parseFloat(input);
                        yearRateView.setText(String.format(Locale.CHINESE, "%.2f", monthRate * 12f / 10f));
                    }catch (NumberFormatException e){
                        yearRateView.setText("");
                        e.printStackTrace();
                    }
                }
            }
        });
//        yearRateView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                String input = s.toString();
//                if (TextUtils.isEmpty(input)){
//                    monthRateView.setText("");
//                }else {
//                    try {
//                        float yearRate = Float.parseFloat(input);
//                        monthRateView.setText(String.format(Locale.CHINESE, "%.2f", yearRate / 12f * 10f));
//                    }catch (NumberFormatException e){
//                        monthRateView.setText("");
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
    }

    private void resetView(){
        startCalendar = null;
        endCalendar = null;
        tickMoneyView.setText("");
        monthRateView.setText("");
        yearRateView.setText("");
        addDayView.setText("3");
        tickServiceMoneyView.setText("");
        startDateView.setText(String.format(Locale.CHINESE, "%tF", new Date()) + " " +String.format(Locale.CHINESE, "%tA", new Date()));
        endDateView.setText(String.format(Locale.CHINESE, "%tF", new Date()) + " " +String.format(Locale.CHINESE, "%tA", new Date()));
        dayCountView.setText("");
        tenThousandTieXiMoneyView.setText("");
        tieXianRateView.setText("");
        tieXianMoneyView.setText("");

    }

    private void setDateCalendar(){
        Date startDate, endDate;
        startDate = startCalendar == null ? new Date(Calendar.getInstance().getTimeInMillis()) :  new Date(startCalendar.getTimeInMillis());
        endDate = endCalendar == null ? new Date(Calendar.getInstance().getTimeInMillis()) : new Date(endCalendar.getTimeInMillis());
        startDateView.setText(String.format(Locale.CHINESE, "%tF", startDate) + " " +String.format(Locale.CHINESE, "%tA", startDate));
        endDateView.setText(String.format(Locale.CHINESE, "%tF", endDate) + " " +String.format(Locale.CHINESE, "%tA", endDate));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //贴现日期
            case R.id.calculate_tick_start_day_view:
                dateType = dateTypeStart;
                int startYear, startMonth, startDay;
                if (startCalendar == null){
                    startCalendar = Calendar.getInstance();
                }
                startYear = startCalendar.get(Calendar.YEAR);
                startMonth = startCalendar.get(Calendar.MONTH);
                startDay = startCalendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog startDialog = new DatePickerDialog(this, onDateSetListener, startYear, startMonth, startDay);
                startDialog.show();
                break;
            //到期日期
            case R.id.calculate_tick_end_day_view:
                dateType = dateTypeEnd;
                int endYear, endMonth, endDay;
                if (endCalendar == null){
                    endCalendar = Calendar.getInstance();
                }
                endYear = endCalendar.get(Calendar.YEAR);
                endMonth = endCalendar.get(Calendar.MONTH);
                endDay = endCalendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog endDialog = new DatePickerDialog(this, onDateSetListener, endYear, endMonth, endDay);
                endDialog.show();
                break;
            //清除
            case R.id.calculate_tick_clear_button:
                resetView();
                break;
            //计算
            case R.id.calculate_tick_calculate_button:
                calculateData();
                break;
            //每十万贴息 再计算
            case R.id.calculate_tick_return_each_ten_thousand_calculate_button:
                reCalculate(tenThousandTieXiMoneyView.getText().toString());
                break;
            //贴现利息  再计算
            case R.id.calculate_tick_rate_return_calculate_button:
                reCalculate(tieXianRateView.getText().toString());
                break;
            //贴现金额  再计算
            case R.id.calculate_tick_pay_money_each_ten_thousand_return_calculate_button:
                reCalculate(tieXianMoneyView.getText().toString());
                break;
        }
    }

    private void reCalculate(String boundString){
        if (TextUtils.isEmpty(boundString)){
            boundString = "0.00";
        }else {
            boundString = boundString.substring(0, boundString.length() - 1);
        }
        Bundle bundle = new Bundle();
        bundle.putString(DotAction.RECALCULATE_STRING, boundString);
        HomeRouter.getInstance(this).showActivity(HomeUI.Recalculate, bundle);
    }

    private void calculateData(){
        String inputMoney = tickMoneyView.getText().toString();
        if (TextUtils.isEmpty(inputMoney)){
            UIHelper.ToastMessage(this, getString(R.string.input_tick_money_empty_hint));
            return;
        }
        float tickMoney = Float.parseFloat(inputMoney);

        String inputMonth = monthRateView.getText().toString();
        if (TextUtils.isEmpty(inputMonth)){
            UIHelper.ToastMessage(this, getString(R.string.input_month_rate_empty_hint));
            return;
        }
        float monthRate = Float.parseFloat(inputMonth);

        String inputYear = yearRateView.getText().toString();
        if (TextUtils.isEmpty(inputYear)){
            UIHelper.ToastMessage(this, getString(R.string.input_year_rate_empty_hint));
            return;
        }
        float yearRate = Float.parseFloat(inputYear);

        if (startCalendar == null){
            startCalendar = Calendar.getInstance();
            startCalendar.set(Calendar.HOUR_OF_DAY, 0);
            startCalendar.set(Calendar.MINUTE, 0);
            startCalendar.set(Calendar.SECOND, 0);
            startCalendar.set(Calendar.MILLISECOND, 0);
        }
        long startLong = startCalendar.getTimeInMillis();
        if (endCalendar == null){
            endCalendar = Calendar.getInstance();
            endCalendar.set(Calendar.HOUR_OF_DAY, 0);
            endCalendar.set(Calendar.MINUTE, 0);
            endCalendar.set(Calendar.SECOND, 0);
            endCalendar.set(Calendar.MILLISECOND, 0);
        }
        long endLong = endCalendar.getTimeInMillis();

        if (startLong >= endLong){
            UIHelper.ToastMessage(this, getString(R.string.choice_start_date_late_end_date));
            return;
        }

        String inputAdd = addDayView.getText().toString();
        if (TextUtils.isEmpty(inputAdd)){
            UIHelper.ToastMessage(this, getString(R.string.input_add_day_empty_hint));
            return;
        }
        int addDay = Integer.parseInt(inputAdd);

        String serviceInput = tickServiceMoneyView.getText().toString();
        if (TextUtils.isEmpty(serviceInput)){
            UIHelper.ToastMessage(this, getString(R.string.input_service_monty_empty_hint));
            return;
        }
        float serviceMoney = Float.parseFloat(serviceInput);
        //计息天数
        int tickDay = (int) ((endLong - startLong) / (24L * 3600 * 1000));
        dayCountView.setText(String.valueOf(tickDay + addDay));
        //每十万贴息
        float tenThousandTiexi = 100000f * yearRate / 360 / 100 * (tickDay + addDay) + serviceMoney;
        tenThousandTieXiMoneyView.setText(String.format(Locale.CHINESE, "%.2f", tenThousandTiexi) + getString(R.string.recharge_money_unit));
        //贴现利息
        float tieXi = tenThousandTiexi * (tickMoney / 10f);
        tieXianRateView.setText(String.format(Locale.CHINESE, "%.2f", tieXi) + getString(R.string.recharge_money_unit));
        //贴息金额
        float tieXian = tickMoney * 10000f - tieXi;
        tieXianMoneyView.setText(String.format(Locale.CHINESE, "%.2f", tieXian) + getString(R.string.recharge_money_unit));
    }

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            if (dateTypeStart.equals(dateType)){
                startCalendar = calendar;
            }else {
                endCalendar = calendar;
            }
            setDateCalendar();
        }
    };
}
