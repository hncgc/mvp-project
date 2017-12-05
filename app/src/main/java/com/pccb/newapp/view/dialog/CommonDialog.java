package com.pccb.newapp.view.dialog;

/**
 *
 * @Date : 2017/10/31
 * @Desc : 通用提示对话框(一条文本消息和一个或者两个按钮）
 *
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.pccb.newapp.R;

/**
 *
 * @Date : 2017/10/31
 * @Desc : 通用提示对话框(一条文本消息和一个或者两个按钮）
 */
public class CommonDialog extends Dialog {

    //对话框类型(一个和两个按钮的区别)
    public enum Type {
        NORMAL, SINGLE
    }

    //消息文本
    @BindView(R.id.tv_message)
    TextView mTvMessage;
    //确认按钮
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    //左边按钮
    @BindView(R.id.btn_left)
    Button mBtnLeft;
    //右边按钮
    @BindView(R.id.btn_right)
    Button mBtnRight;
    //底部两个按钮布局
    @BindView(R.id.ll_buttons)
    LinearLayout mLayoutButtons;

    //对话框类型
    private Type mType = Type.NORMAL;

    //提示消息
    private String mMessage;
    //确认按钮文本
    private String mConfirmBtnText;
    //左边按钮文本
    private String mLeftBtnText;
    //右边按钮文本
    private String mRightBtnText;

    private View.OnClickListener mConfirmButtonListener;
    private View.OnClickListener mLeftButtonListener;
    private View.OnClickListener mRightButtonListener;

    public CommonDialog(@NonNull Context context) {
        this(context, Type.NORMAL);
    }

    public CommonDialog(@NonNull Context context, Type type) {
        super(context, R.style.AlertDialog);
        this.mType = type;

        this.mConfirmBtnText = context.getString(R.string.common_btn_confirm);
        this.mLeftBtnText = context.getString(R.string.common_btn_cancel);
        this.mRightBtnText = context.getString(R.string.common_btn_confirm);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        if (mType == Type.SINGLE) {
            mBtnConfirm.setVisibility(View.VISIBLE);
            mLayoutButtons.setVisibility(View.GONE);
        }

        if (mMessage != null) {
            mTvMessage.setText(mMessage);
        }

        setLeftBtnText(mLeftBtnText);
        setRightBtnText(mRightBtnText);
        setConfirmBtnText(mConfirmBtnText);
    }

    @OnClick({R.id.btn_confirm, R.id.btn_left, R.id.btn_right})
    public void onClick(View view) {
        dismiss();

        int id = view.getId();
        switch (id) {
            case R.id.btn_confirm:
                if (mConfirmButtonListener != null) {
                    mConfirmButtonListener.onClick(view);
                }
                break;
            case R.id.btn_left:
                if (mLeftButtonListener != null) {
                    mLeftButtonListener.onClick(view);
                }
                break;
            case R.id.btn_right:
                if (mRightButtonListener != null) {
                    mRightButtonListener.onClick(view);
                }
                break;
        }
    }

    public void setMessage(int resId) {
        setMessage(getContext().getString(resId));
    }

    public void setMessage(String message) {
        this.mMessage = message;
        if (mTvMessage != null) {
            mTvMessage.setText(message);
        }
    }

    public void setConfirmBtnText(int resId) {
        setConfirmBtnText(getContext().getString(resId));
    }

    public void setConfirmBtnText(String text) {
        this.mConfirmBtnText = text;
        if (mBtnConfirm != null) {
            mBtnConfirm.setText(text);
        }
    }

    public void setLeftBtnText(int resId) {
        setLeftBtnText(getContext().getString(resId));
    }

    public void setLeftBtnText(String text) {
        this.mLeftBtnText = text;
        if (mBtnLeft != null) {
            mBtnLeft.setText(text);
        }
    }

    public void setRightBtnText(int resId) {
        setRightBtnText(getContext().getString(resId));
    }

    public void setRightBtnText(String text) {
        this.mRightBtnText = text;
        if (mBtnRight != null) {
            mBtnRight.setText(text);
        }
    }

    public void setConfirmButtonListener(View.OnClickListener confirmButtonListener) {
        this.mConfirmButtonListener = confirmButtonListener;
    }

    public void setLeftButtonListener(View.OnClickListener leftButtonListener) {
        this.mLeftButtonListener = leftButtonListener;
    }

    public void setRightButtonListener(View.OnClickListener rightButtonListener) {
        this.mRightButtonListener = rightButtonListener;
    }
}
