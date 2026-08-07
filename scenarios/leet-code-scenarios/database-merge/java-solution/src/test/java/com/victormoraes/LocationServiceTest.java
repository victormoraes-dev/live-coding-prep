package com.victormoraes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LocationServiceTest {

    private LocationService locationService = new LocationService();

    @Test
    void nodesWithDifferentDataShouldReturnFalse() {

        TreeNode<String> nodeA = new TreeNode<String>("Brazil");
        nodeA.addChild(new TreeNode<String>("PR"));

        TreeNode<String> nodeB = new TreeNode<String>("Brazil");
        nodeA.addChild(new TreeNode<String>("RJ"));

        Assertions.assertFalse(locationService.sameStructure(nodeA, nodeB));
    }

    @Test
    void nodesWithDifferentDataShouldReturnFalseV2() {

        TreeNode<String> nodeA = new TreeNode<String>("Brazil");
        nodeA.addChild(new TreeNode<String>("PR"));

        TreeNode<String> nodeB = new TreeNode<String>("Brazil");
        nodeA.addChild(new TreeNode<String>("RJ"));

        Assertions.assertFalse(locationService.sameStructureSortingChildren(nodeA, nodeB));
    }

    @Test
    void nodesWithDifferentChildrenSizeShouldReturnFalse() {

        TreeNode<String> nodeA = new TreeNode<String>("Brazil");
        nodeA.addChild(new TreeNode<String>("PR"));
        nodeA.addChild(new TreeNode<String>("SP"));

        TreeNode<String> nodeB = new TreeNode<String>("Brazil");
        nodeA.addChild(new TreeNode<String>("RJ"));

        Assertions.assertFalse(locationService.sameStructure(nodeA, nodeB));
    }

    @Test
    void nodesWithDifferentChildrenSizeShouldReturnFalseV2() {

        TreeNode<String> nodeA = new TreeNode<String>("Brazil");
        nodeA.addChild(new TreeNode<String>("PR"));
        nodeA.addChild(new TreeNode<String>("SP"));

        TreeNode<String> nodeB = new TreeNode<String>("Brazil");
        nodeA.addChild(new TreeNode<String>("RJ"));

        Assertions.assertFalse(locationService.sameStructureSortingChildren(nodeA, nodeB));
    }

    @Test
    void nodesWithDifferentOrderButSameStructureShouldReturnTrue() {

        TreeNode<String> nodeA = new TreeNode<String>("Brazil");
        TreeNode<String> nodePR = new TreeNode<String>("PR");
        nodePR.addChild(new TreeNode<String>("Curitiba"));
        nodePR.addChild(new TreeNode<String>("Londrina"));

        TreeNode<String> nodeSP = new TreeNode<String>("SP");
        nodeSP.addChild(new TreeNode<String>("São Paulo"));

        nodeA.addChild(nodePR);
        nodeA.addChild(nodeSP);

        TreeNode<String> nodeB = new TreeNode<String>("Brazil");
        TreeNode<String> nodeBSP = new TreeNode<String>("SP");
        nodeBSP.addChild(new TreeNode<String>("São Paulo"));

        TreeNode<String> nodeBPR = new TreeNode<String>("PR");
        nodeBPR.addChild(new TreeNode<String>("Londrina"));
        nodeBPR.addChild(new TreeNode<String>("Curitiba"));

        nodeB.addChild(nodeBSP);
        nodeB.addChild(nodeBPR);

        Assertions.assertTrue(locationService.sameStructure(nodeA, nodeB));
    }

    @Test
    void nodesWithDifferentOrderButSameStructureShouldReturnTrueV2() {

        TreeNode<String> nodeA = new TreeNode<String>("Brazil");
        TreeNode<String> nodePR = new TreeNode<String>("PR");
        nodePR.addChild(new TreeNode<String>("Curitiba"));
        nodePR.addChild(new TreeNode<String>("Londrina"));

        TreeNode<String> nodeSP = new TreeNode<String>("SP");
        nodeSP.addChild(new TreeNode<String>("São Paulo"));

        nodeA.addChild(nodePR);
        nodeA.addChild(nodeSP);

        TreeNode<String> nodeB = new TreeNode<String>("Brazil");
        TreeNode<String> nodeBSP = new TreeNode<String>("SP");
        nodeBSP.addChild(new TreeNode<String>("São Paulo"));

        TreeNode<String> nodeBPR = new TreeNode<String>("PR");
        nodeBPR.addChild(new TreeNode<String>("Londrina"));
        nodeBPR.addChild(new TreeNode<String>("Curitiba"));

        nodeB.addChild(nodeBSP);
        nodeB.addChild(nodeBPR);

        Assertions.assertTrue(locationService.sameStructureSortingChildren(nodeA, nodeB));
    }
}
