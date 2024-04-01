# Spring Eureka Server

## Descrição
Este projeto configura um servidor Eureka que atua como um serviço de descoberta para 
microserviços. Ele permite que instâncias de serviços se registrem nele e fornece um mecanismo 
para que os serviços descubram a localização de outros serviços por meio do nome da aplicação.

### Começando
Siga estas instruções para ter o servidor Eureka executando em sua máquina local para fins de desenvolvimento e teste.

### Pré-requisitos
Certifique-se de ter os seguintes softwares instalados:

- Git
- Java JDK 17
- Maven

### Instalação
1. Clone o repositório do projeto:
    ```bash
    git clone https://github.com/ghmiyoshi/ms-ead.git
    ```

2. Navegue até a pasta do projeto e execute o seguinte comando para construir o projeto:
    ```bash
    mvn clean install
    ```

3. Inicie o servidor Eureka:
    ```bash
    mvn spring-boot:run
    ```

4. O servidor Eureka estará acessível em:
    ```bash
    http://localhost:8761
    ```

5. O servidor está protegido com autenticação básica. Use as seguintes credenciais:
    ```bash
    Usuário: guest
    Senha: guest
    ```

Acessando a interface web será possível visualizar as 
instâncias dos microserviços que estão registradas no discover server.