package de.webtech.util;

import java.util.Set;

public class PagingResult<T> {
    private int page;
    private int itemsPerPage;
    private int totalItems;
    private Set<T> values;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public Set<T> getValues() {
        return values;
    }

    public void setValues(Set<T> values) {
        this.values = values;
    }
}
