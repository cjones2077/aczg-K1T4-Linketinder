# Banco de Dados: Sistema de Recrutamento

Este projeto define um banco de dados relacional para o sistema de recrutamentos **Linketinder**, permitindo o gerenciamento de **candidatos, empresas, vagas e competências**.  
O modelo foi projetado para armazenar informações de usuários, suas formações, vagas de emprego e as relações entre eles.

---

## 🧩 Estrutura das Tabelas

- **candidatos**: contém informações pessoais e profissionais dos candidatos.  
- **empresas**: armazena dados cadastrais das empresas.  
- **vagas**: define as oportunidades de emprego oferecidas por cada empresa.  
- **competencias**: lista de habilidades e competências.  
- **paises**: lista de países, relacionados a candidatos e empresas.  
- **candidatos_competencias**: relação N:N entre candidatos e competências.  
- **empresas_competencias**: relação N:N entre empresas e competências.  
- **empresa_curte_candidato**: relação entre empresas e candidatos curtidos.  
- **candidato_curte_vaga**: relação entre candidatos e vagas curtidas.

---

## 🧠 Modelo Conceitual

Há um **diagrama DER** disponível em PDF neste projeto, que representa graficamente todas as entidades e seus relacionamentos.

---

## 💾 Instruções de Uso

1. Crie o banco de dados no PostgreSQL:
   ```sql
   CREATE DATABASE recrutamento;
   ```

2. Execute o script SQL com a estrutura:
   ```bash
   psql -U seu_usuario -d recrutamento -f commands.sql
   ```

3. Para popular as tabelas:
   ```bash
   psql -U seu_usuario -d recrutamento -f populate.sql
   ```

---

## 👥 Autores

- Carlos Eduardo Paiva Locatelli  
