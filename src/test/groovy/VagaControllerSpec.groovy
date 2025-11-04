import model.DAO.CompetenciaDAO
import model.DAO.EmpresaDAO
import model.DAO.VagaDAO
import model.Entity.Competencia
import model.Entity.Empresa
import model.Entity.Vaga
import spock.lang.Specification
import utils.ConsoleInputReader
import controller.CompetenciaController
import controller.EmpresaController
import controller.VagaController

class VagaControllerSpec extends Specification {

    VagaDAO vagaDAOMock = Mock()
    EmpresaDAO empresaDAOMock = Mock()
    EmpresaController empresaControllerMock = Mock()
    CompetenciaDAO competenciaDAOMock = Mock()
    CompetenciaController competenciaControllerMock = Mock()
    ConsoleInputReader consoleMock = Mock()

    VagaController controller = new VagaController(
            vagaDAOMock,
            empresaDAOMock,
            empresaControllerMock,
            competenciaDAOMock,
            competenciaControllerMock,
            consoleMock
    )

    def "cadastrarVaga deve criar vaga chamando DAO com dados corretos"() {
        given:
        Empresa e = new Empresa(nome: "Empresa1", cnpj: "123")
        empresaDAOMock.buscarEmpresas() >> [e]
        consoleMock.readLine("Escolha a empresa dona da vaga (índice): ") >> "1"
        consoleMock.readLine("Nome da vaga: ") >> "Vaga1"
        consoleMock.readLine("Descrição: ") >> "Descricao Vaga1"
        consoleMock.readLine("Local da vaga: ") >> "Porto Alegre"

        when:
        controller.cadastrarVaga()

        then:
        1 * vagaDAOMock.criarVaga({ Vaga v ->
            v.nome == "Vaga1" &&
                    v.descricao == "Descricao Vaga1" &&
                    v.local_vaga == "Porto Alegre" &&
                    v.empresa_cnpj == "123"
        })
    }

    def "atualizarVaga deve chamar DAO com dados atualizados"() {
        given:
        Vaga v = new Vaga(nome: "OldVaga", descricao: "OldDesc", local_vaga: "OldLocal", id: 1)
        vagaDAOMock.buscarVagas() >> [v]

        consoleMock.readLine("Escolha a vaga a atualizar (índice): ") >> "1"
        consoleMock.readLine("Nome [OldVaga]: ") >> "NovaVaga"
        consoleMock.readLine("Descrição [OldDesc]: ") >> "NovaDesc"
        consoleMock.readLine("Local [OldLocal]: ") >> "NovoLocal"

        when:
        controller.atualizarVaga()

        then:
        1 * vagaDAOMock.atualizarVaga({ Vaga va ->
            va.nome == "NovaVaga" &&
                    va.descricao == "NovaDesc" &&
                    va.local_vaga == "NovoLocal"
        })
    }

    def "adicionarCompetenciaVaga deve associar competência corretamente"() {
        given:
        Vaga v = new Vaga(nome: "Vaga1", id: 1)
        Competencia comp = new Competencia(nome: "Java", id: 99)
        vagaDAOMock.buscarVagas() >> [v]
        competenciaDAOMock.buscarCompetencias() >> [comp]

        consoleMock.readLine("Escolha índice da vaga: ") >> "1"
        consoleMock.readLine("Escolha índice da competencia: ") >> "1"

        when:
        controller.adicionarCompetenciaVaga()

        then:
        1 * competenciaDAOMock.associarCompetenciaVaga(1, 99)
        v.competencias.contains(comp)
    }

    def "deletarVaga deve chamar DAO com id correto"() {
        given:
        Vaga v = new Vaga(nome: "Vaga1", id: 42)
        vagaDAOMock.buscarVagas() >> [v]

        consoleMock.readLine("Escolha a vaga a deletar (índice): ") >> "1"

        when:
        controller.deletarVaga()

        then:
        1 * vagaDAOMock.deletarPorID(42)
    }
}
