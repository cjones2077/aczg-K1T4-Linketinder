import model.DAO.EmpresaDAO
import model.Entity.Empresa
import spock.lang.Specification
import utils.ConsoleInputReader
import controller.EmpresaController

class EmpresaControllerSpec extends Specification {

    EmpresaDAO empresaDAOMock = Mock()
    ConsoleInputReader consoleMock = Mock()

    EmpresaController controller = new EmpresaController(empresaDAOMock, consoleMock)

    def 'buscarEmpresas deve chamar buscarEmpresas e imprimir lista'() {
        given:
        Empresa e1 = new Empresa(nome: "Empresa1", cnpj: "123", email: "e1@test.com")
        Empresa e2 = new Empresa(nome: "Empresa2", cnpj: "456", email: "e2@test.com")
        empresaDAOMock.buscarEmpresas() >> [e1, e2]

        when:
        controller.buscarEmpresas()

        then:
        1 * empresaDAOMock.buscarEmpresas()
    }

    def "cadastrarEmpresa deve criar empresa chamando DAO"() {
        given:
        consoleMock.readLine("Nome: ") >> "NomeTeste"
        consoleMock.readLine("Email: ") >> "email@test.com"
        consoleMock.readLine("CEP: ") >> "12345-678"
        consoleMock.readLine("Descrição: ") >> "Descricao teste"
        consoleMock.readLine("CNPJ: ") >> "123456789"
        consoleMock.readLine("País: ") >> "Brasil"
        consoleMock.readLine("Estado: ") >> "RS"

        when:
        controller.cadastrarEmpresa()

        then:
        1 * empresaDAOMock.criarEmpresa({ Empresa e ->
            e.nome == "NomeTeste" &&
                    e.email == "email@test.com" &&
                    e.cep == "12345-678" &&
                    e.descricao == "Descricao teste" &&
                    e.cnpj == "123456789" &&
                    e.pais == "Brasil" &&
                    e.estado == "RS"
        })
    }

    def "atualizarEmpresa deve atualizar empresa existente"() {
        given:
        Empresa e = new Empresa(nome: "OldNome", email: "old@test.com", pais: "BR", cep: "00000", descricao: "OldDesc", senha: "123")
        empresaDAOMock.buscarEmpresas() >> [e]

        consoleMock.readLine("Escolha índice da empresa: ") >> "1"
        consoleMock.readLine("Nome [OldNome]: ") >> "NovoNome"
        consoleMock.readLine("Email [old@test.com]: ") >> "novo@test.com"
        consoleMock.readLine("País [BR]: ") >> "BR"
        consoleMock.readLine("CEP [00000]: ") >> "11111"
        consoleMock.readLine("Descrição [OldDesc]: ") >> "NovaDesc"
        consoleMock.readLine("Senha [123]: ") >> "456"

        when:
        controller.atualizarEmpresa()

        then:
        1 * empresaDAOMock.atualizarEmpresa({ Empresa updated ->
            updated.nome == "NovoNome" &&
                    updated.email == "novo@test.com" &&
                    updated.cep == "11111" &&
                    updated.descricao == "NovaDesc" &&
                    updated.senha == "456"
        })
    }

    def "deletarEmpresa deve chamar DAO com CNPJ correto"() {
        given:
        Empresa e = new Empresa(nome: "Empresa1", cnpj: "123")
        empresaDAOMock.buscarEmpresas() >> [e]

        consoleMock.readLine("Escolha índice da empresa: ") >> "1"

        when:
        controller.deletarEmpresa()

        then:
        1 * empresaDAOMock.deletarPorCnpj("123")
    }
}
