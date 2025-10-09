package controllers

import DAO.EmpresaDAO
import org.Entity.Empresa

class EmpresaController {
    EmpresaDAO empresaDAO = new EmpresaDAO()

    void listarEmpresas() {
        println "\n-- Empresas --"
        empresaDAO.listarTodasEmpresas().eachWithIndex { e, idx ->
            println "${idx + 1} - ${e.nome} | CNPJ: ${e.cnpj} | Email: ${e.email}"
        }
    }

    void cadastrarEmpresa() {
        def reader = System.in.newReader()
        print "Nome: "; String nome = reader.readLine()
        print "Email: "; String email = reader.readLine()
        print "CEP: "; String cep = reader.readLine()
        print "Descrição: "; String descricao = reader.readLine()
        print "CNPJ: "; String cnpj = reader.readLine()
        print "País: "; String pais = reader.readLine()
        print "Estado: "; String estado = reader.readLine()
        def empresa = new Empresa(nome: nome, email: email, cep: cep, descricao: descricao,
                cnpj: cnpj, pais: pais, estado: estado)
        empresaDAO.criarEmpresa(empresa)
        println "Empresa cadastrada!"
    }

    void atualizarEmpresa() {
        def reader = System.in.newReader()
        List<Empresa> empresas = empresaDAO.listarTodasEmpresas()
        listarEmpresas()
        print "Escolha índice da empresa: "; int idx = reader.readLine().toInteger() - 1
        Empresa e = empresas.get(idx)
        print "Nome [${e.nome}]: "; String nome = reader.readLine(); if(nome) e.nome = nome
        print "Email [${e.email}]: "; String email = reader.readLine(); if(email) e.email = email
        empresaDAO.atualizarEmpresa(e)
        println "Empresa atualizada!"
    }

    void deletarEmpresa() {
        def reader = System.in.newReader()
        List<Empresa> empresas = empresaDAO.listarTodasEmpresas()
        listarEmpresas()
        print "Escolha índice da empresa: "; int idx = reader.readLine().toInteger() - 1
        Empresa e = empresas.get(idx)
        empresaDAO.deletarEmpresa(e.cnpj)
        println "Empresa deletada!"
    }
}
