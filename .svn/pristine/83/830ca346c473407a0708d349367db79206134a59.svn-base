package com.baluobo.product.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.common.tools.UIHelper;

import java.util.Locale;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/12/22.
 */
public class ProductSellProgress extends LinearLayout {
    private TextView rateView, rateBlockView;
    private TextView line1Block1View, line1Block2View;
    private TextView line2Block1View, line2Block2View;
    private TextView backgroundView;
    private LinearLayout foregroundView;
    private Context context;
    private int bitmapWidth;
    public ProductSellProgress(Context context) {
        super(context);
        init(context);
    }

    public ProductSellProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProductSellProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.product_sell_progress_view_layout, this);
        rateView = (TextView) findViewById(R.id.product_sell_count_rate_view);
        rateBlockView = (TextView) findViewById(R.id.product_sell_count_rate_block_view);
        line1Block1View = (TextView) findViewById(R.id.product_sell_line_block1);
        line1Block2View = (TextView) findViewById(R.id.product_sell_line_block2);
        line2Block1View = (TextView) findViewById(R.id.product_sell_line2_block1);
        line2Block2View = (TextView) findViewById(R.id.product_sell_line2_block2);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.product_sell_icon);
        bitmapWidth = bitmap.getWidth();
    }

    public void setRate(float rate){
        if (rate == 0){
            rateView.setText("0%");
        }else if (rate == 1){
            rateView.setText("100%");
        }else {
            float rateFloat = rate * 100;
            rateView.setText(String.format(Locale.CHINA, "%.1f%%", rateFloat));
        }

        int[] screen = UIHelper.getScreenDisplay(context);
        int screenWidth = screen[0];
        int margin = context.getResources().getDimensionPixelSize(R.dimen.product_progress_margin);
        int progressWidth = screenWidth - margin * 2 - bitmapWidth;
        int rateBlockViewWidth = (int)(progressWidth * (1 - rate));
        LayoutParams params = (LayoutParams) rateBlockView.getLayoutParams();
        params.width = rateBlockViewWidth;
        params.rightMargin = bitmapWidth / 2;
        rateBlockView.setLayoutParams(params);

        LayoutParams params11 = (LayoutParams) line1Block1View.getLayoutParams();
        params11.weight = rate;
        line1Block1View.setLayoutParams(params11);

        LayoutParams params12 = (LayoutParams) line1Block2View.getLayoutParams();
        params12.weight = 1-rate;
        line1Block2View.setLayoutParams(params12);

        LayoutParams params21 = (LayoutParams) line2Block1View.getLayoutParams();
        params21.weight = rate;
        line2Block1View.setLayoutParams(params21);

        LayoutParams params22 = (LayoutParams) line2Block2View.getLayoutParams();
        params22.weight = 1 - rate;
        line2Block2View.setLayoutParams(params22);

        backgroundView = (TextView) findViewById(R.id.product_sell_progress_background);
        FrameLayout.LayoutParams backgroundParams = (FrameLayout.LayoutParams) backgroundView.getLayoutParams();
        backgroundParams.setMargins(bitmapWidth / 2, 0, bitmapWidth / 2, 0);
        backgroundView.setLayoutParams(backgroundParams);

        foregroundView = (LinearLayout) findViewById(R.id.product_sell_progress_foreground);
        FrameLayout.LayoutParams foregroundParams = (FrameLayout.LayoutParams) foregroundView.getLayoutParams();
        foregroundParams.setMargins(bitmapWidth / 2, 0, bitmapWidth / 2, 0);
        foregroundView.setLayoutParams(foregroundParams);

    }
}
