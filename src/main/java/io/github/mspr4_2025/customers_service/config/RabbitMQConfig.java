package io.github.mspr4_2025.customers_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.*;
import org.springframework.context.annotation.Bean;

@Configuration
public class RabbitMQConfig {
    
    public static final String customersExchange = "customers-topic-exchange";
    public static final String ordersExchange = "orders-exchange";
    public static final String productsExchange = "products-exchange";
    public static final String eventsExchange = "events-exchange";

    public static final String customersQueue = "customers-queue";
    public static final String ordersQueue = "orders-queue";
    public static final String productsQueue = "products-queue";
    public static final String eventsQueue = "events-queue";

    public static final String customersRoutingKey = "customers.key";
    public static final String ordersRoutingKey = "orders.key";
    public static final String eventsRoutingKey = "events.key";

    public static final String customersGetAllRouting = "customers.get.all";
    public static final String customersGetRouting = "customers.get";
    public static final String customersCreateRouting = "customers.create";
    public static final String customersUpdateRouting = "customers.update";
    public static final String customersDeleteRouting = "customers.delete";


    @Bean
    public CachingConnectionFactory cf() {
        return new CachingConnectionFactory("rabbitmq", 5672);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory cf) {
        RabbitTemplate template = new RabbitTemplate(cf);
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory cf) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cf);
        factory.setPrefetchCount(1);
        return factory;
    }

    @Bean
    public RabbitAdmin admin(ConnectionFactory cf) {
        return new RabbitAdmin(cf);
    }

    @Bean
    public TopicExchange customersExchange() {
        return new TopicExchange(customersExchange);
    }

    @Bean
    public TopicExchange ordersExchange() {
        return new TopicExchange(ordersExchange);
    }
    @Bean
    public TopicExchange productsExchange() {
        return new TopicExchange(productsExchange);
    }

    @Bean
    public TopicExchange eventsExchange() {
        return new TopicExchange(eventsExchange);
    }

    @Bean
    public Queue ordersQueue() {
        return QueueBuilder.durable(ordersQueue)
        

        .build();
    }

    @Bean
    public Queue eventsQueue() {
        return QueueBuilder.durable(eventsQueue)
        
        .build();
    }

    @Bean
    public Queue customersQueue(){
        return QueueBuilder.durable(customersQueue)
        
        .build();

    }


   
    @Bean
    public Binding customersCreateBinding(TopicExchange customersExchange, Queue customersQueue) {
        return BindingBuilder.bind(customersQueue).to(customersExchange).with(customersCreateRouting);
    }

    @Bean
    public Binding customersGetAllBinding(TopicExchange customersExchange, Queue customersQueue) {
        return BindingBuilder.bind(customersQueue).to(customersExchange).with(customersGetAllRouting);
    }

    @Bean
    public Binding customersGetBinding(TopicExchange customersExchange, Queue customersQueue) {
        return BindingBuilder.bind(customersQueue).to(customersExchange).with(customersGetRouting);
    }

    @Bean
    public Binding customersUpdateBinding(TopicExchange customersExchange, Queue customersQueue) {
        return BindingBuilder.bind(customersQueue).to(customersExchange).with(customersUpdateRouting);
    }

    @Bean
    public Binding customersDeleteBinding(TopicExchange customersExchange, Queue customersQueue) {
        return BindingBuilder.bind(customersQueue).to(customersExchange).with(customersDeleteRouting);
    }


}
