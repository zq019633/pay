package com.pay_sdk;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.pay_sdk.Utils.Constants;
import com.pay_sdk.Utils.checkPermissions;
import com.pay_sdk.wx.WXPayBean;
import com.pay_sdk.wxapi.WXPayEntryActivity;
import com.pay_sdk.zfb.Alipay;
import com.pay_sdk.zfb.PayBean;
import com.pay_sdk.zfb.PayResult;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private static final int SDK_PAY_FLAG = 1;

    private Button btn;
    private WXPayBean payBean;
    private Button zfb;
    private Gson gson;
    private PayBean zfbPayBean;
    private String orderInf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        gson = new Gson();
        initView();
    }

    private void initView() {
        checkPermissions.Permission(this);
        btn = (Button) findViewById(R.id.bt);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wxs = "{\"isok\":true,\"data\":{\"appid\":\"wx214ad894fe5a2049\",\"partnerid\":\"1311639201\",\"prepayid\":\"wx201707271342536340aff95d0214256436\",\"package\":\"Sign=WXPay\",\"noncestr\":\"b5ejznh3tcy9vocdbqk6uiv3ox3xbztt\",\"timestamp\":1501134173,\"sign\":\"C002E8DC951AFA8FD273697835A21E81\",\"package_val\":\"Sign=WXPay\"}}";
                payBean = gson.fromJson(wxs, WXPayBean.class);
                doWX(payBean);
            }
        });

        zfb = (Button) findViewById(R.id.bt2);
        zfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String zfbs = "{\"isok\":true,\"data\":{\"needToSignData\":{\"app_id\":\"2016041401298151\",\"method\":\"alipay.trade.app.pay\",\"format\":\"JSON\",\"charset\":\"utf-8\",\"sign_type\":\"RSA2\",\"timestamp\":\"2017-07-27 01:45:00\",\"version\":\"1.0\",\"notify_url\":\"https:\\/\\/api.enet.com.cn\\/ciweek\\/alipay_notify.php\",\"biz_content\":\"{\\\"body\\\":\\\"\\\\u4e92\\\\u8054\\\\u7f51\\\\u5468\\\\u520a\\\\u7b2c633\\\\u671f\\\",\\\"subject\\\":\\\"\\\\u4e92\\\\u8054\\\\u7f51\\\\u5468\\\\u520a\\\\u7b2c633\\\\u671f\\\",\\\"out_trade_no\\\":\\\"ZFB_20170727014500602\\\",\\\"timeout_express\\\":\\\"15m\\\",\\\"total_amount\\\":\\\"0.01\\\",\\\"product_code\\\":\\\"QUICK_MSECURITY_PAY\\\"}\"},\"biz_content\":{\"body\":\"\\u4e92\\u8054\\u7f51\\u5468\\u520a\\u7b2c633\\u671f\",\"subject\":\"\\u4e92\\u8054\\u7f51\\u5468\\u520a\\u7b2c633\\u671f\",\"out_trade_no\":\"ZFB_20170727014500602\",\"timeout_express\":\"15m\",\"total_amount\":\"0.01\",\"product_code\":\"QUICK_MSECURITY_PAY\"},\"sign\":\"mzIVRmTk+urtfZJRW8uvWdFnsrhY5UiDwHsL5NEBXCAwzdcFqdE9x\\/vL4DtQMabm+z8SCKQNaNM3Zk7BByf6MvJ4fjRo5nFP\\/tlrAqyJ5pIaltsqhKR\\/K6b2Y73I3hAvcBGZa\\/Kuz4tCB85Dylg6clTHqNTKpREzLApgXfy4iFHNmFbppg7ncx0vukTPogilRwKxGclduu7itAONNAfg4cwwz1v76vGpAt7vzYO2Uv8Dgwt2QD+wUS2Q8hG8i6zUKsRQ4NRMbmgHMPkZE\\/AYkveROecc8fi1Ll5OxgRDk+C6B7nRmV0Q9fpEMnc45nWjHD6T1b\\/2iUxNtLQguldfhg==\",\"urlencode_sign\":\"mzIVRmTk%2BurtfZJRW8uvWdFnsrhY5UiDwHsL5NEBXCAwzdcFqdE9x%2FvL4DtQMabm%2Bz8SCKQNaNM3Zk7BByf6MvJ4fjRo5nFP%2FtlrAqyJ5pIaltsqhKR%2FK6b2Y73I3hAvcBGZa%2FKuz4tCB85Dylg6clTHqNTKpREzLApgXfy4iFHNmFbppg7ncx0vukTPogilRwKxGclduu7itAONNAfg4cwwz1v76vGpAt7vzYO2Uv8Dgwt2QD%2BwUS2Q8hG8i6zUKsRQ4NRMbmgHMPkZE%2FAYkveROecc8fi1Ll5OxgRDk%2BC6B7nRmV0Q9fpEMnc45nWjHD6T1b%2F2iUxNtLQguldfhg%3D%3D\",\"sign_content\":\"app_id=2016041401298151&biz_content={\\\"body\\\":\\\"\\\\u4e92\\\\u8054\\\\u7f51\\\\u5468\\\\u520a\\\\u7b2c633\\\\u671f\\\",\\\"subject\\\":\\\"\\\\u4e92\\\\u8054\\\\u7f51\\\\u5468\\\\u520a\\\\u7b2c633\\\\u671f\\\",\\\"out_trade_no\\\":\\\"ZFB_20170727014500602\\\",\\\"timeout_express\\\":\\\"15m\\\",\\\"total_amount\\\":\\\"0.01\\\",\\\"product_code\\\":\\\"QUICK_MSECURITY_PAY\\\"}&charset=utf-8&format=JSON&method=alipay.trade.app.pay¬ify_url=https:\\/\\/api.enet.com.cn\\/ciweek\\/alipay_notify.php&sign_type=RSA2×tamp=2017-07-27 01:45:00&version=1.0\",\"urlencode_sign_content\":\"app_id=2016041401298151&biz_content=%7B%22body%22%3A%22%5Cu4e92%5Cu8054%5Cu7f51%5Cu5468%5Cu520a%5Cu7b2c633%5Cu671f%22%2C%22subject%22%3A%22%5Cu4e92%5Cu8054%5Cu7f51%5Cu5468%5Cu520a%5Cu7b2c633%5Cu671f%22%2C%22out_trade_no%22%3A%22ZFB_20170727014500602%22%2C%22timeout_express%22%3A%2215m%22%2C%22total_amount%22%3A%220.01%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=utf-8&format=JSON&method=alipay.trade.app.pay¬ify_url=https%3A%2F%2Fapi.enet.com.cn%2Fciweek%2Falipay_notify.php&sign_type=RSA2×tamp=2017-07-27+01%3A45%3A00&version=1.0\"}}";

                zfbPayBean = gson.fromJson(zfbs, PayBean.class);
                orderInf = zfbPayBean.getData().getUrlencode_sign_content() + "&sign=" + zfbPayBean.getData().getUrlencode_sign();
                Log.e("支付串", "=" + orderInf);
                zfb(orderInf);


            }
        });

    }
