package app

import DAO.CandidatoDAO
import DAO.CompetenciaDAO
import DAO.EmpresaDAO
import DAO.VagaDAO
import Persistence.DBConnection
import View.MenuView
import org.Entity.Candidato
import org.Entity.Curtida
import org.Entity.Empresa
import org.Entity.Pessoa
import controllers.*
import utils.ConsoleInputReader


class Linketinder {
    private final MenuView menuView
    private final CandidatoDAO candidatoDAO
    private final EmpresaDAO empresaDAO
    private final CompetenciaDAO competenciaDAO
    private final VagaDAO vagaDAO

    private final CandidatoController candidatoController
    private final EmpresaController empresaController
    private final CompetenciaController competenciaController
    private final VagaController vagaController

    private final ConsoleInputReader consoleInputReader

    private final List<Curtida> curtidas = []

    Linketinder(
            CandidatoDAO candidatoDAO,
            EmpresaDAO empresaDAO,
            CompetenciaDAO competenciaDAO,
            VagaDAO vagaDAO,
            CandidatoController candidatoController,
            EmpresaController empresaController,
            CompetenciaController competenciaController,
            VagaController vagaController,
            ConsoleInputReader consoleInputReader,
            MenuView menuView
    ) {
        this.candidatoDAO = candidatoDAO
        this.empresaDAO = empresaDAO
        this.competenciaDAO = competenciaDAO
        this.vagaDAO = vagaDAO
        this.candidatoController = candidatoController
        this.empresaController = empresaController
        this.competenciaController = competenciaController
        this.vagaController = vagaController
        this.consoleInputReader = consoleInputReader
        this.menuView = menuView
    }

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
        String opcaoC = consoleInputReader.readLine("Digite o índice do candidato que irá curtir: ")
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
        String opcaoE = consoleInputReader.readLine("Digite o índice da empresa que irá curtir: ")
        Empresa origem = empresas.get(Integer.parseInt(opcaoE) - 1)

       candidatoController.listarCandidatos()
        String opcaoC = consoleInputReader.readLine("Digite o índice do candidato que será curtido: ")
        Candidato destino = candidatos.get(Integer.parseInt(opcaoC) - 1)
        registrarCurtida(origem, destino)
    }

    void menu() {
        Boolean sair = false
        while (!sair) {
            menuView.mostrarMenu()
            String opcao = consoleInputReader.readLine(null)?.trim()
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