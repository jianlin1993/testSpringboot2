package com.wxy.wjl.testspringboot2.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestBlobDO implements Serializable {
    private static final long serialVersionUID = 6236436211510552555L;

    private Integer id;

    private byte[] bytes;

}
