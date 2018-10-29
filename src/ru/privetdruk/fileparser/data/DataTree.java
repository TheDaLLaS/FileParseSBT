package ru.privetdruk.fileparser.data;

import java.util.LinkedList;
import java.util.List;

public class DataTree implements Tree {
    private int nodeID;
    private String nodeName;
    private String nodeValue;

    private DataTree parentNode;
    private List<DataTree> childrenNode;

    private boolean isRoot = false;

    public DataTree() {
        nodeID = 1;
        isRoot = true;
    }

    private DataTree(String nodeName, DataTree tree) {
        this.nodeName = nodeName;
        this.nodeID = getLastNodeID(tree) + 1;
    }

    private int getNodeID() {
        return nodeID;
    }

    private String getNodeValue() {
        return nodeValue;
    }

    private void setParent(DataTree parentNode) {
        this.parentNode = parentNode;
    }

    private int getParentID() {
        return parentNode == null ? 0 : parentNode.getNodeID();
    }


    private List<DataTree> getChildrenNodeList() {
        return childrenNode;
    }

    private int getLastNodeID(DataTree treeData) {
        int id = 0;

        if (treeData.getChildrenNodeList() == null)
            id = treeData.getNodeID();
        else
            for (DataTree tree : treeData.getChildrenNodeList())
                id = getLastNodeID(tree);

        return id;
    }

    private void setTreeList(DataTree node, List<String> list) {
        if (node != null) {
            list.add(node.toString());

            if (node.getChildrenNodeList() != null)
                for (DataTree treeNode : node.getChildrenNodeList())
                    setTreeList(treeNode, list);
        }
    }

    @Override
    public boolean isRoot() {
        return isRoot;
    }

    @Override
    public DataTree addNode(String nodeName) {
        DataTree node = new DataTree(nodeName, this);
        node.setParent(this);

        if (childrenNode == null)
            childrenNode = new LinkedList<>();
        childrenNode.add(node);
        return node;
    }

    @Override
    public String getNodeName() {
        return nodeName;
    }

    @Override
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    @Override
    public void setNodeValue(String nodeValue) {
        this.nodeValue = nodeValue;
    }

    @Override
    public List<String> getTreeList() {
        List<String> list = new LinkedList<>();
        setTreeList(this, list);
        return list;
    }

    @Override
    public void printTree() {
        for (String str : getTreeList())
            System.out.println(str);
    }

    @Override
    public String toString() {
        return String.format("%d %d %s %s", getNodeID(), getParentID(), getNodeName(), getNodeValue());
    }
}
