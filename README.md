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


