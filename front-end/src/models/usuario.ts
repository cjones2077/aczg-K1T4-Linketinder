export class Usuario {
    constructor(
        public nome: string,
        public email: string,
        public CEP: string,
        public descricao: string,
        public estado: string,
        public competencias: string[]
    ) {}
}