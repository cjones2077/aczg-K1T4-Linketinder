package app


import factory.connections.DBConnection
import factory.connections.DatabaseConnectionFactory
import DAO.CandidatoDAO
import DAO.CompetenciaDAO
import DAO.CurtidaDAO
import DAO.EmpresaDAO
import DAO.VagaDAO
import view.CandidatoView
import view.CompetenciaView
import view.CurtidaView
import view.EmpresaView
import view.MenuView
import controller.*
import utils.ConsoleInputReader
import view.VagaView

class AppContainer {

    private static final AppContainer instancia = new AppContainer()

    static AppContainer getInstancia() {
        return instancia
    }

    final DBConnection dbConnection
    final ConsoleInputReader consoleInputReader
    final CandidatoDAO candidatoDAO
    final EmpresaDAO empresaDAO
    final CompetenciaDAO competenciaDAO
    final VagaDAO vagaDAO
    final CurtidaDAO curtidaDAO

    final CandidatoController candidatoController
    final EmpresaController empresaController
    final CompetenciaController competenciaController
    final VagaController vagaController
    final CurtidaController curtidaController

    final MenuView menuView
    final CandidatoView candidatoView
    final EmpresaView empresaView
    final CompetenciaView competenciaView
    final VagaView vagaView
    final CurtidaView curtidaView

    private AppContainer() {
        this.dbConnection = DatabaseConnectionFactory.criarConexao("postgres")
        dbConnection.abrirConexao()

        consoleInputReader = new ConsoleInputReader()

        candidatoDAO = new CandidatoDAO(dbConnection.conexao)
        empresaDAO = new EmpresaDAO(dbConnection.conexao)
        competenciaDAO = new CompetenciaDAO(dbConnection.conexao)
        vagaDAO = new VagaDAO(dbConnection.conexao)
        curtidaDAO = new CurtidaDAO(dbConnection.conexao)

        empresaController = new EmpresaController(empresaDAO)
        competenciaController = new CompetenciaController(competenciaDAO)
        curtidaController = new CurtidaController(curtidaDAO)

        candidatoController = new CandidatoController(
                candidatoDAO, competenciaDAO, competenciaController, consoleInputReader
        )

        vagaController = new VagaController(
                vagaDAO, empresaDAO, empresaController,
                competenciaDAO, competenciaController,
                consoleInputReader
        )

        competenciaView = new CompetenciaView(competenciaController)
        candidatoView = new CandidatoView(candidatoController, competenciaView)
        empresaView = new EmpresaView(empresaController)
        vagaView = new VagaView(vagaController, competenciaView, empresaView)
        curtidaView = new CurtidaView(curtidaController)
        menuView = new MenuView(candidatoView, empresaView, competenciaView, vagaView, curtidaView)
    }

    void fecharConexao() {
        dbConnection.fecharConexao()
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
