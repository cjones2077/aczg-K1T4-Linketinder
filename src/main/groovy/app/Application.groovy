package app


import Persistence.DBConnection

static void main(String[] args) {
    try{
        def linketinder = new Linketinder()
        DBConnection.conectar()
        linketinder.menu()
        DBConnection.fechaConexao()
    } catch (Exception e){
        e.printStackTrace()
    }

}