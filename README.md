# Order Processing System

Este projeto é um sistema de processamento de pedidos construído com **Java 17**, **Spring Boot**, **Docker**, e **RabbitMQ**. Ele é responsável por receber pedidos via RabbitMQ, salvá-los no MongoDB, enviá-los para um serviço de pagamento, e posteriormente processar as respostas do pagamento e atualizar as informações no banco.

---

## Funcionalidades

1. **Receber Pedidos**:
    - O sistema consome mensagens da fila `order_queue` (via RabbitMQ) representando novos pedidos.
    - Após consumir, o pedido é salvo no MongoDB.

2. **Enviar para Pagamento**:
    - Após salvar o pedido, ele é enviado para a fila `order_payment_queue` para processamento por um serviço de pagamento externo.

3. **Receber Resposta de Pagamento**:
    - O sistema consome mensagens da fila `payment_queue` contendo informações sobre o status do pagamento.

4. **Atualizar Banco de Dados**:
    - Após processar a resposta do pagamento, o pedido é atualizado no MongoDB com o status 'paid'.

---

## Tecnologias Utilizadas

- **Linguagem**: Java 17
- **Framework**: Spring Boot
- **Mensageria**: RabbitMQ
- **Persistência**: MongoDB (com Spring Data MongoDB)
- **Containerização**: Docker
- **Gerenciamento de Dependências**: Maven
- **Testes**: Testes Unitários com JUnit 5 e Mockito.


---

## Pré-requisitos

- **Java 17**
- **Docker e Docker Compose**
- **RabbitMQ** (ou configurado via Docker Compose)
- **MongoDB** (ou configurado via Docker Compose)

---

## Estrutura do Projeto

### Pacotes Principais

- **controller**: Endpoints REST para interagir com o sistema.
- **service**: Contém a lógica de negócio.
- **repository**: Interfaces para acesso ao banco de dados usando Spring Data MongoDB.
- **listener**: Consumidores de mensagens RabbitMQ.
- **requests**: DTOs usados para enviar e receber dados.
- **entity**: Representações dos documentos no MongoDB.
- **exception**: Tratamento de exceções personalizadas.

---

## Como Rodar o Projeto

### 1. Clonar o Repositório

```bash
git clone https://github.com/GabrielJacobina/order-service.git
cd order-service
```

### 2. Subir os serviços locais com Docker Compose:**
   ```bash
   docker-compose up - d
   ```
