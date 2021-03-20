
package com.vb.htest.data.network.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InfoResponse implements Serializable
{
    private String status;
    private Integer page;
    private List<Datum> data = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}
