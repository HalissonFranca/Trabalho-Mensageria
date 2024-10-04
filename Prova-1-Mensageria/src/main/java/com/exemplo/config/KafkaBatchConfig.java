package com.exemplo.config;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import java.util.HashMap;
import java.util.Map;
/**
 * Configuração para o consumidor Kafka que suporta processamento em lote.
 *
 * Esta classe define como o consumidor Kafka se comporta em relação ao processamento em lote,
 * incluindo o número máximo de mensagens que ele pode processar por vez.
 */
@EnableKafka
@Configuration
public class KafkaBatchConfig {
    /**
     * Metodo que cria o `ConsumerFactory`, responsável por configurar e instanciar consumidores Kafka.
     *
     * Aqui, ajustamos a configuração para permitir o consumo de mensagens em lote,
     * especificando a quantidade máxima de mensagens que o consumidor pode processar de uma vez.
     *
     * @return Um `ConsumerFactory` configurado para processamento em lote.
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "batch-grupo");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        //Configurações de Batching no consumidor
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "10");
        return new DefaultKafkaConsumerFactory<>(props);
    }
    /**
     * Cria uma `ConcurrentKafkaListenerContainerFactory` configurada para processamento em lote.
     *
     * Esta fábrica é usada para criar containers que permitem o processamento de mensagens em lote,
     * o que significa que várias mensagens serão processadas em uma única invocação do método do consumidor.
     *
     * @return Uma `ConcurrentKafkaListenerContainerFactory` que suporta processamento em lote.
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setBatchListener(true); //Habilita o consumo em lote
        return factory;
    }
}