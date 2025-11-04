package app

import factory.connections.DBConnection
import factory.connections.DatabaseConnectionFactory

static void main(String[] args) {
    AppContainer container = new AppContainer()
    try {
        Linketinder linketinder = container.criarAplicacao()
        linketinder.mostrarMenu()
    } catch (Exception e) {
        e.printStackTrace()
    }
    finally {
        container.fecharConexao()
    }
}