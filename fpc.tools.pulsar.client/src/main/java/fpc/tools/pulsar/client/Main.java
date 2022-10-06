package fpc.tools.pulsar.client;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

public class Main {

    public static void main(String[] args) throws PulsarClientException {
        var client = PulsarClient.builder().serviceUrl("localhost:8080").build();
    }

}
