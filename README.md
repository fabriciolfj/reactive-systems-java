# Reactive Systems Java
- reatividade tem como objetivo construir sistemas melhor distribuídos
- Sistemas que responde a estímulos
- aplicações responsivas, resistentes e elásticas
- a essência de ser reativo e ser assíncrono.

## Assíncrono
- o aplicativo precisa usar código assíncrono e I/O não bloqueante
- é essencial para fazer mais com recursos limitados
- abordar uma arquitetura orientada a eventos

## Funcionamento quarkus
- o start de um app quarkus é rapida devido:
  - as peças necessárias na inicialização da app, são quebradas em partes pequenas
  - com essas peças menores, o gradle ou maven as utiliza durante o processo de compilação(build time), em vez de esperar o tempo de execução (runtime)
  - utilizar essas classes (peças) durante o processo de construção, significa que elas não seram incluídas na jvm em tempo de execução
  - poupa tempo e memória
  - isso é possível pois o quarkus, com suas extenções, constrói tudo em bytecode
  - fazendo com que a jvm carregue as classes escritas em bytecode, sem custo de memória e de classes para fazer o trabalho de startup
  - quarkus efetua a leitura de configuração, durante o processo de compilação
  - otimiza o código do aplicativo e dependências

# Nativo
- O que é ser nativo? Significa que o código do aplicativo foi compilado para instruções de baixo nível, para um sistema operacional específico.
- Anteriormente as aplicações java necessitavam de uma jvm, hoje não, graças ao projeto Graalvm

## Etapas de construção não nativo
- as etapas a abaixo, são necessárias para o processo de inicilização:
  - aumento: construção de descriptors, annotations e classes da aplicação, gerando um bytecode contendo os metadados necessários. Essa fase é executada dentro de um processo de construção em uma jvm
  - static initialization: executa todas as etapas, para capturar o bytecode
  - runtime initilization: executa o principal método do aplicativo

## Etapas de construção nativo
- O que muda e a partir da static initilization salientada acima, onde:
 - a inicialização static e realizada em 2 partes
   - 1 inicialização estática e feita junto com a etapa de construção do bytecode
   - 2 retira-se as classes que foram utilizadas no processo de construção (não necessárias no app), do executável nativo.

# Mensagens assíncronas e eventos
- passagem de mensagens é a essência dos sistemas reativos
- através da passagem de mensagens, conseguimos: elasticidade, resiliência e responsividade.
- promove o desacoplamento do espaço e tempo, tornando o sistema mais robusto
- mensagens assíncronas e eventos, nem sempre são a mesma coisa.

#### Evento
- evento representa um fato, algo que aconteceu. ex: toque de tecla, uma falha e etc. Pode ser o resultado de um comando.
  - imagine uma resposta http, esse é um evento
  - o evento pode ser transmitido para interessados, saber o que aconteceu.
  - eventos são imutáveis, ou seja, não posso excluir
  - para refutar um fator, preciso disparar outro evento invalidando-o 

#### Mensagem assíncrona
- mensagem é uma estrutura de dados que descreve o evento, e quaisquer detalhes relevantes sobre o mesmo, como: quem emitiu, em que momento e etc.
- mensagens também podemos transmitir comandos, ou seja, ações.

# Dissociação do tempo
- a possibilidade de um componente emitir mensagens ao broker, sem se preocupar quando serão consumidas
- pode ocorrer do consumidor da mensagem estar indisponível, no entanto quando uma instância ficar up, este receberam os eventos

# Sem bloqueio
- a troca de mensagens deve ser eficiente, ou seja, sem bloqueio de threads
- a criação e gerenciamento de threads para lidar com requisições (de forma assíncrona), não é eficiente, pois isso carreta:
  - aumento de consumo de cpu
  - demora no processamento devido I/O
  - se limita ao número de threads que você pode criar

## IO não bloqueante
- as solicitações são enfileiradas, e processadas no futuro
- quando estiver pronto o processamento, emite-se uma resposta ao requisitante.
- podemos utilizar 1 thread para lidar com várias solicitações
- recomenda-se utilizar o pattern reactor, onde:
  -  recebe-se os eventos de vários canais
  -  os distribui sequencialmente para os manipuladores de eventos correspondentes.
  -  quando é concluída a execução, outro loop de eventos é registrado para dar a resposta a outro manipulador
   
### Detalhando o pattern reactor
- camada inferior -> implementa o modelo de loop de eventos, lida e gerencia a parte de I/O não bloqueante
- segunda camada -> fornece apis de alto nível, para usar e escrever seu código
- camada superior -> temos a nossa aplicação
- obs: nunca bloqueie uma thread, pois isso trava todo o loop de eventos.

# Projeto loom
- adiciona o conceito de threads virtuais
- uma única thread pode ter milhares de threads virtuais
- o projeto loom gerencia a thread virtual e ele não bloqueia a thread principal
- como funciona:
  - você programa de forma síncrona
  - em background o código bloqueante e delegado a uma thread virtual
  - se esse código chama outro serviço bloqueante, a thread virtual fica em standby e é delegada a outra thread virtual responsável por aguardar a resposta
  - com a reposta é encaminhada a thread virtual e essa encaminha a thread principal. 
