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

# Programação imperativa
- uma sequencia de comandos com objetivo de gerar um resultado
- cada thread so pode realizar uma solicitação
- em operações de I/O, a thread fica aguardando resposta, ou seja, ociosa.
- devido ao bloqueio de threads, minha aplicação tem um número reduzido de solicitações
- alto volume de recursos alocados para buffer, onde ficam as solicitações que estão aguardando, a thread ser liberada para uso

# Programação reativa
- combina a programação funcional, o padrão observer e o padrão iterável
- observamos um fluxo e reage sobre ele

## Diferença entre programação reativa e imperativa
- programação reativa parte do principio da continuação, ou seja, tudo seja assíncrono
- podemos receber mais solicitações
- aumento do nível de concorrência
- para o modelo reativo, precisamos separar o código e pequenas partes
- o entendimento da aplicação, fica mais dificil no modelo reativo

## Unificando programação reativa e imperativa em quarkus
- quando construimos uma aplicação em quarkus, podemos escrever programação reativa e imperativa
- o motor reativo Vert.x (motor utilizado pelo quarkus), possui uma camada de roteamento, onde:
  - caso a programação seja reativa, envie para a camada de I/O
  - caso a programação seja imperativa, envie para thread bloqueante, continue o processamento e depois essa thread notifica sua conclusão, convocando um manipulador de eventos para dar continuação


## Fluxos reativos
- fluxos reativos vão emitir os eventos, quando alguem os assina (subscribe)
- apó a assinatura este envia os eventos (items), falha (após a falha novos itens não serão emitidos, mesmo se houver um fallback) e conclusão (quando não há mais itens a serem enviados).
- o fluxos a serem observados ou assinativos, chamamos de upstream, ou fluxo com eventos originais
- o fluxo resultando é chamado de downstream, ou seja, fluxo recebido/transformado ou modificado.

## Operadores
- operadores na programação reativa, retornam novos fluxos (streams)
- ele observa o fluxo anterior e cria um novo combinando sua lógico sobre os eventos recebidos

## Entidades participantes
- publisher -> quem emite os itens, os eventos
- subscriber -> quem assina o editor ou seja, o publisher, que receberá os eventos
- item ou evento -> contrato que é emitido pelo publisher aos subscribers
- subscriptions -> assinatura ou vinculo do assinante ao publicador
- processor -> ligação entre assinante e publicador, é por ele que ambos se comunicam

### backpressure
- macanismo para não sobrecarregar assinantes lentos
- existem situações onde o publisher emite eventos
rapidamente, mas o assinante não consegui consumir na mesma velocidade
- desta forma o assinante precisa comunicar ao publisher, sua capacidade de consumo, solicitando mais itens quando esta for processada

# Mutiny
- api reativa utilizada pelo quarkus
- foi criada com objetivo de ser mais compreensível que as outras apis de mercado, como reactor ou rxjava
- o motor do quarkus por padrão é reactivo e utiliza o vert.x, onde o mutiny é uma camada acima que lida com esse motor, expondo apis chamada: Uni e Multi
  - Uni -> emite 0 ou 1 evento
  - Multi -> emite 0 ou N eventos
  - ambos os eventos de falha são terminas e claro o complete.
  - Exemplo abaixo de uso:

````
getAllUsers()
       .onItem()
       .transform(user -> user.name().toLowerCase())
       .select().where(name -> name.startsWith("s"))
       .collect().asList()
       .subscribe().with(System.out::println);
````

## Controle de fluxo
### Grupo onOverflow
- quando o número de eventos emitidos é maior que o número de eventos solicitados pelo assinante, Multi emite um estouro

### invoke (invocação)
- para observar eventos
- ideal para registrar logs
- não modifica o evento

### transforming
- ao contrário do invoke, este modifica os eventos e envio o resultado para o assinante downstream.
- Existem algums transforming análogo ao flatmap, como:
  - transformtoMulti
  - transformToUni
  - transformtoMultiAndConcatenate 

### failure
- falha é um evento terminal, ou seja, após este o evento complete e emitido
- no caso do Multi, os demais eventos não serão recebidos
- no Uni, este será substituido pelo evento de fallback para lidar com a falha
- para chamar o invoke ou retry, tem que ser antes da tratativa do erro, conforme o exemplo abaixo:
```
    private static void addUser() {
        Uni.createFrom()
                .item(new User(null))
                .onItem().transform(s -> s.testFail())
                .onFailure().invoke(e -> System.out.println("Ocorreu a falha: " + e.getMessage()))
                .onFailure().retry().atMost(3)
                .onFailure().recoverWithItem(
                        err -> "Usuario não inserido: " + err
                                .getMessage())
                .subscribe()
                .with(
                        item -> System.out.println(item),
                        err -> System.out.println(err)
                );
    }
```

