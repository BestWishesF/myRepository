package cn.hotol.wechat.service.testapi;

import cn.hotol.wechat.domain.model.testapi.TestAPI;
import cn.hotol.wechat.repository.testapi.TestAPIRepository;
import cn.hotol.wechat.toolutil.modelutil.Result;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Lost on ⊙﹏⊙2014/6/24 ⊙︿⊙13:41.
 */
@Service
public class TestAPIServiceImpl implements TestAPIService {

    private TestAPIRepository testAPIRepository = new TestAPIRepository();

    @Override
    public String addAPI(TestAPI testAPI, String path) {

        return testAPIRepository.addAPI(testAPI,path);
    }

    @Override
    public void delAPI(String cid,String path) {

        testAPIRepository.delAPI(cid,path);
    }

    @Override
    public Result<List<TestAPI>> readAPI(String path) {

        return testAPIRepository.readAPI(path);
    }

    @Override
    public String editAPI(TestAPI testAPI, String path) {
          return testAPIRepository.editAPI(testAPI,path);
    }
}
