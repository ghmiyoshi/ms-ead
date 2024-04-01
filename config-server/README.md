# Spring Config Server

## Descrição
O Spring Config Server é uma aplicação centralizada que gerencia as configurações externas para aplicações construídas com o ecossistema Spring. Este projeto fornece uma solução de armazenamento de configurações que permite a atualização dinâmica de configurações sem a necessidade de reiniciar os serviços. Ele suporta integração com sistemas de controle de versão como Git, permitindo que as configurações sejam versionadas e auditadas.

### Começando
Siga estas instruções para ter o config server executando em sua máquina local para fins de 
desenvolvimento e teste.

### Pré-requisitos

- Java JDK 17
- Maven
- Um repositório de controle de versão suportado (por exemplo, Git) para armazenar os arquivos de configuração

### Instalação

Para instalar e executar o Spring Config Server, siga estes passos:

1. Clone o repositório do projeto:
   ```shell
   git clone https://github.com/ghmiyoshi/ms-ead.git
   ```
   
2. Navegue até a pasta do projeto e execute o seguinte comando para construir o projeto:
    ```bash
    mvn clean install
    ```

3. Inicie o config server:
    ```bash
    mvn spring-boot:run
    ```

4. O config server estará acessível em:
    ```bash
    http://localhost:8888
    ```
5. Exemplos de rotas:
    ```bash
    http://localhost:8888/application/development
    http://localhost:8888/ead-authuser/development
    ```