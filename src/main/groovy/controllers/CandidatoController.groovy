package controllers

import DAO.CandidatoDAO
import DAO.CompetenciaDAO
import org.Entity.Candidato
import org.Entity.Competencia
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

    void listarCandidatos() {
        println "\n-- Candidatos --"

        candidatoDAO.buscarCandidatos().eachWithIndex { c, idx ->
            println "${idx + 1} - ${c.nome} ${c.sobrenome} | CPF: ${c.cpf} | Email: ${c.email} | Comp.: ${c.imprimirCompetencias()}"
        }
    }

    void cadastrarCandidato() {
        BufferedReader reader = System.in.newReader()
        print "Nome: "; String nome = reader.readLine()
        print "Email: "; String email = reader.readLine()
        print "CEP: "; String cep = reader.readLine()
        print "Descrição: "; String descricao = reader.readLine()
        print "CPF: "; String cpf = reader.readLine()
        print "Idade: "; int idade = reader.readLine().toInteger()
        print "Estado: "; String estado = reader.readLine()

        Candidato candidato = new Candidato(
                nome: nome,
                email: email,
                cep: cep,
                descricao: descricao,
                cpf: cpf,
                idade: idade,
                estado: estado
        )
        candidatoDAO.criarCandidato(candidato)
        println "\nCandidato cadastrado com sucesso!\n"
    }

    void atualizarCandidato() {
        BufferedReader reader = System.in.newReader()
        List<Candidato> candidatos = candidatoDAO.buscarCandidatos()
        listarCandidatos()
        int idx = consoleInputReader.readLine("Escolha índice do candidato: ").toInteger() - 1
        Candidato c = candidatos.get(idx)

        print "Nome [${c.nome}]: "; String nome = reader.readLine(); if (nome) c.nome = nome
        print "Sobrenome [${c.sobrenome}]: "; String sobrenome = reader.readLine(); if (sobrenome) c.sobrenome = sobrenome
        print "Email [${c.email}]: "; String email = reader.readLine(); if (email) c.email = email
        print "Data de nascimento [${c.data_nascimento}]: "; String data = reader.readLine(); if (data) c.data_nascimento = java.sql.Date.valueOf(data)
        print "Formação [${c.formacao}]: "; String formacao = reader.readLine(); if (formacao) c.formacao = formacao
        print "País [${c.pais}]: "; String pais = reader.readLine(); if (pais) c.pais = pais
        print "CEP [${c.cep}]: "; String cep = reader.readLine(); if (cep) c.cep = cep
        print "Descrição [${c.descricao}]: "; String descricao = reader.readLine(); if (descricao) c.descricao = descricao
        print "Senha [${c.senha}]: "; String senha = reader.readLine(); if (senha) c.senha = senha

        candidatoDAO.atualizarCandidato(c)
        println "Candidato atualizado!"
    }

    void adicionarCompetenciaCandidato() {
        BufferedReader reader = System.in.newReader()
        List<Candidato> candidatos = candidatoDAO.buscarCandidatos()
        listarCandidatos()
        print "Escolha índice do candidato: "; int idx = reader.readLine().toInteger() - 1
        Candidato c = candidatos.get(idx)

        List<Competencia> competencias = competenciaDAO.buscarCompetencias()
        competenciaController.listarCompetencias()
        print "Escolha índice da competencia: "; idx = reader.readLine().toInteger() - 1
        Competencia comp = competencias.get(idx)

        c.competencias << comp
        competenciaDAO.associarCompetenciaCandidato(c.cpf, comp.id)
        println "Competencia ${comp.nome} adicionada ao candidato ${c.nome}\n"
    }

    void deletarCandidato() {
        BufferedReader reader = System.in.newReader()
        List<Candidato> candidatos = candidatoDAO.buscarCandidatos()
        listarCandidatos()
        print "Escolha índice do candidato: "; int idx = reader.readLine().toInteger() - 1
        Candidato c = candidatos.get(idx)
        candidatoDAO.deletarPorCpf(c.cpf)
        println "Candidato deletado!"
    }
}
