package com.wxy.wjl.testspringboot2.cloud.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class RestTempleteRspBO implements Serializable {

    private static final long serialVersionUID = -1966390513232311353L;

    private String name;

    private Integer levle;
}
