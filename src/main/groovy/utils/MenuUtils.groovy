package utils

class MenuUtils {
    static int mostrarMenu(ArrayList<String> opcoes) {
        println "\n=== Menu ==="
        opcoes.eachWithIndex { op, i ->
            println "${i + 1} - ${op}"
        }
        print "Escolha uma opção: "
        String input = System.in.newReader().readLine()
        try {
            int escolha = input.toInteger()
            if (escolha < 1 || escolha > opcoes.size()) {
                println "Opção inválida!"
                return mostrarMenu(opcoes)
            }
            return escolha - 1
        } catch (Exception e) {
            println "Entrada inválida! Digite um número."
            return mostrarMenu(opcoes)
        }
    }
}
