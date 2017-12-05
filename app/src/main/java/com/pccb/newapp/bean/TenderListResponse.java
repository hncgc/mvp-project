package com.pccb.newapp.bean;

import java.util.List;

/**
 * Created by WangYi
 *
 * @Date : 2017/11/10
 * @Desc : 产品标列表
 */
public class TenderListResponse extends Response {
    private int totalPages;
    private int totalRows;
    private List<Tender> rows;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public List<Tender> getRows() {
        return rows;
    }

    public void setRows(List<Tender> rows) {
        this.rows = rows;
    }
}
