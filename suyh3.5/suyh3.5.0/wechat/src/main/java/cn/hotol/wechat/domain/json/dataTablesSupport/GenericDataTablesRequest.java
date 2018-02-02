package cn.hotol.wechat.domain.json.dataTablesSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * login: Hill Pan
 * Date: 1/30/12
 * Time: 4:32 PM
 */
public class GenericDataTablesRequest<T> extends DataTablesRequest implements Serializable {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private T filter;

    public T getFilter() {
        return filter;
    }

    public void setFilter(T filter) {
        this.filter = filter;
    }
}
