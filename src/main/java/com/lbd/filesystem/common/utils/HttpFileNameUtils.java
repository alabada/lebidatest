package com.lbd.filesystem.common.utils;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 
 * @company: qishon
 * @author zhanghua.luo
 * @date: 2016年4月25日 下午14:58:10
 * @Description: HttpFileNameUtils
 */
public class HttpFileNameUtils {
	/**
	 * 获取http header Content-Disposition中filename的内容<br>
	 * <p>
	 * Content-Disposition值可以有以下几种编码格式：
	 * 1. 直接urlencode（IE支持）：
	 *   Content-Disposition: attachment; filename="%E6%B5%8B%E8%AF%95.txt"
	 * 2. Base64编码（Chrome、FireFox支持）：
	 *   Content-Disposition: attachment; filename="=?UTF8?B?5rWL6K+VLnR4dA==?="
	 * 3. RFC2231规定的标准（Chrome、FireFox、Opera、Safari支持）：
	 *   Content-Disposition: attachment; filename*=UTF-8''%E6%B5%8B%E8%AF%95.txt
	 * 测试环境：
	 * IE:6
	 * Chrome:5.0.375.127
	 * FireFox:3.6.8
	 * Opera:10.61
	 * Safari:5.0.1
	 * </p>
	 * @param agent 浏览器类型
	 * @param encoding 返回的文件名使用的编码
	 * @param fileName 文件名
	 * @return filename=...
	 * @throws UnsupportedEncodingException
	 * @author liangjin
	*/
	public static String getFileName(String agent, String encoding,
                                     String fileName) throws UnsupportedEncodingException {
		
		final StringBuilder rtn = new StringBuilder();
		//如果agent为空（默认认为是IE浏览器）或agent为IE浏览器，使用URLEncoder编码
		if (isBlank(agent)
				|| (agent != null && agent.toLowerCase().contains("msie"))
				|| (agent.toLowerCase().contains("safari") && agent.toLowerCase().contains("chrome"))) {
			rtn.append("filename=\"").append(
					new String(fileName.getBytes(encoding), "ISO-8859-1")).append("\"");
		}
		//其余浏览器都支持RFC2231标准
		else {
			final String newFileName = urlEncode(fileName, encoding);
			rtn.append("filename*=").append(encoding).append("''").append(
					newFileName);
		}
		return rtn.toString();
	}
	
	/**
	 * 
	 * @param url
	 * @param encoding
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String urlEncode(String url, String encoding)
			throws UnsupportedEncodingException {
		String tmp = URLEncoder.encode(url, encoding);
		//URLEncoder会将空格转换为+，需要替换为%20，否则客户端浏览器不能将+还原为空格
		tmp = tmp.replaceAll("\\+", "%20");
		return tmp;
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		final int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}
    
	/**
	 * 
	 * @param language
	 * @return
	 */
    public static String getHeaderEncoding(String language) {
        language = (StringUtils.isBlank(language) ? "cn" : language.toLowerCase());
        if (language.contains("cn")) {
            return "GBK";
        } else if (language.contains("tw")) {
            return "Big5";
        } else if (language.contains("hk")) {
            return "Big5";
        } else {
            return "UTF-8";
        }
    }

	public static void main(String[] args) {
		try {
		    System.out.println(getHeaderEncoding(null));
			String fileName = "测试 空格 .txt";
			System.out.println(getFileName("MSIE", "GBK", fileName));
			System.out.println(getFileName("Opera", "GBK", fileName));
			System.out.println(getFileName("Safari", "GBK", fileName));
			System.out.println(getFileName("Chrome", "GBK", fileName));
			System.out.println(getFileName("FireFox", "GBK", fileName));
			System.out.println(getFileName(null, "GBK", fileName));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
