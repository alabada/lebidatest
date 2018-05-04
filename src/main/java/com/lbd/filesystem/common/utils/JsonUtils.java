package com.lbd.filesystem.common.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONStringer;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.io.*;
import java.util.*;


/**
 * Created by luozhanghua on 16/4/15.
 */
public final class JsonUtils {
    private JsonUtils(){}

    /**
     * @Description:把一个json数组串转换成集合，且集合里存放的为实例Bean
     * @param jsonArrStr
     * @param clazz
     * @return
     * @author yaohua.cai
     * @date 2015-6-2 下午3:33:40
     */
    @SuppressWarnings("unchecked")
    public static <E> List<E> getListFromJsonArrStr(String jsonArrStr, Class<E> clazz) {
        final JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
        final List<E> list = new ArrayList<E>();
        for (int i = 0; i < jsonArr.size(); i++) {
            list.add((E) JSONObject.toBean(jsonArr.getJSONObject(i), clazz));
        }
        return list;
    }

    /**
     * @Description:把json串转换成单一的实体对象
     * @param jsonStr e.g. {'name':'get','dateAttr':'2009-11-12'}
     * @param clazz
     * @return Object
     * @author yaohua.cai
     * @date 2015-6-2 下午2:30:40
     */
    public static Object getObjFromJsonStr(String jsonStr, Class<?> clazz) {
        return JSONObject.toBean(JSONObject.fromObject(jsonStr), clazz);
    }
    
    /**
     * @Description: 获取json对象 单一对象
     * @param jsonStr
     * @author zhicong.lin
     * @date 2015年9月28日 上午10:26:32
     */
    public static JSONObject getObjFromJsonStr(String jsonStr) {
        return JSONObject.fromObject(jsonStr);
    }
    
    /**
     * @Description: 获取json对象 为数组对象
     * @param jsonStr
     * @author zhicong.lin
     * @date 2015年9月28日 上午10:26:32
     */
    public static JSONArray getArrFromJsonStr(String jsonStr) {
        return JSONArray.fromObject(jsonStr);
    }

    /**
     *
     * @Description:从json串转换成实体对象，并且实体集合属性存有另外实体Bean
     * @param jsonStr e.g. {'data':[{'name':'get'},{'name':'set'}]}
     * @param clazz
     * @param classMap
     * @return Object
     * @author yaohua.cai
     * @date 2015-6-2 下午2:33:48
     */
    public static Object getObjFromJsonStr(String jsonStr, Class<?> clazz, Map<?, ?> classMap) {
        return JSONObject.toBean(JSONObject.fromObject(jsonStr), clazz, classMap);
    }

    /**
     *
     * @Description:把一个json数组串转换成普通数组
     * @param jsonArrStr e.g. ['get',1,true,null]
     * @return Object[]
     * @author yaohua.cai
     * @date 2015-6-2 下午2:37:53
     */
    public static Object[] getArrayFromJsonArrStr(String jsonArrStr) {
        return JSONArray.fromObject(jsonArrStr).toArray();
    }

    /**
     *
     * @Description:把一个json数组串转换成实体数组
     * @param jsonArrStr e.g. [{'name':'get'},{'name':'set'}]
     * @param clazz
     * @return Object[]
     * @author yaohua.cai
     * @date 2015-6-2 下午2:42:04
     */
    public static Object[] getObjArrayFromJsonArrStr(String jsonArrStr, Class<?> clazz) {
        final JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
        if (jsonArr == null || jsonArr.size() == 0) {
            return null;
        }
        final Object[] objArr = new Object[jsonArr.size()];
        for (int i = 0; i < jsonArr.size(); i++) {
            objArr[i] = JSONObject.toBean(jsonArr.getJSONObject(i), clazz);
        }
        return objArr;
    }

    /**
     *
     * @Description:把一个json数组串转换成Map集合
     * @param jsonArrStr e.g. [{'name':'get'},{'name':'set'}]*
     * @return Object[]
     * @author yaohua.cai
     * @date 2015-6-2 下午2:42:04
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, String>> getMapArrayFromJsonArrStr(String jsonArrStr) {
        //String jsonArrStr = "[{'fileSource':'fileSource','filePath':'filePath'},{'fileSource':'fileSource1','filePath':'filePath1'}]";
        final JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
        if (jsonArr == null || jsonArr.size() == 0) {
        	return null;
        }
        final List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
        for (int i = 0; i < jsonArr.size(); i++) {
            final JSONObject jsonObject = JSONObject.fromObject(jsonArr.get(i));
            final Map<String, String> map = new HashMap<String, String>();
            final Iterator<String> it = jsonObject.keys();
            while (it.hasNext()) {
                final String key = it.next();
                map.put(key, String.valueOf(jsonObject.get(key)));
            }
            dataList.add(map);
        }
        return dataList;
    }

    /**
     * @Description:把一个json数组串转换成实体数组，且数组元素的属性含有另外实例Bean
     * @param jsonArrStr e.g. [{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]
     * @param clazz
     * @param classMap
     * @return Object[]
     * @author yaohua.cai
     * @date 2015-6-2 下午2:45:55
     */
    public static Object[] getObjArrFromJsonArrStr(String jsonArrStr, Class<?> clazz, Map<?, ?> classMap) {
        final JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
        if (jsonArr == null || jsonArr.size() == 0) {
            return null;
        }
        final Object[] obj = new Object[jsonArr.size()];
        for (int i = 0; i < jsonArr.size(); i++) {
            final JSONObject jsonObject = jsonArr.getJSONObject(i);
            obj[i] = JSONObject.toBean(jsonObject, clazz, classMap);
        }
        return obj;
    }

