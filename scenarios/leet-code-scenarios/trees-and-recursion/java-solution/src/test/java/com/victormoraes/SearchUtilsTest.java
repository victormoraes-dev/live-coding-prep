package com.victormoraes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class SearchUtilsTest {

    private SearchUtils<String> search;
    private TreeNode<String> root;

    @BeforeEach
    void setUp() {
        search = new SearchUtils<String>();
        root = new TreeNode<>("search");
    }

    @Nested
    class DfsPreOrder {
        // dfsPreOrder() should return a List<T> with nodes in visited order
        // Pattern: visit current node BEFORE recursing into children

        @Test
        void singleNodeReturnsItself() {
            assertEquals(List.of("search"), search.dfsPreOrder(root));
        }

        @Test
        void visitesSearchBeforeChildren() {

            root.addChild("A");
            root.addChild("B");
            assertEquals(List.of("search", "A", "B"), search.dfsPreOrder(root));
        }

        @Test
        void exhaustsFirstBranchBeforeNextBranch() {
            // search
            // ├── A
            // │ └── A1
            // └── B
            TreeNode<String> a = root.addChild("A");
            a.addChild("A1");
            root.addChild("B");
            assertEquals(List.of("search", "A", "A1", "B"), search.dfsPreOrder(root));
        }

        @Test
        void deepTreeVisitsAllNodesInOrder() {
            // search → PR → Curitiba → Londrina → SP → São Paulo → RJ
            TreeNode<String> pr = root.addChild("PR");
            pr.addChild("Curitiba");
            pr.addChild("Londrina");
            root.addChild("SP").addChild("São Paulo");
            root.addChild("RJ");
            assertEquals(
                    List.of("search", "PR", "Curitiba", "Londrina", "SP", "São Paulo", "RJ"),
                    search.dfsPreOrder(root));
        }

        @Test
        void worksFromNonSearchNode() {
            TreeNode<String> pr = root.addChild("PR");
            pr.addChild("Curitiba");
            pr.addChild("Londrina");
            root.addChild("SP");
            // calling from PR should only traverse PR's subtree
            assertEquals(List.of("PR", "Curitiba", "Londrina"), search.dfsPreOrder(pr));
        }
    }

    @Nested
    class DfsPostOrder {
        // dfsPostOrder() should return a List<T> with nodes in visited order
        // Pattern: recurse into children BEFORE visiting current node

        @Test
        void singleNodeReturnsItself() {
            assertEquals(List.of("search"), search.dfsPostOrder(root));
        }

        @Test
        void visitsChildrenBeforeSearch() {
            root.addChild("A");
            root.addChild("B");
            assertEquals(List.of("A", "B", "search"), search.dfsPostOrder(root));
        }

        @Test
        void exhaustsEntireBranchBeforeParent() {
            // search
            // ├── A
            // │ └── A1
            // └── B
            TreeNode<String> a = root.addChild("A");
            a.addChild("A1");
            root.addChild("B");
            assertEquals(List.of("A1", "A", "B", "search"), search.dfsPostOrder(root));
        }

        @Test
        void deepTreeVisitsAllNodesInOrder() {
            // Curitiba → Londrina → PR → São Paulo → SP → RJ → search
            TreeNode<String> pr = root.addChild("PR");
            pr.addChild("Curitiba");
            pr.addChild("Londrina");
            root.addChild("SP").addChild("São Paulo");
            root.addChild("RJ");
            assertEquals(
                    List.of("Curitiba", "Londrina", "PR", "São Paulo", "SP", "RJ", "search"),
                    search.dfsPostOrder(root));
        }

        @Test
        void worksFromNonSearchNode() {
            TreeNode<String> pr = root.addChild("PR");
            pr.addChild("Curitiba");
            pr.addChild("Londrina");
            root.addChild("SP");
            assertEquals(List.of("Curitiba", "Londrina", "PR"), search.dfsPostOrder(pr));
        }
    }

    @Nested
    class Bfs {
        // bfs() should return a List<T> with nodes in level-by-level order
        // Data structure: Queue (FIFO) — processes nodes in the order they were
        // discovered

        @Test
        void singleNodeReturnsItself() {
            assertEquals(List.of("search"), search.bfs(root));
        }

        @Test
        void visitsAllChildrenBeforeGrandchildren() {
            root.addChild("A");
            root.addChild("B");
            root.addChild("C");
            assertEquals(List.of("search", "A", "B", "C"), search.bfs(root));
        }

        @Test
        void visitsLevel1BeforeLevel2() {
            // search
            // ├── A
            // │ └── A1
            // └── B
            // └── B1
            TreeNode<String> a = root.addChild("A");
            a.addChild("A1");
            TreeNode<String> b = root.addChild("B");
            b.addChild("B1");
            assertEquals(List.of("search", "A", "B", "A1", "B1"), search.bfs(root));
        }

        @Test
        void deepTreeVisitsAllLevelsInOrder() {
            // level 0: search
            // level 1: PR, SP, RJ
            // level 2: Curitiba, Londrina, São Paulo
            TreeNode<String> pr = root.addChild("PR");
            pr.addChild("Curitiba");
            pr.addChild("Londrina");
            root.addChild("SP").addChild("São Paulo");
            root.addChild("RJ");
            assertEquals(
                    List.of("search", "PR", "SP", "RJ", "Curitiba", "Londrina", "São Paulo"),
                    search.bfs(root));
        }

        @Test
        void worksFromNonSearchNode() {
            TreeNode<String> pr = root.addChild("PR");
            pr.addChild("Curitiba");
            pr.addChild("Londrina");
            root.addChild("SP");
            // calling from PR — search and SP are invisible
            assertEquals(List.of("PR", "Curitiba", "Londrina"), search.bfs(pr));
        }
    }
}
