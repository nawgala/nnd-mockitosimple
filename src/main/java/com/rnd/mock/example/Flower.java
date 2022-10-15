package com.rnd.mock.example;

public interface Flower {
    Leaf getNumberOfLeaf(int i) throws LeafNotFoundException;

    int getNumberOfLeafs();

}
