package cn.hotol.app.service.comment;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.comment.TdHtCommentDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.CommonUtil;
import cn.hotol.app.repository.TdHtCommentRepository;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lizhun on 2016/12/24.
 */
public class CommentServiceImpl implements CommentService{
    private static Logger logger = Logger.getLogger(CommentServiceImpl.class);
    private TdHtCommentRepository tdHtCommentRepository;

    @Override
    public RetInfo commentPage(int currentPage, int pageSize, HttpServletRequest request) {
        String logInfo = this.getClass().getName() + ":commentPage:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        HttpSession session = request.getSession(false);
        try {
            TdHtAdminDto tdHtAdminDto = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);

            Map<String, Object> params = new HashMap<>();
            params.put("add_province", tdHtAdminDto.getProvince_id());
            params.put("add_city", tdHtAdminDto.getCity_id());
            params.put("add_region", tdHtAdminDto.getRegion_id());
            params.put("divide_id", tdHtAdminDto.getDivide_id());

            int totalRecord = tdHtCommentRepository.findCommentSize(params);//总条数
            if (totalRecord > 0) {
                Map<String, Object> map = CommonUtil.page(totalRecord ,currentPage ,pageSize);
                map.putAll(params);
                List<TdHtCommentDto> tdHtCommentDtos = tdHtCommentRepository.findCommentPage(map);
                map.put("currentPage", currentPage);
                map.put("comments", tdHtCommentDtos);
                retInfo.setMark("0");
                retInfo.setTip("成功");
                retInfo.setObj(map);
            } else {
                retInfo.setMark("1");
                retInfo.setTip("暂无您要查找的结果");
                params.put("totalPage", 0);
                params.put("totalRecord", 0);
                params.put("currentPage", 1);
                retInfo.setObj(params);
            }

        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    public void setTdHtCommentRepository(TdHtCommentRepository tdHtCommentRepository) {
        this.tdHtCommentRepository = tdHtCommentRepository;
    }
}
