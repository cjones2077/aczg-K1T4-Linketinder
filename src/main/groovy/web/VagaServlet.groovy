package web

import app.AppContainer
import controller.VagaController
import model.Vaga

import javax.servlet.http.*
import javax.servlet.annotation.WebServlet
import java.sql.SQLException
import com.fasterxml.jackson.databind.ObjectMapper

@WebServlet("/vagas")
class VagaServlet extends HttpServlet {

    private final VagaController vagaController = AppContainer.getInstancia().vagaController

    static {
        try {
            Class.forName("org.postgresql.Driver")
            println "Driver PostgreSQL registrado com sucesso"
        } catch (ClassNotFoundException e) {
            e.printStackTrace()
            throw new RuntimeException("Driver PostgreSQL não encontrado")
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Vaga> lista = vagaController.buscarVagas()

        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")

        ObjectMapper mapper = new ObjectMapper()
        String json = mapper.writeValueAsString(lista)

        resp.getWriter().write(json)
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        def vaga = new Vaga(
                nome: req.getParameter("nome"),
                descricao: req.getParameter("descricao"),
                local_vaga: req.getParameter("local_vaga"),
                empresa_cnpj: req.getParameter("empresa_cnpj")
        )

        try {
            vagaController.criarVaga(vaga)
            resp.getWriter().write("Vaga criada!")
        } catch (SQLException e) {
            e.printStackTrace()
            resp.getWriter().write("Erro ao acessar o banco: " + e.message)
        }
    }
}