### Combine
- Quando temos 2 fluxos e estes são executados sequencialmente, mas um independe do outro.

### Select
- selecione itens de um Multi, e descarta aqueles que não correspondem ao predicate.
- Existe o:
  - select().where sincrono
  - select().when assíncrono
  - select().distinct (elimita os duplicados e não é aplicado a fluxos infinitos),

# Http reativa
- Por padrão as requisições http no quarkus não são bloqueantes, pois faz uso do seu motor vert.x.
- Como funciona uma requisição em quarkus:
  - servidor recebe a requisição http
  - delega ao quarkus processar essa requisição -> nesse ponto entra em ação a camada de roteamento, que direciona a requisição ao motor reactivo quarkus
  - o quarkus verifica se tem algum interceptador para lidar com segurança ou logs
  - procura o método para lidar com a requisição, com base no path/verbo http presente na mesma
  - ao achar o método, manda processar 
  
- Podemos verificar a pontuação da nossa api, quanto maior mais disponibilidade para ser utilizada será: http://localhost:8080/q/dev/io.quarkus.quarkus-resteasy-reactive/scores
- outro ponto, para eventos infinitos, utilize o @Produces(MediaType.SERVER_SENT_EVENTS)

### Acesso a base de dados
- atualmente o acesso a base de dados, utilizando jdbc, bloqueia a thread participante até que a base de dados de algum retorno
- isso implica algums problemas, como:
  - por mais que tenhamos configurado um pool de conexões (várias threads para acessar a base de dados), dependente do volume de acesso, não teremos mais threads para acessar a base de dados
  - latência de rede, implicando na baixa performance da nossa aplicação.
  
### Acesso a base de dados de forma reativa
- nessa situação uma conexão (thread) pode prover acesso a base de dados, para vários usuários
- não é bloqueio da thread de conexão
- o hibernate reactive provê todos os recursos que existem atualmente, que facilitam a o mapeamento relacional das classes com as tabelas no banco de dados, mas apresenta uma nova camada reativa, no qual utilizamos para programar de forma assíncrona.
- o hibernate reactive do quarkus, vem em conjunto com o Panache, que podemos utilizar de duas formas:
  - repository: similar ao Spring Data, onde temos uma classe que cuida da iteração com a base de dados (sugerido)
  - entity: a entidade gerencia e provê os métodos de iteração com a base de dados

# Mensagens reativas
- mensagem é um envelope que carrega um tipo
- ela pode conter metadas e fornecer métodos de reconhecimento para notificar se o processamento foi bem sucessido ou não.
- as mensagens transitam nos canais, seja eles uma fila ou topic.
-  Os aplicativos enviam e recebem mensagens. Uma mensagem envolve uma carga útil e pode ser estendida com alguns metadados. Com o conector Kafka, uma mensagem corresponde a um registro Kafka.
-  As mensagens transitam nos canais. Os componentes do aplicativo se conectam aos canais para publicar e consumir mensagens. O conector Kafka mapeia canais para tópicos Kafka.
-  Os canais são conectados a back-ends de mensagens usando conectores. Os conectores são configurados para mapear mensagens de entrada para um canal específico (consumido pelo aplicativo) e coletar mensagens de saída enviadas para um canal específico. Cada conector é dedicado a uma tecnologia de mensagens específica. Por exemplo, o conector que lida com Kafka é chamado smallrye-kafka.

# IMPORTANTE
- os nomes dos canais devem possuir nomes diferentes, por exemplo: @Incoming("prices") e @Outgoing("prices"), não vai compilar.
- Olhe a configuração de deserialização e serialização

## Producer
- o quarkus reactiver message existem duas formas de produzirmos uma mensagem

### Emitter
- programamos de forma imperativa mas transmissao e feita de forma reactiva
- utiliza o buffer como tratamento backpressure.

```
    @Channel("transaction-approve")
    @OnOverflow(value = OnOverflow.Strategy.BUFFER)
    private MutinyEmitter<String> producer;

```
### Outgoing
- tem tratamento backpressure automaticamente.
- utilizado via anotação, o retorno do método é embrulhado dentro de uma mensagem
- e iniciado quando a aplicação estiver up
```
@Outgoing("to")
```

