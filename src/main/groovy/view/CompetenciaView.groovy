package view

import controller.CompetenciaController
import model.Entity.Competencia
import utils.ConsoleInputReader
import utils.MenuUtils

class CompetenciaView {
    CompetenciaController competenciaController
    ConsoleInputReader reader = new ConsoleInputReader()

    void menuDeOpcoes(){
        int opt = MenuUtils.mostrarMenu(["Listar Competencias",
                                         "Cadastrar Competencia",
                                         "Atualizar Competencia",
                                         "Deletar Competencia",
                                         "Sair"])
        switch (opt + 1) {
            case 1: mostrarCompetencias(competenciaController.buscarCompetencias()); break
            case 2: competenciaController.cadastrarCompetencia(lerCompetencia()); break
            case 3: atualizarCompetencia(); break
            case 4: deletarCompetencia(); break
            case 5: return
        }
    }

    void atualizarCompetencia() {
        mostrarCompetencias(competenciaController.buscarCompetencias())
        Competencia comp = lerIndiceCompetencia()

        String nome = reader.readLine("Nome [${comp.nome}]: ")
        if (nome) comp.nome = nome

        competenciaController.atualizarCompetencia(comp)
        println "Competencia atualizada!"
    }


    static void mostrarCompetencias(List<Competencia> competencias) {
        println "\n-- Competências --"
        competencias.eachWithIndex { c, idx ->
            println "${idx + 1} - ${c.nome} | ID: ${c.id}"
        }
    }

    Competencia lerIndiceCompetencia() {
        List<Competencia> competencias = competenciaController.buscarCompetencias()
        mostrarCompetencias(competencias)
        int idx = reader.readLine("Escolha índice da competencia: ").toInteger() - 1
        Competencia c = competencias.get(idx)
        return c
    }

    void deletarCompetencia() {
        Competencia c = lerIndiceCompetencia()
        competenciaController.deletarCompetencia(c.id)
        println "Competencia deletada!"
    }

    Competencia lerCompetencia() {
        Competencia competencia

        print "Nome: "; String nome = reader.readLine()

        competencia = new Competencia(nome: nome)
        return competencia
    }

    CompetenciaView(CompetenciaController competenciaController) {
        this.competenciaController = competenciaController
    }
}
