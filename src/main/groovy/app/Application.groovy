package app


import Persistence.DBConnection

static void main(String[] args) {
    def linketinder = new Linketinder()
    DBConnection.conectar()
    linketinder.menu()
}