package cn.hotol.app.service.member;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lizhun on 2016/12/17.
 */
public interface MemberService {
    /**
     * @Purpose  用户分页
     * @version  3.0
     * @author   lizhun
     * @param    currentPage
     * @param    pageSize
     * @return   RetInfo
     */
    public RetInfo memberPage(int currentPage, int pageSize, HttpServletRequest request);
    /**
     * @Purpose  锁定用户或解锁用户
     * @version  3.0
     * @author   lizhun
     * @param    tdHtMemberDto
     * @return   RetInfo
     */
    public RetInfo lock(TdHtMemberDto tdHtMemberDto);
    /**
     * @Purpose  用户分页
     * @version  3.0
     * @author   lizhun
     * @param    currentPage
     * @param    pageSize
     * @return   RetInfo
     */
    public RetInfo memberSearchPage(TdHtMemberDto tdHtMemberDto,int currentPage, int pageSize, HttpServletRequest request);

    /**
     * @Purpose  修改用户角色
     * @version  3.0
     * @author   lubin
     * @param   tdHtMemberDto
     * @return   RetInfo
     */
    public RetInfo updateRole(TdHtMemberDto tdHtMemberDto);

    /**
     * @Purpose  赠送用户优惠券
     * @version  3.0
     * @author   lubin
     * @time     2017-04-13
     * @param
     * @return   RetInfo
     */
    public RetInfo giveMemberCoupon();

}
