package app

import DAO.CandidatoDAO
import DAO.CompetenciaDAO
import DAO.EmpresaDAO
import DAO.VagaDAO
import Persistence.DBConnection
import org.Entity.Candidato
import org.Entity.Curtida
import org.Entity.Empresa
import org.Entity.Pessoa
import controllers.*
import utils.ConsoleInputReader


class Linketinder {

    CandidatoDAO candidatoDAO = new CandidatoDAO(DBConnection.conexao)
    EmpresaDAO empresaDAO = new EmpresaDAO(DBConnection.conexao)

    CompetenciaDAO competenciaDAO = new CompetenciaDAO(DBConnection.conexao)
    VagaDAO vagaDAO = new VagaDAO(DBConnection.conexao)

    ConsoleInputReader consoleInputReader = new ConsoleInputReader()

    EmpresaController empresaController = new EmpresaController(empresaDAO, consoleInputReader)
    CompetenciaController competenciaController = new CompetenciaController(competenciaDAO, consoleInputReader)
    CandidatoController candidatoController = new CandidatoController(
            candidatoDAO,
            competenciaDAO,
            competenciaController,
            consoleInputReader
    )
    VagaController vagaController = new VagaController(
            vagaDAO,
            empresaDAO,
            empresaController,
            competenciaDAO,
            competenciaController,
            consoleInputReader
    )

    List<Curtida> curtidas = []


    void listarCurtidas() {
        println "\n-- Histórico de Curtidas --"
        curtidas.each { println it }
    }


    void registrarCurtida(Pessoa origem, Pessoa destino) {
        Curtida curtida = new Curtida(origem: origem, destino: destino)
        curtidas << curtida
        println "${origem.nome} curtiu ${destino.nome}"

        Curtida reciproca = curtidas.find { it.origem == destino && it.destino == origem }
        if (reciproca) {
            curtida.match = true
            reciproca.match = true
            println "Match entre ${origem.nome} e ${destino.nome}!"
        }
    }

    void curtirEmpresa() {
        List<Candidato> candidatos = candidatoDAO.buscarCandidatos()
        List<Empresa> empresas = empresaDAO.buscarEmpresas()

        candidatoController.listarCandidatos()
        print "Digite o índice do candidato que irá curtir: "
        String opcaoC = System.in.newReader().readLine()
        Candidato origem = candidatos.get(Integer.parseInt(opcaoC) - 1)

        empresaController.listarEmpresas()
        print "Digite o índice da empresa que será curtida: "
        String opcaoE = System.in.newReader().readLine()
        Empresa destino = empresas.get(Integer.parseInt(opcaoE) - 1)
        registrarCurtida(origem, destino)
    }

    void curtirCandidato() {
        List<Candidato> candidatos = candidatoDAO.buscarCandidatos()
        List<Empresa> empresas = empresaDAO.buscarEmpresas()

        empresaController.listarEmpresas()
        print "Digite o índice da empresa que irá curtir: "
        String opcaoE = System.in.newReader().readLine()
        Empresa origem = empresas.get(Integer.parseInt(opcaoE) - 1)

       candidatoController.listarCandidatos()
        print "Digite o índice do candidato que será curtido: "
        String opcaoC = System.in.newReader().readLine()
        Candidato destino = candidatos.get(Integer.parseInt(opcaoC) - 1)
        registrarCurtida(origem, destino)
    }

    static void mostrarMenu() {
        println "\n===== Menu Principal =====\n"

        println "===== Opções de Candidato ====="
        println "1  - Listar Candidatos"
        println "2  - Cadastrar Candidato"
        println "3  - Atualizar Candidato"
        println "4  - Deletar Candidato"
        println "5  - Adicionar Competência"
        println ""

        println "===== Opções de Empresa ====="
        println "6  - Listar Empresas"
        println "7  - Cadastrar Empresa"
        println "8  - Atualizar Empresa"
        println "9  - Deletar Empresa"
        println ""

        println "===== Opções de Vaga ====="
        println "10 - Listar Vagas"
        println "11 - Cadastrar Vaga"
        println "12 - Atualizar Vaga"
        println "13 - Deletar Vaga"
        println "14 - Adicionar Competência"
        println ""

        println "===== Opções de Competência ====="
        println "15 - Listar Competências"
        println "16 - Cadastrar Competência"
        println "17 - Atualizar Competência"
        println "18 - Deletar Competência"
        println ""

        println "===== Opções de Curtida ====="
        println "19 - Listar Curtidas"
        println "20 - Candidato Curtir Empresa"
        println "21 - Empresa Curtir Candidato"
        println ""

        println "0  - Sair"
        print "\nEscolha uma opção: "
    }

    void menu() {
        BufferedReader reader = System.in.newReader()
        Boolean sair = false
        while (!sair) {
            mostrarMenu()
            String opcao = reader.readLine()?.trim()
            println opcao
            switch (opcao) {
                case "1": candidatoController.listarCandidatos(); break
                case "2": candidatoController.cadastrarCandidato(); break
                case "3": candidatoController.atualizarCandidato(); break
                case "4": candidatoController.deletarCandidato(); break
                case "5": candidatoController.adicionarCompetenciaCandidato(); break

                case "6": empresaController.listarEmpresas(); break
                case "7": empresaController.cadastrarEmpresa(); break
                case "8": empresaController.atualizarEmpresa(); break
                case "9": empresaController.deletarEmpresa(); break

                case "10": vagaController.listarVagas(); break
                case "11": vagaController.cadastrarVaga(); break
                case "12": vagaController.atualizarVaga(); break
                case "13": vagaController.deletarVaga(); break
                case "14": vagaController.adicionarCompetenciaVaga(); break

                case "15": competenciaController.listarCompetencias(); break
                case "16": competenciaController.cadastrarCompetencia(); break
                case "17": competenciaController.atualizarCompetencia(); break
                case "18": competenciaController.deletarCompetencia(); break

                case "19": listarCurtidas(); break
                case "20": curtirEmpresa(); break
                case "21": curtirCandidato(); break


                case "0": sair = true; break

                default: println "Opção inválida!"; break
            }
        }
    }
}