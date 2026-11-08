package org.example.visitors;

import org.example.elements.Element;
import org.example.elements.FileNode;
import org.example.elements.DirectoryNode;
import org.example.elements.AbstractElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class SearchVisitor implements Visitor {

    private final Predicate<String> filenameCriterion;
    private final long minimumSize;

    private final List<String> results = new ArrayList<>();
    private final List<String> path = new ArrayList<>();

    private DirectoryNode root;

    public SearchVisitor(Predicate<String> filenameCriterion,
                         long minimumSize) {

        if (filenameCriterion == null) {
            throw new IllegalArgumentException(
                    "Filename criterion cannot be null"
            );
        }

        if (minimumSize < 0) {
            throw new IllegalArgumentException(
                    "Minimum size cannot be negative"
            );
        }

        this.filenameCriterion = filenameCriterion;
        this.minimumSize = minimumSize;
    }

    public List<String> search(DirectoryNode root) {
        if (root == null) {
            throw new IllegalArgumentException(
                    "Root cannot be null"
            );
        }

        results.clear();
        path.clear();
        this.root = root;

        root.accept(this);

        Collections.sort(results);

        return Collections.unmodifiableList(
                new ArrayList<>(results)
        );
    }

    @Override
    public void visit(FileNode file) {
        if (filenameCriterion.test(file.getName())
                && file.getSize() >= minimumSize) {

            List<String> fullPath = new ArrayList<>(path);
            fullPath.add(file.getName());

            results.add(String.join("/", fullPath));
        }
    }

    @Override
    public void visit(DirectoryNode directory) {

        if (directory != root) {
            path.add(directory.getName());
        }

        for (AbstractElement child : directory.getChildren()) {
            child.accept(this);
        }

        if (directory != root) {
            path.remove(path.size() - 1);
        }
    }
}
