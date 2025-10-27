package View

class MenuView {
    static void mostrarMenu() {
        println "\n===== Menu Principal =====\n"

        println "===== Opções de Candidato ====="
        println "1  - Listar Candidatos"
        println "2  - Cadastrar Candidato"
        println "3  - Atualizar Candidato"
        println "4  - Deletar Candidato"
        println "5  - Adicionar Competência"
        println ""

        println "===== Opções de Empresa ====="
        println "6  - Listar Empresas"
        println "7  - Cadastrar Empresa"
        println "8  - Atualizar Empresa"
        println "9  - Deletar Empresa"
        println ""

        println "===== Opções de Vaga ====="
        println "10 - Listar Vagas"
        println "11 - Cadastrar Vaga"
        println "12 - Atualizar Vaga"
        println "13 - Deletar Vaga"
        println "14 - Adicionar Competência"
        println ""

        println "===== Opções de Competência ====="
        println "15 - Listar Competências"
        println "16 - Cadastrar Competência"
        println "17 - Atualizar Competência"
        println "18 - Deletar Competência"
        println ""

        println "===== Opções de Curtida ====="
        println "19 - Listar Curtidas"
        println "20 - Candidato Curtir Empresa"
        println "21 - Empresa Curtir Candidato"
        println ""

        println "0  - Sair"
        print "\nEscolha uma opção: "
    }
}
