<!DOCTYPE html>
<html lang="zh-cn">
<head>
  <meta charset="utf-8"/>
  <title>用户管理 - 用户列表</title>
  <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
  <link rel="icon" href="" type="image/x-icon"/>
  <link rel="shortcut icon" href=""/>
  <link href="${basePath}/js/common/bootstrap/3.3.5/css/bootstrap.min.css?${_v}" rel="stylesheet"/>
  <link href="${basePath}/css/common/base.css?${_v}" rel="stylesheet"/>
  <script src="//cdn.bootcss.com/jquery/1.8.3/jquery.min.js"></script>
  <script src="${basePath}/js/common/layer/layer.js"></script>
  <script src="${basePath}/js/common/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  <script src="${basePath}/js/shiro.demo.js"></script>
</head>

<body data-target="#one" data-spy="scroll">
<@_top.top 2/>
<div class="container" style="padding-bottom: 15px;min-height: 300px; margin-top: 40px;">
  <div class="row">

  <@_left.member 1/>

    <div class="col-md-10">
      <h2>用户列表</h2>
      <hr>
      <form method="post" action="" id="formId" class="form-inline">
        <div clss="well">
          <div class="form-group">
            <input type="text" class="form-control" style="width: 300px;" value="${findContent?default('')}"
                   name="findContent" id="findContent" placeholder="输入用户昵称、用户手机号">
          </div>
          <span class=""> <#--pull-right -->
            <button type="submit" class="btn btn-primary">查询</button>
          </span>
        </div>
        <hr>
        <table class="table table-bordered">
          <tr>
            <th>序号</th>
            <th>open_id</th>
            <th>用户昵称</th>
            <th>Email/帐号</th>
            <th>手机号码</th>
            <th>订单数</th>
            <th>累计消费</th>
            <th>操作</th>
          </tr>
        <#if page?exists && page.list?size gt 0 >
            <#list page.list as it>
              <tr>
                <td>${it_index+1}</td>
                <td>${it.openId?default('-')}</td>
                <td>${it.nickname?default('未设置')}</td>
                <td>${it.email?default('未设置')}</td>
                <td>${it.mobile?default('未设置')}</td>
                <td>${it.orderQuantity?default('还未消费')}</td>
                <td>${it.totalExpenses?default('还未消费')}</td>
                <td>
                  <a href="${basePath}/universal/get_all_orders.shtml?openid=${it.openId}">订单明细</a>
                </td>
              </tr>
            </#list>
        <#else>
          <tr>
            <td class="text-center danger" colspan="6">没有找到用户</td>
          </tr>
        </#if>
        </table>
      <#if page?exists>
        <div class="pagination pull-right">
        ${page.pageHtml}
        </div>
      </#if>
      </form>
    </div>
  </div><#--/row-->
</div>
</body>
</html>