package com.kexie.acloud.exception;


import com.alibaba.fastjson.support.spring.FastJsonJsonView;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created : wen
 * DateTime : 2017/5/7 19:25
 * Description : 全局异常处理 常规的异常都可以放到这里来统一处理。
 */
public class GlobalHandlerExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        ex.printStackTrace();

        if (ex instanceof FormException) {
            return handlerFormException(request, response, (FormException) ex);
        } else {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ModelAndView mv = new ModelAndView();

        /*  使用FastJson提供的FastJsonJsonView视图返回，不需要捕获异常   */
            FastJsonJsonView view = new FastJsonJsonView();

            Map<String, String> attributes = new HashMap<>();
            attributes.put("msg", ex.getMessage());

            view.setAttributesMap(attributes);

            mv.setView(view);

            return mv;
        }
    }

    /**
     * 处理表单错误
     */
    private ModelAndView handlerFormException(HttpServletRequest request, HttpServletResponse response, FormException ex) {

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        ModelAndView mv = new ModelAndView();
        FastJsonJsonView view = new FastJsonJsonView();

        view.setAttributesMap(ex.getErrorForm());
        mv.setView(view);

        return mv;
    }
}
