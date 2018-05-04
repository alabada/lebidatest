<#macro top index>
<script baseUrl="${basePath}" src="${basePath}/js/user.login.js"></script>

<nav class="navbar navbar-default" style="height: 90px">
  <div class="container-fluid" style="height: auto">

    <div class="navbar-header" style="padding-top: 10px">
      <a class="navbar-brand" href="#">
        <img alt="Brand" src="${basePath}/asset/img/test.png">
      </a>
      <ul class="nav navbar-nav">
        <li>
          <h4 style="margin-top: 10px">乐必达后台</h4>
          <h4 style="margin-top: 0px">标准化管理系统</h4>
        </li>
      </ul>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav navbar-right">
        <li><a href="#">通知</a></li>
        <li><a href="#"> ${token.nickname?default('匿名登录')}</a></li>
        <li><a href="${basePath}/role/mypermission.shtml">修改密码</a></li>
        <li><a href="javascript:void(0);" onclick="logout();">退出</a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

<ul class="nav nav-pills nav-tabs" style="margin-left: 300px">
  <li role="presentation" class="${(index==1)?string('active',' ')}"><a href="${basePath}/">首页</a></li>
  <li role="presentation" class="${(index==2)?string('active',' ')}"><a href="${basePath}/member/list.shtml">用户管理</a></li>
</ul>

</#macro>
