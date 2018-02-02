package cn.hotol.wechat.service.testapi;

import cn.hotol.wechat.domain.model.testapi.TestAPI;
import cn.hotol.wechat.toolutil.modelutil.Result;

import java.util.List;

/**
 * Created by Lost on ⊙﹏⊙2014/6/24 ⊙︿⊙13:40.
 */
public interface TestAPIService {

    String addAPI(TestAPI testAPI, String path);

    void delAPI(String cid, String path);

    Result<List<TestAPI>> readAPI(String path);

    String editAPI(TestAPI testAPI, String path);
}
