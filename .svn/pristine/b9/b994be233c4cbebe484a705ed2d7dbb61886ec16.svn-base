package com.baluobo.common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baluobo.R;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/12.
 */
public class CustomInputView extends LinearLayout {
    private EditText editView;
    private IconTextView visibleView;
    private TextView buttonView;
    private ImageView deleteView;
    private OnBtnClickListener listener;
    private OnVisibleClickListener onVisibleClickListener;
    private CustomTextWatcher customTextWatcher;

    public CustomInputView(Context context) {
        super(context);
    }

    public CustomInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.custon_input_view_layout, this);
        editView = (EditText) findViewById(R.id.custom_input_view_text);
        visibleView = (IconTextView) findViewById(R.id.custom_input_visible_icon);
        buttonView = (TextView) findViewById(R.id.custom_input_view_button);
        deleteView = (ImageView) findViewById(R.id.custom_input_delete_icon);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomInputView);
        boolean setPassword = false;
        boolean showButton = false;
        try {
            String hintString = typedArray.getString(R.styleable.CustomInputView_hintText);
            boolean showVisible = typedArray.getBoolean(R.styleable.CustomInputView_iconVisible, false);
            showButton = typedArray.getBoolean(R.styleable.CustomInputView_buttonVisible, false);
            setPassword = typedArray.getBoolean(R.styleable.CustomInputView_setPassword, false);
            String btnString = typedArray.getString(R.styleable.CustomInputView_buttonText);
            editView.setHint(hintString);
            visibleView.setVisibility(showVisible ? VISIBLE : GONE);
            buttonView.setVisibility(showButton ? VISIBLE : GONE);
            buttonView.setText(btnString);
        } finally {
            typedArray.recycle();
        }

        visibleView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int type = editView.getInputType();
                switch (type) {
                    case InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD:
                        setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        visibleView.setText(R.string.icon_string_open_eye);
                        break;
                    case InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD:
                        setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        visibleView.setText(R.string.icon_string_close_eye);
                        break;
                }

                if (onVisibleClickListener != null) {
                    onVisibleClickListener.onVisibleClick();
                }
            }
        });
        buttonView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onBtnClickListener();
                }
            }
        });

        deleteView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editView.setText("");
            }
        });

        //当输入的为密码时，限制输入只能为大小写字母和数字
        if (setPassword) {
            editView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable edt) {
                    String temp = edt.toString();
                    if (temp.length() < 1){
                        return;
                    }
                    for (int i = 0; i< temp.length(); i++){
                        char mid = temp.charAt(i);
                        if (mid >= 48 && mid <= 57) {//数字
                            continue;
                        }
                        if (mid >= 65 && mid <= 90) {//大写字母
                            continue;
                        }
                        if (mid >= 97 && mid <= 122) {//小写字母
                            continue;
                        }
                        edt.delete(i, i + 1);
                        break;
                    }
                }
            });
        }

        //当显示“获取验证码”按钮时，判断输入的手机号是否正确，再决定按钮是否可用
        if (showButton){
            editView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String input = s.toString().trim();
                    if (input.length() < 11){
                        buttonView.setEnabled(false);
                    }else {
                        buttonView.setEnabled(true);
                    }
                }
            });
        }

        editView.addTextChangedListener(new TextWatcher() {
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
                    deleteView.setVisibility(GONE);
                }else {
                    deleteView.setVisibility(VISIBLE);
                }

                if (customTextWatcher != null){
                    customTextWatcher.afterTextChanged(s);
                }
            }
        });
        editView.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (!TextUtils.isEmpty(editView.getText().toString())){
                        deleteView.setVisibility(VISIBLE);
                    }else {
                        deleteView.setVisibility(GONE);
                    }
                }else {
                    deleteView.setVisibility(GONE);
                }
            }
        });
    }

    public interface CustomTextWatcher{
        void afterTextChanged(Editable s);
    }

    public void addCustomTextChangedListener(CustomTextWatcher textWatcher){
        this.customTextWatcher = textWatcher;
    }

    public void setInputContent(String content){
        editView.setText(content);
    }

    public void setInputHint(String hint){
        editView.setHint(hint);
    }

    public void setInputType(int inputType) {
        editView.setInputType(inputType);
    }

    public void setMaxInput(int count) {
        editView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(count)});
    }

    public String getInputText() {
        return editView.getText().toString().trim();
    }

    public interface OnBtnClickListener {
        void onBtnClickListener();
    }

    public void setOnBtnClickListener(OnBtnClickListener l) {
        this.listener = l;
    }

    public void setButtonText(String btnString) {
        buttonView.setText(btnString);
    }

    public void setButtonClickable(boolean clickable) {
        buttonView.setClickable(clickable);
    }

    public interface OnVisibleClickListener {
        void onVisibleClick();
    }

    public void setOnVisibleClickListener(OnVisibleClickListener listener) {
        this.onVisibleClickListener = listener;
    }

    public void changeVisibleState() {
        int type = editView.getInputType();
        switch (type) {
            case InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD:
                setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                break;
            case InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD:
                setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
        }
    }
}
