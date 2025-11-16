package view

import controller.EmpresaController
import model.Empresa
import utils.ConsoleInputReader
import utils.MenuUtils

class EmpresaView {
    EmpresaController empresaController
    ConsoleInputReader reader = new ConsoleInputReader()
    List<String> opcoes = new ArrayList<String>()

    void menuDeOpcoes(){
        int opt = MenuUtils.mostrarMenu(["Listar Empresas",
                                         "Cadastrar Empresa",
                                         "Atualizar Empresa",
                                         "Deletar Empresa",
                                         "Sair"])
        switch (opt + 1) {
            case "1": mostrarEmpresas(empresaController.buscarEmpresas()); break
            case "2": empresaController.cadastrarEmpresa(lerEmpresa()); break
            case "3": atualizarEmpresa(); break
            case "4": deletarEmpresa(); break
            case "5": return
        }
    }

    void atualizarEmpresa() {
        mostrarEmpresas(empresaController.buscarEmpresas())
        Empresa e = lerIndiceEmpresa()

        String nome = reader.readLine("Nome [${e.nome}]: "); if (nome) e.nome = nome
        String email = reader.readLine("Email [${e.email}]: "); if (email) e.email = email
        String pais = reader.readLine("País [${e.pais}]: "); if (pais) e.pais = pais
        String cep = reader.readLine("CEP [${e.cep}]: "); if (cep) e.cep = cep
        String descricao = reader.readLine("Descrição [${e.descricao}]: "); if (descricao) e.descricao = descricao
        String senha = reader.readLine("Senha [${e.senha}]: "); if (senha) e.senha = senha
        print "Data de criação[${e.data_criacao}]: "; String data = reader.readLine(); if (data) e.data_criacao = java.sql.Date.valueOf(data)


        empresaController.atualizarEmpresa(e)
        println "Empresa atualizada!"
    }


    static void mostrarEmpresas(List<Empresa> empresas) {
        println "\n-- Empresas --"
        empresas.eachWithIndex { e, idx ->
            println "${idx + 1} - ${e.nome} | CNPJ: ${e.cnpj} | Email: ${e.email}"
        }
    }

    Empresa lerIndiceEmpresa() {
        List<Empresa> candidatos = empresaController.buscarEmpresas()
        mostrarEmpresas(candidatos)
        int idx = reader.readLine("Escolha índice do empresa: ").toInteger() - 1
        Empresa e = candidatos.get(idx)
        return e
    }

    void deletarEmpresa() {
        Empresa e = lerIndiceEmpresa()
        empresaController.deletarEmpresa(e.cnpj)
        println "Empresa deletada!"
    }

    Empresa lerEmpresa() {
        Empresa empresa

        String nome = reader.readLine("Nome: ")
        String email = reader.readLine("Email: ")
        print "Data de criacao (AAAA-MM-DD): "; String data = reader.readLine()
        String cep = reader.readLine("CEP: ")
        String descricao = reader.readLine("Descrição: ")
        String cnpj = reader.readLine("CNPJ: ")
        String pais = reader.readLine("País: ")
        print "Senha: "; String senha = reader.readLine()

        empresa = new Empresa(
                nome: nome,
                email: email,
                data_criacao: data,
                cep: cep,
                descricao: descricao,
                cnpj: cnpj,
                pais: pais,
                senha: senha
        )

        return empresa
    }

    EmpresaView(EmpresaController empresaController) {
        this.empresaController = empresaController
    }
}
