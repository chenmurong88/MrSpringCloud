package com.murong.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccessFilter extends ZuulFilter {

    /*
    * 过滤逻辑
    * */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        Object accessToken = request.getParameter("accessToken");
        if(accessToken==null) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            //为了被error过滤器捕获
            ctx.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ctx.set("error.exception",new RuntimeException("AccessToken is null"));
            return null;
        }
        return null;
    }

    /*
    * 过滤器是否被执行，只有true时才会执行run()里的代码。
    * 我们这里除开访问163会放行其他情况都需要进行过滤判断
    * 在生产环境一般是要根据函数条件来判断的。
    * */
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        if(request.getRequestURI().equals("/163")) {
            return false;
        }else {
            return true;
        }
    }

    /*
    * 过滤器的执行顺序，多个过滤器同时存在时根据这个order来决定先后顺序，越小优先级越高
    * */
    @Override
    public int filterOrder() {
        return 0;
    }

    /*
    * 过滤器类型，决定了过滤器在哪个周期生效。类型有pre、route、post、error
    * 对应Spring AOP里的前加强、前后加强、后加强、异常处理。
    * */
    @Override
    public String filterType() {
        return "pre";
    }

}
