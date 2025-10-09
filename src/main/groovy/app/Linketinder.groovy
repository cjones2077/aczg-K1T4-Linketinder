package app

import DAO.CandidatoDAO
import DAO.EmpresaDAO
import org.Entity.Candidato
import org.Entity.Curtida
import org.Entity.Empresa
import org.Entity.Pessoa
import controllers.*


class Linketinder {
    List<Curtida> curtidas = []
    CandidatoDAO candidatoDAO = new CandidatoDAO()
    EmpresaDAO empresaDAO = new EmpresaDAO()
    CandidatoController candidatoController = new CandidatoController()
    EmpresaController empresaController = new EmpresaController()
    VagaController vagaController = new VagaController()
    CompetenciaController competenciaController = new CompetenciaController()

    void listarCurtidas() {
        println "\n-- Histórico de Curtidas --"
        curtidas.each { println it }
    }


    void registrarCurtida(Pessoa origem, Pessoa destino) {
        def curtida = new Curtida(origem: origem, destino: destino)
        curtidas << curtida
        println "${origem.nome} curtiu ${destino.nome}"

        Curtida reciproca = curtidas.find { it.origem == destino && it.destino == origem }
        if (reciproca) {
            curtida.match = true
            reciproca.match = true
            println "Match entre ${origem.nome} e ${destino.nome}!"
        }
    }

    def curtirEmpresa() {
        List<Candidato> candidatos = candidatoDAO.listarTodosCandidatos()
        List<Empresa> empresas = empresaDAO.listarTodasEmpresas()

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

    def curtirCandidato() {
        List<Candidato> candidatos = candidatoDAO.listarTodosCandidatos()
        List<Empresa> empresas = empresaDAO.listarTodasEmpresas()

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
        def reader = System.in.newReader()
        def sair = false
        while (!sair) {
            println "\nMenu Principal"
            println "1 - Listar Candidatos"
            println "2 - Listar Empresas"
            println "3 - Listar Curtidas"
            println "4 - Cadastrar Candidato"
            println "5 - Cadastrar Empresa"
            println "6 - Candidato Curtir Empresa"
            println "7 - Empresa Curtir Candidato"
            println "8 - Atualizar Candidato"
            println "9 - Atualizar Empresa"
            println "10 - Deletar Candidato"
            println "11 - Deletar Empresa"
            println "12 - Listar Vagas"
            println "13 - Cadastrar Vaga"
            println "14 - Atualizar Vaga"
            println "15 - Deletar Vaga"
            println "16 - Listar Competências"
            println "17 - Cadastrar Competência"
            println "18 - Atualizar Competência"
            println "19 - Deletar Competência"
            println "0  - Sair"
            print "Escolha uma opção: "

            def opcao = reader.readLine()?.trim()
            println opcao
            switch (opcao) {
                case "1": candidatoController.listarCandidatos(); break
                case "2": empresaController.listarEmpresas(); break
                case "3": listarCurtidas(); break
                case "4": candidatoController.cadastrarCandidato(); break
                case "5": empresaController.cadastrarEmpresa(); break
                case "6": curtirEmpresa(); break
                case "7": curtirCandidato(); break
                case "8": candidatoController.atualizarCandidato(); break
                case "9": empresaController.atualizarEmpresa(); break
                case "10": candidatoController.deletarCandidato(); break
                case "11": empresaController.deletarEmpresa(); break
                case "12": vagaController.listarVagas(); break
                case "13": vagaController.cadastrarVaga(); break
                case "14": vagaController.atualizarVaga(); break
                case "15": vagaController.deletarVaga(); break
                case "16": competenciaController.listarCompetencias(); break
                case "17": competenciaController.cadastrarCompetencia(); break
                case "18": competenciaController.atualizarCompetencia(); break
                case "19": competenciaController.deletarCompetencia(); break
                case "0": sair = true; break
                default: println "Opção inválida!"; break
            }
        }
    }
}