package utils.yc.com.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018-01-29.
 */

public class PublicUtils {
    public static String getTest(){
        return "fangliguo";
    }
    /**
     * get App versionCode
     *
     * @param context
     * @return
     */

    public static String getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionCode = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * get App versionName
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionName = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 检查新版本
     * @param context
     */
    public static void checkVersion(Context context) {
        AllenVersionChecker
                .getInstance()
                .requestVersion()
//                .setRequestUrl(BaseConfig.BASE_API_URL + "checkVersion?version=" + PublicUtils.getVersionCode(context))
                .setRequestUrl("http://api-xy.hx0452.com.cn/api/user/reg")
                .request(new RequestVersionListener() {
                    @Nullable
                    @Override
                    public UIData onRequestVersionSuccess(String result) {
                        if (TextUtils.isEmpty(result)) {
                            return null;
                        } else {
                            String title = "";
                            String content = "";
                            String downloadurl = "";
                            try {


                                JSONObject jsonObject = new JSONObject(result);
                                title = jsonObject.getString("title");
                                content = jsonObject.getString("content");
                                downloadurl = jsonObject.getString("downloadurl");

                            } catch (JSONException e) {

                            }
                            return UIData.create()
                                    .setTitle(title)
                                    .setContent(content)
                                    .setDownloadUrl(downloadurl);

                        }
                    }

                    @Override
                    public void onRequestVersionFailure(String message) {

                    }
                })
                .excuteMission(context);
    }
}
