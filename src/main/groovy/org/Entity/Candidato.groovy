package org.Entity

class Candidato extends Pessoa {
    String cpf
    int idade
    String estado
    Date data_nascimento
    String formacao
    String sobrenome
    List<String> competencias = []

    @Override
    String toString() {
        """[Candidato]
        ${super.toString()}
        CPF: $cpf
        Idade: $idade
        Estado: $estado
        Competências: ${competencias.join(", ")}"""
    }
}