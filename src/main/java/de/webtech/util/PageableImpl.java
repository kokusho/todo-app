package de.webtech.util;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableImpl implements Pageable {

    private int pageNumber;
    private int pageSize;

    public PageableImpl(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    @Override
    public int getPageNumber() {
        return this.pageNumber;
    }

    @Override
    public int getPageSize() {
        return this.pageSize;
    }

    @Override
    public long getOffset() {
        return this.pageNumber * this.pageSize;
    }

    @Override
    public Sort getSort() {
        return null;
    }

    @Override
    public Pageable next() {
        return new PageableImpl(this.pageNumber + 1, this.pageSize);
    }

    @Override
    public Pageable previousOrFirst() {
        return new PageableImpl(Math.max(0, this.pageNumber-1), this.pageSize);
    }

    @Override
    public Pageable first() {
        return new PageableImpl(0, this.pageSize);
    }

    @Override
    public boolean hasPrevious() {
        return pageSize >= 1;
    }
}
