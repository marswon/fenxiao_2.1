package com.kedang.fenxiao.service.jiangsukami;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class JiangSuKaMiFlow {

    private static Logger logger = LoggerFactory.getLogger(JiangSuKaMiFlow.class);

    /**
     * 注：经常修改参数使用static变量声明
     * //"accNbr=18362774899;ticketNum=3L5VKRM3JM;ztInterSource=201002_tzcd;
     * //staffValue=13338660192;pubAreaCode=025;pushUserId=jszt_123456"
     */

    private static String ztInterSource = "201002_tzcd";
    private static String staffValue = "13338660192";
    private static String pubAreaCode = "025";
    private static String pushUserId = "jszt_123456";
    private static String flowUrl = "http://202.102.111.142/jszt/llzq/useElectronTicketBatch";//下单URL


    public static JiangSuKaMiResponse flowPay(String phone, String ticketNum) {

        JiangSuKaMiResponse _jiangSuKaMiResponse = null;

        try {
            logger.info("#################### 开始手工充值到[江苏卡密] ####################");
            //组装请求信息
            StringBuffer sb = new StringBuffer();
            sb.append("accNbr=").append(phone).append(";ticketNum=").append(ticketNum).append(";ztInterSource=")
                    .append(ztInterSource).append(";staffValue=").append(staffValue).append(";pubAreaCode=")
                    .append(pubAreaCode).append(";pushUserId=").append(pushUserId);
            logger.info(phone + "=======手工充值到江苏卡密参数:[" + sb.toString() + "]=====");
            Map<String, String> params = new HashMap<String, String>();
            String request = SimpleDesAndroid.encrypt(sb.toString(), DataDirection.TO_SERVER);
            params.put("para", request);
            logger.info(request);
            String response = HttpClientHelper.post2(flowUrl, params);
            if (null != response && response.contains("TSR_RESULT")) {
                response = response.replace("null(", "").replace(")", "");
                logger.info(phone + "手工充值到[江苏卡密]返回结果：" + response);
                // 组装可当返回结果
                _jiangSuKaMiResponse = new Gson().fromJson(response, JiangSuKaMiResponse.class);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.info(phone + "#################### 结束手工充值到[江苏卡密] ####################");
        return _jiangSuKaMiResponse;
    }
}
