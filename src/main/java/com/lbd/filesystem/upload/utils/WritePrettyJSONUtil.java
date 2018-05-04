package com.lbd.filesystem.upload.utils;

import net.sf.json.JSONArray;

import java.io.*;

/**
 * Created by luozhanghua on 16/4/19.
 */
public class WritePrettyJSONUtil {
    public static void WritePrettyJSON(Object o) throws IOException {

        File file = new File("/Users/luozhanghua/Desktop/Project/qishon-file-system/target/qishon-file-system/uploadFiles/test/test/json.json");
        //String jsonStr = "[{a:1,b:{b1:[{a:2},{a:1}]},c:3},{a:1},{b:1}]";
//      String jsonStr = "{a:1,b:{b1:[{a:2},{a:1}]},c:3,a:2,b:2}";
        JSONArray jsonObj = JSONArray.fromObject(o);
//      JSONObject jsonObj = JSONObject.fromObject(jsonStr);

        char[] stack = new char[1024]; // 存放括号，如 "{","}","[","]"
        int top = -1;

        String string = jsonObj.toString();
        StringBuffer sb = new StringBuffer();
        char[] charArray = string.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if ('{' == c || '[' == c) {
                stack[++top] = c; // 将括号添加到数组中，这个可以简单理解为栈的入栈
                sb.append(charArray[i] + "\n");
                for (int j = 0; j <= top; j++) {
                    sb.append("\t");
                }
                continue;
            }
            if ((i + 1) <= (charArray.length - 1)) {
                char d = charArray[i+1];
                if ('}' == d || ']' == d) {
                    top--; // 将数组的最后一个有效内容位置下标减 1，可以简单的理解为将栈顶数据弹出
                    sb.append(charArray[i] + "\n");
                    for (int j = 0; j <= top; j++) {
                        sb.append("\t");
                    }
                    continue;
                }
            }
            if (',' == c) {
                sb.append(charArray[i] + "\n");
                for (int j = 0; j <= top; j++) {
                    sb.append("\t");
                }
                continue;
            }
            sb.append(c);
        }

        Writer write = new FileWriter(file);
        BufferedWriter br = new BufferedWriter(write);
        br.write(sb.toString());
        br.flush();
        br.close();
    }
}
