package com.victormoraes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TreeNode<T> {

    private T data;
    private TreeNode<T> parent;
    private List<TreeNode<T>> children;

    public TreeNode(T data) {
        this.data = data;
        children = new ArrayList<>();
    }

    public boolean sameStructure(TreeNode<String> other) {

        return false;
    }

    public TreeNode<T> addChild(T data) {
        TreeNode<T> child = new TreeNode<T>(data);
        child.setParent(this);
        this.children.add(child);
        return child;
    }

    public boolean isLeaf() {
        return this.getChildren().isEmpty();
    }

    public boolean isRoot() {
        return Objects.isNull(this.getParent());
    }

    /**
     * Count parents to root
     * 
     * @param node
     */
    public int getDepth() {

        if (this.isRoot()) {
            return 0;
        } else {
            return 1 + this.getParent().getDepth();
        }
    }

    /**
     * Count the number of children
     * 
     * @param node
     */

    public int getDegree() {
        return this.getChildren().size();
    }

    public int getHeight() {

        if (this.isLeaf())
            return 0;

        int max = 0;

        for (TreeNode<T> treeNode : this.children) {
            max = Math.max(max, treeNode.getHeight());
        }

        return 1 + max;
    }

    public T getData() {
        return data;
    }

    public TreeNode<T> getParent() {
        return parent;
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public void setParent(TreeNode<T> parent) {
        this.parent = parent;
    }
}
