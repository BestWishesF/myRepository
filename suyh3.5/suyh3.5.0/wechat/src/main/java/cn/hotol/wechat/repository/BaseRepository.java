package cn.hotol.wechat.repository;

import cn.hotol.wechat.domain.model.BaseEntity;
import cn.hotol.wechat.domain.mybatis.GroupEntity;
import cn.hotol.wechat.domain.mybatis.W13SqlProvider;
import org.apache.ibatis.annotations.*;

/**
 * login: Hill Pan
 * Date: 3/5/12
 * Time: 5:14 PM
 * 
 */
public interface BaseRepository<T extends BaseEntity> {

    @Delete("delete from ${table} where id=#{id}")
    void delete(final T t);

    @Select("select * from ${table} where id=#{id}")
    T load(final T t);

	@InsertProvider(type = W13SqlProvider.class, method = "insert")
	@Options(useGeneratedKeys = true)
	void create(GroupEntity entity);

	@UpdateProvider(type = W13SqlProvider.class, method = "update")
	void update(GroupEntity entity);

    @Delete("delete from role_authorization where id=#{id}")
    void remove(Long id);
}
