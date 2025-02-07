# Leasing Project

Para esse projeto você vai precisar de:
- Java 21 para buildar e executar o projeto,
  recomendo o uso do [Jabba](https://github.com/shyiko/jabba);
- Docker e Docker Compose instalados na máquina,
  que caso não tenha pode instalar seguindo o que diz nessa [documentação](https://docs.docker.com/compose/install/).

Antes de startar o projeto vá até a pasta compose
e execute o comando para inicializar o [docker-compose.yml](docker-compose.yml):
```shell
docker compose up -d
```
Esse comando irá subir um banco de dados MySQl que é o banco de dados da aplicação.
Ao iniciar, não é necessário realizar nenhuma migration pois o JPA + Hibernate já
consegue criar as tabelas da base! No futuro podemos usar Flyway mas essa é uma
solução provisoria que deve funcionar.

Informações de como acessar o banco você pode fuçar o [docker-compose.yml](docker-compose.yml) e também
o [application-local.yml](src/main/resources/application-local.yml).

Para rodar a aplicação lembre-se sempre de apontar para o ambiente local!
Para isso, execute a task gradle para limpar a pasta de build e depois realize
o build da aplicação com o comando abaixo:
```shell
./gradlew clean build
```

Após ter o projeto buildado em sua última versão execute o seguinte comando para
gerar o .jar que vamos iniciar o projeto:
```shell
./gradlew bootJar
```
E agora para finalizar de executar o projeto rode o comando:
```shell
java -Dspring.profiles.active=local -jar build/libs/leasing-project-0.0.1.jar
```
Você pode observar que a aplicação está funcionando abrindo o /actuator/health como
mostra o link abaixo:
> http://localhost:8080/actuator/health

## Requests

Nós temos dois recursos principais hoje que são:
- User Resource;
- OKR Resource.

Com o "User Resource" é possível realizar o cadastro de um usuário e gerar um JWT
que autentica o usuário. Só é possível realizar requisições na API de OKR com esse
JWT.

No "OKR Resource" é possível criar um OKR, editar e listar!
Lembrando que todos precisam do header `Authorization: Bearer <valor do JWT gerado>`.

Importe tudo no Postman e começe a usar para entender como o projeto funciona!
Na pasta [./postman-collection](./postman-collection) temos o arquivo para importar
o ambiente "Local", a collection de request para criação e geração de usuários e requests
do recurso de criação, edição e listagem de OKR.

## Executando imagem Docker

Nesse projeto também temos o [Dockerfile](./Dockerfile) que pode gerar uma
imagem do projeto e executada, assim você pode subir containers com essa
aplicação!

Para isso você pode buildar a imagem do projeto usando o seguinte comando:
````shell
docker build -t leasing-project .
````

Isso irá gerar uma imagem do projeto que pode ser executada a qualquer momento
pelo docker.
E para rodar essa imagem execute o seguinte comando:
````shell
docker run -p 8080:8080 leasing-project:latest
````
O argumento `-p 8080:8080` vai expor a porta 8080 no seu localhost e assim você
poderá realizar requisições direto para a máquina.

> Porém, a aplicação pode não rodar por não conseguir acesso ao banco de dados.

## Atualizando a View sem rebuildar o projeto

Para atualizar a view sem rebuildar o projeto você deve usar o comando
command + f9 no MacOS ou ir até o menu sanduiche, no menu superior que
apareceu vá em code e depois clique em build.

## Gerenciamento de versão

Para gerenciar a versão desse projeto nós vamos usar o seguinte esquema:
```shell
M.m.p-[alpha ou rc]
```
Onde:
- **M**: É o Major, só deve ser incrementado quando houve uma mudança de
  arquitetura e isso provavelmente vai afetar o usuário final;
- **m**: É a Minor, e geralmente se trata de uma nova funcionalidade ou
  corração que afeta ou não o cliente mas que muda a estrutura do sistema;
- **p**: Correção simples que via apenas uma correção, melhoria ou ajuste
  não visível ou quase imperceptível pelo cliente.

- **alpha**: Quando o desenvolvedor pega o código para iniciar o
  desenvolvimento ele deve adicionar o valor correspondente ao
  `M.m.p` e adicionar o `-alpha`, no final.
- **rc**: Quando estamos em homologação e integramos todas as funcionalidades,
  testando tudo junto para verificar se tudo está correto e sem problemas! Caso
  sim, seguimos para produção removendo o sufixo `-rc`.
