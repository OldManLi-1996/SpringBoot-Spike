package com.springboot.spike.alipay;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.request.AlipayUserAgreementPageSignRequest;
import com.alipay.api.response.AlipayUserAgreementPageSignResponse;

/**
 * User: 最帅气的老李头
 * Date: 2022/5/30
 * Time: 14:15
 * Description:
 */
public class AliPayController {

    //支付宝固定网关
    public static final String URL = "https://openapi.alipay.com/gateway.do";

    //应用的APP_ID
    public static final String APP_ID = "2021003128607208";

    //支付宝账号的APP_PRIVATE_KEY
    public static final String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCZKza/b0EKAmaWoVJXQlTd/AtU5DrrD+uWwSJgYUI5Vcs4OAm3n/YOA4njtAd/i+jY/BoRgvfJ4AnBdljOT2lWrnJ6zfgUhpO5rNVdqU4PLQG9kp3YCigNdM52/NHn4lgyHEwGz3ucXCPi3WNnViGODFGoV781hOh2cTyFQn7BB+B2uHMeiahaF+/siVMy4ah07LIGl1J3IEaAYKf1ZBLWQmQ7cP4AKtrHOezUk021+CVVupGy4r/OPflur193xpAckl9hW64UpsSClZl2WFIA3vxEu3Cs936ZcnfO3axwPIdmi00fNGzD766401iHBg7Sdvc0l1GdnNrBQ9uCxwCdAgMBAAECggEAKEkCZEVJsO1CuDmD46lxktUY0eIZUPvl9cekV/8coOHcylOK0r4CxFrP6JDQLA7LVk6wOIzeyTzgs/6Bivm99et59qy3pAZlUj+P+stt2XX38DlgxJKfSDLBnvAN8VRJ6BwjLuNhWpWtZpzd6S7xMmAxFfrGMrhbP9O2nEIJ/pC+w3WbSefPxWV4Eu0sVzIc8oB0O7jHYSHP/pDY57hf3X/vsVCxabMndUDEdE6NLEv9YUTKfpMxrlKzWeGo9Ub9CY9IPD3PBPBI6+Azzk5L+rJ7feKKJLEkXIYmHR376K16FfZoAYvV8NA9vbBR+w34I+U3IupYTOjSoHr6iA3BgQKBgQDvQCKHZDOAc+gIx019opvMRxll+z4TJeGaVV32v7hnLo/MMenTNKj82hpRv5GlptRjwtIsMgj3AdTKXwNzQ0XjJhIogockuXKJ4MXHJ4ZMNzgS+mg14KpTigK3g3Dwo/X20VDlIrxCeYCdCHwMmVfpv5QKrAjuqa3fRga54EXxIQKBgQCj5FDwJWbHoHvzrKnSjtrwpoUP1SeB2sjDLsFpZJVLiUi+EaGfSVK1zjp6HCo5cgU1+mwPnC6/X/FVugGid4tZcXgBHTT+tDD6g7wxNALs3dQloKxAM15WCAwwtChx2yL0KFPgoLt7JiX131xdr4LwKK78oXw0ayHMEe5ErzpT/QKBgF2CtLw0VsjbBJMByvDS8I/8LholA9MaGD5pJUu/DYUOttd5nsmVKIODf4v6EyQqu5KuX9U0TTq19YSPdFrFrXglti0tL0MUVIc9WjAH6764Smtogfik8g2qwHsXnboAa69ninW0iN52fOVjnpCSEsFixqsRKICXw0SkKxbVaKDhAoGBAJVfW1F0lTdukmplvSX2I5/f9TiX6nkUwZP7QjZfgR6mUhFBmo/F0Ud4XZ27m9Rdklg/FEwp6gVsWVXrwN+lmvD7VQeW6h0Wd+lrNZOWtu/RwjBFP5kH+Hvqu8mL+azDsBjdxIzfkeBMxZ5RZCrS80EVo18Hg193IQeQdBxPpJa1AoGBAJ/nejS+CunsRmssZEWOsamT+QtpDv1K/CUoa4G+lKtEv9gMxRjY3u60NTK+LImS+eQBAso3yRVgL5laE3rqatl8K6qxnsq0P9aRdLsLzcu336mTwT+7MH/7VaeEQi8jfLRgUF5lMV6I6a1KNGrFqUeUqUIK5+gtSzU2HgY9LQTV";

