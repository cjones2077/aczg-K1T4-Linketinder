package utils

class ConsoleInputReader {
    static String readLine(String prompt) {
        if (prompt) print prompt
        return System.in.newReader().readLine()
    }
}
