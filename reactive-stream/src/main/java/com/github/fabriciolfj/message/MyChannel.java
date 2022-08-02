package com.github.fabriciolfj.message;

import com.github.fabriciolfj.Person;
import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.kafka.IncomingKafkaCloudEventMetadata;
import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class MyChannel {

    @Outgoing("my-channel2")
    public Multi<Message<Person>> produceAStreamOfMessagesOfPersons() {
        return Multi.createFrom().items(
                Message.of(new Person("Luke"))
                        .addMetadata(OutgoingKafkaRecordMetadata.builder()
                                .withKey("light").build()),
                Message.of(new Person("Leia"))
                        .addMetadata(OutgoingKafkaRecordMetadata.builder()
                                .withKey("light").build()),
                Message.of(new Person("Palpatine"))
                        .addMetadata(OutgoingKafkaRecordMetadata.builder()
                                .withKey("dark").build()))
                .invoke(() ->  System.out.println("Mensagem enviada"));
    }

    @Incoming("my-channel")
    CompletionStage<Void> consome(Message<Person> personMessage) {
        System.out.println("Mensagem recebida :" + personMessage);
        String msgKey = (String) personMessage.getMetadata(IncomingKafkaCloudEventMetadata.class).get().getKey();
        System.out.println(msgKey);
        return personMessage.ack();
    }
}
