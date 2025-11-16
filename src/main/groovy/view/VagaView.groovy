package view

import controller.VagaController
import model.Empresa
import model.Vaga
import model.Competencia
import utils.ConsoleInputReader
import utils.MenuUtils

class VagaView {

    VagaController vagaController
    ConsoleInputReader reader = new ConsoleInputReader()
    List<String> opcoes = new ArrayList<String>()
    CompetenciaView competenciaView
    EmpresaView empresaView

    void menuDeOpcoes(){
        int opt = MenuUtils.mostrarMenu(["Listar Vagas",
                                         "Cadastrar Vaga",
                                         "Atualizar Vaga",
                                         "Deletar Vaga",
                                         "Adicionar Competência",
                                         "Sair"])
        switch (opt + 1) {
            case "1": mostrarVagas(vagaController.buscarVagas()); break
            case "2": vagaController.cadastrarVaga(lerVaga()); break
            case "3": atualizarVaga(); break
            case "4": deletarVaga(); break
            case "5": adicionarCompetencia(); break
            case "6": MenuView.menu()
        }
    }

    void adicionarCompetencia() {
        Vaga vaga = lerIndiceVaga()
        Competencia comp = competenciaView.lerIndiceCompetencia()

        vagaController.adicionarCompetenciaVaga(vaga, comp)
        println "Competencia ${comp.nome} adicionada ao vaga ${vaga.nome}\n"
    }

    void atualizarVaga() {
        mostrarVagas(vagaController.buscarVagas())
        Vaga vaga = lerIndiceVaga()

        String nome = reader.readLine("Nome [${vaga.nome}]: "); if (nome) vaga.nome = nome
        String desc = reader.readLine("Descrição [${vaga.descricao}]: "); if (desc) vaga.descricao = desc
        String local = reader.readLine("Local [${vaga.local_vaga}]: "); if (local) vaga.local_vaga = local

        vagaController.atualizarVaga(vaga)
        println "Vaga atualizado!"
    }


    static void mostrarVagas(List<Vaga> vagas) {
        println "\n-- Vagas --"
        vagas.eachWithIndex { v, idx ->
            println "${idx + 1} - ${v.nome} | Empresa CNPJ: ${v.empresa_cnpj} | Local: ${v.local_vaga} | Comp.: ${v.imprimirCompetencias()}"
        }
    }

    Vaga lerIndiceVaga() {
        List<Vaga> vagas = vagaController.buscarVagas()
        mostrarVagas(vagas)
        int idx = reader.readLine("Escolha índice do vaga: ").toInteger() - 1
        Vaga v = vagas.get(idx)
        return v
    }

    void deletarVaga() {
        Vaga v = lerIndiceVaga()
        vagaController.deletarVaga(v.id)
        println "Vaga deletada!"
    }

    Vaga lerVaga() {
        Empresa empresa = empresaView.lerIndiceEmpresa()

        String nome = reader.readLine("Nome da vaga: ")
        String descricao = reader.readLine("Descrição: ")
        String local = reader.readLine("Local da vaga: ")

        Vaga vaga = new Vaga(nome: nome, descricao: descricao, local_vaga: local, empresa_cnpj: empresa.cnpj)

        return vaga
    }

    VagaView(VagaController vagaController, CompetenciaView competenciaView, EmpresaView empresaView) {
        this.vagaController = vagaController
        this.competenciaView = competenciaView
        this.empresaView = empresaView
    }
}
