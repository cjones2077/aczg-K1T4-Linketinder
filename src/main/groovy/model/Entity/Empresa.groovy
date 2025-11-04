package model.Entity

class Empresa extends Pessoa {
    String cnpj
    Date data_criacao
    String nome
    String email
    String cep
    String descricao
    String pais
    String senha

    @Override
    String toString() {
        """[Empresa]
        ${super.toString()}
        CNPJ: $cnpj
        País: $pais
        Estado: $estado"""
    }
}
