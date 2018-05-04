package com.lbd.wechat.service;


public interface PayService {
	
	/**
	 * 统一下单接口
	 * @param remoteAddr 请求主机ip
	 * @return prepayId 预支付id
	 */
	String unifiedOrder(String openId, String orderId, String type, String remoteAddr);

	void updateOrderStatus(String outTradeNo);

}
