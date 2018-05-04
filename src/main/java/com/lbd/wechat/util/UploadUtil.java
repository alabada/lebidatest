package com.lbd.wechat.util;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * @Author 温枝达
 * @Email alabadazi@gmail.com
 * @Date 2017/3/30 14:34
 * @Description 自定义群发工具类
 */
public class UploadUtil {
    private static Logger log = LoggerFactory.getLogger(UploadUtil.class);

    // 上传图片（POST）
//    public final static String uploadimg = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";
    public final static String uploadimg = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=image";

    // 上传图文（POST）
    public final static String uploadnews = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";

    /**
     * 模拟form表单的形式，上传文件。以输出流的形式把文件写入到url中，然后用输入流来获取url的响应
     * @param url   请求地址 form表单url地址
     * @param filePath  图片在服务器保存路径
     * @return  String url的响应信息返回值
     * @throws IOException
     */
    private static String sendImg(String url, String filePath) throws IOException {
        String result = null;
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在");
        }
        /**
         * 第一部分
         */
        URL urlObj = new URL(url);
        // 连接
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        /**
         * 设置关键值
         */
        con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false); // post方式不能使用缓存
        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        // 设置边界
        String BOUNDARY = "---------------------------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary="
                + BOUNDARY);
        // 请求正文信息
        // 第一部分：
        StringBuilder sb = new StringBuilder();
        sb.append("--"); // 必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"media\";filename=\""
                + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
        byte[] head = sb.toString().getBytes("utf-8");
        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);
        // 文件正文部分
        // 把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
        out.write(foot);

        out.flush();
        out.close();

        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
            throw new IOException("数据读取异常");
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        System.out.println();
        System.out.println(result);
        System.out.println();

        return JSONObject.fromObject(result).getString("media_id");
    }


    public static String uploadImg(String accessToken, String filePath) throws IOException{
        String url = uploadimg.replace("ACCESS_TOKEN", accessToken);
        return sendImg(url, filePath);
    }


    public static String uploadNews(String accessToken, Map<String, Object> articles) {

        String url = uploadnews.replace("ACCESS_TOKEN", accessToken);
        String returnData = sendNews(url, articles);

        return returnData;
    }


    private static String sendNews(String url, Map<String, Object> articles) {

        // 将对象转换成JSON字符串
        String jsonText = JSONObject.fromObject(articles).toString();
        // 发起POST请求创建菜单
        JSONObject jsonObject = CommonUtil.httpsRequest(url, "POST", jsonText);

        if (null != jsonObject) {

            String type = jsonObject.getString("type");
            if ("news".equals(type)) { // 发送正常
                return jsonObject.getString("media_id");
            }
        }
        return "";
    }


}
