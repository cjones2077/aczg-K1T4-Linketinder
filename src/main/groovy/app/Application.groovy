package app


import Persistence.DBConnection

static void main(String[] args) {
    try{
        DBConnection.abrirConexao()
        def linketinder = new Linketinder()
        linketinder.menu()
        DBConnection.fecharConexao()
    } catch (Exception e){
        e.printStackTrace()
    }
}