package first.net.liteapp.constant;

/**
 * Created by yuqiubo on 2018/2/9.
 */

public interface IConfig {
    String CONFIG_APPID = "1254439744";
    String CONFIG_BIZID = "20981";
    String CONFIG_KEY_SAFETY = "45e1f711d4c5c4e394087ebc1e6bfb2c";
    String CONFIG_KEY_AUTHENTICATION = "d61344649a9a016deab4d8ef40428867";

    String CONFIG_NUMBER_HOUSE = CONFIG_BIZID+"_test12345";
    String CONFIG_URL_FLOW ="rtmp://"+CONFIG_BIZID+".livepush.myqcloud.com/live/"+CONFIG_NUMBER_HOUSE+"?bizid="+CONFIG_BIZID;
}
