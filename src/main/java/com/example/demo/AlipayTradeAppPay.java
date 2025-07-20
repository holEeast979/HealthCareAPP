package com.example.demo;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.domain.ExtUserInfo;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.domain.ExtendParams;
import com.alipay.api.domain.GoodsDetail;

import java.util.ArrayList;
import java.util.List;

public class AlipayTradeAppPay {

    public static void main(String[] args) throws AlipayApiException {
        // 初始化SDK
        AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());

        // 构造请求参数以调用接口
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

        // 设置商户订单号
        model.setOutTradeNo("70501111111S001111119");

        // 设置订单总金额
        model.setTotalAmount("9.00");

        // 设置订单标题
        model.setSubject("大乐透");

        // 设置产品码
        model.setProductCode("QUICK_MSECURITY_PAY");

        // 设置订单包含的商品列表信息
        List<GoodsDetail> goodsDetail = new ArrayList<GoodsDetail>();
        GoodsDetail goodsDetail0 = new GoodsDetail();
        goodsDetail0.setGoodsName("ipad");
        goodsDetail0.setAlipayGoodsId("20010001");
        goodsDetail0.setQuantity(1L);
        goodsDetail0.setPrice("2000");
        goodsDetail0.setGoodsId("apple-01");
        goodsDetail0.setGoodsCategory("34543238");
        goodsDetail0.setCategoriesTree("124868003|126232002|126252004");
        goodsDetail0.setShowUrl("http://www.alipay.com/xxx.jpg");
        goodsDetail.add(goodsDetail0);
        model.setGoodsDetail(goodsDetail);

        // 设置订单绝对超时时间
        model.setTimeExpire("2016-12-31 10:05:00");

        // 设置业务扩展参数
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088511833207846");
        extendParams.setHbFqSellerPercent("100");
        extendParams.setHbFqNum("3");
        extendParams.setIndustryRefluxInfo("{\"scene_code\":\"metro_tradeorder\",\"channel\":\"xxxx\",\"scene_data\":{\"asset_name\":\"ALIPAY\"}}");
        extendParams.setCardType("S0JP0000");
        model.setExtendParams(extendParams);

        // 设置公用回传参数
        model.setPassbackParams("merchantBizType%3d3C%26merchantBizNo%3d2016010101111");

        // 设置商户的原始订单号
        model.setMerchantOrderNo("20161008001");

        // 设置外部指定买家
        ExtUserInfo extUserInfo = new ExtUserInfo();
        extUserInfo.setCertType("IDENTITY_CARD");
        extUserInfo.setCertNo("362334768769238881");
        extUserInfo.setName("李明");
        extUserInfo.setMobile("16587658765");
        extUserInfo.setMinAge("18");
        extUserInfo.setNeedCheckInfo("F");
        model.setExtUserInfo(extUserInfo);

        // 设置通知参数选项
        List<String> queryOptions = new ArrayList<String>();
        queryOptions.add("hyb_amount");
        queryOptions.add("enterprise_pay_info");
        model.setQueryOptions(queryOptions);

