package ru.privetdruk.fileparser.data;

import ru.privetdruk.fileparser.exception.ParserFormatException;

public final class DataParser {
    private DataParser() {
    }

    public static final char CHR_OPEN_NODE_LIST = '{';
    public static final char CHR_CLOSE_NODE_LIST = '}';
    public static final char CHR_SPEC_SYMB_NODE = '_';
    public static final char CHR_ASSIGNMENT = '=';
    private static final char CHR_VALUE = '"';
    private static final char CHR_SPACE = ' ';
    private static final int EMPTY = 0;

    enum FormatValidationStatus {
        DEFAULT,
        VALUE,
        VALUE_CLOSE,
        NODE_TYPE,
        NODE_NAME,
        NODE_ASSIGNMENT,
        NODE_CLOSE
    }

    public static void getDataTree(Tree root, String inputData) throws ParserFormatException {
        int out = getDataTree(root, inputData, inputData.length(), 0);
    }

    private static int getDataTree(Tree node, String inputData, int inputLength, int startPosition) throws ParserFormatException {
        StringBuilder nodeName = new StringBuilder();
        StringBuilder nodeValue = new StringBuilder();
        Tree newNode = null;

        FormatValidationStatus status = FormatValidationStatus.DEFAULT;

        for (int position = startPosition; position < inputLength; position++) {
            char symbol = inputData.charAt(position);

            if (status != FormatValidationStatus.VALUE) {
                if (symbol == CHR_SPACE) {
                    if (status == FormatValidationStatus.NODE_NAME)
                        status = FormatValidationStatus.NODE_ASSIGNMENT;
                    continue;
                }



                if (nodeName.length() == EMPTY) {
                    if (status == FormatValidationStatus.VALUE_CLOSE && symbol == CHR_CLOSE_NODE_LIST)
                        status = FormatValidationStatus.NODE_CLOSE;
                    else if (symbol != CHR_CLOSE_NODE_LIST) {
                        if (Character.isDigit(symbol) && !Character.isLetter(symbol) && symbol != CHR_SPEC_SYMB_NODE)
                            throw new ParserFormatException(ParserFormatException.CodeFormatError.INCORRECT_BEGIN_NODE, symbol);
                        else
                            status = FormatValidationStatus.NODE_NAME;
                    }
                }

                switch (status) {
                    case NODE_NAME:
                        if (Character.isDigit(symbol) || Character.isLetter(symbol) || symbol == CHR_SPEC_SYMB_NODE)
                            nodeName.append(symbol);
                        else if (symbol == CHR_ASSIGNMENT) {
                            position--;
                            status = FormatValidationStatus.NODE_ASSIGNMENT;
                        } else
                            throw new ParserFormatException(ParserFormatException.CodeFormatError.INCORRECT_NAME_NODE, symbol);
                        break;
                    case NODE_ASSIGNMENT:
                        switch (symbol) {
                            case CHR_ASSIGNMENT:
                                if (node.isRoot() && node.getNodeName() == null)
                                    node.setNodeName(nodeName.toString());
                                else
                                    newNode = node.addNode(nodeName.toString());
                                status = FormatValidationStatus.NODE_TYPE;
                                break;
                            default:
                                throw new ParserFormatException(ParserFormatException.CodeFormatError.INCORRECT_ASSIGNMENT, symbol);
                        }
                        break;
                    case NODE_TYPE:
                        switch (symbol) {
                            case CHR_VALUE:
                                nodeValue.setLength(0);
                                status = FormatValidationStatus.VALUE;
                                break;
                            case CHR_OPEN_NODE_LIST:
                                position++;
                                if (newNode != null)
                                    position = getDataTree(newNode, inputData, inputLength, position);
                                else
                                    position = getDataTree(node, inputData, inputLength, position);
                                nodeName.setLength(0);

                                status = FormatValidationStatus.NODE_CLOSE;
                                break;
                            default:
                                throw new ParserFormatException(ParserFormatException.CodeFormatError.INCORRECT_TYPE_NODE, symbol);

                        }
                        break;
                    case NODE_CLOSE:
                        switch (symbol) {
                            case CHR_CLOSE_NODE_LIST:
                                if (node.isRoot() && inputData.substring(position + 1).trim().length() > 0)
                                    throw new ParserFormatException(ParserFormatException.CodeFormatError.INCORRECT_MORE_ROOT, symbol);

                                return position;
                            default:
                                throw new ParserFormatException(ParserFormatException.CodeFormatError.INCORRECT_CLOSE_NODE, symbol);

                        }
                    default:
                        throw new ParserFormatException(ParserFormatException.CodeFormatError.UNEXPECTED, symbol);
                }
            } else {
                switch (symbol) {
                    case CHR_VALUE:
                        if (newNode == null)
                            throw new ParserFormatException(ParserFormatException.CodeFormatError.INCORRECT_TYPE_ROOT, symbol);

                        newNode.setNodeValue(nodeValue.toString());
                        status = FormatValidationStatus.VALUE_CLOSE;
                        nodeName.setLength(0);
                    default:
                        nodeValue.append(symbol);
                }
            }
        }
        return 0;
    }
}
