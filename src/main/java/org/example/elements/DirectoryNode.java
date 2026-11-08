package org.example.elements;

import org.example.visitors.Visitor;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class DirectoryNode extends AbstractElement {

    private final Map<String, AbstractElement> children;

    public DirectoryNode(String name) {
        super(name);
        this.children = new LinkedHashMap<>();
    }

    public void addChild(AbstractElement child) {
        if (child == null) {
            throw new IllegalArgumentException(
                    "Child cannot be null"
            );
        }

        if (children.containsKey(child.getName())) {
            throw new IllegalArgumentException(
                    "Duplicate sibling name: " + child.getName()
            );
        }

        if (child.getParent() != null) {
            throw new IllegalArgumentException(
                    "Element already belongs to another directory"
            );
        }

        // Prevent adding this directory to itself or its descendants
        DirectoryNode current = this;

        while (current != null) {
            if (current == child) {
                throw new IllegalArgumentException(
                        "Cycle detected"
                );
            }

            current = current.getParent();
        }

        children.put(child.getName(), child);
        child.setParent(this);
    }

    public Collection<AbstractElement> getChildren() {
        return Collections.unmodifiableCollection(
                children.values()
        );
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
