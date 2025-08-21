package org.Entity

class Curtida {
    Pessoa origem
    Pessoa destino
    Date data = new Date()
    boolean match = false

    @Override
    String toString() {
        "$origem.nome curtiu $destino.nome"
    }
}


