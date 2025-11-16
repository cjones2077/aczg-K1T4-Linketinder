package app

import DAO.CandidatoDAO
import DAO.CompetenciaDAO
import DAO.EmpresaDAO
import DAO.VagaDAO
import view.MenuView
import model.Candidato
import model.Curtida
import model.Empresa
import model.Pessoa
import controller.*
import utils.ConsoleInputReader


class Linketinder {
    private final MenuView menuView
    private final CandidatoDAO candidatoDAO
    private final EmpresaDAO empresaDAO
    private final CompetenciaDAO competenciaDAO
    private final VagaDAO vagaDAO

    private final CandidatoController candidatoController
    private final EmpresaController empresaController
    private final CompetenciaController competenciaController
    private final VagaController vagaController

    private final ConsoleInputReader consoleInputReader

    Linketinder(
            CandidatoDAO candidatoDAO,
            EmpresaDAO empresaDAO,
            CompetenciaDAO competenciaDAO,
            VagaDAO vagaDAO,
            CandidatoController candidatoController,
            EmpresaController empresaController,
            CompetenciaController competenciaController,
            VagaController vagaController,
            ConsoleInputReader consoleInputReader,
            MenuView menuView
    ) {
        this.candidatoDAO = candidatoDAO
        this.empresaDAO = empresaDAO
        this.competenciaDAO = competenciaDAO
        this.vagaDAO = vagaDAO
        this.candidatoController = candidatoController
        this.empresaController = empresaController
        this.competenciaController = competenciaController
        this.vagaController = vagaController
        this.consoleInputReader = consoleInputReader
        this.menuView = menuView
    }

    void mostrarMenu() {
        this.menuView.menu()
    }


}