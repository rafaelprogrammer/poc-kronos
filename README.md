POC - Pós Graduação - Arquitetura de Sistemas Distribuídos - PUC Minas

**Acessando pela nuvem:**

https://web-kronos.herokuapp.com


**Credenciais:**
```
usuário: 64141756687
senha: selva
```
---------------------------------
Rodando o projeto na máquina local

**Execução do projeto:**

**Docker - Na pasta do projeto execute:**
```
docker-compose up
```
**Maven - Na pasta do projeto execute:**

```
1 - mvn clean package
2 - mvn install
3 - mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

**Acesse:**

```
http://localhost:8080
```

**INTEROPERABILIDADE**

- API LISTA ALUNOS FREQUÊNCIA:

curl --location --request GET 'http://localhost:8080/api/frequencias?idDisciplina=146&idAtividade=43703&idSubFaseExecucao=208&anoTurma=2022'

![This is an image](https://github.com/rafaelprogrammer/poc-kronos/blob/main/images/EvidenciaAlunosFrequenciasAPI.png)

````json
[
    {
        "id": 645682,
        "idAtividade": 43703,
        "idCredito": 28949,
        "idPessoa": 5173,
        "aluno": "Aluno 2977",
        "numeroAtividade": 3,
        "frequencia": [
            "P",
            "P",
            "P"
        ]
    },
    {
        "id": 645684,
        "idAtividade": 43703,
        "idCredito": 28977,
        "idPessoa": 4524,
        "aluno": "Aluno 2986",
        "numeroAtividade": 3,
        "frequencia": [
            "P",
            "P",
            "P"
        ]
    }
]
````

- API HABILIDADES AVALIADAS:

curl --location --request GET 'http://localhost:8080/api/avaliacoes/habilidades?idAvaliacao=8482'

![This is an image](https://github.com/rafaelprogrammer/poc-kronos/blob/main/images/EvidenciaHabilidadesDeUmaAvaliacaoAPI.png)

`````json
[
    {
        "id": 17046,
        "idAvaliacao": 8482,
        "idDisciplinaHabilidade": 51410,
        "codigo": "EF03LP01",
        "descricao": "Ler e escrever palavras com correspondências regulares contextuais entre grafemas e fonemas – c/qu; g/gu; r/rr; s/ss; o (e não u) e e (e não i) em sílaba átona em final de palavra – e com marcas de nasalidade (til, m, n)",
        "bncc": true,
        "qtdResultado": 0
    },
    {
        "id": 17047,
        "idAvaliacao": 8482,
        "idDisciplinaHabilidade": 51459,
        "codigo": "EF15LP03",
        "descricao": "Localizar informações explícitas em textos",
        "bncc": true,
        "qtdResultado": 0
    }
]
`````

**USABILIDADE**

- LISTA DE FREQUÊNCIA:

Lista de alunos para registro de frequência. Somente é necessário um clique por aluno para registrar a frequência ou registrar para todos uma determinada frequência.

![This is an image](https://github.com/rafaelprogrammer/poc-kronos/blob/main/images/EvidenciaAlunasFrequenciasUsabilidade.png)

- LISTA DE RESULTADOS DE AVALIAÇÕES POR HABILIDADES:

Registro das avalições por habilidades em uma só tela.

![This is an image](https://github.com/rafaelprogrammer/poc-kronos/blob/main/images/EvidenciaResultadoAvaliacoesPorHabilidadeUsabilidade.png)

**MANUTENABILIDADE**

- DOMÍNIO DIÁRIO (CAMADA - APLICAÇÃO):

Este trecho de código representa o domínio diário e um local que possui fácil acesso para alterações específicas como tamanho dos campos e obrigatoriedade.

![This is an image](https://github.com/rafaelprogrammer/poc-kronos/blob/main/images/EvidenciaDiarioManutenabilidade.png)

- REPOSITÓRIO FREQUÊNCIA (CAMADA - ADAPTADORES):

Classe que representa o repositório frequencia. Esta camada é totalmente isolada do dominio e específica para determinadas tecnologias como exemplo o banco de dados Postgresql. Suas alterações não impactam na camada de domínio (aplicação).

![This is an image](https://github.com/rafaelprogrammer/poc-kronos/blob/main/images/EvidenciaFrequenciasRepositorioManutenabilidade.png)



