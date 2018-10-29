package ru.privetdruk.fileparser.exception;

import ru.privetdruk.fileparser.data.DataParser;

public class ParserFormatException extends Exception {
    public enum CodeFormatError {
        UNEXPECTED,
        INCORRECT_MORE_ROOT,
        INCORRECT_TYPE_ROOT,
        INCORRECT_BEGIN_NODE,
        INCORRECT_NAME_NODE,
        INCORRECT_CLOSE_NODE,
        INCORRECT_TYPE_NODE,
        INCORRECT_ASSIGNMENT
    }

    public ParserFormatException(CodeFormatError code, char symbol) {
        super(getMessageError(code, symbol));
    }

    private static String getMessageError(CodeFormatError code, char symbol) {
        StringBuilder message = new StringBuilder("Ошибка формата!\n");

        switch (code) {
            case INCORRECT_MORE_ROOT:
                message.append(">\t Корневой узел может быть только один\n");
                break;
            case INCORRECT_TYPE_ROOT:
                message.append(">\t Некорректной тип корневого узла:\n");
                message.append(String.format(">>\t\t ожидался символ '%c'\n", DataParser.CHR_OPEN_NODE_LIST));
                break;
            case INCORRECT_BEGIN_NODE:
                message.append(">\t Некорректный первый символ в названии узла:\n");
                message.append(String.format(">>\t\t ожидалась буква или '%c'\n", DataParser.CHR_SPEC_SYMB_NODE));
                break;
            case INCORRECT_NAME_NODE:
                message.append(">\t Некорректный символ в названии узла:\n");
                message.append(String.format(">>\t\t ожидалась буква, цифра или '%c'\n", DataParser.CHR_SPEC_SYMB_NODE));
                break;
            case INCORRECT_ASSIGNMENT:
                message.append(">\t Некорректное символ после названия узла:\n");
                message.append(String.format(">>\t\t ожидался символ '%c'\n", DataParser.CHR_ASSIGNMENT));
                break;
            case INCORRECT_TYPE_NODE:
                message.append(">\t Некорректный символ определения типа узла:\n");
                message.append(String.format(">>\t\t ожидался символ '%c' для списка или '%c' для значения\n", DataParser.CHR_OPEN_NODE_LIST, DataParser.CHR_ASSIGNMENT));
                break;
            case INCORRECT_CLOSE_NODE:
                message.append(">\t Некорректный символ закрытия узла:\n");
                message.append(String.format(">>\t\t ожидался символ '%c'\n", DataParser.CHR_CLOSE_NODE_LIST));
                break;
            default:
                    message.append(">\t Не удалось определить тип ошибки\n");
        }

        if (code != CodeFormatError.UNEXPECTED && code != CodeFormatError.INCORRECT_MORE_ROOT)
            message.append(String.format(">>>\t\t был прочитан символ '%c'", symbol));

        return message.toString();
    }
}