    //支付宝账号的ALIPAY_PUBLIC_KEY
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmSs2v29BCgJmlqFSV0JU3fwLVOQ66w/rlsEiYGFCOVXLODgJt5/2DgOJ47QHf4vo2PwaEYL3yeAJwXZYzk9pVq5yes34FIaTuazVXalODy0BvZKd2AooDXTOdvzR5+JYMhxMBs97nFwj4t1jZ1YhjgxRqFe/NYTodnE8hUJ+wQfgdrhzHomoWhfv7IlTMuGodOyyBpdSdyBGgGCn9WQS1kJkO3D+ACraxzns1JNNtfglVbqRsuK/zj35bq9fd8aQHJJfYVuuFKbEgpWZdlhSAN78RLtwrPd+mXJ3zt2scDyHZotNHzRsw++uuNNYhwYO0nb3NJdRnZzawUPbgscAnQIDAQAB";

    //固定参数
    public static final String FORMAT = "json";

    public static final String SIGN_TYPE = "RSA2";

    public static final String CHARSET = "utf-8";

    public static void main(String[] args) throws AlipayApiException {
//        getAgencyMessage();
        add1();
//        String privateKey = APP_PRIVATE_KEY;
//        String alipayPublicKey = ALIPAY_PUBLIC_KEY;
//        AlipayConfig alipayConfig = new AlipayConfig();
//        alipayConfig.setServerUrl("https://openapi.alipay.com/gateway.do");
//        alipayConfig.setAppId(APP_ID);
//        alipayConfig.setPrivateKey(privateKey);
//        alipayConfig.setFormat("json");
//        alipayConfig.setAlipayPublicKey(alipayPublicKey);
//        alipayConfig.setCharset("GBK");
//        alipayConfig.setSignType("RSA2");
//        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
//        AlipayUserAgreementPageSignRequest request = new AlipayUserAgreementPageSignRequest();
//        AlipayUserAgreementPageSignModel model = new AlipayUserAgreementPageSignModel();
//        AccessParams accessParams = new AccessParams();
//        accessParams.setChannel("ALIPAYAPP");
//        model.setAccessParams(accessParams);
//        model.setSignValidityPeriod("2m");
//        SubMerchantParams subMerchant = new SubMerchantParams();
//        subMerchant.setSubMerchantServiceName("滴滴出行免密支付");
//        subMerchant.setSubMerchantServiceDescription("免密付车费，单次最高500");
//        subMerchant.setSubMerchantName("滴滴出行");
//        subMerchant.setSubMerchantId("2088123412341234");
//        model.setSubMerchant(subMerchant);
//        SpecifiedChannelParam specifiedSortChannelParams = new SpecifiedChannelParam();
//        specifiedSortChannelParams.setBankCardType("DD");
//        specifiedSortChannelParams.setAssetTypeCode("LEDGER_ACCOUNT");
//        specifiedSortChannelParams.setPayToolType("BANKCARD");
//        specifiedSortChannelParams.setInstId("CCB");
//        specifiedSortChannelParams.setAssetId("123456789");
//        model.setSpecifiedSortChannelParams(specifiedSortChannelParams);
//        model.setProductCode("GENERAL_WITHHOLDING");
//        model.setPassParams("{\"key\":\"value\"}，具体值需要接入时确认");
//        model.setAgreementEffectType("DIRECT");
//        ProdParams prodParams = new ProdParams();
//        prodParams.setAuthBizParams("{\"platform\":\"taobao\"}");
//        model.setProdParams(prodParams);
//        model.setPromoParams("{\"key\":\"value\"}");
//        model.setMerchantProcessUrl("https://www.merchantpage.com/index?processId=2345678");
//        model.setEffectTime(300L);
//        SpecifiedAsset specifiedAsset = new SpecifiedAsset();
//        specifiedAsset.setBankCardNo("62XXXXXXXXXXXXXX");
//        specifiedAsset.setSpecifiedReasonCode("线下沟通具体传值，会根据对应参数拼接具体限定渠道的原因文案展示给用户");
//        specifiedAsset.setPayToolType("BANKCARD");
//        specifiedAsset.setInstId("CCB");
//        model.setSpecifiedAsset(specifiedAsset);
//        model.setExternalLogonId("13852852877");
//        model.setSignScene("INDUSTRY|CARRENTAL");
//        model.setPersonalProductCode("GENERAL_WITHHOLDING_P");
//        model.setUserAgeRange("{\"min\":\"18\",\"max\":\"30\"}");
//        IdentityParams identityParams = new IdentityParams();
//        identityParams.setCertNo("61102619921108888");
//        identityParams.setIdentityHash("8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92");
//        identityParams.setSignUserId("2088202888530893");
//        identityParams.setUserName("张三");
//        model.setIdentityParams(identityParams);
//        model.setAllowHuazhiDegrade("false");
//        model.setExternalAgreementNo("test");
//        DeviceParams deviceParams = new DeviceParams();
//        deviceParams.setDeviceName("电视");
//        deviceParams.setDeviceType("TV");
//        deviceParams.setDeviceId("device12345");
//        model.setDeviceParams(deviceParams);
//        PeriodRuleParams periodRuleParams = new PeriodRuleParams();
//        periodRuleParams.setPeriodType("DAY");
//        periodRuleParams.setPeriod(3L);
//        periodRuleParams.setTotalPayments(12L);
//        periodRuleParams.setExecuteTime("2019-01-23");
//        periodRuleParams.setSingleAmount("10.99");
//        periodRuleParams.setTotalAmount("600");
//        model.setPeriodRuleParams(periodRuleParams);
//        model.setThirdPartyType("PARTNER");
//        ZmAuthParams zmAuthParams = new ZmAuthParams();
//        zmAuthParams.setBuckleMerchantId("268820000000414397785");
//        zmAuthParams.setBuckleAppId("1001164");
//        model.setZmAuthParams(zmAuthParams);
//        request.setBizModel(model);
//        AlipayUserAgreementPageSignResponse response = alipayClient.pageExecute(request);
//        System.out.println(response.getBody());
//        if (response.isSuccess()) {
//            System.out.println("调用成功");
//        } else {
//            System.out.println("调用失败");
//        }
    }


