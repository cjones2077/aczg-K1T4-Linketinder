package controller

import model.DAO.EmpresaDAO
import model.Entity.Empresa
import utils.ConsoleInputReader

class EmpresaController {
    private final EmpresaDAO empresaDAO

    EmpresaController(EmpresaDAO empresaDAO) {
        this.empresaDAO = empresaDAO
    }

    List<Empresa> buscarEmpresas() {
        return empresaDAO.buscarEmpresas()
    }

    void cadastrarEmpresa(Empresa empresa) {
        empresaDAO.criarEmpresa(empresa)
    }

    void atualizarEmpresa(Empresa e) {
        empresaDAO.atualizarEmpresa(e)
    }

    void deletarEmpresa(String cnpj) {
        empresaDAO.deletarPorCnpj(cnpj)
    }
}
