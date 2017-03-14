package com.baluobo.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.common.views.TradingCircleView;
import com.baluobo.user.model.Order;
import com.baluobo.user.model.TradingRecord;
import com.nostra13.universalimageloader.utils.L;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/18.
 */
public class TradingRecordAdapter extends BaseAdapter {
    private List<TradingRecord> data;
    private Context context;
    public TradingRecordAdapter(Context cnt){
        this.context = cnt;
        data = new ArrayList<>();
    }
    public void setRefreshData(List<TradingRecord> list){
        if (list == null){
            return;
        }
        this.data = list;
    }

    public void setLoadingData(List<TradingRecord> list){
        if (list == null){
            return;
        }
        data.addAll(list);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.trading_recording_item_layout, null);
        }
        TextView timeView = (TextView) convertView.findViewById(R.id.trading_recording_time);
        TextView typeView = (TextView) convertView.findViewById(R.id.trading_recording_type);
        TextView moneyView = (TextView) convertView.findViewById(R.id.trading_recording_money);
        TextView resultView = (TextView) convertView.findViewById(R.id.trading_recording_result);
        TradingRecord record = data.get(position);
        if (record != null){
            long timeLong = record.getCreateTime();
            Date date = new Date(timeLong);
            String timeString = String.format(Locale.US, "%tR", date);
            String dayString = String.format(Locale.US, "%tF", date);
            timeView.setText(dayString + " " + timeString);
            int type = record.getType();
            String symbol = "";
            //1表示投资 2表示提现 3表示充值 4表示赎回 5表示还款 6表示冻结 7表示解除冻结
            switch (type){
                case 1:
                    symbol = "-";
                    break;
                case 2:
                    symbol = "-";
                    break;
                case 3:
                    symbol = "+";
                    break;
                case 4:
                    symbol = "+";
                    break;
                case 5:
                    symbol = "+";
                    break;
                case 6:
                case 7:
                    break;
            }
            typeView.setText(record.getMessage());
            String handleString = String.format(Locale.US, "%.2f", record.getMoney());
            moneyView.setText(symbol + handleString);
            int result = record.getFlg();
            if (result == 0){
                resultView.setText(context.getText(R.string.trading_recording_fail));
            }else {
                resultView.setText(context.getText(R.string.trading_recording_success));
            }
        }
        return convertView;
    }
}
