# Exemplo de estudo sobre como distribuir mensagens via kafka
- [Kafka](https://kafka.apache.org/41/getting-started/) é um **software** de código aberto que permite que aplicações se conectem ao kafka para **produzir/consumir** mensagens
- Kafka é um servidor de mensageria robusto no seu tratamento de **produção/consumo** de mensagens
- Tem uma estrutura configurável de como ele deve se comportar, garante distribuição, e confirmação de que algo foi processado ou não
- Este projeto aqui é um producer, que envia para o [consumer](https://github.com/Danilo-dps/dps-kakfa-consumer)
- Nesse exemplo, essa pequena API tem um endpoint para criar um usuário no banco de dados, onde o **request** tem nome, e sobrenome
- Na camada do serviço que faz a persistência do dado, é chamado o método que envia a mensagem para o **tópico** que está no servidor kafka conectado a aplicação
- Para esse exemplo de estudos, é feito uso de um container kafka, e nesse container é criado o tópico que recebe as mensagens
- Quando o evento chega no tópico, o serviço conectado a ele ([consumer](https://github.com/Danilo-dps/dps-kakfa-consumer)) é acionado para consumir 
- O fluxo atual é bem simples, na API que está o producer é criado uma tabela que tem as seguintes colunas: **userId**, **name**, **lastName** e **createdAt**
- O [consumer](https://github.com/Danilo-dps/dps-kakfa-consumer) por sua vez tem um serviço que ao consumir essa mensagem, chama um método responsável por criar outro registro em outra tabela no mesmo banco de dados,
 onde é feito uma persistência de dados com as seguintes colunas: **userId**, **fullName** e **createdAt**
- O foco nesse exemplo é mostrar a produção e consumo de mensagens entre serviços