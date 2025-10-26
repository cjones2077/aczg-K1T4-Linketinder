import DAO.CompetenciaDAO
import org.Entity.Competencia
import spock.lang.Specification
import utils.ConsoleInputReader
import controllers.CompetenciaController

class CompetenciaControllerSpec extends Specification {

    CompetenciaDAO competenciaDAOMock = Mock()
    ConsoleInputReader consoleMock = Mock()
    CompetenciaController controller = new CompetenciaController(competenciaDAOMock, consoleMock)

    def "cadastrarCompetencia deve criar competência chamando DAO"() {
        given:
        consoleMock.readLine("Nome da competência: ") >> "CompetenciaTeste"

        when:
        controller.cadastrarCompetencia()

        then:
        1 * competenciaDAOMock.criarCompetencia({ Competencia c ->
            c.nome == "CompetenciaTeste"
        })
    }

    def "listarCompetencias deve chamar buscarCompetencias e imprimir lista"() {
        given:
        Competencia c1 = new Competencia(nome: "Comp1", id: 1)
        Competencia c2 = new Competencia(nome: "Comp2", id: 2)
        competenciaDAOMock.buscarCompetencias() >> [c1, c2]

        when:
        controller.listarCompetencias()

        then:
        1 * competenciaDAOMock.buscarCompetencias()
    }

    def "atualizarCompetencia deve atualizar competência existente"() {
        given:
        Competencia comp = new Competencia(nome: "OldComp", id: 1)
        competenciaDAOMock.buscarCompetencias() >> [comp]

        consoleMock.readLine("Escolha a competência a atualizar (índice): ") >> "1"
        consoleMock.readLine("Nome [OldComp]: ") >> "NovaComp"

        when:
        controller.atualizarCompetencia()

        then:
        1 * competenciaDAOMock.atualizarCompetencia({ Competencia c ->
            c.nome == "NovaComp"
        })
    }

    def "deletarCompetencia deve chamar DAO com id correto"() {
        given:
        Competencia comp = new Competencia(nome: "CompDel", id: 5)
        competenciaDAOMock.buscarCompetencias() >> [comp]

        consoleMock.readLine("Escolha a competência a deletar (índice): ") >> "1"

        when:
        controller.deletarCompetencia()

        then:
        1 * competenciaDAOMock.deletarCompetencia(5)
    }
}