    /**
     *
     * @Description:把一个json数组串转换成存放普通类型元素的集合
     * @param jsonArrStr
     * @return
     * @author yaohua.cai
     * @date 2015-6-2 下午2:50:08
     */
    public static List<?> getListFromJsonArrStr(String jsonArrStr) {
        final JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
        if (jsonArr == null || jsonArr.size() == 0) {
            return null;
        }
        final List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < jsonArr.size(); i++) {
            list.add(jsonArr.get(i));
        }
        return list;
    }

    /**
     *
     * @Description:把一个json数组串转换成集合，且集合里的对象的属性含有另外实例Bean
     * @param jsonArrStr e.g. [{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]
     * @param clazz
     * @param classMap
     * @return List
     * @author yaohua.cai
     * @date 2015-6-2 下午3:37:18
     */
    public static List<?> getListFromJsonArrStr(String jsonArrStr, Class<?> clazz, Map<?, ?> classMap) {
        final JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
        final List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < jsonArr.size(); i++) {
            list.add(JSONObject.toBean(jsonArr.getJSONObject(i), clazz, classMap));
        }
        return list;
    }

    /**
     *
     * @Description:把json对象串转换成map对象
     * @param jsonObjStr
     * @return
     * @author yaohua.cai
     * @date 2015-6-2 下午3:56:33
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getMapFromJsonObjStr(String jsonObjStr) {
        final JSONObject jsonObject = JSONObject.fromObject(jsonObjStr);
        final Map<String, Object> map = new HashMap<String, Object>();
        final Iterator<String> it = jsonObject.keys();
        while (it.hasNext()) {
            final String key = it.next();
            map.put(key, jsonObject.get(key));
        }
        return map;
    }

    /**
     *
     * @Description:把json对象串转换成map对象，且map对象里存放的为其他实体Bean
     * @param jsonObjStr
     * @param clazz
     * @return
     * @author yaohua.cai
     * @date 2015-6-2 下午3:58:12
     */
    public static Map<?, ?> getMapFromJsonObjStr(String jsonObjStr, Class<?> clazz) {
        final JSONObject jsonObject = JSONObject.fromObject(jsonObjStr);
        final Map<String, Object> map = new HashMap<String, Object>();
        final Iterator<?> it = jsonObject.keys();
        while (it.hasNext()) {
            final String key = (String) it.next();
            map.put(key, JSONObject.toBean(jsonObject.getJSONObject(key), clazz));
        }
        return map;
    }

    /**
     *
     * @Description:把json对象串转换成map对象，且map对象里存放的其他实体Bean还含有另外实体Bean
     * @param jsonObjStr
     * @param clazz
     * @param classMap
     * @return
     * @author yaohua.cai
     * @date 2015-6-2 下午3:59:26
     */
    public static Map<?, ?> getMapFromJsonObjStr(String jsonObjStr, Class<?> clazz, Map<?, ?> classMap) {
        final JSONObject jsonObject = JSONObject.fromObject(jsonObjStr);

        final Map<String, Object> map = new HashMap<String, Object>();
        final Iterator<?> it = jsonObject.keys();
        while (it.hasNext()) {
            final String key = (String) it.next();
            map.put(key, JSONObject.toBean(jsonObject.getJSONObject(key), clazz, classMap));
        }
        return map;
    }

    /**
     *
     * @Description:把实体Bean、Map对象、数组、列表集合转换成Json串
     * @param obj
     * @return
     * @author yaohua.cai
     * @date 2015-6-2 下午4:02:09
     */
    public static String getJsonStr(Object obj) {
        String jsonStr = "";
        if (obj == null) {
            return "{}";
        }

        if (obj instanceof Collection || obj instanceof Object[]) {
            jsonStr = JSONArray.fromObject(obj).toString();
        } else {
            jsonStr = JSONObject.fromObject(obj).toString();
        }
        return jsonStr;
    }

    /**
     *
     * @Description:把List转换成JSON数据
     * @param list
     * @return
     * @author yaohua.cai
     * @date 2015-6-3 上午10:55:03
     */
    public static JSONArray parseListToJsonArray(List<?> list) {
        return JSONArray.fromObject(list);
    }

    /**
     *
     * @Description:快速构建JSON格式的文本，并转换成String，写入到文件
     * @param filePath
     * @param key
     * @author yaohua.cai
     * @date 2015-6-2 下午4:47:57
     */
    public static void parserJsonToFile(String filePath, String key, JSONArray jsonArray) {
        final JSONStringer js = new JSONStringer();
        js.object().key(key).value(jsonArray).endObject();
        OutputStream out = null;
        PrintWriter writer = null;
        try {
            out = new FileOutputStream(new File(filePath));
            writer = new PrintWriter(out);
            writer.write(js.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *
     * @Description: 从数据库里查询出来的list.get(i).toString() 存放到 Map<String, Object>里面
     * @param str list.get(i).toString()
     * @param typeface 返回值的map key的大小写，如果是upper为大写，如果为lower为小写，其他情况就不修改
     * @return
     * @author jianfu.lai
     * @date 2015年7月11日 上午12:37:12
     */
    public static Map<String, Object> stringToMap(String str, String typeface) {
        final String[] midderStr = str.substring(1, str.length() - 1).split(",");
        final Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0, j = midderStr.length; i < j; i++) {
            String[] keyValue = midderStr[i].split("=");
            if (keyValue.length != 2) {
                keyValue = midderStr[i].split(":");
                if (keyValue.length != 2) {
                    continue;
                }
            }
            if ("null".equals(keyValue[1])) {
                keyValue[1] = "";
            }

            if ("lower".equals(typeface)) {
                map.put(keyValue[0].trim().toLowerCase(), keyValue[1].trim());
            } else if ("upper".equals(typeface)) {
                map.put(keyValue[0].trim().toUpperCase(), keyValue[1].trim());
            } else {
                map.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }
        return map;
    }

    /**
     * @Description: 把model 转换成map
     * @param obj model对应的 Object :   Model mo = new Model();	modelParamToMap(mo);
     * @return
     * @author jianfu.lai
     * @date 2015年8月4日 上午11:54:18
     */
    public static Map<String, Object> modelParamToMap(Object obj) {
        if (obj == null) {
            return null;
        }
        final Map<String, Object> map = new HashMap<String, Object>();
        final PropertyDescriptor[] model = PropertyUtils.getPropertyDescriptors(obj);
        for (int i = 0; i < model.length; i++) {
            final String name = model[i].getName();
            if (!"class".equals(name)) {
                try {
                    String modelAttribute = String.valueOf(PropertyUtils.getNestedProperty(obj, name));
                    modelAttribute = modelAttribute == null ? "" : modelAttribute;
                    map.put(name, modelAttribute);
//					System.out.println(name + "==" + modelAttribute);
                } catch (Exception e) {
                    map.put(name, "");
                }
            }
        }
        return map;
    }

    /**
     * @Description:把一个json数组串转换成集合，且集合里存放的为实例Bean ，这个是可以共用的，getListFromJsonArrStr这个方法当model属性为空时会报错
     * 但是，这里现在只支持单层设置值，如果model里面有对象的话对象里面的值是没法放进去的，如model.menu.fdKey
     * @param jsonArrStr json数据
     * @param clazz model对应的class
     * @return
     * @author jianfu.lai
     * @date 2015年10月27日 下午6:58:51
     */
    public static <E> List<E> listFromJsonArrStrGeneral(String jsonArrStr, Class<E> clazz) {
        final JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
        final List<E> list = new ArrayList<E>();
        for (int i = 0; i < jsonArr.size(); i++) {
            final JSONObject obj = jsonArr.getJSONObject(i);
            try {
                list.add((E) setModelParam(clazz, obj));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * @Description: 给model设置值
     * @param modelClass model对应的class
     * @param obj json数据
     * @return
     * @throws Exception
     * @author jianfu.lai
     * @date 2015年10月27日 下午6:21:19
     */
    @SuppressWarnings("unchecked")
    private static <E> E setModelParam(Class<E> modelClass, JSONObject obj) throws Exception {
        final E model = (E) Class.forName(modelClass.getName()).newInstance();
        for (Object key : obj.keySet()) {
            final Object param = obj.get(key);
            try {
                if (key.toString().indexOf(".") != -1) {
                    PropertyUtils.setNestedProperty(model, key.toString(), param);
                } else {
                    PropertyUtils.setSimpleProperty(model, key.toString(), param);
                }
            } catch (Exception e) {
                continue;
            }
        }
        return model;
    }
}
