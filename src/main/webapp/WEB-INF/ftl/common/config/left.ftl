<#macro user index>
<div id="one" class="col-md-2">
  <ul data-spy="affix" class="nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix"
      style="top: 100px; z-index: 100; margin-top: 120px">
    <li class="${(index==1)?string('active',' ')}">
      <a href="${basePath}/user/index.shtml">
        <i class="glyphicon glyphicon-chevron-right"></i>个人资料
      </a>
      <ul class="dropdown-menu" aria-labelledby="dLabel" style="margin-left: 160px; margin-top: -40px;">
        <li><a href="${basePath}/user/updateSelf.shtml">资料修改</a></li>
        <li><a href="${basePath}/user/updatePswd.shtml">密码修改</a></li>
      </ul>
    </li>
    <li class="${(index==2)?string('active',' ')} dropdown">
      <a href="${basePath}/role/mypermission.shtml">
        <i class="glyphicon glyphicon-chevron-right"></i>我的权限
      </a>
    </li>
  </ul>
</div>
</#macro>

<#macro member index>
    <@shiro.hasAnyRoles name='888888,100002'>
    <div id="one" class="col-md-2">
      <ul data-spy="affix" class="nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix"
          style="top: 100px; z-index: 100; margin-top: 120px">
        <li class="${(index==1)?string('active',' ')}">
          <a href="${basePath}/member/list.shtml">
            <i class="glyphicon glyphicon-chevron-right"></i>用户管理
          </a>
        </li>
      </ul>
    </div>
    </@shiro.hasAnyRoles>
</#macro>

<#macro role index>
    <@shiro.hasAnyRoles name='888888,100003'>
    <div id="one" class="col-md-2">
      <ul data-spy="affix" class="nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix"
          style="top: 100px; z-index: 100; margin-top: 120px">

          <@shiro.hasPermission name="/role/index.shtml">
            <li class="${(index==1)?string('active',' ')}">
              <a href="${basePath}/role/index.shtml">
                <i class="glyphicon glyphicon-chevron-right"></i>角色列表
              </a>
            </li>
          </@shiro.hasPermission>
          <@shiro.hasPermission name="/role/allocation.shtml">
            <li class="${(index==2)?string('active',' ')} dropdown">
              <a href="${basePath}/role/allocation.shtml">
                <i class="glyphicon glyphicon-chevron-right"></i>角色分配
              </a>
            </li>
          </@shiro.hasPermission>
          <@shiro.hasPermission name="/permission/index.shtml">
            <li class="${(index==3)?string('active',' ')} dropdown">
              <a href="${basePath}/permission/index.shtml">
                <i class="glyphicon glyphicon-chevron-right"></i>权限列表
              </a>
            </li>
          </@shiro.hasPermission>
          <@shiro.hasPermission name="/permission/allocation.shtml">
            <li class="${(index==4)?string('active',' ')} dropdown">
              <a href="${basePath}/permission/allocation.shtml">
                <i class="glyphicon glyphicon-chevron-right"></i>权限分配
              </a>
            </li>
          </@shiro.hasPermission>
      </ul>
    </div>
    </@shiro.hasAnyRoles>
</#macro>

<#macro catering index>
<div id="one" class="col-md-2">
  <ul data-spy="affix" class="nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix"
      style="top: 100px; z-index: 100; margin-top: 120px">
    <li class="${(index==1)?string('active',' ')}">
      <a href="${basePath}/catering/dish_items.shtml">
        <i class="glyphicon glyphicon-chevron-right"></i>菜品管理
      </a>
    </li>
    <li class="${(index==2)?string('active',' ')} dropdown">
      <a href="${basePath}/catering/dish_types.shtml">
        <i class="glyphicon glyphicon-chevron-right"></i>菜品分类
      </a>
    </li>
    <li class="${(index==3)?string('active',' ')} dropdown">
      <a href="${basePath}/catering/dining_tables.shtml">
        <i class="glyphicon glyphicon-chevron-right"></i>餐桌管理
      </a>
    </li>
  </ul>
</div>
</#macro>

<#macro order index>
<div id="one" class="col-md-2">
  <ul data-spy="affix" class="nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix"
      style="top: 100px; z-index: 100; margin-top: 120px">
    <li class="${(index==1)?string('active',' ')}">
      <a href="${basePath}/catering/dish_orders.shtml">
        <i class="glyphicon glyphicon-chevron-right"></i>餐饮订单
      </a>
    </li>
    <li class="${(index==2)?string('active',' ')} dropdown">
      <a href="${basePath}/buddha/buddha_orders.shtml">
        <i class="glyphicon glyphicon-chevron-right"></i>佛具订单
      </a>
    </li>
  </ul>
