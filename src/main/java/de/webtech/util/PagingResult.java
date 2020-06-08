package de.webtech.util;

import java.util.Set;

public class PagingResult<T> {

    private PageableImpl pagingInfo;
    private Set<T>       values;

    public PagingResult(PageableImpl pagingInfo) {
        this.pagingInfo = pagingInfo;
    }

    public Set<T> getValues() {
        return values;
    }

    public void setValues(Set<T> values) {
        this.values = values;
    }

    public PageableImpl getPagingInfo() {
        return pagingInfo;
    }

    public void setPagingInfo(PageableImpl pagingInfo) {
        this.pagingInfo = pagingInfo;
    }
}
