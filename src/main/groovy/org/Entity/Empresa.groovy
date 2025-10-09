package org.Entity

class Empresa extends Pessoa {
    String cnpj
    String pais
    String estado
    Date data_criacao

    @Override
    String toString() {
        """[Empresa]
        ${super.toString()}
        CNPJ: $cnpj
        País: $pais
        Estado: $estado
        Competências esperadas: ${competencias.join(", ")}"""
    }
}
