package org.example.visitors;

import org.example.elements.Element;
import org.example.elements.FileNode;
import org.example.elements.DirectoryNode;
import org.example.elements.AbstractElement;

public class StatisticsVisitor implements Visitor {

    private int fileCount;
    private int directoryCount;
    private int maxDepth;
    private int currentDepth;

    public void calculate(Element root) {
        fileCount = 0;
        directoryCount = 0;
        maxDepth = 0;
        currentDepth = 0;

        root.accept(this);
    }

    @Override
    public void visit(FileNode file) {
        fileCount++;
        maxDepth = Math.max(maxDepth, currentDepth);
    }

    @Override
    public void visit(DirectoryNode directory) {
        directoryCount++;
        maxDepth = Math.max(maxDepth, currentDepth);

        for (AbstractElement child : directory.getChildren()) {
            currentDepth++;
            child.accept(this);
            currentDepth--;
        }
    }

    public int getFileCount() {
        return fileCount;
    }

    public int getDirectoryCount() {
        return directoryCount;
    }

    public int getMaxDepth() {
        return maxDepth;
    }
}
