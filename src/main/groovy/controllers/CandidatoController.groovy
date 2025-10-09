package controllers

import DAO.CandidatoDAO
import org.Entity.Candidato

class CandidatoController {
    CandidatoDAO candidatoDAO = new CandidatoDAO()

    void listarCandidatos() {
        println "\n-- Candidatos --"
        candidatoDAO.listarTodosCandidatos().eachWithIndex { c, idx ->
            println "${idx + 1} - ${c.nome} ${c.sobrenome} | CPF: ${c.cpf} | Email: ${c.email}"
        }
    }

    void cadastrarCandidato() {
        def reader = System.in.newReader()
        print "Nome: "; String nome = reader.readLine()
        print "Email: "; String email = reader.readLine()
        print "CEP: "; String cep = reader.readLine()
        print "Descrição: "; String descricao = reader.readLine()
        print "CPF: "; String cpf = reader.readLine()
        print "Idade: "; int idade = reader.readLine().toInteger()
        print "Estado: "; String estado = reader.readLine()

        def candidato = new Candidato(nome: nome, email: email, cep: cep, descricao: descricao,
                cpf: cpf, idade: idade, estado: estado)
        candidatoDAO.criarCandidato(candidato)
        println "\nCandidato cadastrado com sucesso!\n"
    }

    void atualizarCandidato() {
        def reader = System.in.newReader()
        List<Candidato> candidatos = candidatoDAO.listarTodosCandidatos()
        listarCandidatos()
        print "Escolha índice do candidato: "; int idx = reader.readLine().toInteger() - 1
        Candidato c = candidatos.get(idx)

        print "Nome [${c.nome}]: "; String nome = reader.readLine(); if(nome) c.nome = nome
        print "Sobrenome [${c.sobrenome}]: "; String sobrenome = reader.readLine(); if(sobrenome) c.sobrenome = sobrenome
        print "Email [${c.email}]: "; String email = reader.readLine(); if(email) c.email = email

        candidatoDAO.atualizarCandidato(c)
        println "Candidato atualizado!"
    }

    void deletarCandidato() {
        def reader = System.in.newReader()
        List<Candidato> candidatos = candidatoDAO.listarTodosCandidatos()
        listarCandidatos()
        print "Escolha índice do candidato: "; int idx = reader.readLine().toInteger() - 1
        Candidato c = candidatos.get(idx)
        candidatoDAO.deletarCandidato(c.cpf)
        println "Candidato deletado!"
    }
}
