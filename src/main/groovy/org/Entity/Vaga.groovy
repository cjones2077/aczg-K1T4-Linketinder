package org.Entity

class Vaga {
    int id
    String nome
    String descricao
    String local_vaga
    String empresa_cnpj
    List<Competencia> competencias = []

    String imprimirCompetencias() {
        return competencias?.collect { it.nome }?.join(", ") ?: "Nenhuma"
    }
}
