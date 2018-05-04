package com.lbd.wechat.util;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 * 信任管理器
 *
 * @author wenzhida
 */
public class MyX509TrustManager implements X509TrustManager {

    // 检查客户端证书
    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {

    }

    // 检查服务端证书
    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {

    }

    // 返回受信任的X509证书数组
    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new java.security.cert.X509Certificate[0];
    }

}
