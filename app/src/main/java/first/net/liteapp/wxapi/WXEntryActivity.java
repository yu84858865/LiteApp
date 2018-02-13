package first.net.liteapp.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import first.net.liteapp.constant.Constant;

/**
 * Created by 10960 on 2018/2/13.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constant.WECHAT_APP_ID,false);
        api.registerApp(Constant.WECHAT_APP_ID);
        api.handleIntent(getIntent(),this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        Intent intent;
        String result = "";
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                switch (baseResp.getType()) {
                    case ConstantsAPI.COMMAND_SENDAUTH:
                        result = "登陆成功";
                        return;
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                        result = "分享成功";
                        break;
                    default:
                        result = "分享成功";
                        break;
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                switch (baseResp.getType()) {
                    case ConstantsAPI.COMMAND_SENDAUTH:
                        result = "取消";
                        break;
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                        result = "取消";
                        break;

                    default:
                        result = "取消";
                        break;
                }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                switch (baseResp.getType()) {
                    case ConstantsAPI.COMMAND_SENDAUTH:
                        result = "登陆失败";
                        break;
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                        result = "登陆失败";
                        break;

                    default:
                        result = "登陆失败";
                        break;
                }
                break;
            default:
                result = "取消";
                break;
        }
    }
}
