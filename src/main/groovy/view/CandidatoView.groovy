package view

import controller.CandidatoController
import model.Entity.Candidato
import model.Entity.Competencia
import utils.ConsoleInputReader
import utils.MenuUtils

class CandidatoView {
    CandidatoController candidatoController
    ConsoleInputReader reader = new ConsoleInputReader()
    List<String> opcoes = new ArrayList<String>()
    CompetenciaView competenciaView

    void menuDeOpcoes(){
       int opt = MenuUtils.mostrarMenu(["Listar Candidatos",
                              "Cadastrar Candidato",
                              "Atualizar Candidato",
                              "Deletar Candidato",
                              "Adicionar Competência",
                                               "Sair"])
        switch (opt + 1) {
            case 1: mostrarCandidatos(candidatoController.buscarCandidatos()); break
            case 2: candidatoController.cadastrarCandidato(lerCandidato()); break
            case 3: atualizarCandidato(); break
            case 4: deletarCandidato(); break
            case 5: adicionarCompetencia(); break
            case 6: return
        }
    }

    void adicionarCompetencia() {
        Candidato candidato = lerIndiceCandidato()
        Competencia comp = competenciaView.lerIndiceCompetencia()

        candidatoController.adicionarCompetenciaCandidato(candidato, comp)
        println "Competencia ${comp.nome} adicionada ao candidato ${candidato.nome}\n"
    }

    void atualizarCandidato() {
        mostrarCandidatos(candidatoController.buscarCandidatos())
        Candidato c = lerIndiceCandidato()

        print "Nome [${c.nome}]: "; String nome = reader.readLine(); if (nome) c.nome = nome
        print "Sobrenome [${c.sobrenome}]: "; String sobrenome = reader.readLine(); if (sobrenome) c.sobrenome = sobrenome
        print "Email [${c.email}]: "; String email = reader.readLine(); if (email) c.email = email
        print "Data de nascimento [${c.data_nascimento}]: "; String data = reader.readLine(); if (data) c.data_nascimento = java.sql.Date.valueOf(data)
        print "Formação [${c.formacao}]: "; String formacao = reader.readLine(); if (formacao) c.formacao = formacao
        print "País [${c.pais}]: "; String pais = reader.readLine(); if (pais) c.pais = pais
        print "CEP [${c.cep}]: "; String cep = reader.readLine(); if (cep) c.cep = cep
        print "Descrição [${c.descricao}]: "; String descricao = reader.readLine(); if (descricao) c.descricao = descricao
        print "Senha [${c.senha}]: "; String senha = reader.readLine(); if (senha) c.senha = senha

        candidatoController.atualizarCandidato(c)
        println "Candidato atualizado!"
    }


    static void mostrarCandidatos(List<Candidato> candidatos) {
        println "\n-- Candidatos --"
        candidatos.eachWithIndex { c, idx ->
            println "${idx + 1} - ${c.nome} ${c.sobrenome} | CPF: ${c.cpf} | Email: ${c.email} | Comp.: ${c.imprimirCompetencias()}"
        }
    }

    Candidato lerIndiceCandidato() {
        List<Candidato> candidatos = candidatoController.buscarCandidatos()
        mostrarCandidatos(candidatos)
        int idx = reader.readLine("Escolha índice do candidato: ").toInteger() - 1
        Candidato c = candidatos.get(idx)
        return c
    }

    void deletarCandidato() {
        Candidato c = lerIndiceCandidato()
        candidatoController.deletarCandidato(c.cpf)
        println "Candidato deletado!"
    }

    Candidato lerCandidato() {
        Candidato candidato

        print "Nome: "; String nome = reader.readLine()
        print "Sobrenome: "; String sobrenome = reader.readLine()
        print "Email: "; String email = reader.readLine()
        print "Data de nascimento (AAAA-MM-DD): "; String data = reader.readLine()
        print "Formação: "; String formacao = reader.readLine()
        print "CEP: "; String cep = reader.readLine()
        print "País: "; String pais = reader.readLine()
        print "Descrição: "; String descricao = reader.readLine()
        print "CPF: "; String cpf = reader.readLine()
        print "Idade: "; int idade = reader.readLine().toInteger()
        print "Estado: "; String estado = reader.readLine()
        print "Senha: "; String senha = reader.readLine()

        candidato = new Candidato(
                nome: nome,
                sobrenome: sobrenome,
                email: email,
                data_nascimento: java.sql.Date.valueOf(data),
                formacao: formacao,
                pais: pais,
                cep: cep,
                descricao: descricao,
                cpf: cpf,
                idade: idade,
                estado: estado,
                senha: senha
        )

        return candidato
    }

    CandidatoView(CandidatoController candidatoController, CompetenciaView competenciaView) {
        this.candidatoController = candidatoController
        this.competenciaView = competenciaView
    }
}