</div>
</#macro>

<#macro buddha index>
<div id="one" class="col-md-2">
  <ul data-spy="affix" class="nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix"
      style="top: 100px; z-index: 100; margin-top: 120px">
    <li class="${(index==1)?string('active',' ')}">
      <a href="${basePath}/buddha/buddha_items.shtml">
        <i class="glyphicon glyphicon-chevron-right"></i>商品管理
      </a>
    </li>
    <li class="${(index==2)?string('active',' ')} dropdown">
      <a href="${basePath}/buddha/buddha_types.shtml">
        <i class="glyphicon glyphicon-chevron-right"></i>商品分类
      </a>
    </li>
    <li class="${(index==3)?string('active',' ')} dropdown">
      <a href="${basePath}/buddha/buddha_carousels.shtml">
        <i class="glyphicon glyphicon-chevron-right"></i>轮播图管理
      </a>
    </li>
  </ul>
</div>
</#macro>

<#macro car index>
<div id="one" class="col-md-2">
  <ul data-spy="affix" class="nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix"
      style="top: 100px; z-index: 100; margin-top: 120px">
    <li class="${(index==1)?string('active',' ')}">
      <a href="${basePath}/car_beauty/car_beauty_items.shtml">
        <i class="glyphicon glyphicon-chevron-right"></i>服务管理
      </a>
    </li>
    <li class="${(index==2)?string('active',' ')} dropdown">
      <a href="${basePath}/car_beauty/car_beauty_types.shtml">
        <i class="glyphicon glyphicon-chevron-right"></i>服务分类
      </a>
    </li>
    <li class="${(index==3)?string('active',' ')} dropdown">
      <a href="${basePath}/car_beauty/car_beauty_carousels.shtml">
        <i class="glyphicon glyphicon-chevron-right"></i>轮播图管理
      </a>
    </li>
  </ul>
</div>
</#macro>

<#macro score index>
  <div id="one" class="col-md-2">
    <ul data-spy="affix" class="nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix"
        style="top: 100px; z-index: 100; margin-top: 120px">
      <li class="${(index==1)?string('active',' ')}">
        <a href="${basePath}/universal/score_products.shtml">
          <i class="glyphicon glyphicon-chevron-right"></i>积分商城
        </a>
      </li>
    </ul>
  </div>
</#macro>

<#macro msgSendAll index>
  <div id="one" class="col-md-2">
    <ul data-spy="affix" class="nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix"
        style="top: 100px; z-index: 100; margin-top: 120px">
      <li class="${(index==1)?string('active',' ')}">
        <a href="${basePath}/universal/to_message_send_all.shtml">
          <i class="glyphicon glyphicon-chevron-right"></i>消息推送
        </a>
      </li>
    </ul>
  </div>
</#macro>

<#macro system index>
<div id="one" class="col-md-2">
  <ul data-spy="affix" class="nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix"
      style="top: 100px; z-index: 100; margin-top: 120px">
    <li class="${(index==1)?string('active',' ')}">
      <a href="${basePath}/universal/business_period.shtml">
        <i class="glyphicon glyphicon-chevron-right"></i>餐饮营业时间
      </a>
    </li>
    <li class="${(index==2)?string('active',' ')} dropdown">
      <a href="${basePath}/universal/shop_telephone.shtml">
        <i class="glyphicon glyphicon-chevron-right"></i>汽车店铺电话
      </a>
    </li>
    <li class="${(index==3)?string('active',' ')} dropdown">
      <a href="${basePath}/universal/shop_address.shtml">
        <i class="glyphicon glyphicon-chevron-right"></i>店铺地址
      </a>
    </li>
    <li class="${(index==4)?string('active',' ')} dropdown">
      <a href="${basePath}/universal/score_exchange_msg.shtml">
        <i class="glyphicon glyphicon-chevron-right"></i>积分兑换须知
      </a>
    </li>
    <li class="${(index==5)?string('active',' ')} dropdown">
      <a href="${basePath}/universal/homepage_carousels.shtml">
        <i class="glyphicon glyphicon-chevron-right"></i>首页轮播图
      </a>
    </li>
    <li class="${(index==6)?string('active',' ')} dropdown">
      <a href="${basePath}/universal/homepage_setting.shtml">
        <i class="glyphicon glyphicon-chevron-right"></i>首页设置
      </a>
    </li>
  </ul>
</div>
</#macro>
