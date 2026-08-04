# 🚀 Estudo de Mensageria Distribuída: Kafka & Spring Boot

Este repositório faz parte de um estudo prático sobre como distribuir mensagens entre aplicações utilizando o ecossistema **Apache Kafka** em conjunto com persistência em banco de dados **PostgreSQL**.

## 🏗️ Arquitetura e Tecnologias

O projeto utiliza uma estrutura moderna baseada em containers para garantir a escalabilidade e facilidade de configuração:

* **[Apache Kafka](https://kafka.apache.org/):** Software de código aberto que atua como uma plataforma de streaming de eventos, permitindo a produção e consumo de mensagens de forma robusta e resiliente.
* **KRaft (Kafka Raft):** Gerente de metadados do cluster. Ele substitui o antigo ZooKeeper, coordenando o estado do Kafka de forma nativa e simplificada.
* **PostgreSQL:** O Sistema Gerenciador de Banco de Dados (SGBD) onde as informações são persistidas.

## 🔄 Fluxo da Aplicação

O objetivo central é demonstrar a comunicação assíncrona entre dois serviços distintos:

1.  **Ação do Usuário:** Uma API (Producer) recebe um request com `name` e `lastName`.
2.  **Persistência Inicial:** O serviço salva o usuário no banco de dados com as colunas: `userId`, `name`, `lastName` e `createdAt`.
3.  **Produção de Evento:** A aplicação envia uma mensagem para um **tópico** específico no servidor Kafka.
4.  **Consumo (Consumer):** O serviço [Consumer](https://github.com/Danilo-dps/dps-kakfa-consumer) monitora o tópico e, ao detectar o evento, inicia o processamento.
5.  **Transformação de Dados:** O Consumer chama um método para criar um novo registro em outra tabela, consolidando os dados para: `userId`, `fullName` e `createdAt`.

## 🛠️ Infraestrutura (Docker)

Para rodar o ambiente de estudos, utilizamos o Docker Compose para subir todos os serviços de forma orquestrada.

| Serviço | Papel |
| :--- | :--- |
| **Kafka (KRaft mode)** | Broker de mensagens |
| **PostgreSQL** | Armazenamento dos dados |

> Confira o [arquivo docker-compose.yaml](https://github.com/Danilo-dps/docker-yamls/blob/main/projetos-com-kafka/docker-compose.yaml) utilizado para configurar este ambiente.

---
*Estudo focado na implementação de padrões de produção e consumo de mensagens entre serviços.*