package com.javamentor.qa.platform.models.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageDto<T, V> implements Serializable {
    private int currentPageNumber;
    private int totalPageCount;
    private int totalResultCount;
    private List<T> items;
    private List<V> meta;
    private int itemsOnPage = 12;

    @Override
    public String toString() {
        return "PageDto{" +
                "currentPageNumber=" + currentPageNumber +
                ", totalPageCount=" + totalPageCount +
                ", totalResultCount=" + totalResultCount +
                ", items=" + items +
                ", meta=" + meta +
                ", itemsOnPage=" + itemsOnPage +
                '}';
    }
}
