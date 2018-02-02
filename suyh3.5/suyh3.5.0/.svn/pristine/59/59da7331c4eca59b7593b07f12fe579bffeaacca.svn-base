package cn.hotol.wechat.domain.json.dataTablesSupport;

import cn.hotol.wechat.toolutil.modelutil.Page;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author inder
 *         login: Hill Pan
 *         Date: 12/20/11
 *         Time: 10:03 AM
 */

public class DataTablesResponse<T> implements Serializable {

    @JsonProperty(value = "iTotalRecords")
    private int totalRecords;

    @JsonProperty(value = "iTotalDisplayRecords")
    private int totalDisplayRecords;

    @JsonProperty(value = "sEcho")
    private String echo;

    @JsonProperty(value = "sColumns")
    private String columns;

    @JsonProperty(value = "aaData")
    private List<T> data = new ArrayList<T>();

    public DataTablesResponse() {
    }

    public DataTablesResponse(int totalRecords, int totalDisplayRecords, String echo, String columns, List<T> data) {
        this.totalRecords = totalRecords;
        this.totalDisplayRecords = totalDisplayRecords;
        this.echo = echo;
        this.columns = columns;
        this.data = data;
    }

    public DataTablesResponse(Page<T> page, DataTablesRequest dataTablesRequest) {
        this.totalRecords = (int) page.getTotalCount();
        this.totalDisplayRecords = (int) page.getTotalCount();
        this.echo = dataTablesRequest.getEcho();
        this.columns = dataTablesRequest.getColumns();
        this.data = page.getItems();
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getTotalDisplayRecords() {
        return totalDisplayRecords;
    }

    public void setTotalDisplayRecords(int totalDisplayRecords) {
        this.totalDisplayRecords = totalDisplayRecords;
    }

    public String getEcho() {
        return echo;
    }

    public void setEcho(String echo) {
        this.echo = echo;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
