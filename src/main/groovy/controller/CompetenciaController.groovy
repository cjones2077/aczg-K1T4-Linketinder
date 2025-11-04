package controller

import model.DAO.CompetenciaDAO
import model.Entity.Competencia
import utils.ConsoleInputReader

class CompetenciaController {
    private final CompetenciaDAO competenciaDAO

    CompetenciaController(CompetenciaDAO competenciaDAO ) {
        this.competenciaDAO = competenciaDAO
    }

    void cadastrarCompetencia(Competencia competencia) {
        competenciaDAO.criarCompetencia(competencia)
    }

    List<Competencia> buscarCompetencias() {
        return competenciaDAO.buscarCompetencias()
    }

    void atualizarCompetencia(Competencia comp) {
        competenciaDAO.atualizarCompetencia(comp)
    }

    void deletarCompetencia(int id) {
        competenciaDAO.deletarCompetencia(id)
    }
}
