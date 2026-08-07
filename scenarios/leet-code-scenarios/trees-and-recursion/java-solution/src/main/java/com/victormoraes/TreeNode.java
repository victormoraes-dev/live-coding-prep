package com.victormoraes;

import java.util.ArrayList;
import java.util.LinkedList;
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

    public boolean sameStructure(TreeNode<T> other) {

        if (!this.data.equals(other.data))
            return false;

        if (this.getChildren().size() != other.getChildren().size())
            return false;

        // Iterate over source A

        // Compare each node in A with all children in B in a recursive way
        for(TreeNode<T> node: this.getChildren()){

            List<TreeNode<T>> unmatched = new LinkedList<>(other.getChildren());
            boolean found = false;

            for(TreeNode<T> otherNode: unmatched){

                if(node.sameStructure(otherNode)){
                    found = true;
                    unmatched.remove(otherNode);
                    break;
                }
            }

            if(!found){
                return false;
            }
        }

        return true;
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
