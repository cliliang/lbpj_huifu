package com.baluobo.product.ui;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baluobo.R;
import com.baluobo.app.ui.BaseFragment;
import com.baluobo.common.net.APIClient;
import com.baluobo.common.tools.LuoboImageLoader;
import com.baluobo.common.views.ProductInfoItemView;
import com.baluobo.product.actions.ProductInfoAction;
import com.baluobo.product.bean.Product;
import com.baluobo.user.model.ProductEnum;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.Locale;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/20.
 */
public class ProductDetailFragment extends BaseFragment {
    private Product product;
    private String imageURL;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            product = bundle.getParcelable(ProductInfoAction.BOUND_FRAGMENT_INFO_DATA);
            imageURL = bundle.getString(ProductInfoAction.BOUND_FRAGMENT_SHEN_HE_DATA);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_detail_fragment_layout, container, false);
        setupView(view);
        return view;
    }

    @Override
    public void setupView(View view) {
        super.setupView(view);
        LinearLayout dingTouLayout = (LinearLayout) view.findViewById(R.id.product_detail_ding_tou_layout);
        LinearLayout yinPaioLayout = (LinearLayout) view.findViewById(R.id.product_detail_yin_piao_miao_layout);

        if (product != null){
            if (ProductEnum.getProduct(product.getGcId()) == ProductEnum.LUOBO_DING_TOU){
                dingTouLayout.setVisibility(View.VISIBLE);
                yinPaioLayout.setVisibility(View.GONE);

//                ProductInfoItemView minInvestView = (ProductInfoItemView) view.findViewById(R.id.product_info_min_invest_view);
                ProductInfoItemView backBankView = (ProductInfoItemView) view.findViewById(R.id.product_info_back_bank);
                ProductInfoItemView startView = (ProductInfoItemView) view.findViewById(R.id.product_info_start_day);
                ProductInfoItemView endView = (ProductInfoItemView) view.findViewById(R.id.product_info_end_day);
//                ProductInfoItemView backView = (ProductInfoItemView) view.findViewById(R.id.product_info_back_account_day);
//                ProductInfoItemView calcualte = (ProductInfoItemView) view.findViewById(R.id.product_info_calculate);
                ProductInfoItemView backTypeView = (ProductInfoItemView) view.findViewById(R.id.product_info_back_type);
//                minInvestView.setContentView(String.format(Locale.US, "%.0f", product.getInvestUnit()));
                String payLabel = product.getPayLabel();
                if (payLabel.length() >= 8){
                    payLabel  = payLabel.substring(3, payLabel.length() - 5);
                }
                backBankView.setContentView(payLabel);
//                if (product.getGcId() != ProductEnum.LUOBO_KUAI_ZHUAN.getProductTypeId()){
//                    calcualte.setVisibility(View.GONE);
//                    startView.setVisibility(View.VISIBLE);
//                    startView.setContentView(product.getValuesTime());
//                    endView.setVisibility(View.VISIBLE);
//                    endView.setContentView(product.getValueTime());
//                    backView.setVisibility(View.VISIBLE);
//                    backView.setContentView(product.getValuedTime());
//                }else {
//                    calcualte.setVisibility(View.VISIBLE);
//                    calcualte.setContentView(getString(R.string.product_calculate_type));
//                    startView.setVisibility(View.GONE);
//                    endView.setVisibility(View.GONE);
//                    backView.setVisibility(View.GONE);
//                }

                startView.setContentView(product.getValuesTime());
                endView.setContentView(product.getValueTime());

                int backType = product.getBackType();
                if (backType == 0){
                    backTypeView.setContentView(getString(R.string.money_back_type1));
                }else {
                    backTypeView.setContentView(getString(R.string.money_back_type2));
                }
            }else {
                dingTouLayout.setVisibility(View.GONE);
                yinPaioLayout.setVisibility(View.VISIBLE);
                LinearLayout showLayout = (LinearLayout) view.findViewById(R.id.product_detail_show_yin_piao_layout);
                showLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showProductImage();
                    }
                });
            }
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
