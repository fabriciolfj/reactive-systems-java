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
