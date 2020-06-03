package com.teach.teach1907.model;

import android.content.Context;

import com.teach.frame.ApiConfig;
import com.teach.frame.FrameApplication;
import com.teach.frame.ICommonModel;
import com.teach.frame.ICommonPresenter;
import com.teach.frame.NetManger;
import com.teach.frame.utils.ParamHashMap;
import com.teach.teach1907.R;
import com.teach.teach1907.base.Application1907;

/**
 * author : 芝龙      2020/6/3 17:48
 **/
public class AccountModel implements ICommonModel {
    private NetManger netManger = NetManger.getInstance();
    private Context context = Application1907.get07ApplicationContext();

    @Override
    public void getData(ICommonPresenter pPresenter, int whichApi, Object[] params) {
        switch (whichApi) {
            case ApiConfig.SEND_VERIFY:
                netManger.netWork(netManger.getService(context.getString(R.string.passport_openapi_user)).getLoginVerify((String) params[0]), pPresenter, whichApi);
                break;
            case ApiConfig.VERIFY_LOGIN:
                netManger.netWork(netManger.getService(context.getString(R.string.passport_openapi_user)).loginByVerify(new ParamHashMap().add("mobile", params[0]).add("code", params[1])), pPresenter, whichApi);
                break;
            case ApiConfig.GET_HEADER_INFO:
                String uid = FrameApplication.getFrameApplication().getLoginInfo().getUid();
                netManger.netWork(netManger.getService(context.getString(R.string.passport_api)).getHeaderInfo(new ParamHashMap().add("zuid", uid).add("uid", uid)), pPresenter, whichApi);
                break;
        }

    }
}
