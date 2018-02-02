package cn.hotol.app.controller.three.gridchange;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.gridchange.TdHtGridChangeDto;
import cn.hotol.app.service.three.gridchange.TdHtGridChangeService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by LuBin
 * Date 2017-01-05.
 */
@Controller
public class TdHtGridChangeController {

    @Resource
    private TdHtGridChangeService tdHtGridChangeService;

    /**
     * @param tdHtGridChangeDto
     * @return
     * @Purpose 更新代理人的坐标
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/uploadCoordinates", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo uploadCoordinates(@Valid @RequestBody TdHtGridChangeDto tdHtGridChangeDto, BindingResult result,
                                     @RequestHeader(value = "token", required = true) String token) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            return tdHtGridChangeService.uploadCoordinates(tdHtGridChangeDto, token);
        }
        return retInfo;
    }

}
