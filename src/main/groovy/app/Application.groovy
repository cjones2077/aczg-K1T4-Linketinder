package app


import Persistence.DBConnection

static void main(String[] args) {
    try{
        DBConnection.abrirConexao()
        AppContainer container = new AppContainer()
        Linketinder linketinder = container.criarAplicacao()
        linketinder.menu()
        DBConnection.fecharConexao()
    } catch (Exception e){
        e.printStackTrace()
    }
}