package com.kedang.fenxiao.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kedang.fenxiao.entity.FXMobileArea;
import com.kedang.fenxiao.repository.FXMobileAreaDao;
import com.kedang.fenxiao.util.CollectionUtils;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.po.ResultDo;

@Component
public class FXMobileAreaService {
    private static final Logger logger = LoggerFactory.getLogger(FXMobileAreaService.class);

    @Autowired
    private FXMobileAreaDao fxMobileAreaDao;

    @PersistenceContext
    private EntityManager em;

    public List<FXMobileArea> getAllProvince() {

        return fxMobileAreaDao.findAllGroupByProvince();
    }

    /**
     * 根据分销商ID查询已经开通的省份
     *
     * @param eId
     * @return
     */
    public List<FXMobileArea> findFXMobileAreaByEid(String eId) {
        return fxMobileAreaDao.findFXMobileAreaByEid(eId);
    }

    @Transactional(readOnly = false)
    public boolean addFxMobileArea(String moblies) {
        List<FXMobileArea> mobileAreas = null;
        try {
            if (StringUtils.isBlank(moblies)) {
                return false;
            }
            mobileAreas = new ArrayList<FXMobileArea>();
            String[] moblesData = moblies.split(",");
            String provinceId = "";
            for (int k = 0; k < moblesData.length; k++) {
                String mobile = moblesData[k].trim().substring(0, 7);
                JSONObject json = queryMobile(moblesData[k].trim());
                logger.info("=====json:" + json + "=========");
                // JSONObject json = queryMobile("18674086168");
                String catName = json.getString("catName");
                if (catName.contains("电信")) {
                    provinceId = "1";
                } else if (catName.contains("移动")) {
                    provinceId = "2";
                } else if (catName.contains("联通")) {
                    provinceId = "3";
                }
                logger.info(mobile + "=====" + showProvince(json.getString("province")) + "===="
                        + json.getString("province") + "===" + provinceId);
                // 判断号段是否存在数据库
                List<FXMobileArea> _list = fxMobileAreaDao.findFXMobileAreaByPrefix(mobile);
                if (CollectionUtils.isNotEmpty(_list)) {
                    logger.info("===号段已存在===" + mobile);
                    return false;
                } else {
                    // 保存到数据库
                    FXMobileArea fxMobileArea = new FXMobileArea();
                    fxMobileArea.setPrefix(mobile);
                    fxMobileArea.setProvinceId(showProvince(json.getString("province")));
                    fxMobileArea.setProvinceName(json.getString("province"));
                    fxMobileArea.setYysTypeId(provinceId);
                    mobileAreas.add(fxMobileArea);
                    fxMobileAreaDao.save(mobileAreas);
                }
            }
            logger.info("===结束添加号段表数据===");
            return true;
        } catch (Exception e) {
            logger.error("===添加号段表数据异常====");
            e.printStackTrace();
        }
        return false;
    }

    public static JSONObject queryMobile(String mobile) {
        // 获取归属地和运营商
        String str = sendGetGBK("https://tcc.taobao.com/cc/json/mobile_tel_segment.htm", "tel=" + mobile + "");
        return JSON.parseObject(str.split("=")[1]);
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            /*
			 * // 获取所有响应头字段 Map<String, List<String>> map =
			 * connection.getHeaderFields(); // 遍历所有的响应头字段 for (String key :
			 * map.keySet()) { System.out.println(key + "--->" + map.get(key));
			 * }
			 */
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static String showProvince(String provinceName) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("北京", "010");
        map.put("广东", "020");
        map.put("上海", "021");
        map.put("天津", "022");
        map.put("重庆", "023");
        map.put("江苏", "025");
        map.put("青海", "026");
        map.put("海南", "027");
        map.put("四川", "028");
        map.put("陕西", "029");
        map.put("山西", "030");
        map.put("河北", "035");
        map.put("河南", "039");
        map.put("内蒙古", "040");
        map.put("辽宁", "041");
        map.put("吉林", "045");
        map.put("黑龙江", "046");
        map.put("安徽", "050");
        map.put("浙江", "055");
        map.put("福建", "059");
        map.put("山东", "060");
        map.put("广西", "070");
        map.put("湖北", "071");
        map.put("湖南", "073");
        map.put("江西", "075");
        map.put("云南", "080");
        map.put("贵州", "085");
        map.put("西藏", "089");
        map.put("宁夏", "090");
        map.put("甘肃", "093");
        map.put("新疆", "095");
        return map.get(provinceName);
    }

