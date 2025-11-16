package web

import app.AppContainer
import controller.EmpresaController
import model.Empresa

import javax.servlet.http.*
import javax.servlet.annotation.WebServlet
import java.sql.SQLException
import com.fasterxml.jackson.databind.ObjectMapper

@WebServlet("/empresas")
class EmpresaServlet extends HttpServlet {

    private final EmpresaController empresaController = AppContainer.getInstancia().empresaController

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
        List<Empresa> lista = empresaController.buscarEmpresas()

        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")

        ObjectMapper mapper = new ObjectMapper()
        String json = mapper.writeValueAsString(lista)

        resp.getWriter().write(json)
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        def empresa = new Empresa(
                cnpj: req.getParameter("cnpj"),
                nome: req.getParameter("nome"),
                email: req.getParameter("email"),
                data_criacao: java.sql.Date.valueOf(req.getParameter("data_criacao")),
                pais: req.getParameter("pais"),
                cep: req.getParameter("cep"),
                descricao: req.getParameter("descricao"),
                senha: req.getParameter("senha")
        )

        try {
            empresaController.criarEmpresa(empresa)
            resp.getWriter().write("Empresa criada!")
        } catch (SQLException e) {
            e.printStackTrace()
            resp.getWriter().write("Erro ao acessar o banco: " + e.message)
        }
    }
}
