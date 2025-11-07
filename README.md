# PetCare – Sistema de Gestão Inteligente para ONGs de Adoção

O **PetCare** é uma plataforma desenvolvida para **ONGs de adoção animal** que desejam modernizar sua gestão e promover **adoções mais responsáveis**.  

Cada ONG pode **criar sua conta individual**, **cadastrar seus pets disponíveis** e gerar **insights personalizados de cuidado** por meio da **integração com OpenAI**, auxiliando potenciais tutores a entender melhor as necessidades de cada animal antes da adoção.

## Objetivo

Promover **adoções conscientes e sustentáveis**, oferecendo às ONGs uma ferramenta que combina **gestão eficiente** e **inteligência artificial** para fornecer informações relevantes sobre cada pet, suas características e cuidados recomendados.

## Integrantes 

- Hellen Marinho Cordeiro, RM 559145, GITHUB: https://github.com/hmarinhoo
- Heloisa Alves de Mesquita, RM 559145, GITHUB: https://github.com/hellomesq

## Tecnologias
- **Java 21 + Spring Boot (MVC)** — arquitetura organizada e escalável.  
- **Thymeleaf** — template engine para renderização de páginas dinâmicas.  
- **Gradle (com gradlew)** — automação de build e gerenciamento de dependências.  
- **Docker Compose** — orquestração dos containers da aplicação e do banco de dados (**PostgreSQL**).  
- **Flyway** — controle de versão e execução automática das migrações SQL.  
- **OpenAI API** — integração para geração inteligente de dicas e insights sobre cuidados dos pets.

## Principais Funcionalidades

- Cadastro e login de ONGs (cada ONG gerencia apenas seus próprios pets).  
- Registro e catálogo de pets para adoção.  
- Geração automática de recomendações de cuidado personalizadas para cada pet (via OpenAI).
- Visualização de pets e dicas para potenciais tutores interessados.  
- Interface web intuitiva e responsiva (via Thymeleaf).  
- Persistência de dados com PostgreSQL. 

---

## 🚀 Como Rodar o Projeto

### Pré-requisitos

- **Java 21** (JDK instalado e configurado)
- **Docker** e **Docker Compose** (para rodar com containers)
- **Chave de API da OpenAI** (necessária para gerar recomendações via IA)

### Variáveis de Ambiente

Crie um arquivo `.env` na raiz do projeto (ou configure diretamente no seu ambiente):

```env
# Banco de dados (para Docker Compose)
POSTGRES_USER=petcare
POSTGRES_PASSWORD=petcarepass
POSTGRES_DB=petcare_db

# OpenAI API Key (obrigatória para funcionalidade de IA)
OPENAI_API_KEY=sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

**Importante:** Substitua `sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx` pela sua chave real da OpenAI ([obtenha aqui](https://platform.openai.com/api-keys)).

---

## Executar Localmente (Desenvolvimento)

### 1. Clonar o repositório

```bash
git clone https://github.com/hellomesq/ONG-PetCare.git
cd ONG-PetCare
```

### 2. Configurar variáveis de ambiente

Defina a chave da OpenAI no terminal (ou adicione ao `.env` e carregue):

**Windows (PowerShell):**
```powershell
$env:OPENAI_API_KEY="sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
```

**Linux/Mac:**
```bash
export OPENAI_API_KEY="sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
```

### 3. Rodar com banco H2 (em memória - para testes rápidos)

O projeto está configurado para usar H2 como fallback caso as variáveis do Postgres não sejam definidas.

```bash
./gradlew bootRun
```

Acesse: [http://localhost:8080](http://localhost:8080)

Console H2: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- JDBC URL: `jdbc:h2:mem:petcare-db`
- Username: `sa`
- Password: (deixe em branco)

---

## Executar com Docker Compose (Produção)

### 1. Configurar variáveis de ambiente

Crie o arquivo `.env` na raiz do projeto com:

```env
POSTGRES_USER=petcare
POSTGRES_PASSWORD=petcarepass
POSTGRES_DB=petcare_db
OPENAI_API_KEY=sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

### 2. Subir os containers (app + Postgres)

```bash
docker compose up --build
```

Isso irá:
- Criar e iniciar o container do **PostgreSQL** (porta 5432)
- Compilar e rodar a aplicação Spring Boot (porta 8080)
- Executar as **migrations do Flyway** automaticamente (criação das tabelas `ong` e `pet`)

