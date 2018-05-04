package com.lbd.filesystem.common.Aspect;

import com.lbd.filesystem.common.constant.Constants;
import com.lbd.filesystem.common.exception.BasesException;
import com.lbd.filesystem.common.pojo.LogInfoPojo;
import com.lbd.filesystem.common.utils.JsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @company: qishon
 * @author zhanghua.luo
 * @date: 2016年4月11日 下午13:56:10
 * @Description: Aop日志
 */

@Aspect
@Component
public class LogAspect {
    private final Log logger = LogFactory.getLog(this.getClass());
    
    private String requestMethod; //请求地址
    
    private String requestPath;
    
    private String ip;
    
    private long startTimeMillis; //开始时间
    
    private StringBuffer logInfo;
    
    private String params;

    private LogInfoPojo logInfoPojo;

   /**
    *service层切点    
    */
    @Pointcut("@annotation(com.qishon.filesystem.common.annotation.SystemServiceLog)")
     public  void serviceAspect() {    
    }    
    
    /**
     * 前置通知
     * @param joinPoint
     */
    
    @Before("serviceAspect()")
    public void doBeforeInServiceLayer(JoinPoint joinPoint) {
    	this.startTimeMillis = System.currentTimeMillis();
        this.logInfo = new StringBuffer();
        this.startTimeMillis = System.currentTimeMillis();
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //请求的IP
        try {
            this.ip = request.getRemoteAddr();
            this.requestMethod =  (joinPoint.getTarget().getClass().getName() + Constants.POINT
            		+ joinPoint.getSignature().getName() + Constants.BRACKETS);
        } catch (Exception e) {
            //记录本地异常日志
            this.logger.error("==前置通知异常==");
            this.logger.error("异常信息:{}", e);
        }
    }

    /**
     * 环绕通知
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("serviceAspect()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        final RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        final ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        final HttpServletRequest request = sra.getRequest();
        final Map<String, Object> paramsMap = new HashMap<String, Object>();
        final Object[] objects = pjp.getArgs();
        for (Object o : objects) {
            if (o instanceof HttpServletRequest) {
                final HttpServletRequest httpRequest = (HttpServletRequest) o;
                final Enumeration<String> enumeration = httpRequest.getParameterNames();
                while (enumeration.hasMoreElements()) {
                    final String paramName = enumeration.nextElement();
                    final String paramValue = httpRequest.getParameter(paramName);
                    paramsMap.put(paramName, paramValue);
                }
                final CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(httpRequest.getSession().getServletContext());

                    if (multipartResolver.isMultipart(httpRequest)) {
                        final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) httpRequest;
                        final Iterator<String> iter = multiRequest.getFileNames();

                        final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                        while (iter.hasNext()) {
                            final Map<String, Object> fileMap = new HashMap<String, Object>();
                            //String saveServicePath = Constants.UPLOAD_FOLDER_NAME;
                            final MultipartFile file = multiRequest.getFile(iter.next());
                            final String myFileName = file.getOriginalFilename();
                            fileMap.put("文件名", myFileName);
                            final String fileType = myFileName.substring(myFileName.lastIndexOf(Constants.POINT) + Constants.NUM_1);
                            fileMap.put("文件类型", fileType);
                            fileMap.put("文件大小", file.getSize());
                            //saveServicePath = saveServicePath + File.separator + paramsMap .get("fileSourse") 
                            //+ File.separator + paramsMap .get("path");
                            //fileMap.put("文件保存路径", saveServicePath);
                            list.add(fileMap);
                        }
                        paramsMap.put("上传文件信息", list);
                    }

            } 
        }
        this.params = JsonUtils.getJsonStr(paramsMap);


        // 获取请求地址
        this.requestPath = request.getRequestURI();
        Object result = null;

        result = pjp.proceed();

        return result;
    }

    /**
     * 后置通知
     * @param joinPoint
     */
    @After("serviceAspect()")
    public void doAfterInServiceLayer(JoinPoint joinPoint) {
        logInfoPojo = new LogInfoPojo();
    	final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    	this.logInfo.append(format.format(new Date(this.startTimeMillis)) + ":");
        this.logInfoPojo.setLog_record_date_string(format.format(new Date(this.startTimeMillis)));
//        this.logInfo.append(Constants.CLIENT_IP + this.ip + Constants.COMMA);
        this.logInfoPojo.setClient_ip(this.ip);
//        this.logInfo.append(Constants.REQUEST_ADDR + this.requestPath);
        this.logInfoPojo.setReq_url(this.requestPath);
//        this.logInfo.append(", 请求方法:" + this.requestMethod + Constants.COMMA);
        this.logInfoPojo.setReq_method(this.requestMethod);
//        this.logInfo.append(Constants.REQUEST_PARAM + this.params);
        this.logInfoPojo.setReq_params(this.params);
        this.logger.info(JsonUtils.getJsonStr(this.logInfoPojo));
        this.logInfo = null;
        this.logInfoPojo = null;
    }
    
