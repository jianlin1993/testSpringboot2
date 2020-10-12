package com.wxy.wjl.testspringboot2;

import com.wxy.wjl.testspringboot2.database.blob.TestBlobDO;
import com.wxy.wjl.testspringboot2.database.mapper.TestBlobMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Testspringboot2ApplicationTests {

    @Autowired
    private TestBlobMapper testBlobMapper;

    /**
     * 测试插入
     */
    @Test
    public void testInsertBlob(){
        TestBlobDO testBlobDO=new TestBlobDO();
        testBlobDO.setId(1);
        String blob="{\"deptNm\":\"1111\",\"firstNm\":\"lzk\",\"funNos\":\"00\",\"lastNm\":\"lzk\",\"mblNo\":\"254766664567\",\"operBeg\":\"000047\",\"operEmail\":\"MRTest0001@163.com\",\"operExpDt\":\"20300831\",\"operNo\":\"keT10241\",\"operOut\":\"235547\",\"operTyp\":\"2\",\"oprDept\":\"0001\",\"oprSts\":\"0\",\"prdGrpCd\":\"LOOP|MSHWARI|FULIZA\",\"valDt\":\"20200702\"}";
        testBlobDO.setBytes(blob.getBytes(StandardCharsets.UTF_8));
        System.out.println(testBlobMapper.add(testBlobDO));
    }

    /**
     * 测试查询
     */
    @Test
    public void testSelectBlob(){
        TestBlobDO testBlobDO=testBlobMapper.find(1);
        System.out.println(new String(testBlobDO.getBytes(),StandardCharsets.UTF_8));
    }
}

