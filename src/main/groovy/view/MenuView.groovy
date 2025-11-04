package view

import utils.ConsoleInputReader
import utils.MenuUtils

class MenuView {
    ConsoleInputReader consoleInputReader = new ConsoleInputReader()
    CandidatoView candidatoView
    EmpresaView empresaView
    CompetenciaView competenciaView
    VagaView vagaView
    CurtidaView curtidaView

    void menu() {
        Boolean sair = false
        while (!sair) {

            int opcao = MenuUtils.mostrarMenu(["Mostrar Opções de Candidato",
                                                     "Mostrar Opções de Empresa",
                                                     "Mostrar Opções de Vaga",
                                                     "Mostrar Opções de Competência",
                                                     "Mostrar Opções de Curtida"])
            switch (opcao + 1) {
                case 1: candidatoView.menuDeOpcoes(); break
                case 2: empresaView.menuDeOpcoes(); break
                case 3: vagaView.menuDeOpcoes(); break
                case 4: competenciaView.menuDeOpcoes(); break
                case 5: curtidaView.menuDeOpcoes(); break
                case 0: sair = true; break

                default: println "Opção inválida!"; break
            }
        }
    }

    MenuView(CandidatoView candidatoView, EmpresaView empresaView, CompetenciaView competenciaView,
             VagaView vagaView, CurtidaView curtidaView) {
        this.candidatoView = candidatoView
        this.empresaView = empresaView
        this.competenciaView = competenciaView
        this.vagaView = vagaView
        this.curtidaView = curtidaView
    }
}
