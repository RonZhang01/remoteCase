package cn.itcast.web.filter;

import com.sun.deploy.net.HttpRequest;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * 登录验证的过滤器
 */
@WebFilter("/*")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //0、强制转换为httpRequest
        HttpServletRequest request = (HttpServletRequest) req;
        //1、获取资源请求路径
        String requestURI = request.getRequestURI();
        //判断是否包含登录相关资源路径，要注意排除掉css/js/图片/验证码等资源
        if (requestURI.contains("/login.jsp") || requestURI.contains("/loginServlet") || requestURI.contains("/css/") || requestURI.contains("/js/") || requestURI.contains("/fonts") || requestURI.contains("/checkCodeServlet")){
            //包含，用户就是想登录，放行
            chain.doFilter(req, resp);
        }else {
            //不包含，需要验证用户是否登录
            //3、从session中获取user对象
            Object user = request.getSession().getAttribute("user");
            if (user != null){
                //登录了，放行
                chain.doFilter(req, resp);
            }else {
                //没有登录，跳转到登录页面
                request.setAttribute("login_msg","您尚未登录，请先登录");
                request.getRequestDispatcher("/login.jsp").forward(req,resp);
            }

        }



    }

    public void init(FilterConfig config) throws ServletException {

    }

}
