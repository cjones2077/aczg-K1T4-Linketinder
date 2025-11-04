package app

import model.DAO.CandidatoDAO
import model.DAO.CompetenciaDAO
import model.DAO.EmpresaDAO
import model.DAO.VagaDAO
import view.MenuView
import model.Entity.Candidato
import model.Entity.Curtida
import model.Entity.Empresa
import model.Entity.Pessoa
import controller.*
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

    void mostrarMenu() {
        this.menuView.menu()
    }

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

        candidatoController.buscarCandidatos()
        String opcaoC = consoleInputReader.readLine("Digite o índice do candidato que irá curtir: ")
        Candidato origem = candidatos.get(Integer.parseInt(opcaoC) - 1)

        empresaController.buscarEmpresas()
        print "Digite o índice da empresa que será curtida: "
        String opcaoE = System.in.newReader().readLine()
        Empresa destino = empresas.get(Integer.parseInt(opcaoE) - 1)
        registrarCurtida(origem, destino)
    }

    void curtirCandidato() {
        List<Candidato> candidatos = candidatoDAO.buscarCandidatos()
        List<Empresa> empresas = empresaDAO.buscarEmpresas()

        empresaController.buscarEmpresas()
        String opcaoE = consoleInputReader.readLine("Digite o índice da empresa que irá curtir: ")
        Empresa origem = empresas.get(Integer.parseInt(opcaoE) - 1)

       candidatoController.buscarCandidatos()
        String opcaoC = consoleInputReader.readLine("Digite o índice do candidato que será curtido: ")
        Candidato destino = candidatos.get(Integer.parseInt(opcaoC) - 1)
        registrarCurtida(origem, destino)
    }

}