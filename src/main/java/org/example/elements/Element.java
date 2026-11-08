package org.example.elements;

import org.example.visitors.Visitor;

public interface Element {

    String getName();

    void accept(Visitor visitor);
}
