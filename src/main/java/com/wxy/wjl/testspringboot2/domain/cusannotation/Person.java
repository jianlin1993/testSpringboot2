package com.wxy.wjl.testspringboot2.domain.cusannotation;

import com.wxy.wjl.testspringboot2.cusannotation.IdInformation;
import lombok.Data;

@Data
public class Person {
    @IdInformation(name="胖墩儿",bodyWeight = 85)
    private String idInfo;
}
