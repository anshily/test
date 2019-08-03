package io.anshily.base.filter;

import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


@Slf4j
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        // 如果不是映射到方法直接通过
//        if (!(handler instanceof HandlerMethod)) {
//            return true;
//        }
//        HandlerMethod handlerMethod = (HandlerMethod) handler;
//        Method method = handlerMethod.getMethod();
//        if (method.isAnnotationPresent(IgnoreSecurity.class)) {
//            return true;
//        }
//
//        //使用自定义ReprocessHttpServletRequest获取参数 防止post后续获取不到参数
//        JSONObject result = JSON.parseObject(new ReprocessHttpServletRequest(request).getRequestBodyString(request));
//
//        //获取该方法所在类上的所有注解
//        Annotation[] annotationData = handlerMethod.getBeanType().getDeclaredAnnotations();
//        //遍历所有注解，如果存在IgnoreSecurity，则返回true
//        for (Annotation annotation : annotationData) {
//            if (annotation.annotationType() == IgnoreSecurity.class) {
//                return true;
//            }
//        }
//        String token;
//        if (result != null) {
//            token = request.getHeader("token") != null ? request.getHeader("token") : (result.get("token") != null ? result.get("token").toString() : (request.getParameter("token") != null ? request.getParameter("token") : ""));
//        } else {
//            token = request.getHeader("token") != null ? request.getHeader("token") : (request.getParameter("token") != null ? request.getParameter("token") : "");
//        }
//        if (!StringUtil.isEmpty(token)) {
//            User user = new User();
//            user.setToken(token);
//            User adminObj = userService.selectOne(user);
//            if (adminObj != null) {
//                return true;
//            } else {
//                response.setCharacterEncoding("UTF-8");
//                response.setHeader("Content-type", "application/json;charset=UTF-8");
//                response.setStatus(200);
//                response.setHeader("Access-Control-Allow-Origin","*");
//                response.getWriter().write(JSON.toJSONString(ResultGenerator.errResult(501)));
//                return false;
//            }
//        } else {
//            response.setCharacterEncoding("UTF-8");
//            response.setHeader("Content-type", "application/json;charset=UTF-8");
//            response.setStatus(200);
//            response.setHeader("Access-Control-Allow-Origin","*");
//            response.getWriter().write(JSON.toJSONString(ResultGenerator.errResult(501)));
//            return false;
//        }
//    }
}
