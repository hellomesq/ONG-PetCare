# PetCare – Sistema de Gestão Inteligente para ONGs de Adoção

O **PetCare** é uma plataforma desenvolvida para **ONGs de adoção animal** que desejam modernizar sua gestão e promover **adoções mais responsáveis**.  

Cada ONG pode **criar sua conta individual**, **cadastrar seus pets disponíveis** e gerar **insights personalizados de cuidado** por meio do **Spring AI**, auxiliando potenciais tutores a entender melhor as necessidades de cada animal antes da adoção.

## Objetivo

Promover **adoções conscientes e sustentáveis**, oferecendo às ONGs uma ferramenta que combina **gestão eficiente** e **inteligência artificial** para fornecer informações relevantes sobre cada pet, suas características e cuidados recomendados.

## Integrantes 

- Hellen Marinho Cordeiro, RM 559145, GITHUB: https://github.com/hmarinhoo
- Heloisa Alves de Mesquita, RM 559145, GITHUB: https://github.com/hellomesq

## Tecnologias
- **Java + Spring Boot (MVC)** — arquitetura organizada e escalável.  
- **Thymeleaf** — template engine para renderização de páginas dinâmicas.  
- **Gradle (com gradlew)** — automação de build e gerenciamento de dependências.  
- **Docker Compose** — orquestração dos containers da aplicação e do banco de dados (**PostgreSQL**).  
- **Flyway** — controle de versão e execução automática das migrações SQL.  
- **Spring AI** — integração com a OpenAI para geração inteligente de dicas e insights sobre cuidados dos pets.

## Principais Funcionalidades

- Cadastro e login de ONGs (cada ONG gerencia apenas seus próprios pets).  
- Registro e catálogo de pets para adoção.  
- Geração automática de recomendações de cuidado personalizadas para cada pet.
- Visualização de pets e dicas para potenciais tutores interessados.  
- Interface web intuitiva e responsiva (via Thymeleaf).  
- Persistência de dados com PostgreSQL. 