        request.setBizModel(model);
        // 第三方代调用模式下请设置app_auth_token
        // request.putOtherTextParam("app_auth_token", "<-- 请填写应用授权令牌 -->");

        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
        String orderStr = response.getBody();
        System.out.println(orderStr);

        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
            // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
            // String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
            // System.out.println(diagnosisUrl);
        }
    }

    private static AlipayConfig getAlipayConfig() {
        String privateKey  = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC0M8UKcegwxzQ7vX/OqmPRoAPRdNYFjXbsT96xOF/FbBSUcwpS7ZSQzZ/2Op6euxCVNl6EZnGw3VUtSASXToRaF58iu/E4RvAKHYGOTEmg8V8lRJn6Zym7/5w3wzUxzq/XG8IPp7C3D+b04uQH2mLnSBgbB7hcYhanruZQGZ+66IEzUJUauj6FVeQFhuwtlJbEQCIvImPpDOA3TPC3c+FBmDpTdfYUmFddti82hyuLvciq5lrFyb7JAHry9B2/mgotMqaNrIFY11Mip/OkWikW46eZg2Lo4JdpCefHQaL0eAuCIdYQzFRVf3GUgcvGkM4r6HR9BycGSU7fENAr03i9AgMBAAECggEABCTInCPgZfa+sbz6ymLKptBaJh/cqAYHvQldIpAcS6rfkkZO73c9GlNQIVZZvWapy/STGdJNPPbp9X+Zc20/x96RLzhJJLUk4eapyv7gFo5WVMIOFF7467TF/9ZCS+Hn0zd1L+z4OE1qTylEexW/fnf+7egwihdLVBHGhAMC1EZofbJt0TbdDwlQzkXZ39kKGvnrzPziVUfnT+SI4kAQSjMKn5EMxLhTlpHjP2JSohPA7rRnCirnGvG6QKkFbUtjFkMAik0KM/BI4Csjx5PNH5gDugKkIciUMD+JW1iuoqldhDiVWwIlih4CtWSql7kxZANwU/zMcC8aiZpqWKm3AQKBgQDsfNOyLbH+FfmNEukV54wnYne7GLVSes+fbBULfTYsfHuE5gtdgHMs0kNmlet69dIWMvt5GtrZ853IwYlvfOk2X8Eke+zdx3hOad3ma7OknU8lVgf+KQWlAxXXlAn3Blh1VxEXIi1XxgPU20+t4w2Tqx74NRW3fqCiS0A3ljXS/QKBgQDDEhA/6FR6jIPyc8duXKQMLESeT+WfwcbrjT2KCq9uDsf4w5kVpR3PK48Dhwe6B9ckCUXOtwEGJ6IGjfOVkz4ZADh2zmFF+JJaF2gLD5y3MzqXUuDIu6hURmdODdatOxN+y7/JtBlfYdz6w9WJsUQvmJku6n2t4GkwR4ynsJ2IwQKBgGGn3meKtSC+hFG8SGJSPq8Y22Z4eo/3xPsJT9txtkhCk2M69+CBpY4RueGH8TjSgBHItc25yDwOfM6lFRLj2w4oFoLxZcvjjgubbhWzzfghOciqMRzEZd9RWdiu+jp29VpnPNh3tY+0Zmqksc+VnkqndF981F30vXKnbnpF3i4lAoGBAIy+WrURdupYudbTzF+HPeF+ix/CO8XxdQl9BZq0rPB2f7evwKMKWCCip/P+mAeUIpjgi9pi1b1QGSorf0U7UIGPVC38J9jpSQbxfmMEp6jSsIKADKkoJIJxrZV2jJ6+UgmZDk6EwA2+uUbAxzAqkyzYKCXekEFE+gAYj5Zp6q/BAoGAT/RiKjTk4hvElE9wns8HrObwX6VvnC0orF2J201YffmiQ/OpE438D7x7UEfuzVopcHYRXlvQHI3X+QIcNFMjYlh33bGCjrtujW4sNarHZ12NXZ6uwZAdziG0PQvHDTxpC9Fowy4GIrbYm3EYgkFond1XYg72ceWpF7c2sJZUTRk=";
        String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuvote/dEMJJme68wnMNx6Tf1PAbHMv9X4ByDFrDuVQfFElxET0kn1XDrzz28Hdl3nzC7SSgsoyBCCJeVKOpduStAJzqJVzzmFMn8Uc1gWcivMxCtPF6tkNZa3f7bYnlyabxHZzeJdMpWqMhHZpyex4ZjkNTDLINS0Yo5PFQAkEzNHOFfCCtkrzaBI2mTMNHKdZJCBdhLjQDLFnAJ++rfyPp7/zjQv8DjzQf9kK4DmnQw30JcXyUrMVPuKJl/vtKUSNPIuIYlAYehNmA+gxiy0os974Se0jU3VKPC+YjwhSG1Ib3uQSxPqtwzkS7EqOeI/W8CuJEAtlktEQspAxIcFQIDAQAB";
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl("https://openapi.alipay.com/gateway.do");
        alipayConfig.setAppId("2021000147660591");
        alipayConfig.setPrivateKey(privateKey);
        alipayConfig.setFormat("json");
        alipayConfig.setAlipayPublicKey(alipayPublicKey);
        alipayConfig.setCharset("UTF-8");
        alipayConfig.setSignType("RSA2");
        return alipayConfig;
    }
}