package com.baluobo.product.ui;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.ui.BaseFragment;
import com.baluobo.common.net.APIClient;
import com.baluobo.common.tools.LuoboImageLoader;
import com.baluobo.product.actions.ProductInfoAction;
import com.baluobo.user.model.ProductEnum;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/20.
 */
public class ProductAuditFragment extends BaseFragment {
    private ProductEnum productEnum;
    private String imageURL;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            int typeId = bundle.getInt(ProductInfoAction.BOUND_FRAGMENT_SHEN_HE_GOOD_TYPE);
            if (typeId == ProductEnum.LUOBO_DING_TOU.getProductTypeId()) {
                productEnum = ProductEnum.LUOBO_DING_TOU;
            } else if (typeId == ProductEnum.LUOBO_KUAI_ZHUAN.getProductTypeId()) {
                productEnum = ProductEnum.LUOBO_KUAI_ZHUAN;
            } else if (typeId == ProductEnum.LUOBO_XIN_SHOU.getProductTypeId()) {
                productEnum = ProductEnum.LUOBO_XIN_SHOU;
            } else {
                productEnum = ProductEnum.LUOBO_YIN_PIAO;
            }
            imageURL = bundle.getString(ProductInfoAction.BOUND_FRAGMENT_SHEN_HE_DATA);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_audit_fragment_layout, container, false);
        setupView(view);
        return view;
    }

    @Override
    public void setupView(View view) {
        super.setupView(view);
        switch (productEnum) {
            case LUOBO_YIN_PIAO:
                ViewStub viewStub = (ViewStub) view.findViewById(R.id.product_audit_view_stub_yin_piao);
                viewStub.inflate();
                ImageView imageView = (ImageView) view.findViewById(R.id.product_audit_image);
                LuoboImageLoader.getInstance().displayImage(APIClient.getBannerHost() + imageURL, imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showProductImage();
                    }
                });
                break;
            case LUOBO_KUAI_ZHUAN:
                ViewStub kuaiZhuanStub = (ViewStub) view.findViewById(R.id.product_audit_view_stub_kuai_zhuan);
                kuaiZhuanStub.inflate();
                TextView flagView1 = (TextView) view.findViewById(R.id.product_audit_kuai_zhuan_flag1);
                TextView flagView2 = (TextView) view.findViewById(R.id.product_audit_kuai_zhuan_flag2);

                SpannableStringBuilder spannable1 = new SpannableStringBuilder(flagView1.getText().toString());
                ForegroundColorSpan span1 = new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.color_40));
                spannable1.setSpan(span1, 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                flagView1.setText(spannable1);

                SpannableStringBuilder spannable2 = new SpannableStringBuilder(flagView2.getText().toString());
                ForegroundColorSpan span2 = new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.color_40));
                spannable2.setSpan(span2, 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                flagView2.setText(spannable2);
                break;
            case LUOBO_DING_TOU:
                ViewStub dingTouStub = (ViewStub) view.findViewById(R.id.product_audit_view_stub_ding_tou);
                dingTouStub.inflate();

                TextView flagView3 = (TextView) view.findViewById(R.id.product_audit_ding_tou_flag1);
                TextView flagView4 = (TextView) view.findViewById(R.id.product_audit_ding_tou_flag2);

                SpannableStringBuilder spannable3 = new SpannableStringBuilder(flagView3.getText().toString());
                ForegroundColorSpan span3 = new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.color_40));
                spannable3.setSpan(span3, 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                flagView3.setText(spannable3);

                SpannableStringBuilder spannable4 = new SpannableStringBuilder(flagView4.getText().toString());
                ForegroundColorSpan span4 = new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.color_40));
                spannable4.setSpan(span4, 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                flagView4.setText(spannable4);
                break;
            case LUOBO_XIN_SHOU:
                ViewStub xinShouStub = (ViewStub) view.findViewById(R.id.product_audit_view_stub_xin_shou);
                xinShouStub.inflate();

                TextView flagView5 = (TextView) view.findViewById(R.id.product_audit_xin_shou_flag1);
                TextView flagView6 = (TextView) view.findViewById(R.id.product_audit_xin_shou_flag2);

                SpannableStringBuilder spannable5 = new SpannableStringBuilder(flagView5.getText().toString());
                ForegroundColorSpan span5 = new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.color_40));
                spannable5.setSpan(span5, 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                flagView5.setText(spannable5);

                SpannableStringBuilder spannable6 = new SpannableStringBuilder(flagView6.getText().toString());
                ForegroundColorSpan span6 = new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.color_40));
                spannable6.setSpan(span6, 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                flagView6.setText(spannable6);
                break;
        }
    }

    private void showProductImage(){
        final Dialog imageDialog = new Dialog(getActivity(), R.style.ImageDialogStyle);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.show_product_image_activity_layout, null);
        final ImageView imageView = (ImageView) view.findViewById(R.id.product_show_image_id);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageDialog.dismiss();
            }
        });
        final PhotoViewAttacher mAttacher = new PhotoViewAttacher(imageView);
        LuoboImageLoader.getInstance().displayImage(APIClient.getBannerHost() + imageURL, imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                imageView.setImageBitmap(loadedImage);
                mAttacher.update();
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
        imageDialog.setContentView(view);
        imageDialog.show();
    }
}
