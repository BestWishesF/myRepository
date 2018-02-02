package cn.hotol.wechat.controller.testapi;

import cn.hotol.wechat.SessionSchema;
import cn.hotol.wechat.domain.model.login.User;
import cn.hotol.wechat.domain.model.testapi.TestAPI;
import cn.hotol.wechat.service.testapi.TestAPIService;
import cn.hotol.wechat.toolutil.modelutil.Result;
import cn.hotol.wechat.toolutil.modelutil.StorageSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Lost on ⊙﹏⊙2014/6/26 ⊙︿⊙14:04.
 */

@Controller
@RequestMapping(headers = "Accept=application/json", produces = "application/json;charset=UTF-8")
public class TestAPIController {

    @Autowired
    private TestAPIService testAPIService;

    @Autowired
    private StorageSupport storageSupport;


    @RequestMapping(value = "/test/api", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<TestAPI>> senderView(){
        return testAPIService.readAPI(storageSupport.getApiPath());
    }

    @RequestMapping(value = "/test/delAPI/{toDeleteId}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result deleteAPI(@PathVariable String toDeleteId) {

        testAPIService.delAPI(toDeleteId, storageSupport.getApiPath());

        return Result.success();
    }

    @RequestMapping(value = "/test/addAPI", method = RequestMethod.DELETE)
    @ResponseBody
    public Result<String> addAPI(@RequestBody TestAPI testAPI) {

        return Result.success(testAPIService.addAPI(testAPI,storageSupport.getApiPath()));
    }

    @RequestMapping(value = "/test/editAPI", method = RequestMethod.DELETE)
    @ResponseBody
    public Result<String> editAPI(@RequestBody TestAPI testAPI) {

        return Result.success(testAPIService.editAPI(testAPI,storageSupport.getApiPath()));
    }

    @RequestMapping(value = "/test/checkLogin", method = RequestMethod.GET)
    @ResponseBody
    public Result<User> checkLogin(HttpSession session) {

        User user = (User) session.getAttribute(SessionSchema.LOGIN_USER);

        return Result.success(user);
    }
}
