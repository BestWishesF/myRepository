package cn.hotol.wechat.repository.login;

import cn.hotol.wechat.domain.model.login.User;
import cn.hotol.wechat.repository.BaseRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 登录使用的dao层
 * Created by LuBin
 * Date 2016-11-29.
 */

@Repository
public interface LoginRepository extends BaseRepository<User> {

    /**
     * 用户登录时验证帐号
     * 作者：陆斌
     * 时间：2016-11-29 11：16
     * 版本：1.0
     * @param mobile    登录手机号
     * @return          返回用户实体类或null
     */
    @Select("SELECT * FROM `user` WHERE mobile_no=#{mobile}")
    User validateLogon(@Param("mobile")String mobile);


}
