# Linketinder - Projeto em Groovy

## 📌 Sobre o Projeto

O **Linketinder** é uma aplicação de console escrita em **Groovy** que simula uma plataforma de recrutamento, combinando funcionalidades de **LinkedIn** e **Tinder**.  
O projeto permite cadastrar candidatos e empresas, listá-los e curtir perfis, simulando interações de uma plataforma de matchmaking profissional.

Além disso, o projeto conta com um **schema SQL** que define as tabelas para armazenar **candidatos, empresas, vagas, competências** e interações, permitindo que a aplicação seja integrada com uma base de dados PostgreSQL.

Este projeto foi desenvolvido como prática de **Programação Orientada a Objetos (POO)** utilizando Groovy.

---

## ⚙️ Funcionalidades

- Listar candidatos cadastrados
- Listar empresas cadastradas
- Cadastrar novos candidatos via console
- Cadastrar novas empresas via console
- Curtir candidatos ou vagas (empresas)
- Rastrear curtidas enviadas
- Integração com banco de dados PostgreSQL via schema SQL

---

## 🛠 Tecnologias Utilizadas

- **Groovy 3+**
- **JDK 11+**
- **PostgreSQL** 
- Console/terminal como interface de usuário

---

## 🚀 Como Executar

### 1. Instalar Groovy

Se não estiver instalado, siga o comando correspondente ao seu sistema operacional:

- **Arch Linux:**
```bash
sudo pacman -S groovy
```

- **Ubuntu/Debian:**
```bash
sudo apt install groovy
```

- Ou baixe a versão oficial: [https://groovy-lang.org/download.html](https://groovy-lang.org/download.html)

---

### 2. Compilar e Executar

No diretório raiz do projeto, rode os seguintes comandos:

```bash
groovyc org/Entity/Candidato.groovy org/Entity/Empresa.groovy Linketinder.groovy
groovy Linketinder
```

> Isso irá compilar as classes e executar a aplicação no console.

---

### 3. Usando o Banco de Dados

1. Crie o banco no PostgreSQL:

```sql
CREATE DATABASE linketinder;
```

2. Execute o **schema SQL** (`schema.sql`) para criar as tabelas:

```bash
psql -U seu_usuario -d linketinder -f schema.sql
```
---

## 👤 Autor

**Carlos Eduardo Paiva Locatelli**