    /**
     * 异常通知，用于拦截service层异常日志
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        this.logInfoPojo = new LogInfoPojo();
        StackTraceElement[] st = e.getStackTrace();
        /*for (StackTraceElement stackTraceElement : st) {
            String exclass = stackTraceElement.getClassName();
            String method = stackTraceElement.getMethodName();
            System.out.println(new Date() + ":" + "[类:" + exclass + "]调用"
                    + method + "时在第" + stackTraceElement.getLineNumber()
                    + "行代码处发生异常!异常类型:" + e.getClass().getName());
        }*/

            String exclass = st[0].getClassName();
            String method = st[0].getMethodName();
            this.logInfoPojo.setException_cause("[类:" + exclass + "]调用" + method + "时在第" + st[0].getLineNumber()
                    + "行代码处发生异常!异常类型:" + e.getClass().getName());
           /* System.out.println(new Date() + ":" + );*/

    	//final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

    	//获取请求ip
    	//final String ip = request.getRemoteAddr();
    	//获取用户请求方法参数并序列化为json字符串

    	//if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
    		//params += JsonUtils.getJsonStr(joinPoint.getArgs());
    	//}
        this.logInfo = new StringBuffer();
    	try {
            final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            this.logInfo.append(format.format(new Date(System.currentTimeMillis())) + ":");
            this.logInfoPojo.setLog_record_date_string(format.format(new Date(this.startTimeMillis)));
    		 /*========控制台输出=========*/
//            this.logInfo.append(Constants.CLIENT_IP + this.ip + Constants.COMMA);
            this.logInfoPojo.setClient_ip(this.ip);
//            this.logInfo.append(Constants.REQUEST_ADDR + this.requestPath);
            this.logInfoPojo.setReq_url(this.requestPath);
//            this.logInfo.append(", 请求方法:" + this.requestMethod + Constants.COMMA);
            this.logInfoPojo.setReq_method(this.requestMethod);
//            this.logInfo.append(Constants.REQUEST_PARAM + this.params + Constants.COMMA);
            this.logInfoPojo.setReq_params(this.params);
            this.logInfo.append("异常通知--> ");
//            this.logInfo.append("异常代码: " + e.getClass().getName() + Constants.COMMA);
            this.logInfoPojo.setException_code(e.getClass().getName());
            if (e instanceof BasesException) {
            	final BasesException ex = (BasesException) e;
//            	this.logInfo.append("异常信息: " + ex.getMsg() + Constants.COMMA);
                this.logInfoPojo.setException_info(ex.getMsg());
            } else {
//            	this.logInfo.append("异常信息: " + e.getMessage() + Constants.COMMA);
                this.logInfoPojo.setException_info(e.getMessage());
            }
//            this.logInfo.append("异常方法: " + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            this.logInfoPojo.setException_method(joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()");
            this.logger.error(JsonUtils.getJsonStr(this.logInfoPojo));
            this.logInfo = null;
            this.logInfoPojo = null;
    	} catch (Exception ex) {
            //记录本地异常日志
            this.logger.error("==异常通知异常==");
            //logger.error("异常信息:{}", ex.getMessage());
        }
         /*==========记录本地异常日志==========*/
        //logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget().getClass().getName()
    	//+ joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage(), params);
    }

}
