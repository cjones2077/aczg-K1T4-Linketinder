import org.Entity.Candidato
import org.Entity.Empresa

class Linketinder {
    List<Candidato> candidatos = []
    List<Empresa> empresas = []

    void listarCandidatos() {
        println "\n- Candidatos: -"
        candidatos.each { println it; println "" }
    }

    void listarEmpresas() {
        println "\n- Empresas: -"
        empresas.each { println it; println "" }
    }

    void cadastrarCandidato() {
        def reader = System.in.newReader()
        print "Nome: "
        String nome = reader.readLine()
        print "Email: "
        String email = reader.readLine()
        print "CEP: "
        String cep = reader.readLine()
        print "Descrição: "
        String descricao = reader.readLine()
        print "CPF: "
        String cpf = reader.readLine()
        print "Idade: "
        int idade = reader.readLine().toInteger()
        print "Estado: "
        String estado = reader.readLine()
        print "Competências (separadas por vírgula): "
        List<String> comps = reader.readLine().split(",")*.trim()
        def candidato = new Candidato(
                nome: nome, email: email, cep: cep, descricao: descricao, cpf: cpf, idade: idade,
                estado: estado, competencias: comps
        )
        candidatos << candidato

        println "\n✅Candidato cadastrado com sucesso!\n"
    }

    void cadastrarEmpresa() {
        def reader = System.in.newReader()
        print "Nome: "
        String nome = reader.readLine()
        print "Email: "
        String email = reader.readLine()
        print "CEP: "
        String cep = reader.readLine()
        print "Descrição: "
        String descricao = reader.readLine()
        print "CNPJ: "
        String cnpj = reader.readLine()
        print "País: "
        String pais = reader.readLine()
        print "Estado: "
        String estado = reader.readLine()
        print "Competências esperadas (separadas por vírgula): "
        List<String> comps = reader.readLine().split(",")*.trim()
        def empresa = new Empresa(
                nome: nome, email: email, cep: cep, descricao: descricao, cnpj: cnpj, pais: pais,
                estado: estado, competencias: comps
        )
        empresas << empresa

        println "\n✅Empresa cadastrada com sucesso!\n"
    }

    void menu() {
        while (true) {
            println "Menu Principal"
            println "1 - Listar Candidatos"
            println "2 - Listar Empresas"
            println "3 - Cadastrar Candidato"
            println "4 - Cadastrar Empresa"
            println "0 - Sair"
            print "Escolha uma opção: "
            String opcao = System.in.newReader().readLine()
            switch(opcao) {
                case "1": listarCandidatos(); break
                case "2": listarEmpresas(); break
                case "3": cadastrarCandidato(); break
                case "4": cadastrarEmpresa(); break
                case "0": return
                default: println "Opção inválida!"
            }
        }
    }
}

def linketinder = new Linketinder()

linketinder.candidatos = [
        new Candidato(nome:"João", email:"joao@mail.com", cpf:"11111111111", idade:25, estado:"RS", cep:"90000-000", descricao:"Dev Backend", competencias:["Java", "Spring"]),
        new Candidato(nome:"Maria", email:"maria@mail.com", cpf:"22222222222", idade:28, estado:"SP", cep:"01000-000", descricao:"Dev Frontend", competencias:["Angular","JS"]),
        new Candidato(nome:"Pedro", email:"pedro@mail.com", cpf:"33333333333", idade:30, estado:"RJ", cep:"20000-000", descricao:"FullStack", competencias:["Python","Django","React"]),
        new Candidato(nome:"Ana", email:"ana@mail.com", cpf:"44444444444", idade:22, estado:"PR", cep:"80000-000", descricao:"Estagiária", competencias:["Java","Python"]),
        new Candidato(nome:"Lucas", email:"lucas@mail.com", cpf:"55555555555", idade:35, estado:"MG", cep:"30000-000", descricao:"Arquiteto de Software", competencias:["Java","Spring","Kotlin"])
]

linketinder.empresas = [
        new Empresa(nome:"TechRS", email:"contato@techrs.com", cnpj:"11111111000101", pais:"Brasil", estado:"RS", cep:"90000-000", descricao:"Consultoria em TI", competencias:["Java","Spring"]),
        new Empresa(nome:"CodeSP", email:"hr@codesp.com", cnpj:"22222222000102", pais:"Brasil", estado:"SP", cep:"01000-000", descricao:"Startup de Software", competencias:["Angular", "JS"]),
        new Empresa(nome:"SoftRio", email:"jobs@softrio.com", cnpj:"33333333000103", pais:"Brasil", estado:"RJ", cep:"20000-000", descricao:"Desenvolvimento Mobile", competencias:["Flutter","Dart"]),
        new Empresa(nome:"DevSul", email:"recrutamento@devsul.com", cnpj:"44444444000104", pais:"Brasil", estado:"PR", cep:"80000-000", descricao:"Fábrica de Software", competencias:["Python","Django"]),
        new Empresa(nome:"MineiroTech", email:"gente@mineirotech.com", cnpj:"55555555000105", pais:"Brasil", estado:"MG", cep:"30000-000", descricao:"Big Data e Cloud", competencias:["Kotlin","AWS"])
]

linketinder.menu()