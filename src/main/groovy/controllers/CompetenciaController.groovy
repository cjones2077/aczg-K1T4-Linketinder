package controllers

import DAO.CompetenciaDAO
import org.Entity.Competencia
import utils.ConsoleInputReader

class CompetenciaController {
    private final CompetenciaDAO competenciaDAO
    private final ConsoleInputReader consoleInputReader

    CompetenciaController(CompetenciaDAO competenciaDAO, ConsoleInputReader consoleInputReader) {
        this.competenciaDAO = competenciaDAO
        this.consoleInputReader = consoleInputReader
    }

    void cadastrarCompetencia() {
        String nome = consoleInputReader.readLine("Nome da competência: ")
        Competencia comp = new Competencia(nome: nome)
        competenciaDAO.criarCompetencia(comp)
        println "Competência cadastrada!"
    }

    void listarCompetencias() {
        println "\n-- Competências --"
        competenciaDAO.buscarCompetencias().eachWithIndex { c, idx ->
            println "${idx + 1} - ${c.nome} | ID: ${c.id}"
        }
    }

    void atualizarCompetencia() {
        List<Competencia> comps = competenciaDAO.buscarCompetencias()
        listarCompetencias()
        int idx = consoleInputReader.readLine("Escolha a competência a atualizar (índice): ").toInteger() - 1
        Competencia comp = comps.get(idx)

        String nome = consoleInputReader.readLine("Nome [${comp.nome}]: ")
        if (nome) comp.nome = nome

        competenciaDAO.atualizarCompetencia(comp)
        println "Competência atualizada!"
    }

    void deletarCompetencia() {
        List<Competencia> comps = competenciaDAO.buscarCompetencias()
        listarCompetencias()
        int idx = consoleInputReader.readLine("Escolha a competência a deletar (índice): ").toInteger() - 1
        Competencia comp = comps.get(idx)
        competenciaDAO.deletarCompetencia(comp.id)
        println "Competência deletada!"
    }
}
