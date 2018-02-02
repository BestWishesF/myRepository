package cn.hotol.app.service.test;

import cn.hotol.app.base.RetInfo;

/**
 * Created by LuBin
 * Date 2017-01-17.
 */
public interface TestService {

    public RetInfo findUser();

    public RetInfo findAgent();

    public RetInfo findTest();

    public RetInfo clearMemberCache();

}
