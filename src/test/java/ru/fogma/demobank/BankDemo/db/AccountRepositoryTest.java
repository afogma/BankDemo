package ru.fogma.demobank.BankDemo.db;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.fogma.demobank.BankDemo.model.TransactionDTO;
import ru.fogma.demobank.BankDemo.service.TransactionService;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = {AccountRepositoryTest.Initializer.class})
public class AccountRepositoryTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountRepository accountRepository;

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
        var source = accountRepository.save(new Account("source", TEN));
        var target = accountRepository.save(new Account("target", ZERO));
        TransactionDTO transactionDTO = new TransactionDTO(source.getId(), target.getId(), BigDecimal.ONE);

        ExecutorService executor = newFixedThreadPool(2);

        for (int i = 0; i < 2; i++) {
            executor.execute(() -> runTest(transactionDTO));
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        var sourceUpdated = accountRepository.findById(source.getId()).get();
        var targetUpdated = accountRepository.findById(target.getId()).get();
        assertEquals(new BigDecimal("9"), sourceUpdated.getBalance().stripTrailingZeros());
        assertEquals(new BigDecimal("1"), targetUpdated.getBalance().stripTrailingZeros());
    }

    private void runTest(TransactionDTO transactionDTO) {
        try {
            transactionService.transfer(transactionDTO);
        } catch (ObjectOptimisticLockingFailureException ex) {
            System.out.println("Caught optimistic lock");
        }
    }

}