## Consumer

### Incoming
- utilizada via anotação, recebendo como parâmetro o nome do canal a ser consumido.
```
@Incoming("to")
```

## Processamento ou meio da campo
- combinamos o consumidor com o produtor, ou seja, consumimos um evento, transformamos e enviamos a outro canal 
```
    @Outgoing("to")
    @Incoming("from")
    Multi<Person> processStream(Multi<String> inputStream) {
        return inputStream.onItem().transform(Person::new);
    }
```

# Acknowledgement (reconhecimento)
- quando trabalhamos com mensageria, o consumidor deve informar ao produtor se o recebimento desta foi realizada com sucesso (acked) ou falha (nacked)
- pois o mesmo pode tomar a decisão de reenviar a mensagem ou manda-la para um dead letter queue(dlq)
- o ack e emitido quando o consumo e efetivado, no exemplo abaixo o ack é emitido após o print na mensagem (obs: no caso de uma corrente, o ack e disparo pelo ultimo consumidor, e vem replicando a cada consumer)

````
@ApplicationScoped
public class HelloMessaging {

    @Outgoing("ticks")
    public Multi<MyMessage> ticks() {
        return Multi.createFrom().ticks()
                .every(Duration.ofSeconds(1))
                .onOverflow().drop()
                .onItem().transform(MyMessage::new);
    }

    @Incoming("ticks")
    @Outgoing("hello")
    public Message<String> hello(final Message<String> tick) {
        return tick.withPayload("Hello " + tick.getPayload());
    }

    @Incoming("hello")
    public void print(final String msg) {
        if (msg.contains("3")) {
            throw new IllegalArgumentException("boom");
        }

        System.out.println(msg);
    }
}
````
- ponto importante: posso trafegar o multi apenas entre os consumers e o ultimo tem que ser o tipo, se o ultimo consumer da cadeia esperar um multi receberá um erro de cast (até o momento ate que acertem esse problema)

## Conectores
- são componentes específicos que mapeiam um canal para algo gerenciado externamente, como um fila ou tópic
- são específicos para um protocolo ou tecnologia
- conectores de entrada: recebem mensagens de canais externos, devem implementar um backpressure do reactive streams e criar uma lógica ack ou nack
- conectores de sáida: estes enviam as mensagens da app para um componente externo, mapeiam para o formato externo e rastreiam o resultado ack ou nack

## Lidando com falhas
- no quarkus temos algumas estratégias quando lidamos com falhas no kafka, que são:

#### throttled (padrão)
- monitora / rastreia os registros recebidos de um consumidor.
- quando a mensagem é processada com sucesso pelo consumidor, o offset e atualizado para o grupo deste.

#### ignorar
- ignora o compromisso de confirmar a entrega da mensagem
- indicado para uso sincrono, quando está habilitado o auto.commit=true
- caso o auto commit esteja falso, qualquer consumidor novo que subir, começará a ouvir as mensagens a partir do offset 0

#### latest
- atualiza o offset depois que cada mensagem for reconhecida, ou seja, o consumir consumiu a mesma com sucesso
- gera problemas de desempenho

## Estratégia de falhas com o conector

##### fail fast
- padrão
- conector é notificado da falha e o aplicativo consumidor e interrompido
- se for transitória, a reinicialização resolve, mas se for no aplicativo, entrará em um loop

##### ignore
- quando não precisamos notificar o produtor que algum deu ruim
- indicado para aplicativos consumidores que ja lidam com falhas de forma intermitente

##### dlq
- mensagens com problemas são encaminhadas para outro tópic, afim de serem analisadas ou tratadas, posteriormente.
- necessita de outro consumir ouvir esse topic dlq para analisar/checar a causa da falha

# Client rest
- o quarkus oferece a api client rest reativa
- diferentes de outros clients, que usam um pool de threads e são bloqueantes, esta é reativa, mas podemos fazer uso de forma bloqueante

## Resiliência no client rest
- lidar com falhas na chamada de serviços remotos é obrigatório
- quarkus oferece alguns recursos atráves da lib smallrye-fault-tolerance (funciona para modelo reactive e imperativo)
- em vez de utilizar a lib salientada acima, podemos utilizar os recursos do mutiny
- abaixo alguns recursos oferecidos pela lib smallrye-fault-tolerance

