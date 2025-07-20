//package com.example.demo.utils;
//
//import com.alibaba.fastjson.JSONObject;
//import com.alipay.api.AlipayApiException;
//import com.alipay.api.AlipayClient;
//import com.alipay.api.DefaultAlipayClient;
//import com.alipay.api.request.AlipayTradePagePayRequest;
//import com.alipay.api.request.AlipayTradeQueryRequest;
//import com.alipay.api.response.AlipayTradeQueryResponse;
//import com.example.demo.config.Alipayconfig;
//import com.example.demo.service.OrdersService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class PayUtil {
//    @Autowired
//    Alipayconfig alipayconfig;
//    //appid
////    private final String APP_ID = alipayconfig.getAppId();
////    //支付宝异步通知路径,付款完毕后会异步调用本项目的方法,必须为公网地址
////    private final String NOTIFY_URL = alipayconfig.getNotifyUrl();
////    //应用私钥
////    private final String APP_PRIVATE_KEY = alipayconfig.getAppPrivateKey();
////    // 支付宝公钥
////    private final String ALIPAY_PUBLIC_KEY = alipayconfig.getAlipayPublicKey();
//    private final String APP_ID = "2021000147660591";
//    //支付宝异步通知路径,付款完毕后会异步调用本项目的方法,必须为公网地址http://it8rg6.natappfree.cc
//    private final String NOTIFY_URL = "http://yr3ca5.natappfree.cc/alipay/pay";
//    //应用私钥
//    private final String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCROwu3i9tyEAejsIQw5bksyca03hv+0ELViisL4+p61Up/kUrj3rM9pUVjA8nvzQz0RzG8f9jXTM7E6sx+mEKiTy+xHnx/q5It8kRIIDMG01KZPfNRDL7YD45YYWhBREmGC4x2bYowNZZg76fb2zSVzl30GykSNiUOLWH+Aa4I2zqEdqsoqnZe1PBT8Ox8t0EiDETUbomDGZjAlg355nYXkKGA3xxNTbAPX19dLgcdDVqE5dTqwv7KgLokq8OiZMo7HNlrc5OcC60/quQixCoH3Auq4h93z38g2R8ezzbVi9wRIwDs4NYC6zNmYBloaY7XiNyzlsdVauhp9hGWpxJLAgMBAAECggEADJCVNO4OsFGet5S/+xME8FEMtf05YtRV0+++RasUUEg/x8WkOw67ZyvzUtuzDuIoqsG2Fd7hvc7RXMwLoTA/I5fCQoqmrs0DOqJpMexHPeHulX98m5iEsmC2KgNTZ8ImskgxgNV9TSp1tjFFzomqhQNzsXjgFslEb3ORtM4kZnOesnraLZhFGUOwxWND/2UVKmlIWA6Yi1RkWQevLh2i2X6JozCVBRiRu/TNr69xLh2ulqiAogrUyt6zHiMBKoOp805kR24MaYgK1V0d4T2xeQ0hl+zTQPVFxJ5ZK1wa0+hKDILjPodDdrRdOmr+BE/nSHXf0xCMWOjIG2UsuFHIoQKBgQD329MDR2wuC61pKP/E4PpH1v5ECM97OB6F/QLh6OM79hUMMvWBxXNKdJQPEbUuAxf8dF7jm7bHtrm0AEVzBUsIiB7MQ3gggeZN0zzao81L0jbixflrJXE1QsjcOQDGpidA/9MLfR2YFkp0azLRpO/Y6WbUuKNJiDtHzfhrE9wLIwKBgQCWAEAdnFMFKavQzcA9blvgTZAXT1OJvHIDQix94xp7GHr9hcxWR06qY6/g7t0h0A3lrfXz/kDpnQ5chXdpvR6coekHvwn+uYEw8iqG6ACWkXPqz30xwguhpM7k0YWOy9UH7t2mc1oK/yWrnybCVPO5WF0TC3WMubh53NvIZWxCuQKBgCDbk22r2juAcMywErgW2xcK+UoroNXE6sb4c5lfNkCMrt2fI8zAUDhenAIjZtFit5tQEgQ6cFCpeJTUNGC7IudOpEaP6yzkC5PlnPufG2ghxgTqGdaKcCePYvFO3Tt4a1vCjkfHnlS7H8OvMl14cBNhXbB1ibAFz1YFiBkp2NHhAoGALAZoywgNqc9AOWmaCa2xU5UQPHcw2pUvlVs/ubcFF6noty4k+hG1ufbGMSuKRZlN6uJy9KjkHmzVQTx+/s7eBDTVM0vTwCpsC5SX/L5tJcQNFJbPdOIyDcBUZP5Fx9J1dP2bmnXysJWQVd3BP3o1immv754GY0ZDKakz7I4wBykCgYBuPAQSyNUXF1TJsj7E0PxEc/OAeRfPwiLGFtwkSV5QV7RB2YzHLn6rQ/w9MnwYJ8l0HetuGmbDZE1kEnpm9IrW6CmEwUfxJGecYJLzdj8ed+qdnW5YvQQiH3/CQj3z/etGAENnVClVK/X2J6fnGLA4HATcNrC41wL0erQe9+O2Ew==";
//    // 支付宝公钥
//    private final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuvote/dEMJJme68wnMNx6Tf1PAbHMv9X4ByDFrDuVQfFElxET0kn1XDrzz28Hdl3nzC7SSgsoyBCCJeVKOpduStAJzqJVzzmFMn8Uc1gWcivMxCtPF6tkNZa3f7bYnlyabxHZzeJdMpWqMhHZpyex4ZjkNTDLINS0Yo5PFQAkEzNHOFfCCtkrzaBI2mTMNHKdZJCBdhLjQDLFnAJ++rfyPp7/zjQv8DjzQf9kK4DmnQw30JcXyUrMVPuKJl/vtKUSNPIuIYlAYehNmA+gxiy0os974Se0jU3VKPC+YjwhSG1Ib3uQSxPqtwzkS7EqOeI/W8CuJEAtlktEQspAxIcFQIDAQAB";
//    //字符编码
//    private final String CHARSET = "UTF-8";
//    //这是沙箱接口路径,正式路径为https://openapi.alipay.com/gateway.do
//    private final String GATEWAY_URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
//    private final String FORMAT = "JSON";
//    //签名方式
//    private final String SIGN_TYPE = "RSA2";
//    //支付宝同步通知路径,也就是当付款完毕后跳转本项目的页面,可以不是公网地址
//    private final String RETURN_URL = "http://localhost:8080/alipay/toSuccess";
//    private AlipayClient alipayClient = null;
//    //支付宝官方提供的接口
//    public String sendRequestToAlipay(String outTradeNo, Float totalAmount, String subject) throws AlipayApiException {
//        //获得初始化的AlipayClient
//        log.info("appid{}",APP_ID);
//        alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
//
//        //设置请求参数
//        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
//        alipayRequest.setNotifyUrl(NOTIFY_URL);
//        alipayRequest.setReturnUrl(RETURN_URL);
//        //商品描述（可空）
//        String body = "";
//        alipayRequest.setBizContent("{\"out_trade_no\":\"" + outTradeNo + "\","
//                + "\"total_amount\":\"" + totalAmount + "\","
//                + "\"subject\":\"" + subject + "\","
//                + "\"body\":\"" + body + "\","
//                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
//        //请求
//        String result = alipayClient.pageExecute(alipayRequest).getBody();
//        System.out.println("返回的结果是："+result );
//        return result;
//    }
//
//    //    通过订单编号查询
//    public String query(String id){
//        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
//        JSONObject bizContent = new JSONObject();
//        bizContent.put("out_trade_no", id);
//        request.setBizContent(bizContent.toString());
//        AlipayTradeQueryResponse response = null;
//        String body=null;
//        try {
//            response = alipayClient.execute(request);
//            body = response.getBody();
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//        if(response.isSuccess()){
//            System.out.println("调用成功");
//        } else {
//            System.out.println("调用失败");
//        }
//        return body;
//    }
//}
