package controller

import model.DAO.CompetenciaDAO
import model.DAO.EmpresaDAO
import model.DAO.VagaDAO
import model.Entity.Competencia
import model.Entity.Empresa
import model.Entity.Vaga
import utils.ConsoleInputReader

class VagaController {

    private final VagaDAO vagaDAO
    private final EmpresaDAO empresaDAO
    private final EmpresaController empresaController
    private final CompetenciaDAO competenciaDAO
    private final CompetenciaController competenciaController
    private final ConsoleInputReader consoleInputReader

    VagaController(
            VagaDAO vagaDAO,
            EmpresaDAO empresaDAO,
            EmpresaController empresaController,
            CompetenciaDAO competenciaDAO,
            CompetenciaController competenciaController,
            ConsoleInputReader consoleInputReader
    ) {
        this.vagaDAO = vagaDAO
        this.empresaDAO = empresaDAO
        this.empresaController = empresaController
        this.competenciaDAO = competenciaDAO
        this.competenciaController = competenciaController
        this.consoleInputReader = consoleInputReader
    }

    void cadastrarVaga(Vaga vaga) {
        vagaDAO.criarVaga(vaga)
    }

    List<Vaga> buscarVagas() {
        return vagaDAO.buscarVagas()
    }

    void atualizarVaga(Vaga vaga) {
        vagaDAO.atualizarVaga(vaga)
    }

    void adicionarCompetenciaVaga(Vaga vaga, Competencia comp) {
        vaga.competencias << comp
        competenciaDAO.associarCompetenciaVaga(vaga.id, comp.id)
        println "Competencia ${comp.nome} adicionada a vaga ${v.nome}"
    }

    void deletarVaga(int id) {
        vagaDAO.deletarPorID(id)
    }
}
