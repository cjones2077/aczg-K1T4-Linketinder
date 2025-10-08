# Linketinder - Projeto em Groovy

## Sobre o Projeto

O **Linketinder** é uma aplicação de console escrita em **Groovy** que
simula uma plataforma de recrutamento semelhante ao LinkedIn + Tinder.\
Permite cadastrar **candidatos** e **empresas**, além de listá-los e **curtir perfis**.

Este projeto foi desenvolvido como prática de programação orientada a
objetos com Groovy.

------------------------------------------------------------------------

## Funcionalidades

-   Listar candidatos cadastrados
-   Listar empresas cadastradas
-   Cadastrar novos candidatos via console
-   Cadastrar novas empresas via console
-   **Curtir candidatos ou vagas (empresas)**
-   **Rastrear curtidas enviadas**

------------------------------------------------------------------------

## Tecnologias Utilizadas

-   [Groovy](https://groovy-lang.org/) 3+
-   JDK 11+

------------------------------------------------------------------------

## Como Executar

### 1. Instalar Groovy

Se não tiver instalado, no **Linux (Arch)** rode:

``` bash
sudo pacman -S groovy
```

No **Ubuntu/Debian**:

``` bash
sudo apt install groovy
```

Ou baixe no site oficial: <https://groovy-lang.org/download.html>

------------------------------------------------------------------------

### 2. Compilar e Executar

No diretório raiz do projeto, rode:

``` bash
groovyc org/Entity/Candidato.groovy org/Entity/Empresa.groovy Linketinder.groovy
groovy Linketinder
```

------------------------------------------------------------------------

## Autor

-   Participante: **Carlos Eduardo Paiva Locatelli**
