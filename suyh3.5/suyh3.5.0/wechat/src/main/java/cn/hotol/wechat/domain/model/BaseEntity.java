package cn.hotol.wechat.domain.model;


import cn.hotol.wechat.domain.mybatis.GroupEntity;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.Transient;

/**
 * login: Hill Pan
 * Date: 3/5/12
 * Time: 5:27 PM
 */
public class BaseEntity extends GroupEntity {

    public BaseEntity() {
    }

    public BaseEntity(Long id) {
        super(id);
    }

    @Transient
    @JsonIgnore
    public boolean isNew() {
        return id == 0L;
    }
}
