package ru.privetdruk.fileparser;

import ru.privetdruk.fileparser.data.DataTree;
import ru.privetdruk.fileparser.data.DataParser;
import ru.privetdruk.fileparser.data.Tree;
import ru.privetdruk.fileparser.workfile.FileManagement;

public class Main {
    public static void main(String[] args) {
        try {
            Tree treeData = new DataTree();
            DataParser.getDataTree(treeData, FileManagement.convertFileToString(ClassLoader.getSystemResource("Data.txt").getPath()));
            treeData.printTree();
            FileManagement.saveListToFile("result.txt", treeData.getTreeList());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
