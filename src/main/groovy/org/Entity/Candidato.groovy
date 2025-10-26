package org.Entity

class Candidato extends Pessoa {
    String cpf
    int idade
    String estado
    Date data_nascimento
    String formacao
    String sobrenome
    List<Competencia> competencias = []

    @Override
    String toString() {
        """[Candidato]
        ${super.toString()}
        CPF: $cpf
        Idade: $idade
        Estado: $estado
        Competências: ${competencias.join(", ")}"""
    }

    String imprimirCompetencias() {
        return competencias?.collect { it.nome }?.join(", ") ?: "Nenhuma"
    }

}