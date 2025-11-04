import controller.CandidatoController
import model.DAO.CandidatoDAO
import model.DAO.CompetenciaDAO
import controller.CompetenciaController
import model.Entity.Candidato
import model.Entity.Competencia
import spock.lang.Specification
import utils.ConsoleInputReader

class CandidatoControllerSpec extends Specification {

    def candidatoDAO = Mock(CandidatoDAO)
    def competenciaDAO = Mock(CompetenciaDAO)
    def competenciaController = Mock(CompetenciaController)
    def inputReader = Mock(ConsoleInputReader)

    def controller = new CandidatoController(
            candidatoDAO,
            competenciaDAO,
            competenciaController,
            inputReader
    )

    def "deve listar candidatos chamando DAO e imprimir corretamente"() {
        given:
        def candidatos = [
                new Candidato(nome: "João", sobrenome: "Silva", cpf: "123", email: "joao@mail.com", competencias: []),
                new Candidato(nome: "Maria", sobrenome: "Souza", cpf: "456", email: "maria@mail.com", competencias: [])
        ]
        candidatoDAO.buscarCandidatos() >> candidatos

        when:
        controller.buscarCandidatos()

        then:
        1 * candidatoDAO.buscarCandidatos()
    }

    def "deve atualizar candidato chamando DAO"() {
        given:
        def candidatoMock = new Candidato(
                nome: "João",
                sobrenome: "Silva",
                email: "joao@email.com",
                cpf: "12345678900",
                idade: 30
        )
        candidatoDAO.buscarCandidatos() >> [candidatoMock]

        // sequencia de entradas do usuário
        inputReader.readLine(_) >>> [
                "1",                  // índice do candidato
                "João Atualizado",    // nome
                "Silva Atualizado",   // sobrenome
                "novo@email.com",     // email
                "",                   // data nascimento (não atualiza)
                "",                   // formação
                "",                   // país
                "",                   // CEP
                "",                   // descrição
                ""                    // senha
        ]

        when:
        controller.atualizarCandidato()

        then:
        1 * candidatoDAO.atualizarCandidato(candidatoMock)
    }

    def "deve deletar candidato chamando DAO"() {
        given:
        def c1 = new Candidato(nome: "João", cpf: "999")
        candidatoDAO.buscarCandidatos() >> [c1]
        System.in = new ByteArrayInputStream(("1\n").bytes)

        when:
        controller.deletarCandidato()

        then:
        1 * candidatoDAO.deletarPorCpf("999")
    }

    def "deve adicionar competencia ao candidato e associar via DAO"() {
        given:
        def candidato = new Candidato(nome: "João", cpf: "123", competencias: [])
        def competencia = new Competencia(id: 5, nome: "Java")

        candidatoDAO.buscarCandidatos() >> [candidato]
        competenciaDAO.buscarCompetencias() >> [competencia]
        System.in = new ByteArrayInputStream(("1\n1\n").bytes)

        when:
        controller.adicionarCompetenciaCandidato()

        then:
        1 * competenciaDAO.associarCompetenciaCandidato("123", 5)
        candidato.competencias.contains(competencia)
    }
}
