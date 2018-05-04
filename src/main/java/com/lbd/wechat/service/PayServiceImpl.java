package com.lbd.wechat.service;

import com.lbd.wechat.constant.WechatConstants;
import com.lbd.wechat.pojo.PaySendData;
import com.lbd.wechat.util.HttpUtil;
import com.lbd.wechat.util.PayUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Service("payService")
public class PayServiceImpl implements PayService {

	/**
	 * 微信支付统一下单
	 **/
	public String unifiedOrder(String openId, String orderId, String type, String remoteAddr) {
		Map<String, String> resultMap = null;
		// 统一下单返回的预支付id
		String prepayId = null;
		PaySendData paySendData = new PaySendData();
		// 构建微信支付请求参数集合
		paySendData.setAppId(WechatConstants.APP_ID);
		paySendData.setMch_id(WechatConstants.MCH_ID);
		paySendData.setNotify_url(WechatConstants.NOTIFY_URL);
		paySendData.setTrade_type(WechatConstants.TRADE_TYPE_JSAPI);
		paySendData.setDevice_info(WechatConstants.WEB);
		// 商品描述
		paySendData.setBody("pay");
		paySendData.setNonce_str(PayUtils.getRandomStr(32));
		// 商户订单号
		paySendData.setOut_trade_no(type + "separate" + orderId);
		// 订单总金额，单位为分
		double totalFee = 0.0;
		// 订单类型 1：菜品；2：佛具；3：汽车美容
		int typeInt = Integer.parseInt(type);
		Double totalPrice = 0.0;
		if (1 == typeInt) { // 菜品
//			totalPrice = dishOrderMapper.selectTotalPriceByPrimaryKey(Integer.parseInt(orderId));
		} else if (2 == typeInt) { // 佛具
//			totalPrice = buddhaOrderMapper.selectTotalPriceByPrimaryKey(Integer.parseInt(orderId));
		}
        totalFee = totalPrice * 100; // 元转为分
		paySendData.setTotal_fee((int)totalFee);

		paySendData.setSpbill_create_ip(remoteAddr);
		paySendData.setOpenId(openId);
		// 将参数拼成map,生产签名
		paySendData.setSign(PayUtils.getSign(buildParamMap(paySendData)));
		// 将请求参数对象转换成xml
		String reqXml = PayUtils.sendDataToXml(paySendData);
		try {
			// 发送请求
			CloseableHttpResponse response = HttpUtil.Post(WechatConstants.UNIFIED_ORDER_URL, reqXml, false);
			try {
				resultMap = PayUtils.parseXml(response.getEntity().getContent());
				// 关闭流
				EntityUtils.consume(response.getEntity());
			} finally {
				response.close();
			}
		} catch (Exception e) {
			System.out.println("微信支付统一下单异常");
		}
		String return_code = resultMap.get("return_code");
		String result_code = resultMap.get("result_code");
		if (WechatConstants.RETURN_SUCCESS.equals(return_code) && WechatConstants.RETURN_SUCCESS.equals(result_code)) {
			// return_code=通信标识
			// result_code=交易标识
			// 只有当returnCode与resultCode均返回“success”，才代表微信支付统一下单成功
			prepayId = "prepay_id=" + resultMap.get("prepay_id");
		}
		return prepayId;
	}

    @Override
    public void updateOrderStatus(String outTradeNo) {

	    // 以separate作为分隔符，前面为订单类型，后面为订单号
        String[] typeAndOrderid = outTradeNo.split("separate");

//        if ("1".equals(typeAndOrderid[0])) { // 菜品订单
//            DishOrder dishOrder = new DishOrder();
//
//			dishOrder.setId(Integer.parseInt(typeAndOrderid[1]));
//
//            // 订单状态(0:已预约,1:已取消,2:已完成)
//            dishOrder.setStatus((byte)0);
//
//			// 付款状态(0:未付款,1:已付款)提交订单时均还未付款
//			dishOrder.setPaymentStatus((byte)1);
//            dishOrderMapper.updateByPrimaryKeySelective(dishOrder);
//        } else if ("2".equals(typeAndOrderid[0])) { // 佛具订单
//            BuddhaOrder buddhaOrder = new BuddhaOrder();
//
//            buddhaOrder.setId(Integer.parseInt(typeAndOrderid[1]));
//
//            // 订单状态(0:已预约,1:已取消,2:已完成)
//            buddhaOrder.setStatus((byte)0);
//
//            // 付款状态(0:未付款,1:已付款)提交订单时均还未付款
//            buddhaOrder.setPaymentStatus((byte)1);
//            buddhaOrderMapper.updateByPrimaryKeySelective(buddhaOrder);
//        }


    }


    /**
	 * 构建统一下单参数map 用于生成签名
	 * 
	 * @param data
	 * @return SortedMap<String,Object>
	 */
	private SortedMap<String, Object> buildParamMap(PaySendData data) {
		SortedMap<String, Object> paramters = new TreeMap<String, Object>();
		Field[] fields = data.getClass().getDeclaredFields();
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				if (null != field.get(data)) {
					paramters.put(field.getName().toLowerCase(), field.get(data).toString());
				}
			}
		} catch (Exception e) {
			System.out.print("构建签名map错误: ");
			e.printStackTrace();
		}

		return paramters;
	}

}
