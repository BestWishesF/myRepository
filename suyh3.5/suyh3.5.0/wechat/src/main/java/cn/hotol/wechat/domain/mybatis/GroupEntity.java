package cn.hotol.wechat.domain.mybatis;



import cn.hotol.wechat.toolutil.modelutil.NameUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.Transient;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * login: Hill Pan
 * Date: 4/18/13
 * Time: 9:54 AM
 */
public abstract class GroupEntity {

    protected Long id = 0L;

    private Set<Class<?>> persistentGroups = new HashSet<Class<?>>();

    protected GroupEntity() {
    }

    protected GroupEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean inPersistentGroup(Class<?> group) {
		return persistentGroups.contains(group);
	}

	public boolean anyInPersistentGroup(Class<?>... groups) {
		for (Class<?> group : groups) {
			if (inPersistentGroup(group)) return true;
		}
		return false;
	}

	public GroupEntity group(Class<?>... groups) {
		Collections.addAll(persistentGroups, groups);
		return this;
	}

    public void setGroup(Class<?>... groups) {
        Collections.addAll(persistentGroups, groups);
    }

	public void clearPersistentGroups() {
		persistentGroups.clear();
	}

    @JsonIgnore
	public Set<Class<?>> getPersistentGroups() {
		return persistentGroups;
	}

	public boolean needPersist(Class<?>[] propertyGroups) {

        if (persistentGroups.isEmpty()) return true;

		if (propertyGroups.length == 0) return false;

		return anyInPersistentGroup(propertyGroups);
	}

	@Transient
    @JsonIgnore
    public String getTable() {
        return NameUtils.camel2Underscore(this.getClass().getSimpleName());
    }

}
