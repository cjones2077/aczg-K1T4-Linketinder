package org.Entity

abstract class Pessoa {
    String nome
    String email
    String cep
    String descricao

    @Override
    String toString() {
        return """Nome: $nome
        Email: $email
        CEP: $cep
        Descrição: $descricao"""
    }
}

class Candidato extends Pessoa {
    String cpf
    int idade
    String estado
    List<String> competencias = []

    @Override
    String toString() {
        return """[Candidato]
        ${super.toString()}
        CPF: $cpf
        Idade: $idade
        Estado: $estado
        Competências: ${competencias.join(", ")}"""
    }
}

class Empresa extends Pessoa {
    String cnpj
    String pais
    String estado
    List<String> competencias = []

    @Override
    String toString() {
        return """[Empresa]
        ${super.toString()}
        CNPJ: $cnpj
        País: $pais
        Estado: $estado
        Competências esperadas: ${competencias.join(", ")}"""
    }
}
