package cn.hotol.wechat.domain.mybatis;

import cn.hotol.wechat.toolutil.modelutil.NameUtils;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ReflectionUtils;

import javax.persistence.Transient;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.*;

/**
 * login: Hill Pan
 * Date: 8/31/12
 * Time: 3:40 PM
 */
public class W13SqlProvider {

    public static final String INSERT = "insert";

    public static final String UPDATE = "update";

    public static final String DELETE = "delete";

    public static final String SQL_TEMPLATE_INSERT = "INSERT INTO {0} ({1}) VALUES ({2})";

    public static final String SQL_TEMPLATE_UPDATE = "UPDATE {0} set {1} WHERE {2}";

	public static Map<CacheKey, String> cachedSQLMap = new HashMap<CacheKey, String>();

	public static Map<Class<? extends GroupEntity>, POMetaData> poMetaDataCache = new HashMap<>();

	public String insert(GroupEntity entity) {

		CacheKey cacheKey = new CacheKey(INSERT, entity.getClass(), entity.getPersistentGroups());
        if (cachedSQLMap.containsKey(cacheKey)) {
			return cachedSQLMap.get(cacheKey);
		}

		String insertSQL =  buildInsertSQL(entity);
		cachedSQLMap.put(cacheKey, insertSQL);
		return insertSQL;
	}

	public String update(GroupEntity entity) {

		CacheKey cacheKey = new CacheKey(UPDATE, entity.getClass(), entity.getPersistentGroups());
		if (cachedSQLMap.containsKey(cacheKey)) {
			return cachedSQLMap.get(cacheKey);
		}
		String updateSQL = buildUpdateSQL(entity);
		cachedSQLMap.put(cacheKey, updateSQL);
		return updateSQL;
	}

    private String buildUpdateSQL(GroupEntity entity) {

        POMetaData poMetaData= retrievePoMetaData(entity);

        List<String> groupedProperties = new ArrayList<>();
        List<String> groupedColumns = new ArrayList<>();

        for (POPropertyMetaData poPropertyMetaData : poMetaData.propertyMetaDatas) {

            if (poPropertyMetaData.propertyName.equals("lastModifiedTime")||
                    entity.needPersist(poPropertyMetaData.persistentGroups)) {

                groupedProperties.add(poPropertyMetaData.propertyName);
                groupedColumns.add(poPropertyMetaData.columnName);
            }
        }

        String[] updateColumnArray = new String[groupedColumns.size()];

        for (int i = 0; i < groupedColumns.size(); i ++) {
            updateColumnArray[i] = groupedColumns.get(i) + "=" + sqlRefWrap(groupedProperties.get(i));
        }

        String updateColumns = StringUtils.join(updateColumnArray, ',');
        String whereCondition;
        if (poMetaData.keyPropertyMetaData == null) {
            whereCondition = "id=#{id}";
        } else {
            whereCondition = poMetaData.keyPropertyMetaData.columnName+"=#{"+poMetaData.keyPropertyMetaData.propertyName+"}";
        }

        return MessageFormat.format(SQL_TEMPLATE_UPDATE, poMetaData.tableName, updateColumns, whereCondition);
    }

    private W13SqlProvider.POMetaData retrievePoMetaData(GroupEntity entity) {

        W13SqlProvider.POMetaData poMetaData = poMetaDataCache.get(entity.getClass());

        if (poMetaData == null) {
            poMetaData= extractMetaData(entity);
            poMetaDataCache.put(entity.getClass(), poMetaData);
        }

        return poMetaData;
    }

    private String buildInsertSQL(GroupEntity entity) {

        POMetaData poMetaData= retrievePoMetaData(entity);

        List<String> groupedProperties = new ArrayList<>();
        List<String> groupedColumns = new ArrayList<>();

        for (POPropertyMetaData poPropertyMetaData : poMetaData.propertyMetaDatas) {
            if (poPropertyMetaData.propertyName.equals("createTime")||
                    poPropertyMetaData.propertyName.equals("lastModifiedTime")||
                    entity.needPersist(poPropertyMetaData.persistentGroups)) {

                groupedProperties.add(poPropertyMetaData.propertyName);
                groupedColumns.add(poPropertyMetaData.columnName);
            }
        }

        String columns = StringUtils.join(groupedColumns, ',');

        String properties = StringUtils.join(toSQLRefs(groupedProperties), ',');

        return MessageFormat.format(SQL_TEMPLATE_INSERT, poMetaData.tableName, columns, properties);
    }

    private POMetaData extractMetaData(GroupEntity entity) {

        PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(entity.getClass());

        List<POPropertyMetaData> propertyMetaDatas = new ArrayList<>();

        POPropertyMetaData keyPropertyMetaData = null;

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {

            if (notNeedPersist(propertyDescriptor)) continue;

            String propertyName = propertyDescriptor.getName();

            Method readMethod = propertyDescriptor.getReadMethod();

            Class<?>[] groups = getGroups(readMethod);

            String columnName = NameUtils.camel2Underscore(propertyName);
            POPropertyMetaData poPropertyMetaData = new POPropertyMetaData(propertyName, groups, columnName);
            if (hasKeyAnnotation(readMethod)) {
                keyPropertyMetaData = poPropertyMetaData;
            }

            propertyMetaDatas.add(poPropertyMetaData);
        }

        String table = entity.getTable();

        return new POMetaData(keyPropertyMetaData, table, propertyMetaDatas);
    }

