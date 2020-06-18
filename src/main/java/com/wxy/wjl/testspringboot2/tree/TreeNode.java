package com.wxy.wjl.testspringboot2.tree;

import lombok.Data;

/**
 * 树节点
 * @param <T>
 */
@Data
public class TreeNode<T> {

    T data;

    TreeNode left;

    TreeNode right;

}
