package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.thirdlogin.TdHtMembThirdLoginDto;
import org.springframework.stereotype.Repository;

/**
 * Created by LuBin
 * Date 2017-01-04.
 */

@Repository
public interface TdHtMembThirdLoginRepository {

    /**
     * @param tdHtMembThirdLoginDto
     * @Purpose 插入用户第三方登录信息
     * @version 3.0
     * @author lubin
     */
    public void insertMembThirdLogin(TdHtMembThirdLoginDto tdHtMembThirdLoginDto);

    /**
     * @param tdHtMembThirdLoginDto
     * @return TdHtMembThirdLoginDto
     * @Purpose 通过第三方标识查询用户第三方登录信息
     * @version 3.0
     * @author lubin
     */
    public TdHtMembThirdLoginDto findMembThirdLogin(TdHtMembThirdLoginDto tdHtMembThirdLoginDto);

    /**
     * @param tdHtMembThirdLoginDto
     * @Purpose 更新用户第三方登录信息
     * @version 3.0
     * @author lubin
     */
    public void updateMembThirdLogin(TdHtMembThirdLoginDto tdHtMembThirdLoginDto);

    /**
     * @param tdHtMembThirdLoginDto
     * @return TdHtMembThirdLoginDto
     * @Purpose 通过用户id和第三方配置id查询用户第三方登录信息
     * @version 3.0
     * @author lubin
     */
    public TdHtMembThirdLoginDto findMembThirdByBean(TdHtMembThirdLoginDto tdHtMembThirdLoginDto);

    /**
     * @param thr_token
     * @return TdHtMembThirdLoginDto
     * @Purpose 通过thr_token查询用户第三方登录信息
     * @version 3.0
     * @author lubin
     */
    public TdHtMembThirdLoginDto findMemberThirdByToken(String thr_token);

    /**
     * @param tdHtMembThirdLoginDto
     * @Purpose 修改用户id
     * @version 3.0
     * @author lubin
     */
    public void updateMemberIdByThrId(TdHtMembThirdLoginDto tdHtMembThirdLoginDto);

    /**
     * @param thr_id
     * @Purpose 删除第三方登录信息
     * @version 3.0
     * @author lubin
     */
    public void deleteThirdMembLogin(Integer thr_id);

}
