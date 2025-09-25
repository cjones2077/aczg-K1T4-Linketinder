import { Usuario } from "./usuario";

export class Candidato extends Usuario {
    constructor(
        nome: string,
        email: string,
        cep: string,
        descricao: string,
        estado: string,
        competencias: string[],
        public formacao: string,
        public cpf: string,
        public idade: number,
    ) {
        super(nome, email, cep, descricao, estado, competencias);
    }
}
