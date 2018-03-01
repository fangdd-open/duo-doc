package com.fangdd.tp.controller.web;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @auth ycoe
 * @date 18/1/24
 */
public class BaseWebController {
    public static ThreadLocal<ModelAndView> modelAndViewThreadLocal = new ThreadLocal<>();

    protected void addData(String key, Object data) {
        ModelAndView view = getModelAndView();
        view.addObject(key, data);
    }

    private ModelAndView getModelAndView() {
        ModelAndView view = modelAndViewThreadLocal.get();
        if (view == null) {
            view = new ModelAndView();
            modelAndViewThreadLocal.set(view);
        }
        return view;
    }

    protected ModelAndView view(String viewPath) {
        ModelAndView view = getModelAndView();
        view.setViewName(viewPath);
        modelAndViewThreadLocal.remove();
        return view;
    }

    /**
     * 301跳转
     *
     * @param url
     * @return
     */
    public View redirect302(String url) {
        return new RedirectView(url);
    }
}
