package com.victormoraes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TreeNodeTest {

    private TreeNode<String> root;

    @BeforeEach
    void setUp() {
        root = new TreeNode<>("root");
    }

    @Nested
    class AddChild {
        @Test
        void addsChildToParent() {
            TreeNode<String> child = root.addChild("child");
            assertTrue(root.getChildren().contains(child));
        }

        @Test
        void setsParentOnChild() {
            TreeNode<String> child = root.addChild("child");
            assertEquals(root, child.getParent());
        }
    }

    @Nested
    class IsLeaf {
        @Test
        void rootWithNoChildrenIsLeaf() {
            assertTrue(root.isLeaf());
        }

        @Test
        void nodeWithChildrenIsNotLeaf() {
            root.addChild("child");
            assertFalse(root.isLeaf());
        }

        @Test
        void childNodeIsLeaf() {
            TreeNode<String> child = root.addChild("child");
            assertTrue(child.isLeaf());
        }
    }

    @Nested
    class IsRoot {
        @Test
        void nodeWithNoParentIsRoot() {
            assertTrue(root.isRoot());
        }

        @Test
        void childNodeIsNotRoot() {
            TreeNode<String> child = root.addChild("child");
            assertFalse(child.isRoot());
        }
    }

    @Nested
    class GetDepth {
        @Test
        void rootHasDepthZero() {
            assertEquals(0, root.getDepth());
        }

        @Test
        void firstLevelChildHasDepthOne() {
            TreeNode<String> child = root.addChild("child");
            assertEquals(1, child.getDepth());
        }

        @Test
        void deepNodeHasCorrectDepth() {
            TreeNode<String> level1 = root.addChild("level1");
            TreeNode<String> level2 = level1.addChild("level2");
            TreeNode<String> level3 = level2.addChild("level3");
            assertEquals(3, level3.getDepth());
        }
    }

    @Nested
    class GetHeight {
        @Test
        void leafNodeHasHeightZero() {
            assertEquals(0, root.getHeight());
        }

        @Test
        void nodeWithOneChildHasHeightOne() {
            root.addChild("child");
            assertEquals(1, root.getHeight());
        }

        @Test
        void heightReflectsDeepestBranch() {
            TreeNode<String> level1 = root.addChild("level1");
            level1.addChild("level2a");
            TreeNode<String> level2b = level1.addChild("level2b");
            level2b.addChild("level3");
            assertEquals(3, root.getHeight());
        }

        @Test
        void heightOfSubtreeIgnoresOtherBranches() {
            TreeNode<String> left = root.addChild("left");
            root.addChild("right");
            left.addChild("leftChild");
            assertEquals(1, left.getHeight());
        }
    }

    @Nested
    class SameStructure {
        // sameStructure(TreeNode<T> other) should return true if both trees
        // have identical structure and data, ignoring child order

        @Test
        void twoSingleNodesWithSameDataAreEqual() {
            TreeNode<String> other = new TreeNode<>("root");
            assertTrue(root.sameStructure(other));
        }

        @Test
        void twoSingleNodesWithDifferentDataAreNotEqual() {
            TreeNode<String> other = new TreeNode<>("different");
            assertFalse(root.sameStructure(other));
        }

        @Test
        void nodeAndNullAreNotEqual() {
            assertFalse(root.sameStructure(null));
        }

        @Test
        void sameChildrenSameOrderAreEqual() {
            root.addChild("A");
            root.addChild("B");
            
            TreeNode<String> other = new TreeNode<>("root");
            other.addChild("A");
            other.addChild("B");
            
            assertTrue(root.sameStructure(other));
        }

        @Test
        void sameChildrenDifferentOrderAreEqual() {
            root.addChild("A");
            root.addChild("B");
            
            TreeNode<String> other = new TreeNode<>("root");
            other.addChild("B");
            other.addChild("A");
            
            assertTrue(root.sameStructure(other));
        }

        @Test
        void differentChildCountAreNotEqual() {
            root.addChild("A");
            root.addChild("B");
            
            TreeNode<String> other = new TreeNode<>("root");
            other.addChild("A");
            
            assertFalse(root.sameStructure(other));
        }

        @Test
        void sameCountButDifferentChildDataAreNotEqual() {
            root.addChild("A");
            root.addChild("B");
            
            TreeNode<String> other = new TreeNode<>("root");
            other.addChild("A");
            other.addChild("C");
            
            assertFalse(root.sameStructure(other));
        }

        @Test
        void deepTreesSameStructureSameOrderAreEqual() {
            // root
            //   ├── PR
            //   │   ├── Curitiba
            //   │   └── Londrina
            //   └── SP
            TreeNode<String> pr = root.addChild("PR");
            pr.addChild("Curitiba");
            pr.addChild("Londrina");
            root.addChild("SP");
            
            TreeNode<String> other = new TreeNode<>("root");
            TreeNode<String> otherPr = other.addChild("PR");
            otherPr.addChild("Curitiba");
            otherPr.addChild("Londrina");
            other.addChild("SP");
            
            assertTrue(root.sameStructure(other));
        }

        @Test
        void deepTreesSameStructureDifferentOrderAreEqual() {
            // root                    other
            //   ├── PR                  ├── SP
            //   │   ├── Curitiba        └── PR
            //   │   └── Londrina            ├── Londrina
            //   └── SP                      └── Curitiba
            TreeNode<String> pr = root.addChild("PR");
            pr.addChild("Curitiba");
            pr.addChild("Londrina");
            root.addChild("SP");
            
            TreeNode<String> other = new TreeNode<>("root");
            other.addChild("SP");
            TreeNode<String> otherPr = other.addChild("PR");
            otherPr.addChild("Londrina");
            otherPr.addChild("Curitiba");
            
            assertTrue(root.sameStructure(other));
        }

        @Test
        void deepTreesDifferentStructureAreNotEqual() {
            // root                    other
            //   ├── PR                  ├── PR
            //   │   └── Curitiba        │   └── Londrina
            //   └── SP                  └── SP
            TreeNode<String> pr = root.addChild("PR");
            pr.addChild("Curitiba");
            root.addChild("SP");
            
            TreeNode<String> other = new TreeNode<>("root");
            TreeNode<String> otherPr = other.addChild("PR");
            otherPr.addChild("Londrina");
            other.addChild("SP");
            
            assertFalse(root.sameStructure(other));
        }

        @Test
        void duplicateChildrenInBothTreesAreEqual() {
            // Both trees have duplicate "A" children
            root.addChild("A");
            root.addChild("A");
            root.addChild("B");
            
            TreeNode<String> other = new TreeNode<>("root");
            other.addChild("A");
            other.addChild("B");
            other.addChild("A");
            
            assertTrue(root.sameStructure(other));
        }

        @Test
        void duplicateChildrenDifferentCountAreNotEqual() {
            // root has 2 A's, other has 3 A's
            root.addChild("A");
            root.addChild("A");
            
            TreeNode<String> other = new TreeNode<>("root");
            other.addChild("A");
            other.addChild("A");
            other.addChild("A");
            
            assertFalse(root.sameStructure(other));
        }

        @Test
        void oneLeafOneNonLeafWithSameDataAreNotEqual() {
            // root is leaf, other.root has children
            TreeNode<String> other = new TreeNode<>("root");
            other.addChild("A");
            
            assertFalse(root.sameStructure(other));
        }

        @Test
        void realWorldScenarioBrazilHierarchy() {
            // Source A:              Source B:
            // Brazil                 Brazil
            //   ├── PR                 ├── RJ
            //   │   ├── Londrina       │   ├── Niterói
            //   │   └── Curitiba       │   └── Rio
            //   ├── SP                 ├── PR
            //   │   ├── São Paulo      │   ├── Curitiba
            //   │   └── Campinas       │   └── Londrina
            //   └── RJ                 └── SP
            //       ├── Rio                ├── Campinas
            //       └── Niterói            └── São Paulo
            
            TreeNode<String> pr = root.addChild("PR");
            pr.addChild("Londrina");
            pr.addChild("Curitiba");
            TreeNode<String> sp = root.addChild("SP");
            sp.addChild("São Paulo");
            sp.addChild("Campinas");
            TreeNode<String> rj = root.addChild("RJ");
            rj.addChild("Rio");
            rj.addChild("Niterói");
            
            TreeNode<String> other = new TreeNode<>("root");
            TreeNode<String> otherRj = other.addChild("RJ");
            otherRj.addChild("Niterói");
            otherRj.addChild("Rio");
            TreeNode<String> otherPr = other.addChild("PR");
            otherPr.addChild("Curitiba");
            otherPr.addChild("Londrina");
            TreeNode<String> otherSp = other.addChild("SP");
            otherSp.addChild("Campinas");
            otherSp.addChild("São Paulo");
            
            assertTrue(root.sameStructure(other));
        }

       @Test
        void realWorldScenarioBrazilHierarchyV2() {
            // Source A:              Source B:
            // Brazil                 Brazil
            //   ├── PR                 ├── RJ
            //   │   ├── Londrina       │   ├── Niterói
            //   │   └── Curitiba       │   └── Rio
            //   ├── SP                 └── PR
            //   │   ├── São Paulo          ├── Curitiba
            //   │   └── Campinas           └── Londrina
            //   └── RJ                 
            //       ├── Rio            
            //       └── Niterói        
            
            TreeNode<String> pr = root.addChild("PR");
            pr.addChild("Londrina");
            pr.addChild("Curitiba");
            TreeNode<String> sp = root.addChild("SP");
            sp.addChild("São Paulo");
            sp.addChild("Campinas");
            TreeNode<String> rj = root.addChild("RJ");
            rj.addChild("Rio");
            rj.addChild("Niterói");
            
            TreeNode<String> other = new TreeNode<>("root");
            TreeNode<String> otherRj = other.addChild("RJ");
            otherRj.addChild("Niterói");
            otherRj.addChild("Rio");
            TreeNode<String> otherPr = other.addChild("PR");
            otherPr.addChild("Curitiba");
            otherPr.addChild("Londrina");
            
            assertTrue(root.sameStructure(other));
        }

        @Test
        void threeChildrenAllDifferentCombinations() {
            // Tests that matching algorithm doesn't have false positives
            // when all children have different data
            root.addChild("X");
            root.addChild("Y");
            root.addChild("Z");
            
            TreeNode<String> other = new TreeNode<>("root");
            other.addChild("Z");
            other.addChild("X");
            other.addChild("Y");
            
            assertTrue(root.sameStructure(other));
        }

        @Test
        void emptyTreesAreEqual() {
            TreeNode<String> tree1 = new TreeNode<>("A");
            TreeNode<String> tree2 = new TreeNode<>("A");
            assertTrue(tree1.sameStructure(tree2));
        }

        @Test
        void asymmetricTreesAreNotEqual() {
            // root                    other
            //   ├── A                   └── A
            //   │   └── A1                  ├── A1
            //   └── B                       └── A2
            TreeNode<String> a = root.addChild("A");
            a.addChild("A1");
            root.addChild("B");
            
            TreeNode<String> other = new TreeNode<>("root");
            TreeNode<String> otherA = other.addChild("A");
            otherA.addChild("A1");
            otherA.addChild("A2");
            
            assertFalse(root.sameStructure(other));
        }

        @Test
        void sameStructureCalledOnSubtree() {
            // Test that sameStructure works when called on non-root nodes
            TreeNode<String> pr = root.addChild("PR");
            pr.addChild("Curitiba");
            pr.addChild("Londrina");
            
            TreeNode<String> other = new TreeNode<>("root");
            TreeNode<String> otherPr = other.addChild("PR");
            otherPr.addChild("Londrina");
            otherPr.addChild("Curitiba");
            
            // Compare just the PR subtrees
            assertTrue(pr.sameStructure(otherPr));
        }

        @Test
        void veryDeepLinearChains() {
            // root → A → B → C → D → E
            TreeNode<String> a = root.addChild("A");
            TreeNode<String> b = a.addChild("B");
            TreeNode<String> c = b.addChild("C");
            TreeNode<String> d = c.addChild("D");
            d.addChild("E");
            
            TreeNode<String> other = new TreeNode<>("root");
            TreeNode<String> otherA = other.addChild("A");
            TreeNode<String> otherB = otherA.addChild("B");
            TreeNode<String> otherC = otherB.addChild("C");
            TreeNode<String> otherD = otherC.addChild("D");
            otherD.addChild("E");
            
            assertTrue(root.sameStructure(other));
        }

        @Test
        void oneExtraLeafMakesThemDifferent() {
            // root → A → B
            // other → A → B → C
            TreeNode<String> a = root.addChild("A");
            a.addChild("B");
            
            TreeNode<String> other = new TreeNode<>("root");
            TreeNode<String> otherA = other.addChild("A");
            TreeNode<String> otherB = otherA.addChild("B");
            otherB.addChild("C");
            
            assertFalse(root.sameStructure(other));
        }
    }

    @Nested
    class GetDegree {
        @Test
        void leafNodeHasDegreeZero() {
            assertEquals(0, root.getDegree());
        }

        @Test
        void nodeWithOneChildHasDegreeOne() {
            root.addChild("child");
            assertEquals(1, root.getDegree());
        }

        @Test
        void nodeWithMultipleChildrenHasCorrectDegree() {
            root.addChild("child1");
            root.addChild("child2");
            root.addChild("child3");
            assertEquals(3, root.getDegree());
        }
    }
}