### fallback
- oferece um resultado alternativo caso ocorra alguma falha na api principal
- exemplo:
````
    @GET
    @Path("/uuid")
    @Fallback(fallbackMethod = "fallback")
    Uni<UUID> getUUID();
    //Uni<Response> getUUID();

    default Uni<UUID> fallback() {
        var u = new UUID();
        u.uuid = java.util.UUID.randomUUID().toString();
        return Uni.createFrom().item(u);
    }
````
- caso precise de mais detalhes, podemos utilizar um handler:
````
    @GET
    @Path("/uuid")
    @Fallback(value =  MyFallback.class)
    Uni<UUID> getUUID();
    //Uni<Response> getUUID();

    class MyFallback implements FallbackHandler<Uni<UUID>> {

        @Override
        public Uni<UUID> handle(ExecutionContext executionContext) {
            System.out.println(executionContext.getFailure());
            var u = new UUID();
            u.uuid = java.util.UUID.randomUUID().toString();
            return Uni.createFrom().item(u);
        }
    }
````

### retries
- processo de retantativa diante a falhas
- utilize para contextos idempotentes

### timeout
- quando uma operação remota não respondeu em tempo hábil
````
    @GET
    @Path("/uuid")
    //@Retry(maxRetries = 3, delay = 10000, jitter = 100)
    @Timeout(value = 3, unit = ChronoUnit.SECONDS)
    @Fallback(value =  MyFallback.class)
    Uni<UUID> getUUID();
````
### circuit breaker
- permite que a chamada ao serviço remoto falhe rápido
- evita ficar efetuando chamadas ao serviço remoto que esteja insalubre
- quando o circuit está fechado, esse é o ambiente normal, efetuando chamadas ao serviço remoto
- quando o circuit está aberto, emitirá uma falha
- quando o circuit está meio aberto, ele tenta algumas vezes chamar o serviço real, caso falhe, ele volta para aberto ou se houver sucesso, vai para fechado.

`````
    @GET
    @Path("/uuid")
    //@Retry(maxRetries = 3, delay = 10000, jitter = 100)
    //@Timeout(value = 3, unit = ChronoUnit.SECONDS)
    //@Fallback(value =  MyFallback.class)
    @CircuitBreaker(
            delayUnit = ChronoUnit.SECONDS,
            //tempo para mudar para semi aberto
            delay = 10,
            //quantidade de requisições com sucesso durante a janela semi-aberta, para fechar o circuito
            successThreshold = 2,
            //percentual de requisições com base no volume informado abaixo, para abrir o circuito
            failureRatio = 0.75,
            requestVolumeThreshold = 10
    )
    Uni<UUID> getUUID();

`````

### bulkheads
- objetivo deste padrão é limitar a propagação de erros
- limita o número de solicitações simultâneas ao serviço remoto
`````
    @Bulkhead(value = 2 (numero maximo de solicitações simultâneas), waitingTaskQueue = 10000 (quantidade máxima de solicitações esperando sua vez, passou desse valor, ele rejeitará))
    Uni<UUID> getUUID();
`````

# Telemetria
- consiste em qualquer informação que coletamos de processos com o propósito de observar um sistema
- Tipos de telemetria:
  - logs: mensagens textuais, geralmente escritas na saído do console ou exportadas em um formato json
  - metrics: mede uma informação específica, como solicitações de servidor http. Existem vários tipos, como: medidor, contador, temporizador e etc.
  - traces: representa uma única solicitação através de um sistema, dividido em operações específicas. 

## Monitoração vs observabilidade
- monitoramento é direcionado a métricas, e comparado a um objetivo. Por exemplo: pedido negado com falha so pode ter no máximo 1, ocorreu emite um alerta
- observabilidade: analisa o comportamento da aplicação, prevê tendências

# kubernetes
## Verificação de vivacidade do pod
- liveness probe: verifica se o pod precisa ser reiniciado
- readiness probe: se o pod pode receber requisições
- startup probe: tempo para a aplicação ficar up
- para atender esses pontos, existe a dependência abaixo:
````
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-smallrye-health</artifactId>
    </dependency>
````

## Metricas
- para expor métricas no formato prometheus, podemos utilizar a lib abaixo:
````
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-micrometer-registry-prometheus</artifactId>
    </dependency>
````
- para expor span/traces, utilizamos:
- e indicamos o local do servidor jaeger: quarkus.opentelemetry.tracer.exporter.jaeger.endpoint=http://simplest-collector.jaeger:14250
````
   <dependency>
      <groupId>io.quarkus</groupId>
     <artifactId>quarkus-opentelemetry-exporter-jaeger</artifactId>
   </dependency>
````
