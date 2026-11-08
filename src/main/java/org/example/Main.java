package org.example;

import org.example.elements.DirectoryNode;
import org.example.elements.FileNode;
import org.example.visitors.TotalSizeVisitor;
import org.example.visitors.SearchVisitor;
import org.example.visitors.StatisticsVisitor;

public class Main {

    public static void main(String[] args) {

        // Creating root directory
        DirectoryNode root = new DirectoryNode("Root");

        // Creating subdirectories
        DirectoryNode documents = new DirectoryNode("Documents");
        DirectoryNode images = new DirectoryNode("Images");
        DirectoryNode projects = new DirectoryNode("Projects");

        DirectoryNode java = new DirectoryNode("Java");

        // Adding files
        documents.addChild(new FileNode("Resume.pdf", 500));
        documents.addChild(new FileNode("Notes.txt", 200));

        images.addChild(new FileNode("Photo.jpg", 1000));
        images.addChild(new FileNode("Logo.png", 300));

        java.addChild(new FileNode("Main.java", 700));

        projects.addChild(java);

        // Building the file system
        root.addChild(documents);
        root.addChild(images);
        root.addChild(projects);

        // 1. Total Size Visitor
        TotalSizeVisitor sizeVisitor = new TotalSizeVisitor();

        System.out.println("=== TOTAL SIZE ===");
        System.out.println(
                "Total size: " + sizeVisitor.calculate(root) + " bytes"
        );

        // 2. Search Visitor
        SearchVisitor searchVisitor = new SearchVisitor(
                name -> name.endsWith(".pdf") || name.endsWith(".java"),
                500
        );

        System.out.println("\n=== SEARCH RESULTS ===");

        for (String path : searchVisitor.search(root)) {
            System.out.println(path);
        }

        // 3. Statistics Visitor
        StatisticsVisitor statisticsVisitor = new StatisticsVisitor();

        statisticsVisitor.calculate(root);

        System.out.println("\n=== STATISTICS ===");
        System.out.println(
                "Files: " + statisticsVisitor.getFileCount()
        );
        System.out.println(
                "Directories: " + statisticsVisitor.getDirectoryCount()
        );
        System.out.println(
                "Maximum depth: " + statisticsVisitor.getMaxDepth()
        );
    }
}
