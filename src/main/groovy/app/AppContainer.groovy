package app

import DAO.*
import Persistence.DBConnection
import View.MenuView
import controllers.*
import utils.ConsoleInputReader

class AppContainer {

    private final DBConnection dbConnection
    private final ConsoleInputReader consoleInputReader
    private final MenuView menuView
    // DAOs
    private final CandidatoDAO candidatoDAO
    private final EmpresaDAO empresaDAO
    private final CompetenciaDAO competenciaDAO
    private final VagaDAO vagaDAO

    // Controllers
    private final CandidatoController candidatoController
    private final EmpresaController empresaController
    private final CompetenciaController competenciaController
    private final VagaController vagaController

    AppContainer() {
        // Infra
        this.dbConnection = new DBConnection()
        this.consoleInputReader = new ConsoleInputReader()

        // DAOs (injeção explícita da conexão)
        this.candidatoDAO = new CandidatoDAO(dbConnection.conexao)
        this.empresaDAO = new EmpresaDAO(dbConnection.conexao)
        this.competenciaDAO = new CompetenciaDAO(dbConnection.conexao)
        this.vagaDAO = new VagaDAO(dbConnection.conexao)

        // Controllers (injeção de dependências)
        this.empresaController = new EmpresaController(empresaDAO, consoleInputReader)
        this.competenciaController = new CompetenciaController(competenciaDAO, consoleInputReader)

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

        this.menuView = new MenuView()
    }

    /**
     * Cria e retorna a instância principal do app com todas as dependências injetadas.
     */
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
