package model.Entity

abstract class Pessoa {
    String nome
    String email
    String cep
    String descricao
    String pais
    String senha

    @Override
    String toString() {
        """Nome: $nome
        Email: $email
        CEP: $cep
        Descrição: $descricao"""
    }
}



