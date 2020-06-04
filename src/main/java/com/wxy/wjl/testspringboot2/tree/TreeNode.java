package com.wxy.wjl.testspringboot2.tree;

import lombok.Data;

@Data
public class TreeNode<T> {

    T data;

    TreeNode left;

    TreeNode right;

}
