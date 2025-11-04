package controller

import model.DAO.CurtidaDAO
import model.Entity.Curtida

class CurtidaController {
    private final CurtidaDAO curtidaDAO

    CurtidaController(CurtidaDAO curtidaDAO) {
        this.curtidaDAO = curtidaDAO
    }

    void candidatoCurteVaga(String cpf, int idVaga) {
        curtidaDAO.curtirVaga(cpf, idVaga)
    }

    void empresaCurteCandidato(String cnpj, String cpf) {
        curtidaDAO.curtirCandidato(cnpj, cpf)
    }

    List<Curtida> buscarCurtidas() {
        return curtidaDAO.buscarCurtidas()
    }

    void deletarCurtida(int id) {
        curtidaDAO.deletarCurtida(id)
    }
}
