package com.kedang.fenxiao.integration.bestpay.config;

/**
 * 支付配置信息
 * @author Ocean
 *
 */
public interface ServerURLConfig{ 
	String TEST_PAY_TO_BANK_URL = "https://finance.bestpay.com.cn/fas/service/payToBank"; //从企业账户扣钱，付款到指定的银行账户中
    String TEST_SIGN_BANKCARD_URL = "https://finance.bestpay.com.cn/fas/service/sign"; //签约服务接口
    String TEST_CANCEL_SIGN_BANKCARD_URL = "https://finance.bestpay.com.cn/fas/service/cancelSign"; //解约服务接口
    String TEST_SIGN_AGENT_RECEIVE_TO_ACCOUNT = "https://finance.bestpay.com.cn/fas/service/signAgentReceiveToAccount";  //签约实时代收入账接口
    String TEST_AUTH_CARD_WITHDRAW = "https://finance.bestpay.com.cn/fas/service/authCardAcctWithDrawal";  //授权银行卡提现接口
    String TEST_TRANS_INTEGRAT_QUERY = "https://finance.bestpay.com.cn/fas/service/transIntegratedQuery";  //交易综合查询接口
	
//    String PAY_TO_BANK_URL = "https://finance.bestpay.com.cn/fas/service/payToBank"; //从企业账户扣钱，付款到指定的银行账户中
//    String TEST_PAY_TO_BANK_URL = "http://183.62.49.171:9090/fas/service/payToBank"; //从企业账户扣钱，付款到指定的银行账户中
//    String TEST_SIGN_BANKCARD_URL = "http://183.62.49.171:9090/fas/service/sign"; //签约服务接口
//    String TEST_CANCEL_SIGN_BANKCARD_URL = "http://183.62.49.171:9090/fas/service/cancelSign"; //解约服务接口
//    String TEST_SIGN_AGENT_RECEIVE_TO_ACCOUNT = "http://183.62.49.171:9090/fas/service/signAgentReceiveToAccount";  //签约实时代收入账接口
//    String TEST_AUTH_CARD_WITHDRAW = "http://183.62.49.171:9090/fas/service/authCardAcctWithDrawal";  //授权银行卡提现接口
//    String TEST_TRANS_INTEGRAT_QUERY = "http://183.62.49.171:9090/fas/service/transIntegratedQuery";  //交易综合查询接口
}
