package com.fangdd.tp.doclet.constant;

/**
 * @author ycoe
 * @date 18/2/11
 */
public class EntityConstant {
    public static final String ANNOTATION_DATE_TIME_FORMAT = "org.springframework.format.annotation.DateTimeFormat";
    public static final String DATE_TIME_FORMAT_ISO_DATE = "org.springframework.format.annotation.DateTimeFormat.ISO.DATE";
    public static final String DATE_TIME_FORMAT_ISO_TIME = "org.springframework.format.annotation.DateTimeFormat.ISO.TIME";
    public static final String DATE_TIME_FORMAT_ISO_DATE_TIME = "org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME";
    public static final String JAVAX_VALIDATION_CONSTRAINTS_NOTNULL = "javax.validation.constraints.NotNull";
    public static final String JAVAX_VALIDATION_CONSTRAINTS_NULL = "javax.validation.constraints.Null";

    public static final String HIBERNATE_VALIDATOR_ANNOTATION_NOT_BLANK = "org.hibernate.validator.constraints.NotBlank";
    public static final String HIBERNATE_VALIDATOR_ANNOTATION_NOT_EMPTY = "org.hibernate.validator.constraints.NotEMPTY";

    /**
     * GraphqlField
     */
    public static final String GRAPHQL_FIELD = "com.fangdd.graphql.provider.annotation.GraphqlField";
    public static final String ANNOTATION_GRAPHQL_PROVIDER = "com.fangdd.graphql.provider.annotation.GraphqlProvider";
}
