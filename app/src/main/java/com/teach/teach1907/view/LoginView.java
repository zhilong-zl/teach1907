package com.teach.teach1907.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;

import com.teach.teach1907.R;
import com.teach.teach1907.interfaces.MyTextWatcher;
import com.yiyatech.utils.ext.ToastUtils;
import com.yiyatech.utils.newAdd.RegexUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author : 芝龙      2020/6/3 16:09
 **/
public class LoginView extends RelativeLayout {

    @BindView(R.id.account_name)
    EditText accountName;
    @BindView(R.id.account_secrete)
    EditText accountSecrete;
    @BindView(R.id.account_module)
    LinearLayout accountModule;
    @BindView(R.id.account_login)
    TextView accountLogin;
    @BindView(R.id.verify_login)
    TextView verifyLogin;
    @BindView(R.id.login_press)
    TextView loginPress;
    @BindView(R.id.verify_point)
    View verifyPoint;
    @BindView(R.id.account_point)
    View accountPoint;
    @BindView(R.id.verify_name)
    EditText verifyName;
    @BindView(R.id.verify_code)
    EditText verifyCode;
    @BindView(R.id.area_code)
    TextView areaCode;
    @BindView(R.id.get_verify_code)
    public TextView getVerifyCode;
    @BindView(R.id.verify_first_cut)
    View verifyFirstCut;
    @BindView(R.id.verify_two_cut)
    View verifyTwoCut;
    @BindView(R.id.verify_area)
    ConstraintLayout verifyView;
    @BindView(R.id.more_type_group)
    Group moreTypeGroup;
    private Context mContext;
    public final int ACCOUNT_TYPE = 1, VERIFY_TYPE = 2;
    public int mCurrentLoginType = ACCOUNT_TYPE;
    private final boolean mIsMoreType;

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.login_view, this);
        ButterKnife.bind(this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LoginView, 0, 0);
        mIsMoreType = ta.getBoolean(R.styleable.LoginView_isMoreType, true);
        initView();
        if (!mIsMoreType) {
            moreTypeGroup.setVisibility(GONE);
        }
    }

    private void initView() {
        loginPress.setEnabled(false);
        accountSecrete.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onMyTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 5 && !TextUtils.isEmpty(accountName.getText().toString().trim())) {
                    loginPress.setEnabled(true);
                } else loginPress.setEnabled(false);
            }
        });
        verifyCode.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onMyTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 3 && RegexUtil.isPhone(verifyName.getText().toString().trim())) {
                    loginPress.setEnabled(true);
                } else {
                    loginPress.setEnabled(false);
                }
            }
        });
    }

    @OnClick({R.id.account_login, R.id.verify_login, R.id.login_press, R.id.get_verify_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.account_login:
                accountLogin.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                accountPoint.setVisibility(VISIBLE);
                verifyLogin.setTextColor(ContextCompat.getColor(mContext, R.color.dark_gray));
                verifyPoint.setVisibility(INVISIBLE);
                accountModule.setVisibility(VISIBLE);
                verifyView.setVisibility(INVISIBLE);
                mCurrentLoginType = ACCOUNT_TYPE;
                break;
            case R.id.verify_login:
                accountLogin.setTextColor(ContextCompat.getColor(mContext, R.color.dark_gray));
                accountPoint.setVisibility(INVISIBLE);
                verifyLogin.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                verifyPoint.setVisibility(VISIBLE);
                accountModule.setVisibility(INVISIBLE);
                verifyView.setVisibility(VISIBLE);
                verifyPoint.setBackgroundColor(ContextCompat.getColor(mContext, R.color.red));
                mCurrentLoginType = VERIFY_TYPE;
                break;
            case R.id.login_press:
                String userName = "", userPwd = "";
                if (mCurrentLoginType == ACCOUNT_TYPE) {
                    userName = accountName.getText().toString().trim();
                    userPwd = accountSecrete.getText().toString().trim();
                } else {
                    userName = verifyName.getText().toString().trim();
                    userPwd = verifyCode.getText().toString().trim();
                }
                if (mLoginViewCallBack != null)
                    mLoginViewCallBack.loginPress(mCurrentLoginType, userName, userPwd);
                accountLogin.setTextColor(ContextCompat.getColor(mContext, R.color.dark_gray));
                break;
            case R.id.get_verify_code:
                if (TextUtils.isEmpty(verifyName.getText().toString())) {
                    ToastUtils.show(mContext, "用户名为空");
                }
                if (!RegexUtil.isPhone(verifyName.getText().toString().trim())) {
                    ToastUtils.show(mContext, "手机号格式错误");
                }
                if (mLoginViewCallBack != null) {

                    mLoginViewCallBack.sendVerifyCode(areaCode.getText().toString() + verifyName.getText().toString().trim());
                }
                accountLogin.setTextColor(ContextCompat.getColor(mContext, R.color.dark_gray));
                break;

        }

    }

    private LoginViewCallBack mLoginViewCallBack;

    public void setLoginViewCallBack(LoginViewCallBack loginViewCallBack) {
        this.mLoginViewCallBack = loginViewCallBack;
    }

    public interface LoginViewCallBack {
        void sendVerifyCode(String phoneNum);

        void loginPress(int type, String userName, String pwd);
    }
}
