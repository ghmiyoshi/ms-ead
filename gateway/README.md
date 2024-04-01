# Spring Gateway

## Descrição
Este projeto implementa um gateway de API usando o Spring Cloud Gateway, que atua como uma 
"porta única" para chamadas de microserviços em uma arquitetura de aplicativo distribuído. O gateway centraliza as solicitações de entrada para vários serviços de backend, fornecendo um único ponto de acesso para o frontend e oferece uma maneira programática de declarar as rotas e filtros da API, permitindo uma configuração dinâmica e flexível.

### Começando
Siga estas instruções para ter o gateway executando em sua máquina local para fins de 
desenvolvimento e teste.

### Pré-requisitos
Certifique-se de ter os seguintes softwares instalados:

- Git
- Java JDK 17
- Maven
- Projeto server (Spring Eureka Server)

### Instalação
1. Clone o repositório do projeto:
    ```bash
    git clone https://github.com/ghmiyoshi/ms-ead.git
    ```

2. Navegue até a pasta do projeto e execute o seguinte comando para construir o projeto:
    ```bash
    mvn clean install
    ```

3. Inicie o gateway:
    ```bash
    mvn spring-boot:run
    ```

4. O gateway estará acessível em:
    ```bash
    http://localhost:8080
    ```

Acessando o eureka server http://localhost:8761/ deverá aparecer o registro da instância do gateway.