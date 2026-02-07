package com.danilodps.kakfaproducer.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);          // Número mínimo de threads ativas
        executor.setMaxPoolSize(10);          // Número máximo de threads no pool
        executor.setQueueCapacity(25);        // Capacidade da fila para tarefas pendentes
        executor.setThreadNamePrefix("executor-"); // Prefixo para os nomes das threads
        executor.initialize();
        return executor;
    }
}
