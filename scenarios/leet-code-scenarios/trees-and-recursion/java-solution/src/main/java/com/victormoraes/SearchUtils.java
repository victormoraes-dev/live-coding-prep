package com.victormoraes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SearchUtils<T> {

    List<T> result = new ArrayList<>();

    public List<T> dfsPreOrder(TreeNode<T> node) {

        result.add(node.getData());

        for (TreeNode<T> childNode : node.getChildren()) {
            dfsPreOrder(childNode);
        }

        return result;
    }

    public List<T> dfsPostOrder(TreeNode<T> node) {

        for (TreeNode<T> childNode : node.getChildren()) {
            dfsPostOrder(childNode);
        }

        result.add(node.getData());
        return result;
    }

    public List<T> bfs(TreeNode<T> node) {

        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(node);

        while (!queue.isEmpty()) {
            TreeNode<T> current = queue.poll();
            result.add(current.getData());
            queue.addAll(current.getChildren());
        }

        return result;
    }
}
