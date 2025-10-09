package controllers

import DAO.CompetenciaDAO
import org.Entity.Competencia

class CompetenciaController {
    CompetenciaDAO competenciaDAO = new CompetenciaDAO()
    void cadastrarCompetencia() {
        def reader = System.in.newReader()
        print "Nome da competência: "; String nome = reader.readLine()
        def comp = new Competencia(nome: nome)
        competenciaDAO.criarCompetencia(comp)
        println "Competência cadastrada!"
    }

    void listarCompetencias() {
        println "\n-- Competências --"
        competenciaDAO.listarTodasCompetencias().eachWithIndex { c, idx ->
            println "${idx + 1} - ${c.nome} | ID: ${c.id}"
        }
    }

    void atualizarCompetencia() {
        def reader = System.in.newReader()
        List<Competencia> comps = competenciaDAO.listarTodasCompetencias()
        listarCompetencias()
        print "Escolha a competência a atualizar (índice): "
        int idx = reader.readLine().toInteger() - 1
        Competencia comp = comps.get(idx)

        print "Nome [${comp.nome}]: "; String nome = reader.readLine(); if (nome) comp.nome = nome
        competenciaDAO.atualizarCompetencia(comp)
        println "Competência atualizada!"
    }

    void deletarCompetencia() {
        def reader = System.in.newReader()
        List<Competencia> comps = competenciaDAO.listarTodasCompetencias()
        listarCompetencias()
        print "Escolha a competência a deletar (índice): "
        int idx = reader.readLine().toInteger() - 1
        Competencia comp = comps.get(idx)
        competenciaDAO.deletarCompetencia(comp.id)
        println "Competência deletada!"
    }
}