Acesse: [http://localhost:8080](http://localhost:8080)

### 3. Parar os containers

```bash
docker compose down
```

Para remover também os volumes (dados do banco):
```bash
docker compose down -v
```

---

## Migrações de Banco de Dados (Flyway)

As migrações estão em `src/main/resources/db/migration/`:

- **V1__init.sql** — Cria as tabelas `ong` e `pet` com relacionamento (FK: `ong_id`)

O Flyway executa automaticamente as migrations ao iniciar a aplicação (configurado em `application.yml`).

---

## Usar a Funcionalidade de IA (OpenAI)

### Como Funciona

O serviço `AiService` gera recomendações personalizadas de cuidado para cada pet cadastrado, usando a API da OpenAI (modelo `gpt-3.5-turbo`).

### Testar a Integração

1. **Cadastre uma ONG** (via formulário ou endpoint `/ong`)
2. **Cadastre um Pet** vinculado à ONG (endpoint `/pet`)
3. **Chame o endpoint de recomendação** (exemplo):

```bash
GET /pet/{id}/recomendacao
```

O serviço irá:
- Buscar os dados do pet (nome, espécie, raça, idade, descrição)
- Enviar um prompt para a OpenAI solicitando 2-3 dicas de cuidado
- Retornar a recomendação gerada pela IA

**Fallback:** Se a chave `OPENAI_API_KEY` não estiver configurada, o serviço retorna uma mensagem padrão sem chamar a API.

---

## Build e Testes

### Build (compilar e gerar JAR)

```bash
./gradlew build
```

O JAR gerado estará em: `build/libs/petcare-ong-0.0.1-SNAPSHOT.jar`

### Rodar apenas os testes

```bash
./gradlew test
```

### Limpar build anterior

```bash
./gradlew clean
```

---

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/petcare/ong/
│   │   ├── config/          # Configurações (RestTemplate, etc.)
│   │   ├── controller/      # Controllers MVC (HomeController, OngController, PetController)
│   │   ├── model/           # Entidades JPA (Ong, Pet)
│   │   ├── repository/      # Repositórios Spring Data JPA
│   │   ├── service/         # Lógica de negócio (OngService, PetService, AiService)
│   │   └── PetcareOngApplication.java
│   └── resources/
│       ├── application.yml  # Configurações do Spring Boot
│       ├── db/migration/    # Scripts Flyway (V1__init.sql)
│       └── templates/       # Templates Thymeleaf (index.html, formOng.html, etc.)
└── test/
    └── java/                # Testes unitários e de integração

docker-compose.yml           # Orquestração de containers (app + Postgres)
Dockerfile                   # Imagem Docker da aplicação
build.gradle                 # Configuração do Gradle e dependências
```

---

## Troubleshooting

### Erro: "Cannot resolve WebClient"
- **Solução:** O projeto agora usa `RestTemplate` (incluído no `spring-boot-starter-web`). Execute `./gradlew clean build` e reinicie a IDE.

### Erro: "OPENAI_API_KEY não definido"
- **Solução:** Configure a variável de ambiente `OPENAI_API_KEY` ou adicione ao `.env` e reinicie o Docker Compose.

### Flyway: "Table already exists"
- **Solução:** Se as tabelas já existirem no banco, o Flyway pode falhar. Para resetar:
  ```bash
  docker compose down -v
  docker compose up --build
  ```

### Porta 8080 já em uso
- **Solução:** Encerre o processo que está usando a porta ou altere a porta no `application.yml`:
  ```yaml
  server:
    port: 8081
  ```

---

## Roadmap / Melhorias Futuras

- [ ] Implementar autenticação e autorização (Spring Security)
- [ ] Adicionar testes unitários para `AiService` (mock da OpenAI)
- [ ] Criar dashboard de administração para ONGs
- [ ] Adicionar upload de fotos dos pets (AWS S3 ou local storage)
- [ ] Implementar busca e filtros avançados de pets
- [ ] Deploy em cloud (Azure, AWS, GCP)

---

## Licença

Este projeto é de uso acadêmico e está licenciado sob a [MIT License](LICENSE).

---

## Contato

Dúvidas ou sugestões? Entre em contato:
- Hellen Marinho: [GitHub](https://github.com/hmarinhoo)
- Heloisa Mesquita: [GitHub](https://github.com/hellomesq)
 
