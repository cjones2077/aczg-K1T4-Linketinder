package utils

class ConsoleInputReader {
    String readLine(String prompt) {
        if (prompt) print prompt
        return System.in.newReader().readLine()
    }
}
