package org.example;

import org.example.elements.DirectoryNode;
import org.example.elements.FileNode;
import org.example.visitors.SearchVisitor;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchVisitorTest {

    @Test
    void testSearchByFilenameAndMinimumSize() {
        DirectoryNode root = new DirectoryNode("Root");

        root.addChild(new FileNode("Report.pdf", 1000));
        root.addChild(new FileNode("Report.txt", 200));
        root.addChild(new FileNode("Notes.pdf", 1500));

        SearchVisitor visitor = new SearchVisitor(
                name -> name.startsWith("Report"),
                500
        );

        List<String> results = visitor.search(root);

        assertEquals(
                List.of("Report.pdf"),
                results
        );
    }

    @Test
    void testRootRelativePathsAndSorting() {
        DirectoryNode root = new DirectoryNode("Root");

        DirectoryNode documents = new DirectoryNode("Documents");
        DirectoryNode projects = new DirectoryNode("Projects");

        documents.addChild(new FileNode("Notes.txt", 100));
        projects.addChild(new FileNode("Main.java", 200));

        root.addChild(projects);
        root.addChild(documents);

        SearchVisitor visitor = new SearchVisitor(
                name -> true,
                0
        );

        List<String> results = visitor.search(root);

        assertEquals(
                List.of(
                        "Documents/Notes.txt",
                        "Projects/Main.java"
                ),
                results
        );
    }

    @Test
    void testSearchWithoutMatches() {
        DirectoryNode root = new DirectoryNode("Root");

        root.addChild(new FileNode("Notes.txt", 100));

        SearchVisitor visitor = new SearchVisitor(
                name -> name.endsWith(".pdf"),
                500
        );

        assertTrue(visitor.search(root).isEmpty());
    }

    @Test
    void testRepeatedSearchDoesNotDuplicateResults() {
        DirectoryNode root = new DirectoryNode("Root");

        root.addChild(new FileNode("Report.pdf", 1000));

        SearchVisitor visitor = new SearchVisitor(
                name -> name.endsWith(".pdf"),
                500
        );

        assertEquals(1, visitor.search(root).size());
        assertEquals(1, visitor.search(root).size());
    }
}
