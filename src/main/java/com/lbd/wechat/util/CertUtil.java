package com.lbd.wechat.util;

import com.lbd.wechat.constant.WechatConstants;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

@SuppressWarnings("deprecation")
public class CertUtil {

	/**
	 * 加载证书
	 */
	public static SSLConnectionSocketFactory initCert() throws Exception {
		FileInputStream instream = null;
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		instream = new FileInputStream(new File(WechatConstants.CERT_PATH));
		keyStore.load(instream, WechatConstants.MCH_ID.toCharArray());
		
		if (null != instream) {
			instream.close();
		}

		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, WechatConstants.MCH_ID.toCharArray()).build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

		return sslsf;
	}
}
