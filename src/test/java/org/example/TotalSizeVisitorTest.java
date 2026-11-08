package org.example;

import org.example.elements.DirectoryNode;
import org.example.elements.FileNode;
import org.example.visitors.TotalSizeVisitor;
import org.example.visitors.Visitor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TotalSizeVisitorTest {

    @Test
    void testFileAndDirectoryDispatch() {
        FileNode file = new FileNode("test.txt", 100);
        DirectoryNode directory = new DirectoryNode("Documents");

        boolean[] fileVisited = {false};
        boolean[] directoryVisited = {false};

        Visitor visitor = new Visitor() {
            @Override
            public void visit(FileNode file) {
                fileVisited[0] = true;
            }

            @Override
            public void visit(DirectoryNode directory) {
                directoryVisited[0] = true;
            }
        };

        file.accept(visitor);
        directory.accept(visitor);

        assertTrue(fileVisited[0]);
        assertTrue(directoryVisited[0]);
    }

    @Test
    void testTotalSizeAcrossNestedDirectories() {
        DirectoryNode root = new DirectoryNode("Root");
        DirectoryNode documents = new DirectoryNode("Documents");
        DirectoryNode projects = new DirectoryNode("Projects");

        root.addChild(new FileNode("readme.txt", 100));
        documents.addChild(new FileNode("notes.txt", 200));
        projects.addChild(new FileNode("main.java", 300));

        root.addChild(documents);
        root.addChild(projects);

        TotalSizeVisitor visitor = new TotalSizeVisitor();

        assertEquals(600, visitor.calculate(root));
    }

    @Test
    void testEmptyDirectory() {
        DirectoryNode root = new DirectoryNode("Root");

        TotalSizeVisitor visitor = new TotalSizeVisitor();

        assertEquals(0, visitor.calculate(root));
    }

    @Test
    void testRepeatedExecutionDoesNotAccumulateResults() {
        DirectoryNode first = new DirectoryNode("First");
        first.addChild(new FileNode("file1.txt", 500));

        DirectoryNode second = new DirectoryNode("Second");
        second.addChild(new FileNode("file2.txt", 200));

        TotalSizeVisitor visitor = new TotalSizeVisitor();

        assertEquals(500, visitor.calculate(first));
        assertEquals(200, visitor.calculate(second));
    }
}
