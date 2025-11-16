package DAO

import model.Candidato
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class CandidatoDAO {

    private final Connection conexao
    private final CompetenciaDAO competenciaDAO

    CandidatoDAO(Connection conexao) {
        this.conexao = conexao
        this.competenciaDAO = new CompetenciaDAO(conexao)
    }

    void criarCandidato(Candidato candidato) {
        String sql = """INSERT INTO candidatos
            (cpf, nome, sobrenome, email, data_nascimento, formacao, pais, cep, descricao, senha)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"""
        try {
            PreparedStatement comando = conexao.prepareStatement(sql)
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
            fecharRecursos(comando)
        } catch (SQLException e) {
            e.printStackTrace()
        }
    }

    List<Candidato> buscarCandidatos() {
        String sql = "SELECT * FROM candidatos"
        List<Candidato> candidatos = []
        try {
            PreparedStatement comando = conexao.prepareStatement(sql)
            ResultSet rs = comando.executeQuery()

            while (rs.next()) {
                candidatos << criarObjetoCandidato(rs)
            }
            fecharRecursos(comando, rs)
        } catch (SQLException e) {
            e.printStackTrace()
        }
        return candidatos
    }

    private Candidato criarObjetoCandidato(ResultSet rs) {
        def candidato = new Candidato(
                cpf: rs.getString("cpf"),
                nome: rs.getString("nome"),
                sobrenome: rs.getString("sobrenome"),
                email: rs.getString("email"),
                data_nascimento: rs.getDate("data_nascimento"),
                formacao: rs.getString("formacao"),
                pais: rs.getString("pais"),
                cep: rs.getString("cep"),
                descricao: rs.getString("descricao"),
                senha: rs.getString("senha")
        )
        candidato.competencias = competenciaDAO.buscarPorCandidato(candidato.cpf)
        return candidato
    }

    void atualizarCandidato(Candidato candidato) {
        String sql = """UPDATE candidatos SET nome = ?, sobrenome = ?, email = ?,
            data_nascimento = ?, formacao = ?, pais = ?, cep = ?, descricao = ?, senha = ? WHERE cpf = ?"""
        try {
            PreparedStatement comando = conexao.prepareStatement(sql)
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
            fecharRecursos(comando)
        } catch (SQLException e) {
            e.printStackTrace()
        }
    }

    void deletarPorCpf(String cpf) {
        String sql = "DELETE FROM candidatos WHERE cpf = ?"
        try {
            PreparedStatement comando = conexao.prepareStatement(sql)
            comando.setString(1, cpf)
            comando.executeUpdate()
            fecharRecursos(comando)
        } catch (SQLException e) {
            e.printStackTrace()
        }
    }

    static void fecharRecursos(PreparedStatement statement, ResultSet rs = null) {
        try {
            statement?.close()
            rs?.close()
        } catch (SQLException ignored) {}
    }
}
