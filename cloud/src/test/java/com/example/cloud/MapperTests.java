package com.example.cloud;


import com.example.cloud.dao.TracksMapper;
import com.example.cloud.entity.TracksInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CloudApplication.class)
public class MapperTests {

    @Autowired
    TracksMapper tracksMapper;

    @Test
    public void tracksTest(){
//        TracksInfo[] tracksInfo = tracksMapper.selectThreeSub(new String[] {"zydzyd", "c4e943626e517af3", "d849cea147018705", "8dd4e161617431f7"}, new BigInteger("1648742400"));
//        TracksInfo[] tracksInfo = tracksMapper.selectOneMain(new String[] {"zydzyd", "e26b1b2a0c829127"});
//        for(TracksInfo tracksInfo0 : tracksInfo)
//            System.out.println(tracksInfo0.toString());
//        FunctionSeek functionSeek = new FunctionSeek();
//        functionSeek.FunSearchId("zydzyd");
    }
}
