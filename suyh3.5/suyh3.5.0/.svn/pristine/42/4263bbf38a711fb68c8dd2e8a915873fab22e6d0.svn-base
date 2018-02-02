package cn.hotol.app.controller.score.goods;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.goods.ScoreGoodsDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.service.funcion.FuncionService;
import cn.hotol.app.service.goods.GoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Administrator on 2017-02-25.
 */

@Controller
public class GoodsController {

    @Resource
    private GoodsService goodsService;
    @Resource
    private FuncionService funcionService;

    /**
     * @Purpose  按条件分页查询积分商品
     * @version  3.0
     * @author   lubin
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/score/goods/list")
    public ModelAndView list(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                             @RequestParam(value = "goods_state", required = true) Integer goods_state,
                             HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/score/goods/index");
        mv.addObject("retInfo", goodsService.findGoodsPage(currentPage ,10, goods_state));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

    /**
     * @Purpose 新增积分商品
     * @version  3.0
     * @author   lubin
     * @param scoreGoodsDto
     * @return RetInfo
     */
    @RequestMapping(value = "/admin/score/goods/insert")
    @ResponseBody
    public RetInfo insertScoreGoods(@Valid @ModelAttribute ScoreGoodsDto scoreGoodsDto, BindingResult result) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            retInfo = goodsService.insertScoreGoods(scoreGoodsDto);
        }
        return retInfo;
    }

    /**
     * @Purpose 修改积分商品
     * @version  3.0
     * @author   lubin
     * @param scoreGoodsDto
     * @return RetInfo
     */
    @RequestMapping(value = "/admin/score/goods/update")
    @ResponseBody
    public RetInfo updateScoreGoods(@Valid @ModelAttribute ScoreGoodsDto scoreGoodsDto, BindingResult result) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            retInfo = goodsService.updateScoreGoods(scoreGoodsDto);
        }
        return retInfo;
    }

    /**
     * @Purpose 上下架积分商品
     * @version  3.0
     * @author   lubin
     * @param scoreGoodsDto
     * @return RetInfo
     */
    @RequestMapping(value = "/admin/score/goods/update/state")
    @ResponseBody
    public RetInfo updateScoreGoodsState(@ModelAttribute ScoreGoodsDto scoreGoodsDto) {
        RetInfo retInfo = goodsService.updateScoreGoodsState(scoreGoodsDto);
        return retInfo;
    }

    /**
     * @Purpose  跳转添加积分商品页面
     * @version  3.0
     * @author   lubin
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/score/goods/jump/insert")
    public ModelAndView jumpInsert(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/score/goods/insert");
        mv.addObject("retInfo", goodsService.jump(0));
        return mv;
    }

    /**
     * @Purpose  跳转修改积分商品页面
     * @version  3.0
     * @author   lubin
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/score/goods/jump/update")
    public ModelAndView jumpUpdate(@RequestParam(value = "goods_id", required = true) Integer goods_id, HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/score/goods/update");
        mv.addObject("retInfo", goodsService.jump(goods_id));
        return mv;
    }

    /**
     * @Purpose  跳转搜索积分商品页面
     * @version  3.0
     * @author   lubin
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/score/goods/search")
    @ResponseBody
    public ModelAndView jumpSearch(@ModelAttribute ScoreGoodsDto scoreGoodsDto, HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/score/goods/search");
        scoreGoodsDto.setPage_size(10);
        mv.addObject("retInfo", goodsService.jumpSearch(scoreGoodsDto));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

}
