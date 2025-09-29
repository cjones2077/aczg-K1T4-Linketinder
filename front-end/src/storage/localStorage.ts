import { Candidato } from "../models/candidato";
import { Empresa } from "../models/empresa";

export function recuperarLocalStorageCandidatos(): Candidato[] {
    const dados = localStorage.getItem("candidatos");
    if (!dados) return [];

    const array = JSON.parse(dados) as any[];
    return array.map(obj => new Candidato(
        obj.nome,
        obj.email,
        obj.cep,
        obj.descricao,
        obj.estado,
        obj.competencias,
        obj.formacao,
        obj.cpf,
        obj.idade,
        obj.numero,
        obj.linkedin
    ));
}

export function recuperarLocalStorageEmpresas(): Empresa[] {
    const dados = localStorage.getItem("empresas");
    if (!dados) return [];

    const array = JSON.parse(dados) as any[];
    return array.map(obj => new Empresa(
        obj.nome_empresa,
        obj.email,
        obj.cep,
        obj.descricao,
        obj.estado,
        obj.competencias,
        obj.cnpj,
        obj.pais,
        obj.nome_vaga
    ));
}

export function salvarLocalStorage<T>(chave: string, dados: T[]) {
  localStorage.setItem(chave, JSON.stringify(dados));
}