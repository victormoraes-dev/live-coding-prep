package com.victormoraes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class TreeNode<T> {
    public T data;
    public List<TreeNode<T>> children;

    public TreeNode(T data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    public void addChild(TreeNode<T> child) {
        this.children.add(child);
    }
}

public class LocationService {

    /**
     * Compares two N-ary trees to check if they have the same structure
     * and data, ignoring the order of children.
     * 
     * @param a Root of the first tree
     * @param b Root of the second tree
     * @return true if structures are identical (ignoring order), false otherwise
     */
    public boolean sameStructure(TreeNode<String> a, TreeNode<String> b) {

        if (!a.data.equals(b.data))
            return false;
        if (a.children.size() != b.children.size())
            return false;

        List<TreeNode<String>> unmatched = new ArrayList<>(b.children);

        for (TreeNode<String> aChild : a.children) {

            boolean found = false;

            for (TreeNode<String> bChild : unmatched) {
                if (sameStructure(aChild, bChild)) {
                    found = true;
                    unmatched.remove(bChild);
                    break;
                }
            }

            if (!found)
                return false;
        }

        return true;
    }

    public boolean sameStructureSortingChildren(TreeNode<String> a, TreeNode<String> b) {
        if (!a.data.equals(b.data))
            return false;
        if (a.children.size() != b.children.size())
            return false;

        List<TreeNode<String>> childrenA = new ArrayList<>(a.children);
        List<TreeNode<String>> childrenB = new ArrayList<>(b.children);

        childrenA.sort(Comparator.comparing(node -> node.data));
        childrenB.sort(Comparator.comparing(node -> node.data));

        for (int i = 0; i < childrenA.size(); i++) {
            if (!sameStructureSortingChildren(childrenB.get(i), childrenB.get(i)))
                return false;
        }

        return true;
    }
}
