package com.lbd.filesystem.common.exception;

import com.lbd.filesystem.common.constant.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @company: qishon
 * @author zhiheng.li
 * @date: 2016年1月27日 下午5:55:35
 * @Description: TODO spring中对apachecommon组件中
 * 抛出的FileSizeLimitExceededException做了转换	
 */
public class ExceptionHandler implements HandlerExceptionResolver, Ordered {

	private Log logger = LogFactory.getLog(this.getClass());

	/**
	 * 
	 */
	public int getOrder() {
		return Integer.MIN_VALUE;
	}

	/**
	 * 													
	 */
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
		final Map<String, Object> errorMap = new HashMap<String, Object>();
		if (ex instanceof BasesException) {
			final BasesException be = (BasesException) ex;
			errorMap.put(Constants.CODE, be.getCode());
			errorMap.put(Constants.MESSAGE, be.getMsg());
		} else {
			final int code = 401;
			errorMap.put(Constants.CODE, code);
			errorMap.put(Constants.MESSAGE, "服务器异常！");
		}
		//errorMap = State.ERROR.toMap();
		// 这里牵扯到spring mvc的异常处理,这里暂时做一个简单处理,不做深究
		try {
			final ObjectMapper objectMapper = new ObjectMapper();
			response.setContentType("text/html;charset=UTF-8");
			final JsonGenerator jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(response.getOutputStream(),
					JsonEncoding.UTF8);
			objectMapper.writeValue(jsonGenerator, errorMap);
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
		}
		return null;
	}


}