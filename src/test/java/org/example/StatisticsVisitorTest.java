package org.example;

import org.example.elements.DirectoryNode;
import org.example.elements.FileNode;
import org.example.visitors.StatisticsVisitor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsVisitorTest {

    @Test
    void testStatisticsForNestedDirectories() {
        DirectoryNode root = new DirectoryNode("Root");

        DirectoryNode documents = new DirectoryNode("Documents");
        DirectoryNode projects = new DirectoryNode("Projects");
        DirectoryNode java = new DirectoryNode("Java");

        root.addChild(documents);
        root.addChild(projects);
        projects.addChild(java);

        documents.addChild(new FileNode("Notes.txt", 100));
        java.addChild(new FileNode("Main.java", 200));

        StatisticsVisitor visitor = new StatisticsVisitor();

        visitor.calculate(root);

        assertEquals(2, visitor.getFileCount());
        assertEquals(4, visitor.getDirectoryCount());
        assertEquals(3, visitor.getMaxDepth());
    }

    @Test
    void testStatisticsForEmptyRoot() {
        DirectoryNode root = new DirectoryNode("Root");

        StatisticsVisitor visitor = new StatisticsVisitor();

        visitor.calculate(root);

        assertEquals(0, visitor.getFileCount());
        assertEquals(1, visitor.getDirectoryCount());
        assertEquals(0, visitor.getMaxDepth());
    }

    @Test
    void testRepeatedExecutionResetsStatistics() {
        DirectoryNode first = new DirectoryNode("First");
        first.addChild(new FileNode("A.txt", 100));

        DirectoryNode second = new DirectoryNode("Second");
        second.addChild(new FileNode("B.txt", 200));
        second.addChild(new FileNode("C.txt", 300));

        StatisticsVisitor visitor = new StatisticsVisitor();

        visitor.calculate(first);
        assertEquals(1, visitor.getFileCount());

        visitor.calculate(second);
        assertEquals(2, visitor.getFileCount());
        assertEquals(1, visitor.getDirectoryCount());
    }
}
