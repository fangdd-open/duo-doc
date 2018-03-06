package com.fangdd.tp.doclet.constant;

/**
 * Created by ycoe on 15-11-28.
 */
public class SpringMvcConstant {

    public static final String ANNOTATION_REST_CONTROLLER = "org.springframework.web.bind.annotation.RestController";

//    public static final String ANNOTATION_CONTROLLER = "org.springframework.stereotype.Controller";

    public static final String ANNOTATION_REQUEST_MAPPING = "org.springframework.web.bind.annotation.RequestMapping";
    public static final String ANNOTATION_GET_MAPPING = "org.springframework.web.bind.annotation.GetMapping";
    public static final String ANNOTATION_POST_MAPPING = "org.springframework.web.bind.annotation.PostMapping";

    public static final String ANNOTATION_NOT_NULL = "javax.validation.constraints.NotNull";

    public static final String ANNOTATION_PATH_VARIABLE = "org.springframework.web.bind.annotation.PathVariable";
    public static final String ANNOTATION_REQUEST_BODY = "org.springframework.web.bind.annotation.RequestBody";
    public static final String ANNOTATION_REQUEST_PARAM = "org.springframework.web.bind.annotation.RequestParam";
    public static final String ANNOTATION_REQUEST_ATTRIBUTE = "org.springframework.web.bind.annotation.RequestAttribute";

    private SpringMvcConstant(){}

}