    /**
     * 获取钱包签约报文，生成页面签约报文，并组装唤起钱包格式
     */
    public static void getAgencyMessage() throws AlipayApiException {
        JSONObject bizObject = new JSONObject();
        JSONObject tempJson = null;
        bizObject.put("sign_validity_period", "2m");        //当前用户签约请求的协议有效周期
        bizObject.put("product_code", "CYCLE_PAY_AUTH");    //周期扣款销售产品码固定为CYCLE_PAY_AUTH。
        bizObject.put("external_logon_id", "李涛");    //用户在商户网站的登录账号，用于在签约页面展示，如果为空，则不展示
        bizObject.put("personal_product_code", "CYCLE_PAY_AUTH_P");    //周期扣款个人签约产品码固定为CYCLE_PAY_AUTH_P。
        bizObject.put("sign_scene", "INDUSTRY|EDU");    //协议签约场景，参见 代扣产品常见场景值 。
        bizObject.put("external_agreement_no", "20220601093700");    //商户签约号，代扣协议中标示用户的唯一签约号（确保在商户系统中唯一）。
        bizObject.put("third_party_type", "PARTNER");    //签约第三方主体类型。

        tempJson = new JSONObject();
        tempJson.put("auth_biz_params", "{\"platform\":\"taobao\"}");
        bizObject.put("prod_params", tempJson);

        bizObject.put("promo_params", "{\"key\":\"value\"}");

        tempJson = new JSONObject();
        tempJson.put("channel", "ALIPAYAPP");
        bizObject.put("access_params", tempJson);

        bizObject.put("merchant_process_url", "");//https://www.merchantpage.com/index?processId=2345678
        tempJson = new JSONObject();
//        tempJson.put("user_name", "张三");
//        tempJson.put("cert_no", "61102619921108888");
//        tempJson.put("identity_hash", "8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92");
//        tempJson.put("sign_user_id", "2088202888530893");
        bizObject.put("identity_params", tempJson);

        bizObject.put("agreement_effect_type", "DIRECT");
        bizObject.put("user_age_range", "{\"min\":\"18\",\"max\":\"30\"}");

        tempJson = new JSONObject();
        tempJson.put("period_type", "MONTH");
        tempJson.put("period", "12");
        tempJson.put("execute_time", "2022-07-01");
        tempJson.put("single_amount", "1");
        tempJson.put("total_amount", "600");
        tempJson.put("total_payments", "12");
        bizObject.put("period_rule_params", tempJson);

        //1. 创建支付宝对象，验签等
        AlipayClient alipayClient = new DefaultAlipayClient(URL,APP_ID,APP_PRIVATE_KEY,FORMAT,CHARSET,ALIPAY_PUBLIC_KEY,SIGN_TYPE);

        //2. 发起签约申请
        AlipayUserAgreementPageSignRequest request = new AlipayUserAgreementPageSignRequest();
        request.setBizContent(bizObject.toString());
        AlipayUserAgreementPageSignResponse response = alipayClient.sdkExecute(request);
        System.out.println(response.getBody());
        if(response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }

    }


