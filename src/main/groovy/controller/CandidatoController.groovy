package controller

import DAO.CandidatoDAO
import DAO.CompetenciaDAO
import model.Candidato
import model.Competencia
import utils.ConsoleInputReader

class CandidatoController {
    private final CandidatoDAO candidatoDAO
    private final CompetenciaDAO competenciaDAO
    private final CompetenciaController competenciaController
    private final ConsoleInputReader consoleInputReader

    CandidatoController(
            CandidatoDAO candidatoDAO,
            CompetenciaDAO competenciaDAO,
            CompetenciaController competenciaController,
            ConsoleInputReader consoleInputReader
    ) {
        this.candidatoDAO = candidatoDAO
        this.competenciaDAO = competenciaDAO
        this.competenciaController = competenciaController
        this.consoleInputReader = consoleInputReader
    }

    List<Candidato> buscarCandidatos() {
        return candidatoDAO.buscarCandidatos()
    }

    void cadastrarCandidato(Candidato candidato) {
        candidatoDAO.criarCandidato(candidato)
    }

    void atualizarCandidato(Candidato c) {
        candidatoDAO.atualizarCandidato(c)
    }

    void adicionarCompetenciaCandidato(Candidato candidato, Competencia comp) {
        candidato.competencias << comp
        competenciaDAO.associarCompetenciaCandidato(candidato.cpf, comp.id)
    }

    void deletarCandidato(String cpf) {
        candidatoDAO.deletarPorCpf(cpf)
    }
}
