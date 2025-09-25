import { Usuario } from "./usuario";

// <!--  nome email CEP descricao cnpj pais estado competencias -->
export class Empresa extends Usuario {
    constructor(
        nome_empresa: string,
        email: string,
        cep: string,
        descricao: string,
        estado: string,
        competencias: string[],
        public cnpj: string,
        public pais: string,
        public nome_vaga: string
    ) {
        super(nome_empresa, email, cep, descricao, estado, competencias);
    }

}