//---------------------封装好的 支付宝支付 回掉到本页   ---以下----------------------------------------
    private void zfb(String orderInf) {
        String aa="app_id=2016041401298151&biz_content=%7B%22body%22%3A%22%5Cu4e92%5Cu8054%5Cu7f51%5Cu5468%5Cu520a%5Cu7b2c637%5Cu671f%22%2C%22subject%22%3A%22%5Cu4e92%5Cu8054%5Cu7f51%5Cu5468%5Cu520a%5Cu7b2c637%5Cu671f%22%2C%22out_trade_no%22%3A%22ZFB_20170727020007893%22%2C%22timeout_express%22%3A%2215m%22%2C%22total_amount%22%3A%2212.00%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=utf-8&format=JSON&method=alipay.trade.app.pay&notify_url=https%3A%2F%2Fapi.enet.com.cn%2Fciweek%2Falipay_notify.php&sign_type=RSA2&timestamp=2017-07-27+02%3A00%3A07&version=1.0&sign=pXhiD9eTq3qU%2BgfAFokUlZQ2W4OGMrV9ZdDVqCE5duyNzyDabaiBsP34DZIy29gM0e9rQgM0hnsaMUua15cthlZYSFHa5sBvK10D41ye65i11OsA%2BEpgzyM%2B6eNp4MpDhuw3q%2FvUijRhSifbt%2Bm0c%2FoTx2kHvcZrMh94ggBS9pfPPZbY%2FYIx0mN1w5usETdnx8lCEMsnRtq199GRqfX6fTL4aHuHtvputX8q5G5P7na7F%2FJG97fVqQaCTAJHNHmQURSSsFtiIStqiUhmz6pyFN%2BHiUuFO5KOTich5UH9dIlrLTvagdDMWYijpp7oJHxv6GloTbu%2BelYI3mMGm7K2Dw%3D%3D";
        new Alipay(MainActivity.this, aa, new Alipay.AlipayResultCallBack() {
            @Override
            public void onSuccess() {

                Toast.makeText(MainActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDealing() {
                Toast.makeText(MainActivity.this, "支付处理中...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int error_code) {
                switch (error_code) {
                    case Alipay.ERROR_RESULT:
                        Toast.makeText(MainActivity.this, "支付失败:支付结果解析错误", Toast.LENGTH_SHORT).show();
                        break;

                    case Alipay.ERROR_NETWORK:
                        Toast.makeText(MainActivity.this, "支付失败:网络连接错误", Toast.LENGTH_SHORT).show();
                        break;

                    case Alipay.ERROR_PAY:
                        Toast.makeText(MainActivity.this, "支付错误:支付码支付失败", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "支付错误", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "支付取消", Toast.LENGTH_SHORT).show();
            }
        }).doPay();

    }

    //----------------------------------------下边是封装好的微信支付------------------------
    //封装原因    如果不封装  结果会回掉在WXPayEntryActivity里   这边没法处理结果
    private void doWX(WXPayBean payBean) {

        final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
        // 将该app注册到微信
        msgApi.registerApp(Constants.APPID);
        Log.e("商户号", "=" + payBean.getData().getPartnerid());
        PayReq payReq = new PayReq();
        payReq.appId = payBean.getData().getAppid();
        payReq.nonceStr = payBean.getData().getNoncestr();
        payReq.packageValue = "Sign=WXPay";
        payReq.timeStamp = payBean.getData().getTimestamp() + "";
        payReq.prepayId = payBean.getData().getPrepayid();
        payReq.partnerId = payBean.getData().getPartnerid();
        payReq.sign = payBean.getData().getSign();
        msgApi.sendReq(payReq);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MainThread) //在ui线程执行
    public void onEventMainThread(WXPayEntryActivity.wxPayResult payResultResult) {
        Log.d("", payResultResult + "");
        if (this == null)
            return;

        switch (payResultResult) {
            case success:
                Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();
                break;
            case cancle:
                Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
                break;
            case fail:
                Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
                break;
            case error:
                Toast.makeText(this, "未知", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


    //--------------------------------下边是没有封装的支付宝----------------------------------------------------------------------------

//    @SuppressLint("HandlerLeak")
//    private Handler mHandler = new Handler() {
//        @SuppressWarnings("unused")
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case SDK_PAY_FLAG: {
//                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
//                    /**
//                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
//                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
//                     * docType=1) 建议商户依赖异步通知
//                     */
//                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
//
//                    String resultStatus = payResult.getResultStatus();
//                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
//                    if (TextUtils.equals(resultStatus, "9000")) {
//                        Toast.makeText(MainActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
//                    } else {
//                        // 判断resultStatus 为非"9000"则代表可能支付失败
//                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
//                        if (TextUtils.equals(resultStatus, "8000")) {
//                            Toast.makeText(MainActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
//
//                        } else {
//                            Log.e("=", "=" + resultStatus.toString());
//                            Log.e("=", "=" + resultInfo.toString());
//
//                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
//                            Toast.makeText(MainActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                    break;
//                }
//                default:
//                    break;
//            }
//        }
//
//    };

    //---------------------------------------------------------分割线----------------------------------------------------------------
//    private void doZFB(final String orderInf) {
//
//        // final String test="app_id=2015052600090779&biz_content=%7B%22timeout_express%22%3A%2230m%22%2C%22seller_id%22%3A%22%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22total_amount%22%3A%220.02%22%2C%22subject%22%3A%221%22%2C%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%22314VYGIAGG7ZOYY%22%7D&charset=utf-8&method=alipay.trade.app.pay&sign_type=RSA2&timestamp=2016-08-15%2012%3A12%3A15&version=1.0&sign=MsbylYkCzlfYLy9PeRwUUIg9nZPeN9SfXPNavUCroGKR5Kqvx0nEnd3eRmKxJuthNUx4ERCXe552EV9PfwexqW%2B1wbKOdYtDIb4%2B7PL3Pc94RZL0zKaWcaY3tSL89%2FuAVUsQuFqEJdhIukuKygrXucvejOUgTCfoUdwTi7z%2BZzQ%3D";
//        final String a = "app_id=2016041401298151&biz_content=%7B%22body%22%3A%22%5Cu4e92%5Cu8054%5Cu7f51%5Cu5468%5Cu520a%5Cu7b2c637%5Cu671f%22%2C%22subject%22%3A%22%5Cu4e92%5Cu8054%5Cu7f51%5Cu5468%5Cu520a%5Cu7b2c637%5Cu671f%22%2C%22out_trade_no%22%3A%22ZFB_20170727020007893%22%2C%22timeout_express%22%3A%2215m%22%2C%22total_amount%22%3A%2212.00%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=utf-8&format=JSON&method=alipay.trade.app.pay&notify_url=https%3A%2F%2Fapi.enet.com.cn%2Fciweek%2Falipay_notify.php&sign_type=RSA2&timestamp=2017-07-27+02%3A00%3A07&version=1.0&sign=pXhiD9eTq3qU%2BgfAFokUlZQ2W4OGMrV9ZdDVqCE5duyNzyDabaiBsP34DZIy29gM0e9rQgM0hnsaMUua15cthlZYSFHa5sBvK10D41ye65i11OsA%2BEpgzyM%2B6eNp4MpDhuw3q%2FvUijRhSifbt%2Bm0c%2FoTx2kHvcZrMh94ggBS9pfPPZbY%2FYIx0mN1w5usETdnx8lCEMsnRtq199GRqfX6fTL4aHuHtvputX8q5G5P7na7F%2FJG97fVqQaCTAJHNHmQURSSsFtiIStqiUhmz6pyFN%2BHiUuFO5KOTich5UH9dIlrLTvagdDMWYijpp7oJHxv6GloTbu%2BelYI3mMGm7K2Dw%3D%3D";
//        Runnable payRunnable = new Runnable() {
//
//            @Override
//            public void run() {
//                PayTask alipay = new PayTask(MainActivity.this);
//                Map<String, String> result = alipay.payV2(a, true);
//
//                Message msg = new Message();
//                msg.what = SDK_PAY_FLAG;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
//            }
//        };
//        // 必须异步调用
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
//
//
//    }

}
