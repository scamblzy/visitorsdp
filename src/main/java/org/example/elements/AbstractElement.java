package org.example.elements;

import java.util.Objects;

public abstract class AbstractElement implements Element {

    private final String name;
    private DirectoryNode parent;

    protected AbstractElement(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Element name cannot be empty");
        }

        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public DirectoryNode getParent() {
        return parent;
    }

    void setParent(DirectoryNode parent) {
        this.parent = parent;
    }
}
