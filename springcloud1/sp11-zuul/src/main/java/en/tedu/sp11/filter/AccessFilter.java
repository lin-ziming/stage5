package en.tedu.sp11.filter;

import cn.tedu.web.util.JsonResult;
import com.fasterxml.jackson.core.JsonFactory;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Haitao
 * @date 2020/11/27 10:32
 */
@Component
@Slf4j
public class AccessFilter extends ZuulFilter {
    /**
     * 设置过滤器的类型：pre, routing, post, error
     */
    @Override
    public String filterType() {
        //return "pre";
        return FilterConstants.PRE_TYPE;//设置成前置过滤器
    }
    /**
     * 设置过滤器的顺序号
     * 默认已经有5个过滤器
     */
    @Override
    public int filterOrder() {
        return 6;
    }

    /**
     * 对客户端的当前请求进行判断，
     * 针对当前请求，是否要执行过滤代码
     */
    @Override
    public boolean shouldFilter() {
        // 如果调用 item-service，要执行过滤代码判断权限
        // 否则不执行过滤代码

        // 获取调用的服务id
        RequestContext ctx = RequestContext.getCurrentContext();//请求上下文对象
        String serviceId = (String) ctx.get(FilterConstants.SERVICE_ID_KEY);

        //判断是不是 item-service
        return "item-service".equalsIgnoreCase(serviceId);//忽略大小写比较字符串
    }

    /**
     * 过滤代码
     */
    @Override
    public Object run() throws ZuulException {
        // http://localhost:3001/item-service/u45y45435?token=y45343t4
        // 获取 request 对象
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        // 接收token参数
        String token = request.getParameter("token");
        // 如果没有收到token参数，认为用户没登录，阻止他继续调用
        if (StringUtils.isBlank(token)){ //org.apache.commons.lang3.StringUtils
            //阻止他继续调用
            ctx.setSendZuulResponse(false);//不再向后台服务转发

            //设置向客户端发送的响应
            ctx.addZuulResponseHeader("Content-Type","application/json");
            // JsonResult - {code:401, msg:"not login", data:null}
            ctx.setResponseBody(JsonResult.err().msg("not login").toString());

        }
        return null; //在当前 zuul 版本中，这个返回值没有任何作用
    }
}