    private boolean hasKeyAnnotation(Method readMethod) {
        Annotation[] annotations = readMethod.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Key) return true;
        }
        return false;
    }

    private Class<?>[] getGroups(Method readMethod) {
        Annotation[] annotations = readMethod.getDeclaredAnnotations();
        Set<Class<?>> groups = new HashSet<>();
        for (Annotation annotation : annotations) {
            Collection<Class<?>> groupsInOneAnnotation = getGroups(annotation);
            groups.addAll(groupsInOneAnnotation);
        }
        return groups.toArray(new Class<?>[groups.size()]);
    }

    private Collection<Class<?>> getGroups(Annotation annotation) {
        if (annotation instanceof Group) {
             return Lists.newArrayList(((Group) annotation).value());
        }
        Method groupsMethod = ReflectionUtils.findMethod(annotation.getClass(), "groups");
        if (groupsMethod == null) return Lists.newArrayList();
        Class<?>[] groups = (Class<?>[]) ReflectionUtils.invokeMethod(groupsMethod, annotation);
        return Lists.newArrayList(groups);
    }

    private boolean notNeedPersist(PropertyDescriptor propertyDescriptor) {
//	private boolean notNeedPersist(GroupEntity entity, PropertyDescriptor propertyDescriptor) {

		String propertyName = propertyDescriptor.getName();

		if (propertyName.equals("id")) {
			return true;
		}
		Method readMethod = propertyDescriptor.getReadMethod();
        if (readMethod == null) {
            return true;
        }
		if (declaringInOrSuper(GroupEntity.class, readMethod)) {
			return true;
		}
		if (readMethod.getAnnotation(Transient.class) != null) {
			return true;
		}
        return false;
/*
		Group group = readMethod.getAnnotation(Group.class);
		return !entity.needPersist(group);
*/
	}

	private boolean declaringInOrSuper(Class<?> targetClass, Method readMethod) {
		Class<?> declaringClass = readMethod.getDeclaringClass();
		return declaringClass.isAssignableFrom(targetClass);
	}

	private List<String> toSQLRefs(List<String> persistentProperties) {
		List<String> ret = new ArrayList<String>();
		for (String persistentProperty : persistentProperties) {
			ret.add(sqlRefWrap(persistentProperty));
		}
		return ret;
	}

	private String sqlRefWrap(String property) {
		return "#{"+property+"}";
	}

	private static class CacheKey {

        private String operation;

		private Class<? extends GroupEntity> entityClass;

		private Set<Class<?>> persistentGroups;

        private CacheKey(String operation, Class<? extends GroupEntity> entityClass, Set<Class<?>> persistentGroups) {
            this.operation = operation;
            this.entityClass = entityClass;
            this.persistentGroups = persistentGroups;
        }

        private CacheKey(Class<? extends GroupEntity> entityClass, Set<Class<?>> persistentGroups) {
			this.entityClass = entityClass;
			this.persistentGroups = persistentGroups;
		}

		@Override
		public boolean equals(Object o) {

			if (this == o) return true;

			if (o == null || getClass() != o.getClass()) return false;

			CacheKey cacheKey = (CacheKey) o;

            return operation.equals(cacheKey.operation) &&
                    entityClass.equals(cacheKey.entityClass) &&
                    persistentGroups.equals(cacheKey.persistentGroups);
        }

		@Override
		public int hashCode() {
			return 63 * operation.hashCode()  + 31 * entityClass.hashCode() + persistentGroups.hashCode();
		}
	}

    private static class POMetaData {

        private POPropertyMetaData keyPropertyMetaData;

        private String tableName;

        private List<POPropertyMetaData> propertyMetaDatas;

        private POMetaData(String tableName, List<POPropertyMetaData> propertyMetaDatas) {
            this.tableName = tableName;
            this.propertyMetaDatas = propertyMetaDatas;
        }

        private POMetaData(POPropertyMetaData keyPropertyMetaData, String tableName, List<POPropertyMetaData> propertyMetaDatas) {
            this.keyPropertyMetaData = keyPropertyMetaData;
            this.tableName = tableName;
            this.propertyMetaDatas = propertyMetaDatas;
        }
    }

    private static class POPropertyMetaData {

        private String propertyName;

        private Class<?>[] persistentGroups;

        private String columnName;

        private POPropertyMetaData(String propertyName, Class<?>[] persistentGroups, String columnName) {
            this.propertyName = propertyName;
            this.persistentGroups = persistentGroups;
            this.columnName = columnName;
        }
    }

    public static void main(String[] args) {
/*
        System.out.println(new W13SqlProvider().insert(new TaoUser()));
        System.out.println(new W13SqlProvider().update(new TaoUser()));
*/
    }
}
