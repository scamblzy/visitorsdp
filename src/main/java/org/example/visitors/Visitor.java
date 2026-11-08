package org.example.visitors;

import org.example.elements.FileNode;
import org.example.elements.DirectoryNode;

public interface Visitor {

    void visit(FileNode file);

    void visit(DirectoryNode directory);
}
