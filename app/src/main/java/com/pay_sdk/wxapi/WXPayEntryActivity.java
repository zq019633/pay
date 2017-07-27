package com.pay_sdk.wxapi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.pay_sdk.Utils.Constants;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import de.greenrobot.event.EventBus;

/**
 * Created by zhouqiang on 2017/7/27.
 */

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case 0:
                setPayResult(wxPayResult.success);
                break;
            case -1:
                setPayResult(wxPayResult.fail);
                break;
            case -2:
                setPayResult(wxPayResult.cancle);
                break;
            default:
                break;
        }

    }

    private void setPayResult(wxPayResult payResultResult) {
        EventBus.getDefault().post(payResultResult);
        finish();

    }

    public enum wxPayResult {success, fail, cancle, error}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constants.APPID);
        api.handleIntent(getIntent(), this);
    }
}
