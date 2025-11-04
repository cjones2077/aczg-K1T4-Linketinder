package view

import controller.CurtidaController
import model.Entity.Competencia
import model.Entity.Curtida
import utils.ConsoleInputReader
import utils.MenuUtils

class CurtidaView {
    private final CurtidaController curtidaController
    private final ConsoleInputReader reader = new ConsoleInputReader()

    CurtidaView(CurtidaController curtidaController) {
        this.curtidaController = curtidaController
    }

    void menuDeOpcoes() {
        int opt = MenuUtils.mostrarMenu(["Listar Curtidas",
                                         "Candidato curte Vaga",
                                         "Empresa curte Candidato",
                                         "Deletar Curtida",
                                         "Sair"])
        switch (opt + 1) {
            case 1: mostrarCurtidas(curtidaController.buscarCurtidas()); break
            case 2:
                String cpf = reader.readLine("CPF do candidato: ")
                int idVaga = reader.readLine("ID da vaga: ").toInteger()
                curtidaController.candidatoCurteVaga(cpf, idVaga)
                break
            case 3:
                String cnpj = reader.readLine("CNPJ da empresa: ")
                String cpfCand = reader.readLine("CPF do candidato: ")
                curtidaController.empresaCurteCandidato(cnpj, cpfCand)
                break
            case 4: deletarCurtida(); break
            case 5: return
        }
    }

    Curtida lerIndiceCurtida() {
        List<Curtida> curtidas = curtidaController.buscarCurtidas()
        mostrarCurtidas(curtidas)
        int idx = reader.readLine("Escolha índice da curtida: ").toInteger() - 1
        Curtida c = curtidas.get(idx)
        return c
    }

    static void mostrarCurtidas(List<Curtida> curtidas) {
        println "\n-- Curtidas --"
        curtidas.eachWithIndex { c, idx ->
            println "${idx + 1} - ${c.cpf_candidato} | ${c.id_vaga} | ${c.cnpj_empresa}"
        }
    }

    void deletarCurtida() {
        Curtida curtida = lerIndiceCurtida()
        curtidaController.deletarCurtida(curtida.id)
        println "Curtida deletada!"
    }
}
