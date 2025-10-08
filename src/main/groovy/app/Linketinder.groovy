package app

import DAO.CandidatoDAO
import Persistence.DBConnection
import org.Entity.Candidato
import org.Entity.Curtida
import org.Entity.Empresa
import org.Entity.Pessoa

import java.sql.Connection

class Linketinder {
    List<Candidato> candidatos = []
    List<Empresa> empresas = []
    List<Curtida> curtidas = []
    Connection connection = DBConnection.conectar()
    CandidatoDAO candidatoDAO = new CandidatoDAO()
    void listarCurtidas() {
        println "\n-- Histórico de Curtidas --"
        curtidas.each { println it }
    }

    void listarCandidatos() {
        println "\n-- Candidatos --"
        candidatoDAO.listarTodos().eachWithIndex { c, idx ->
            println "${idx+1} - ${c.nome} ${c.sobrenome} | CPF: ${c.cpf} | Email: ${c.email}"
        }
    }


    void listarEmpresas() {
        println "\n-- Empresas: --"
        empresas.eachWithIndex { empresa, idx ->
            println "${idx + 1} - ${empresa}"
            println ""
        }
    }

    void criarCandidato(String nome, String email, String cep, String descricao,
                             String cpf, int idade, String estado, List<String> comps) {
        def candidato = new Candidato(
                nome: nome, email: email, cep: cep, descricao: descricao, cpf: cpf,
                idade: idade, estado: estado, competencias: comps
        )
        candidatos << candidato
    }

    void criarEmpresa(String nome, String email, String cep, String descricao,
                         String cnpj, String pais, String estado, List<String> comps) {
        def empresa = new Empresa(
                nome: nome, email: email, cep: cep, descricao: descricao, cnpj: cnpj,
                pais: pais, estado: estado, competencias: comps
        )
        empresas << empresa
    }

    void cadastrarCandidato() {
        def reader = System.in.newReader()
        print "Nome: " //
        String nome = reader.readLine()
        print "Email: "
        String email = reader.readLine()
        print "CEP: "
        String cep = reader.readLine()
        print "Descrição: "
        String descricao = reader.readLine()
        print "CPF: "
        String cpf = reader.readLine()
        print "Idade: "
        int idade = reader.readLine().toInteger()
        print "Estado: "
        String estado = reader.readLine()
        print "Competências (separadas por vírgula): "
        List<String> comps = reader.readLine().split(",")*.trim()
        criarCandidato( nome, email, cep, descricao, cpf, idade, estado, comps)
        println "\n✅Candidato cadastrado com sucesso!\n"
    }

    void registrarCurtida(Pessoa origem, Pessoa destino) {
        def curtida = new Curtida(origem: origem, destino: destino)
        curtidas << curtida
        println "${origem.nome} curtiu ${destino.nome}"

        Curtida reciproca = curtidas.find { it.origem == destino && it.destino == origem }
        if (reciproca) {
            curtida.match = true
            reciproca.match = true
            println "💚Match entre ${origem.nome} e ${destino.nome}!"
        }
    }

    void cadastrarEmpresa() {
        def reader = System.in.newReader()
        print "Nome: "
        String nome = reader.readLine()
        print "Email: "
        String email = reader.readLine()
        print "CEP: "
        String cep = reader.readLine()
        print "Descrição: "
        String descricao = reader.readLine()
        print "CNPJ: "
        String cnpj = reader.readLine()
        print "País: "
        String pais = reader.readLine()
        print "Estado: "
        String estado = reader.readLine()
        print "Competências esperadas (separadas por vírgula): "
        List<String> comps = reader.readLine().split(",")*.trim()
        criarEmpresa(nome, email, cep, descricao, cnpj, pais, estado, comps)

        println "\n✅Empresa cadastrada com sucesso!\n"
    }

    def curtirEmpresa(){
        listarCandidatos()
        print "Digite o índice do candidato que irá curtir: "
        String opcaoC = System.in.newReader().readLine()
        def origem = candidatos.get(Integer.parseInt(opcaoC) - 1)
        listarEmpresas()
        print "Digite o índice da empresa que será curtida: "
        String opcaoE = System.in.newReader().readLine()
        def destino = empresas.get(Integer.parseInt(opcaoE) - 1)
        registrarCurtida(origem, destino)
    }

    def curtirCandidato(){
        listarEmpresas()
        print "Digite o índice da empresa que irá curtir: "
        String opcaoE = System.in.newReader().readLine()
        def origem = empresas.get(Integer.parseInt(opcaoE) - 1)
        listarCandidatos()
        print "Digite o índice do candidato que será curtido: "
        String opcaoC = System.in.newReader().readLine()
        def destino = candidatos.get(Integer.parseInt(opcaoC) - 1)
        registrarCurtida(origem, destino)
    }

    void menu() {
        while (true) {
            println "Menu Principal"
            println "1 - Listar Candidatos"
            println "2 - Listar Empresas"
            println "3 - Listar Curtidas"
            println "4 - Cadastrar Candidato"
            println "5 - Cadastrar Empresa"
            println "6 - Candidato Curtir Empresa"
            println "7 - Empresa Curtir Candidato"
            println "0 - Sair"
            print "Escolha uma opção: "
            String opcao = System.in.newReader().readLine()
            switch(opcao) {
                case "1": listarCandidatos(); break
                case "2": listarEmpresas(); break
                case "3": listarCurtidas(); break
                case "4": cadastrarCandidato(); break
                case "5": cadastrarEmpresa(); break
                case "6": curtirEmpresa(); break
                case "7": curtirCandidato(); break
                case "0": connection.close(); return
                default: println "Opção inválida!"
            }
        }
    }
}