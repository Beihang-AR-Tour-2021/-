package cn.edu.buaa.smarttour.interceptor;

import cn.edu.buaa.smarttour.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Date;

//登陆拦截器，保证要用户登录后才能进行的操作
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 再执行目标方法之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("preHandle拦截的请求路径是{}", requestURI);
        //登录检查逻辑
        HttpSession session = request.getSession();
        Object customer = session.getAttribute("customer");

        if (customer!= null) {
            // 放行
            return true;
        }
        // 未登录，应跳转到登录页，参考逻辑实现如下
        /*
        request.setAttribute("msg", "请先登录");
        request.getRequestDispatcher("/").forward(request, response);
        */
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        JSONObject res = new JSONObject();
        res.put("code", 1003);
        res.put("message", "请先登录");
        res.put("time",new Date());
        out = response.getWriter();
        out.append(res.toString());
        return false;
    }

    /**
     * 目标方法执行完成之后
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }


    /**
     * 页面渲染完成以后
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
