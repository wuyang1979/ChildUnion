package com.qinzi123;

import com.qinzi123.dao.CardDao;
import com.qinzi123.service.impl.BusinessWeixinServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @title: BusinessWeixinServiceTest
 * @projectName: trunk
 * @description: 亲子企服
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BusinessWeixinServiceTest {

    @Autowired
    CardDao weixinDao;

    @Autowired
    private BusinessWeixinServiceImpl businessWeixinService;

    //@Ignore
    @Test
    public void testSetUser() {
/*		Map map = weixinDao.getCardInfoById("479");
		map.put("code", "000");
		map.put("tag", "1101");
		int result = businessWeixinService.setUser(map);
		Assert.assertTrue(result > 0);*/
    }
}
