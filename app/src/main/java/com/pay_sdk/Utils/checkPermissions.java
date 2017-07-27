package com.pay_sdk.Utils;

import android.Manifest;

import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpOptions;
import com.pay_sdk.MainActivity;


/**
 * Created by zhouqiang on 2017/6/21.
 */

public class checkPermissions {
    public static void Permission(MainActivity mainActivity) {
        Acp.getInstance(mainActivity).request(new AcpOptions.Builder()
                        .setPermissions(
                                Manifest.permission.ACCESS_WIFI_STATE
                                , Manifest.permission.INTERNET
                                , Manifest.permission.READ_PHONE_STATE

                        )
                        //以下为自定义提示语、按钮文字
                        .setDeniedMessage("您拒绝存储权限申请，下载功能将不能正常使用，您可以去设置页面重新授权")
                        .setDeniedCloseBtn("关闭")
                        .setDeniedSettingBtn("设置权限")
                        .setRationalMessage("下载功能需要您授权，否则将不能正常使用")
                        .setRationalBtn("我知道了")
                        .build(),
                null);
    }
}