package app

import Factory.Connections.DBConnection
import Factory.Connections.DatabaseConnectionFactory

static void main(String[] args) {
    try{
        AppContainer container = new AppContainer()
        Linketinder linketinder = container.criarAplicacao()
        linketinder.menu()
    } catch (Exception e){
        e.printStackTrace()
    }
}