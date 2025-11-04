package Factory.Connections

class DatabaseConnectionFactory {
    static DBConnection criarConexao(String tipo) {
        switch (tipo){
            case "postgres":
                return PostgreSQLConnection.instancia
            case "mysql":
                return MySQLConnection.instancia
        }
    }
}
