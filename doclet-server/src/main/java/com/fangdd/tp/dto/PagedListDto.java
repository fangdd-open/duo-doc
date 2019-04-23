package com.fangdd.tp.dto;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 带分页数据列表
 */
public class PagedListDto<T> {
    /**
     * 当前页数据列表
     */
    private List<T> list;

    /**
     * 总记录数
     *
     * @demo 1024
     */
    private Long total;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public static <T>PagedListDto<T> empty() {
        PagedListDto<T> pagedListDto = new PagedListDto<>();
        pagedListDto.setList(Lists.newArrayList());
        pagedListDto.setTotal(0L);
        return pagedListDto;
    }
}
