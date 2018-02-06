package com.fangdd.tp.controller.web;

import org.springframework.web.servlet.ModelAndView;

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
}
