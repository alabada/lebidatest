<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.lbd.wechat.pojo.SNSUserInfo" %>
<html>
<head>
    <title>Oauth2.0 网页授权</title>
    <meta name="viewport" content="width=device-width", user-scalable="0">
    <style type="text/css">
        *{margin:0; padding: 0}
        table{border: 1px dashed #B9B9DD; font-size: 12pt}
        td{border: 1px dashed #B9B9DD;word-break: break-all;word-wrap: break-word}
    </style>
</head>
<body>

    <%
        SNSUserInfo user = (SNSUserInfo)request.getAttribute("snsUserInfo");
        if (null != user) {
    %>

    <table width="100%" cellspacing="0" cellpadding="0">
        <tr><td width="20%">属性</td><td width="80%">值</td></tr>
        <tr><td>openId</td><td><%=user.getOpenId()%></td></tr>
    </table>

    <%
        } else {
            out.print("未获取到用户信息");
        }
    %>

</body>
</html>
