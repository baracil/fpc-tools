package fpc.tools.pulsar.client;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;

public class Main {

    public static void main(String[] args) throws PulsarClientException {
        try (var client = PulsarClient.builder().serviceUrl("http://localhost:8080").build()) {
            final var producer = client.newProducer(Schema.STRING)
                    .topic("non-persistent://twitch/chat/perococco")
                                       .create();
            producer.send("Hello");
        }
    }

}
