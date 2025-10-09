package DAO

import Persistence.DBConnection
import org.Entity.Vaga
import java.sql.PreparedStatement
import java.sql.ResultSet

class VagaDAO {
    static void criarVaga(Vaga vaga) {
        String sql = """INSERT INTO vagas (nome, descricao, local_vaga, empresa_cnpj)
                        VALUES (?, ?, ?, ?)"""
        PreparedStatement comando = DBConnection.conexao.prepareStatement(sql)
        comando.setString(1, vaga.nome)
        comando.setString(2, vaga.descricao)
        comando.setString(3, vaga.local_vaga)
        comando.setString(4, vaga.empresa_cnpj)
        comando.executeUpdate()
        comando.close()
    }

    static List<Vaga> listarTodasVagas() {
        String sql = "SELECT * FROM vagas"
        PreparedStatement comando = DBConnection.conexao.prepareStatement(sql)
        ResultSet rs = comando.executeQuery()
        def lista = []
        while (rs.next()) {
            lista << new Vaga(
                    id: rs.getInt("id"),
                    nome: rs.getString("nome"),
                    descricao: rs.getString("descricao"),
                    local_vaga: rs.getString("local_vaga"),
                    empresa_cnpj: rs.getString("empresa_cnpj")
            )
        }
        rs.close()
        comando.close()
        return lista
    }

    static void atualizarVaga(Vaga vaga) {
        String sql = """UPDATE vagas SET nome = ?, descricao = ?, local_vaga = ?, empresa_cnpj = ?
                        WHERE id = ?"""
        PreparedStatement comando = DBConnection.conexao.prepareStatement(sql)
        comando.setString(1, vaga.nome)
        comando.setString(2, vaga.descricao)
        comando.setString(3, vaga.local_vaga)
        comando.setString(4, vaga.empresa_cnpj)
        comando.setInt(5, vaga.id)
        comando.executeUpdate()
        comando.close()
    }

    static void deletarVaga(int id) {
        String sql = "DELETE FROM vagas WHERE id = ?"
        PreparedStatement comando = DBConnection.conexao.prepareStatement(sql)
        comando.setInt(1, id)
        comando.executeUpdate()
        comando.close()
    }
}
