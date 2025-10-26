package controllers

import DAO.EmpresaDAO
import org.Entity.Empresa
import utils.ConsoleInputReader

class EmpresaController {
    private final EmpresaDAO empresaDAO
    private final ConsoleInputReader consoleInputReader

    EmpresaController(EmpresaDAO empresaDAO, ConsoleInputReader consoleInputReader) {
        this.empresaDAO = empresaDAO
        this.consoleInputReader = consoleInputReader
    }

    void listarEmpresas() {
        println "\n-- Empresas --"
        empresaDAO.buscarEmpresas().eachWithIndex { e, idx ->
            println "${idx + 1} - ${e.nome} | CNPJ: ${e.cnpj} | Email: ${e.email}"
        }
    }

    void cadastrarEmpresa() {
        String nome = consoleInputReader.readLine("Nome: ")
        String email = consoleInputReader.readLine("Email: ")
        String cep = consoleInputReader.readLine("CEP: ")
        String descricao = consoleInputReader.readLine("Descrição: ")
        String cnpj = consoleInputReader.readLine("CNPJ: ")
        String pais = consoleInputReader.readLine("País: ")
        String estado = consoleInputReader.readLine("Estado: ")

        Empresa empresa = new Empresa(
                nome: nome,
                email: email,
                cep: cep,
                descricao: descricao,
                cnpj: cnpj,
                pais: pais,
                estado: estado
        )

        empresaDAO.criarEmpresa(empresa)
        println "Empresa cadastrada!"
    }

    void atualizarEmpresa() {
        List<Empresa> empresas = empresaDAO.buscarEmpresas()
        listarEmpresas()
        int idx = consoleInputReader.readLine("Escolha índice da empresa: ").toInteger() - 1
        Empresa e = empresas.get(idx)

        String nome = consoleInputReader.readLine("Nome [${e.nome}]: "); if (nome) e.nome = nome
        String email = consoleInputReader.readLine("Email [${e.email}]: "); if (email) e.email = email
        String pais = consoleInputReader.readLine("País [${e.pais}]: "); if (pais) e.pais = pais
        String cep = consoleInputReader.readLine("CEP [${e.cep}]: "); if (cep) e.cep = cep
        String descricao = consoleInputReader.readLine("Descrição [${e.descricao}]: "); if (descricao) e.descricao = descricao
        String senha = consoleInputReader.readLine("Senha [${e.senha}]: "); if (senha) e.senha = senha

        empresaDAO.atualizarEmpresa(e)
        println "Empresa atualizada!"
    }

    void deletarEmpresa() {
        List<Empresa> empresas = empresaDAO.buscarEmpresas()
        listarEmpresas()
        int idx = consoleInputReader.readLine("Escolha índice da empresa: ").toInteger() - 1
        Empresa e = empresas.get(idx)
        empresaDAO.deletarPorCnpj(e.cnpj)
        println "Empresa deletada!"
    }
}
