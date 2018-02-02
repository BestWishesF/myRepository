package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by lizhun on 2016/11/30.
 */
@Repository
public interface TdHtMemberRepository {
    /**
     * @Purpose  查找用户数量
     * @version  3.0
     * @author   lizhun
     * @param
     * @return   int
     */
    public int findMemberSize(Map<String, Object> map);
    /**
     * @Purpose  分页查找用户
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   List<TdHtMemberDto>
     */
    public List<TdHtMemberDto> findMemberPage(Map<String, Object> map);
    /**
     * @Purpose  锁定用户或解锁用户
     * @version  3.0
     * @author   lizhun
     * @param    tdHtMemberDto
     * @return   void
     */
    public void updateMemberLock(TdHtMemberDto tdHtMemberDto);
    /**
     * @Purpose  搜索用户数量
     * @version  3.0
     * @author   lizhun
     * @param
     * @return   int
     */
    public int SearchMemberSize(Map<String, Object> map);
    /**
     * @Purpose  搜索查找用户
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   List<TdHtMemberDto>
     */
    public List<TdHtMemberDto> SearchMemberPage(Map<String, Object> map);

    /**
     * @Purpose  通过用户手机号查询用户
     * @version  3.0
     * @author   lubin
     * @param    memb_phone
     * @return   TdHtMemberDto
     */
    public TdHtMemberDto findMemberByMobile(String memb_phone);

    /**
     * @Purpose  修改用户角色
     * @version  3.0
     * @author   lubin
     * @param    tdHtMemberDto
     * @return
     */
    public void updateMemberRole(TdHtMemberDto tdHtMemberDto);

    /**
     * @Purpose  查询未锁定的用户数量
     * @version  3.0
     * @author   lubin
     * @return
     */
    public int findMembCount();

    /**
     * @Purpose  查询新增用户数量
     * @version  3.0
     * @author   lubin
     * @return
     */
    public int findNewMembCount();

    /**
     * @Purpose  查询一定时间内每天的新用户量
     * @version  3.0
     * @author   lubin
     * @return
     */
    public List<Map<String,Object>> statisticsMemberByCondition(Map<String,Object> map);

    /**
     * @Purpose  通过id查询用户信息
     * @version  3.0
     * @author   lubin
     * @param memb_id
     * @return TdHtMemberDto
     */
    public TdHtMemberDto findMemberById(int memb_id);

    /**
     * @Purpose  保存新的注册用户
     * @version  3.0
     * @author   lubin
     * @return
     */
    public void insertMember(TdHtMemberDto tdHtMemberDto);

    /**
     * @Purpose   查询一段时间注册的用户
     * @version   3.0
     * @author    lubin
     * @time      2017-04-12
     * @param     tdHtMemberDto  时间
     * @return
     */
    public List<TdHtMemberDto> findMembByRegTime(TdHtMemberDto tdHtMemberDto);

    /**
     * @Purpose   修改用户余额
     * @version   3.0
     * @author    lubin
     * @time      2017-04-28
     * @param     tdHtMemberDto
     * @return
     */
    public void updateMemberBalance(TdHtMemberDto tdHtMemberDto);
}
