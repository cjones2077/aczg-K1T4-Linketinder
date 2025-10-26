package controllers

import DAO.CompetenciaDAO
import DAO.EmpresaDAO
import DAO.VagaDAO
import org.Entity.Candidato
import org.Entity.Competencia
import org.Entity.Empresa
import org.Entity.Vaga
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

    void cadastrarVaga() {
        List<Empresa> empresas = empresaDAO.buscarEmpresas()
        empresaController.listarEmpresas()
        int idx = consoleInputReader.readLine("Escolha a empresa dona da vaga (índice): ").toInteger() - 1
        Empresa empresa = empresas.get(idx)

        String nome = consoleInputReader.readLine("Nome da vaga: ")
        String descricao = consoleInputReader.readLine("Descrição: ")
        String local = consoleInputReader.readLine("Local da vaga: ")

        Vaga vaga = new Vaga(nome: nome, descricao: descricao, local_vaga: local, empresa_cnpj: empresa.cnpj)
        vagaDAO.criarVaga(vaga)
        println "\nVaga cadastrada com sucesso!"
    }

    void listarVagas() {
        println "\n-- Vagas --"
        vagaDAO.buscarVagas().eachWithIndex { v, idx ->
            println "${idx + 1} - ${v.nome} | Empresa CNPJ: ${v.empresa_cnpj} | Local: ${v.local_vaga} | Comp.: ${v.imprimirCompetencias()}"
        }
    }

    void atualizarVaga() {
        List<Vaga> vagas = vagaDAO.buscarVagas()
        listarVagas()
        int idx = consoleInputReader.readLine("Escolha a vaga a atualizar (índice): ").toInteger() - 1
        Vaga vaga = vagas.get(idx)

        String nome = consoleInputReader.readLine("Nome [${vaga.nome}]: "); if (nome) vaga.nome = nome
        String desc = consoleInputReader.readLine("Descrição [${vaga.descricao}]: "); if (desc) vaga.descricao = desc
        String local = consoleInputReader.readLine("Local [${vaga.local_vaga}]: "); if (local) vaga.local_vaga = local

        vagaDAO.atualizarVaga(vaga)
        println "\nVaga atualizada com sucesso!"
    }

    void adicionarCompetenciaVaga() {
        List<Vaga> vagas = vagaDAO.buscarVagas()
        listarVagas()
        int idx = consoleInputReader.readLine("Escolha índice da vaga: ").toInteger() - 1
        Vaga v = vagas.get(idx)

        List<Competencia> competencias = competenciaDAO.buscarCompetencias()
        competenciaController.listarCompetencias()
        idx = consoleInputReader.readLine("Escolha índice da competencia: ").toInteger() - 1
        Competencia comp = competencias.get(idx)

        v.competencias << comp
        competenciaDAO.associarCompetenciaVaga(v.id, comp.id)
        println "Competencia ${comp.nome} adicionada a vaga ${v.nome}"
    }

    void deletarVaga() {
        List<Vaga> vagas = vagaDAO.buscarVagas()
        listarVagas()
        int idx = consoleInputReader.readLine("Escolha a vaga a deletar (índice): ").toInteger() - 1
        Vaga vaga = vagas.get(idx)
        vagaDAO.deletarPorID(vaga.id)
        println "Vaga deletada!"
    }
}
