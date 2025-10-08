package DAO
import Persistence.DBConnection
import org.Entity.Candidato

import java.sql.PreparedStatement
import java.sql.ResultSet


class CandidatoDAO {
    static void criar(Candidato candidato) {
        String sql = """INSERT INTO candidatos
            (cpf, nome, sobrenome, email, data_nascimento, formacao, pais, cep, descricao, senha)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"""
        PreparedStatement comando = DBConnection.conexao.prepareStatement(sql)
        comando.setString(1, candidato.cpf)
        comando.setString(2, candidato.nome)
        comando.setString(3, candidato.sobrenome ?: "")
        comando.setString(4, candidato.email)
        comando.setDate(5, new java.sql.Date(candidato.data_nascimento.time))
        comando.setString(6, candidato.formacao ?: "")
        comando.setString(7, candidato.pais ?: "")
        comando.setString(8, candidato.cep)
        comando.setString(9, candidato.descricao)
        comando.setString(10, candidato.senha)
        comando.executeUpdate()
        comando.close()
    }

    static List listarTodos() {
        String sql = "SELECT * FROM candidatos"
        PreparedStatement comando = DBConnection.conexao.prepareStatement(sql)
        ResultSet rs = comando.executeQuery()
        def lista = []
        while(rs.next()) {
            lista << [
                    cpf: rs.getString("cpf"),
                    nome: rs.getString("nome"),
                    sobrenome: rs.getString("sobrenome"),
                    email: rs.getString("email"),
                    dataNascimento: rs.getDate("data_nascimento"),
                    formacao: rs.getString("formacao"),
                    pais: rs.getString("pais"),
                    cep: rs.getString("cep"),
                    descricao: rs.getString("descricao")
            ]
        }
        rs.close()
        comando.close()
        return lista
    }

    static void atualizarCandidato(Candidato candidato) {
        String sql = "UPDATE candidatos SET nome = ?, sobrenome = ?, email = ?," +
                " data_nascimento = ?, formacao = ?, pais = ?, cep = ?, descricao = ?, senha = ? WHERE cpf = ?"
        def comando = DBConnection.conexao.prepareStatement(sql)
        comando.setString(1, candidato.nome)
        comando.setString(2, candidato.sobrenome)
        comando.setString(3, candidato.email)
        comando.setDate(4, new java.sql.Date(candidato.data_nascimento.time))
        comando.setString(5, candidato.formacao)
        comando.setString(6, candidato.pais)
        comando.setString(7, candidato.cep)
        comando.setString(8, candidato.descricao)
        comando.setString(9, candidato.senha)
        comando.setString(10, candidato.cpf)
        comando.executeUpdate()
        comando.close()
    }

    static void deletarCandidato(cpf) {
        String sql = "DELETE FROM candidatos WHERE cpf = ?"
        PreparedStatement comando = DBConnection.conexao.prepareStatement(sql)
        comando.setString(1, cpf)
        comando.executeUpdate()
        comando.close()
    }
}