    public static void add1() throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(URL,APP_ID,APP_PRIVATE_KEY,FORMAT,CHARSET,ALIPAY_PUBLIC_KEY,SIGN_TYPE);
        AlipayUserAgreementPageSignRequest request = new AlipayUserAgreementPageSignRequest();
        AlipayUserAgreementPageSignModel model = new AlipayUserAgreementPageSignModel();
        AccessParams accessParams = new AccessParams();
        accessParams.setChannel("ALIPAYAPP");
        model.setAccessParams(accessParams);
        model.setSignValidityPeriod("2m");
        SubMerchantParams subMerchant = new SubMerchantParams();
        subMerchant.setSubMerchantServiceName("滴滴出行免密支付");
        subMerchant.setSubMerchantServiceDescription("免密付车费，单次最高500");
        subMerchant.setSubMerchantName("滴滴出行");
        subMerchant.setSubMerchantId("2088123412341234");
        model.setSubMerchant(subMerchant);
        SpecifiedChannelParam specifiedSortChannelParams = new SpecifiedChannelParam();
        specifiedSortChannelParams.setBankCardType("DD");
        specifiedSortChannelParams.setAssetTypeCode("LEDGER_ACCOUNT");
        specifiedSortChannelParams.setPayToolType("BANKCARD");
        specifiedSortChannelParams.setInstId("CCB");
        specifiedSortChannelParams.setAssetId("123456789");
        model.setSpecifiedSortChannelParams(specifiedSortChannelParams);
        model.setProductCode("CYCLE_PAY_AUTH");
        model.setPassParams("{\"key\":\"value\"}，具体值需要接入时确认");
        model.setAgreementEffectType("DIRECT");
        ProdParams prodParams = new ProdParams();
        prodParams.setAuthBizParams("{\"platform\":\"taobao\"}");
        model.setProdParams(prodParams);
        model.setPromoParams("{\"key\":\"value\"}");
        model.setMerchantProcessUrl("");//https://www.merchantpage.com/index?processId=2345678
        SpecifiedAsset specifiedAsset = new SpecifiedAsset();
        specifiedAsset.setBankCardNo("62XXXXXXXXXXXXXX");
        specifiedAsset.setSpecifiedReasonCode("线下沟通具体传值，会根据对应参数拼接具体限定渠道的原因文案展示给用户");
        specifiedAsset.setPayToolType("BANKCARD");
        specifiedAsset.setInstId("CCB");
        model.setSpecifiedAsset(specifiedAsset);
        model.setExternalLogonId("13852852877");
        model.setSignScene("INDUSTRY|CARRENTAL");
        model.setPersonalProductCode("CYCLE_PAY_AUTH_P");
        model.setUserAgeRange("{\"min\":\"18\",\"max\":\"30\"}");
        IdentityParams identityParams = new IdentityParams();
//        identityParams.setCertNo("61102619921108888");
//        identityParams.setIdentityHash("8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92");
//        identityParams.setSignUserId("2088202888530893");
//        identityParams.setUserName("张三");
        model.setIdentityParams(identityParams);
        model.setExternalAgreementNo("test");
        DeviceParams deviceParams = new DeviceParams();
        deviceParams.setDeviceName("电视");
        deviceParams.setDeviceType("TV");
        deviceParams.setDeviceId("device12345");
        model.setDeviceParams(deviceParams);
        PeriodRuleParams periodRuleParams = new PeriodRuleParams();
        periodRuleParams.setPeriodType("DAY");
        periodRuleParams.setPeriod(7L);
        periodRuleParams.setTotalPayments(12L);
        periodRuleParams.setExecuteTime("2019-01-23");
        periodRuleParams.setSingleAmount("10.99");
        periodRuleParams.setTotalAmount("600");
        model.setPeriodRuleParams(periodRuleParams);
        model.setThirdPartyType("PARTNER");
        ZmAuthParams zmAuthParams = new ZmAuthParams();
        zmAuthParams.setBuckleMerchantId("268820000000414397785");
        zmAuthParams.setBuckleAppId("1001164");
        model.setZmAuthParams(zmAuthParams);
        request.setBizModel(model);
        AlipayUserAgreementPageSignResponse response = alipayClient.pageExecute(request);
        System.out.println(response.getBody());
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }



}
