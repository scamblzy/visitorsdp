package org.example;

import org.example.elements.DirectoryNode;
import org.example.elements.FileNode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreeIntegrityTest {

    @Test
    void testNegativeFileSizeIsRejected() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new FileNode("invalid.txt", -100)
        );
    }

    @Test
    void testDuplicateSiblingNamesAreRejected() {
        DirectoryNode root = new DirectoryNode("Root");

        root.addChild(new FileNode("Notes.txt", 100));

        assertThrows(
                IllegalArgumentException.class,
                () -> root.addChild(new FileNode("Notes.txt", 200))
        );
    }

    @Test
    void testMultipleParentOwnershipIsRejected() {
        DirectoryNode first = new DirectoryNode("First");
        DirectoryNode second = new DirectoryNode("Second");

        FileNode file = new FileNode("Notes.txt", 100);

        first.addChild(file);

        assertThrows(
                IllegalArgumentException.class,
                () -> second.addChild(file)
        );
    }

    @Test
    void testCircularReferencesAreRejected() {
        DirectoryNode root = new DirectoryNode("Root");
        DirectoryNode child = new DirectoryNode("Child");
        DirectoryNode nested = new DirectoryNode("Nested");

        root.addChild(child);
        child.addChild(nested);

        assertThrows(
                IllegalArgumentException.class,
                () -> nested.addChild(root)
        );
    }

    @Test
    void testValidNestedDirectoriesAreSupported() {
        DirectoryNode root = new DirectoryNode("Root");
        DirectoryNode child = new DirectoryNode("Child");
        DirectoryNode nested = new DirectoryNode("Nested");

        root.addChild(child);
        child.addChild(nested);

        assertEquals(root, child.getParent());
        assertEquals(child, nested.getParent());
    }
}
