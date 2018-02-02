package cn.hotol.app.controller.three.channel;

import cn.hotol.app.service.three.channel.ChannelService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Administrator on 2017-03-16.
 */

@Controller
public class ChannelController {

    @Resource
    private ChannelService channelService;

    /**
     * @param params
     * @return Map<String, String>
     * @Purpose 花喵渠道接口
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/channel/huamiao", method = {RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public Map<String, String> channelHuamiao(@RequestBody Map<String, String> params){
        return channelService.channelHuamiao(params);
    }

}
