package DAO

import Persistence.DBConnection
import org.Entity.Competencia
import java.sql.PreparedStatement
import java.sql.ResultSet

class CompetenciaDAO {

    static void criarCompetencia(Competencia competencia) {
        String sql = "INSERT INTO competencias (nome) VALUES (?)"
        PreparedStatement comando = DBConnection.conexao.prepareStatement(sql)
        comando.setString(1, competencia.nome)
        comando.executeUpdate()
        comando.close()
    }

    static List<Competencia> listarTodasCompetencias() {
        String sql = "SELECT * FROM competencias"
        PreparedStatement comando = DBConnection.conexao.prepareStatement(sql)
        ResultSet rs = comando.executeQuery()
        def lista = []
        while (rs.next()) {
            lista << new Competencia(
                    id: rs.getInt("id"),
                    nome: rs.getString("nome")
            )
        }
        rs.close()
        comando.close()
        return lista
    }

    static void atualizarCompetencia(Competencia competencia) {
        String sql = "UPDATE competencias SET nome = ? WHERE id = ?"
        PreparedStatement comando = DBConnection.conexao.prepareStatement(sql)
        comando.setString(1, competencia.nome)
        comando.setInt(2, competencia.id)
        comando.executeUpdate()
        comando.close()
    }

    static void deletarCompetencia(int id) {
        String sql = "DELETE FROM competencias WHERE id = ?"
        PreparedStatement comando = DBConnection.conexao.prepareStatement(sql)
        comando.setInt(1, id)
        comando.executeUpdate()
        comando.close()
    }
}
