package ru.privetdruk.fileparser.data;

import java.util.List;

public interface Tree {
    boolean isRoot();

    Tree addNode(String nodeName);

    void setNodeValue(String nodeValue);

    String getNodeName();

    void setNodeName(String nodeName);

    List<String> getTreeList();

    void printTree();
}
