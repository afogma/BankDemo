package ru.fogma.demobank.BankDemo.db;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.fogma.demobank.BankDemo.model.TransactionDTO;
import ru.fogma.demobank.BankDemo.service.TransactionService;


import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = {AccountRepositoryTest.Initializer.class})
public class AccountRepositoryTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountRepository accountRepository;

    private final UUID sourceUUID = UUID.fromString("43e8a3e9-56ad-4217-87a1-17e6999ddfed");
    //    private final UUID sourceUUID = UUID.fromString("43923e92-5d18-49de-91a8-0fb28bfa0d08");
    private final UUID targetUUID = UUID.fromString("eafcdcd1-8d74-4096-8d8a-fca03d6aebe6");
//    private final UUID targetUUID = UUID.fromString("b9661cce-b839-4d78-be7a-fc865480fedc");

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres")
            .withDatabaseName("banking")
            .withUsername("postgres")
            .withPassword("pgadmin");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    public void should_throw_optimistic_lock_exception() throws InterruptedException {

        TransactionDTO transactionDTO =  new TransactionDTO(sourceUUID, targetUUID, new BigDecimal("1"));

        final Account acc = accountRepository.save(new Account());
        ExecutorService executor = Executors.newFixedThreadPool(10);
        assertEquals(0, acc.getVersion());

        for (int i = 0; i < 10; i++) {
            executor.execute(() -> transactionService.transfer(transactionDTO));
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }

}