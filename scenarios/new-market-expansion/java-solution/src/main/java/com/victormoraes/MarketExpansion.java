package com.victormoraes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;

class TreeNode<T extends Comparable<T>> {

    private T data;
    private TreeNode<T> parent;
    private List<TreeNode<T>> children;

    public TreeNode(T data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    // Time Complexity O(n2)
    // Space Complexity O(h)
    public boolean sameStructure(TreeNode<T> another) {

        if (!this.getData().equals(another.getData()))
            return false;
        if (!(this.getChildren().size() == another.getChildren().size()))
            return false;

        for (TreeNode<T> node : this.children) {
            List<TreeNode<T>> unmatched = another.getChildren();
            boolean found = false;

            for (TreeNode<T> anotherChild : unmatched) {

                if (node.sameStructure(anotherChild)) {
                    found = true;
                    unmatched.remove(anotherChild);
                    break;
                }
            }

            if (!found)
                return false;
        }

        return true;
    }

    // Time Complexity O(N log N)
    // Space Complexity O(1)
    public boolean sameStructureV2(TreeNode<T> another) {

        if (!this.getData().equals(another.getData()))
            return false;
        if (!(this.getChildren().size() == another.getChildren().size()))
            return false;

        this.children.sort(Comparator.comparing(TreeNode::getData));
        another.children.sort(Comparator.comparing(TreeNode::getData));

        for (int i = 0; i < this.children.size(); i++) {

            if (!this.children.get(i).sameStructure(another.getChildren().get(i)))
                return false;
        }

        return true;
    }

    public TreeNode<T> addChild(T data) {
        TreeNode<T> child = new TreeNode<>(data);
        child.parent = this;
        this.children.add(child);
        return child;
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

}

public class MarketExpansion {

    public static void main(String[] args) {
        System.out.println("Example 1 — Same structure, different child order → true");
        System.out.println(getDifferentChildOrderExampleResult(TreeNode::sameStructure)); // true

        System.out.println("Example 1 — Same structure V2, different child order → true");
        System.out.println(getDifferentChildOrderExampleResult(TreeNode::sameStructureV2)); // true

        System.out.println("Example 2 — Same structure, Partner API is missing a province → false");
        System.out.println(getMissingAProvinceExampleResult(TreeNode::sameStructure)); // true

        System.out.println("Example 2 — Same structure V2, Partner API is missing a province → false");
        System.out.println(getMissingAProvinceExampleResult(TreeNode::sameStructureV2));

        System.out.println("Example 3 — Same structure, A city name is wrong in the partner data → false");
        System.out.println(getWrongPartnerDataExampleResult(TreeNode::sameStructure)); // true

        System.out.println("Example 3 — Same structure V2, A city name is wrong in the partner data → false");
        System.out.println(getWrongPartnerDataExampleResult(TreeNode::sameStructureV2));

    }

    // Example 3 — A city name is wrong in the partner data → false
    private static Boolean getWrongPartnerDataExampleResult(
            BiFunction<TreeNode<String>, TreeNode<String>, Boolean> methodToCall) {

        // Tree A — internal system
        TreeNode<String> treeA = new TreeNode<>("Argentina");
        TreeNode<String> buenosAires = treeA.addChild("Buenos Aires");
        buenosAires.addChild("CABA");
        buenosAires.addChild("La Plata");
        TreeNode<String> cordoba = treeA.addChild("Córdoba");
        cordoba.addChild("Río Cuarto");
        cordoba.addChild("Villa Carlos Paz");
        TreeNode<String> mendoza = treeA.addChild("Mendoza");
        mendoza.addChild("Ciudad de Mendoza");

        // Tree B — typo in La Plata
        TreeNode<String> wrongTreeB = new TreeNode<>("Argentina");
        TreeNode<String> buenosAiresWrongB = wrongTreeB.addChild("Buenos Aires");
        buenosAiresWrongB.addChild("CABA");
        buenosAiresWrongB.addChild("La Plates"); // typo
        TreeNode<String> cordobaWrongB = wrongTreeB.addChild("Córdoba");
        cordobaWrongB.addChild("Río Cuarto");
        cordobaWrongB.addChild("Villa Carlos Paz");
        TreeNode<String> mendozaWrongB = wrongTreeB.addChild("Mendoza");
        mendozaWrongB.addChild("Ciudad de Mendoza");

        return methodToCall.apply(treeA, wrongTreeB);
    }

    private static Boolean getMissingAProvinceExampleResult(
            BiFunction<TreeNode<String>, TreeNode<String>, Boolean> methodToCall) {
        // Example 2 — Partner API is missing a province → false

        // Tree A — internal system
        TreeNode<String> treeA = new TreeNode<>("Argentina");
        TreeNode<String> buenosAires = treeA.addChild("Buenos Aires");
        buenosAires.addChild("CABA");
        buenosAires.addChild("La Plata");
        TreeNode<String> cordoba = treeA.addChild("Córdoba");
        cordoba.addChild("Río Cuarto");
        cordoba.addChild("Villa Carlos Paz");
        TreeNode<String> mendoza = treeA.addChild("Mendoza");
        mendoza.addChild("Ciudad de Mendoza");

        // Tree B — Mendoza missing
        TreeNode<String> treeMissingB = new TreeNode<>("Argentina");
        TreeNode<String> cordobaMissingB = treeMissingB.addChild("Córdoba");
        cordobaMissingB.addChild("Villa Carlos Paz");
        cordobaMissingB.addChild("Río Cuarto");
        TreeNode<String> buenosAiresMissingB = treeMissingB.addChild("Buenos Aires");
        buenosAiresMissingB.addChild("La Plata");
        buenosAiresMissingB.addChild("CABA");

        return methodToCall.apply(treeA, treeMissingB);
    }

    private static Boolean getDifferentChildOrderExampleResult(
            BiFunction<TreeNode<String>, TreeNode<String>, Boolean> methodToCall) {
        // Example 1 — Same structure, different child order → true
        // Tree A — internal system
        TreeNode<String> treeA = new TreeNode<>("Argentina");
        TreeNode<String> buenosAires = treeA.addChild("Buenos Aires");
        buenosAires.addChild("CABA");
        buenosAires.addChild("La Plata");
        TreeNode<String> cordoba = treeA.addChild("Córdoba");
        cordoba.addChild("Río Cuarto");
        cordoba.addChild("Villa Carlos Paz");
        TreeNode<String> mendoza = treeA.addChild("Mendoza");
        mendoza.addChild("Ciudad de Mendoza");

        // Tree B — partner API
        TreeNode<String> treeB = new TreeNode<>("Argentina");
        TreeNode<String> cordobaB = treeB.addChild("Córdoba");
        cordobaB.addChild("Villa Carlos Paz");
        cordobaB.addChild("Río Cuarto");
        TreeNode<String> mendozaB = treeB.addChild("Mendoza");
        mendozaB.addChild("Ciudad de Mendoza");
        TreeNode<String> buenosAiresB = treeB.addChild("Buenos Aires");
        buenosAiresB.addChild("La Plata");
        buenosAiresB.addChild("CABA");
        return methodToCall.apply(treeA, treeB);
    }
}