    @Transactional(readOnly = false)
    public ResultDo addAreaAndAreaName(String moblies) {
        int sum = 0;
        int ok = 0;
        int no = 0;
        int error = 0;
        List<FXMobileArea> mobileAreas = null;
        try {
            if (StringUtils.isBlank(moblies)) {
                return ResultFactory.getFailedResult("请输入有效号码!!");
            }
            mobileAreas = new ArrayList<FXMobileArea>();
            String[] moblesData = moblies.split(",");
            String provinceId = "";
            for (int k = 0; k < moblesData.length; k++) {
                sum++;
                String mobile = moblesData[k].trim().substring(0, 7);
                //===================
                //查询该手机号
                String result = sendGet("http://apis.juhe.cn/mobile/get",
                        "dtype=json&key=92b5bbb92c2659a8b37ac73cede9d782&phone=" + mobile);
                logger.info("===result==" + result);
                if (StringUtils.isNotBlank(result)) {
                    JSONObject js = JSONObject.parseObject(result);
                    if (null == js) {
                        logger.error("=========js 解析异常============");
                    }
                    String code = js.getString("resultcode");
                    if (code.equals("200")) {
                        String info = js.getString("result");
                        if (StringUtils.isNotBlank(info)) {
                            JSONObject j = JSONObject.parseObject(info);
                            if (null != j) {
                                //成功
                                String areaName = j.getString("city");
                                String areaCode = j.getString("areacode");
                                String province = j.getString("province");
                                String  company = j.getString("company");
                                if(company.contains("电信")){
                                	provinceId="1";
                                }else if(company.contains("移动")){
                                	provinceId="2";
                                }else if(company.contains("联通")){
                                	provinceId="3";
                                }
                                logger.info("code:" + code);
                                logger.info("areaName:" + areaName + ",areacode:" + areaCode+",provinceId:"+provinceId);
                                // 判断号段是否存在数据库
                                List<FXMobileArea> _list = fxMobileAreaDao.findFXMobileAreaByPrefix(mobile);
                                if (CollectionUtils.isNotEmpty(_list)) {
                                    no++;
                                    logger.info("===号段已存在===" + mobile);
                                } else {
                                	logger.info("===保存号段===" + mobile);
                                    // 保存到数据库
                                    FXMobileArea fxMobileArea = new FXMobileArea();
                                    fxMobileArea.setPrefix(mobile);
                                    fxMobileArea.setProvinceId(showProvince(province));
                                    fxMobileArea.setProvinceName(province);
                                    fxMobileArea.setYysTypeId(provinceId);
                                    fxMobileArea.setAreaCode(areaCode);
                                    fxMobileArea.setAreaName(areaName);
                                    mobileAreas.add(fxMobileArea);
                                    ok++;
                                }

                                logger.info(areaCode + "==" + showProvince(province) + "==" + province + "=="
                                        + areaName);
                            } else {
                                logger.info("==================第一次未查询到号段结果，请求第二次===================");
                                if (addFxMobileArea(moblies))
                                    ok++;
                                else
                                    no++;
                            }
                        }
                    } else {
                        logger.info("==================第一次未查询到号段结果，请求第二次===================");
                        if (addFxMobileArea(moblies))
                            ok++;
                        else
                            no++;
                    }
                } else {
                    logger.info("==================第一次未查询到号段结果，请求第二次===================");
                    if (addFxMobileArea(moblies))
                        ok++;
                    else
                        no++;
                }
            }
            if (CollectionUtils.isNotEmpty(mobileAreas))
                fxMobileAreaDao.save(mobileAreas);
            logger.info("===结束添加号段表数据===");
        } catch (Exception e) {
            logger.error("===添加号段表数据异常====");
            e.printStackTrace();
        }
        return ResultFactory.getSuccessResult("号码总个数:[" + sum + "]个,保存成功:[" + ok + "]个,已存在号段:[" + no + "]个,失败:["
                + error + "]");
    }

    @Transactional(readOnly = false)
    public void updateMobileArea(FXMobileArea fxMobileArea) {
        if (null != fxMobileArea) {
            fxMobileAreaDao.save(fxMobileArea);
        }
    }

    public static void main(String[] args) {
        String mobile = "1807027";
        String result = sendGet("http://apis.juhe.cn/mobile/get",
                "dtype=json&key=92b5bbb92c2659a8b37ac73cede9d782&phone=" + mobile);
        JSONObject js = JSONObject.parseObject(result);
        if (null == js) {
            return;
        }
        String code = js.getString("resultcode");
        if (code.equals("200")) {
            String info = js.getString("result");
            if (StringUtils.isNotBlank(info)) {
                JSONObject j = JSONObject.parseObject(info);
                if (null != j) {
                    //成功
                    String city = j.getString("city");
                    String areaCode = j.getString("areacode");
                    System.out.println("city:" + city + ",areacode:" + areaCode);
                    logger.info("code:" + code);
                    logger.info("city:" + city + ",areacode:" + areaCode);
                }
            }
        } else {
            logger.error("异常");
        }
        System.out.println(result);
    }

    /**
     * 向指定URL发送GET方法的请求 返回gbk格式
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGetGBK(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
			/*
			 * // 获取所有响应头字段 Map<String, List<String>> map =
			 * connection.getHeaderFields(); // 遍历所有的响应头字段 for (String key :
			 * map.keySet()) { System.out.println(key + "--->" + map.get(key));
			 * }
			 */
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "GBK"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}
