package app


import factory.connections.DBConnection
import factory.connections.DatabaseConnectionFactory
import model.DAO.CandidatoDAO
import model.DAO.CompetenciaDAO
import model.DAO.CurtidaDAO
import model.DAO.EmpresaDAO
import model.DAO.VagaDAO
import view.CandidatoView
import view.CompetenciaView
import view.CurtidaView
import view.EmpresaView
import view.MenuView
import controller.*
import utils.ConsoleInputReader
import view.VagaView

class AppContainer {

    private final DBConnection dbConnection
    private final ConsoleInputReader consoleInputReader
    private final MenuView menuView
    // DAOs
    private final CandidatoDAO candidatoDAO
    private final EmpresaDAO empresaDAO
    private final CompetenciaDAO competenciaDAO
    private final VagaDAO vagaDAO
    private final CurtidaDAO curtidaDAO

    // Controllers
    private final CandidatoController candidatoController
    private final EmpresaController empresaController
    private final CompetenciaController competenciaController
    private final VagaController vagaController
    private final CurtidaController curtidaController

    // Views
    private final CandidatoView candidatoView
    private final EmpresaView empresaView
    private final CompetenciaView competenciaView
    private final VagaView vagaView
    private final CurtidaView curtidaView

    AppContainer() {
        this.dbConnection = DatabaseConnectionFactory.criarConexao("postgres")
        dbConnection.abrirConexao()
        this.consoleInputReader = new ConsoleInputReader()

        this.candidatoDAO = new CandidatoDAO(dbConnection.getConexao())
        this.empresaDAO = new EmpresaDAO(dbConnection.getConexao())
        this.competenciaDAO = new CompetenciaDAO(dbConnection.getConexao())
        this.vagaDAO = new VagaDAO(dbConnection.getConexao())
        this.curtidaDAO = new CurtidaDAO(dbConnection.getConexao())

        this.empresaController = new EmpresaController(empresaDAO)
        this.competenciaController = new CompetenciaController(competenciaDAO)
        this.curtidaController = new CurtidaController(curtidaDAO)

        this.candidatoController = new CandidatoController(
                candidatoDAO,
                competenciaDAO,
                competenciaController,
                consoleInputReader
        )

        this.vagaController = new VagaController(
                vagaDAO,
                empresaDAO,
                empresaController,
                competenciaDAO,
                competenciaController,
                consoleInputReader
        )

        this.competenciaView = new CompetenciaView(competenciaController)
        this.candidatoView = new CandidatoView(candidatoController, competenciaView)
        this.empresaView = new EmpresaView(empresaController)
        this.vagaView = new VagaView(vagaController, competenciaView, empresaView)
        this.curtidaView = new CurtidaView(curtidaController)
        this.menuView = new MenuView(candidatoView, empresaView, competenciaView, vagaView, curtidaView)
    }

    void fecharConexao() {
        this.dbConnection.fecharConexao()
    }

    Linketinder criarAplicacao() {
        return new Linketinder(
                candidatoDAO,
                empresaDAO,
                competenciaDAO,
                vagaDAO,
                candidatoController,
                empresaController,
                competenciaController,
                vagaController,
                consoleInputReader,
                menuView
        )
    }
}
