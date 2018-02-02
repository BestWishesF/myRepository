package cn.hotol.app.base;

/**
 * Created by lizhun on 16/1/13.
 */
public class Page {
    private int currentPage = 1;
    private int pageSize = 10;
    private Object obj;


    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }
}
