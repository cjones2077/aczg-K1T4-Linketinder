package DAO

import model.Competencia
import model.Curtida

import java.sql.*

class CurtidaDAO {
    private final Connection conexao

    CurtidaDAO(Connection conexao) {
        this.conexao = conexao
    }

    List<Curtida> buscarCurtidas() {
        String sql = "SELECT * FROM curtidas"
        ArrayList<Curtida> curtidas = new ArrayList<Competencia>()

        try {
            PreparedStatement comando = conexao.prepareStatement(sql)
            ResultSet curtidasResult = comando.executeQuery()
            while (curtidasResult.next()) {
                curtidas << new Curtida(
                        id: curtidasResult.getInt("id"),
                        cpf_candidato: curtidasResult.getString("cpf_candidato"),
                        id_vaga: curtidasResult.getInt("id_vaga"),
                        cnpj_empresa: curtidasResult.getString("cnpj_empresa") 
                )
            }
            fecharRecursos(comando, curtidasResult)
        } catch (SQLException sqlException) {
            sqlException.printStackTrace()
        }

        return curtidas
    }

    void curtirVaga(String cpfCandidato, int idVaga) {
        String sql = """
            INSERT INTO curtidas (cpf_candidato, id_vaga) VALUES (?, ?)
        """
        try {
            PreparedStatement comando = conexao.prepareStatement(sql)
            comando.setString(1, cpfCandidato)
            comando.setInt(2, idVaga)
            comando.executeUpdate()
            comando.close()

            verificarMatch(cpfCandidato, idVaga)
        } catch (SQLException e) {
            println "Erro ao curtir vaga: ${e.message}"
        }
    }

    void curtirCandidato(String cnpjEmpresa, String cpfCandidato) {
        String sql = """
            INSERT INTO curtidas (cnpj_empresa, cpf_candidato)
            VALUES (?, ?)
        """
        try {
            PreparedStatement comando = conexao.prepareStatement(sql)
            comando.setString(1, cnpjEmpresa)
            comando.setString(2, cpfCandidato)
            comando.executeUpdate()
            comando.close()

            verificarMatch(cpfCandidato, null, cnpjEmpresa)
        } catch (SQLException e) {
            println "Erro ao curtir candidato: ${e.message}"
        }
    }

    // --- Verifica se já existe reciprocidade ---
    private void verificarMatch(String cpfCandidato, Integer idVaga = null, String cnpjEmpresa = null) {
        if (idVaga != null) {
            String sql = """
                SELECT cnpj_empresa FROM curtidas
                WHERE cpf_candidato = ? AND id_vaga = ?
            """
            PreparedStatement comando = conexao.prepareStatement(sql)
            comando.setString(1, cpfCandidato)
            comando.setInt(2, idVaga)
            ResultSet rs = comando.executeQuery()
            if (rs.next()) {
                String empresa = rs.getString("cnpj_empresa")
                criarMatchSeReciproco(cpfCandidato, empresa, idVaga, "candidato")
            }
            comando.close()
        } else if (cnpjEmpresa != null) {
            String sql = """
                SELECT id_vaga FROM curtidas
                WHERE cpf_candidato = ? AND cnpj_empresa = ?
            """
            PreparedStatement comando = conexao.prepareStatement(sql)
            comando.setString(1, cpfCandidato)
            comando.setString(2, cnpjEmpresa)
            ResultSet rs = comando.executeQuery()
            while (rs.next()) {
                int id = rs.getInt("id_vaga")
                criarMatchSeReciproco(cpfCandidato, cnpjEmpresa, id, "empresa")
            }
            comando.close()
        }
    }

    // --- Cria um match se ainda não existe ---
    private void criarMatchSeReciproco(String cpf, String cnpj, int idVaga, String origem) {
        String sqlCheck = """
            SELECT COUNT(*) FROM matches
            WHERE cpf_candidato = ? AND cnpj_empresa = ? AND id_vaga = ?
        """
        PreparedStatement checkCmd = conexao.prepareStatement(sqlCheck)
        checkCmd.setString(1, cpf)
        checkCmd.setString(2, cnpj)
        checkCmd.setInt(3, idVaga)
        ResultSet rs = checkCmd.executeQuery()
        rs.next()
        int existe = rs.getInt(1)
        checkCmd.close()

        if (existe == 0) {
            String insert = """
                INSERT INTO matches (cpf_candidato, cnpj_empresa, id_vaga, origem)
                VALUES (?, ?, ?, ?)
            """
            PreparedStatement insertCmd = conexao.prepareStatement(insert)
            insertCmd.setString(1, cpf)
            insertCmd.setString(2, cnpj)
            insertCmd.setInt(3, idVaga)
            insertCmd.setString(4, origem)
            insertCmd.executeUpdate()
            insertCmd.close()
            println "🔥 Match criado entre candidato ${cpf} e empresa ${cnpj} na vaga ${idVaga}!"
        }
    }

    void deletarCurtida(int id) {
        String sql = "DELETE FROM curtidas WHERE id = ?"
        try {
            PreparedStatement comando = conexao.prepareStatement(sql)
            comando.setInt(1, id)
            comando.executeUpdate()
            fecharRecursos(comando)
        } catch (SQLException sqlException) {
            sqlException.printStackTrace()
        }

    }
    static void fecharRecursos(PreparedStatement statement, ResultSet competenciasResult = null) {
        statement.close()
        competenciasResult?.close()
    }
}
