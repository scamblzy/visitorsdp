package org.example.visitors;

import org.example.elements.Element;
import org.example.elements.FileNode;
import org.example.elements.DirectoryNode;
import org.example.elements.AbstractElement;

public class TotalSizeVisitor implements Visitor {

    private long totalSize;

    public long calculate(Element root) {
        totalSize = 0;
        root.accept(this);
        return totalSize;
    }

    @Override
    public void visit(FileNode file) {
        totalSize += file.getSize();
    }

    @Override
    public void visit(DirectoryNode directory) {
        for (AbstractElement child : directory.getChildren()) {
            child.accept(this);
        }
    }
}
