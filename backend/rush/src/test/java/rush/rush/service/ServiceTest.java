package rush.rush.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:init-table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public abstract class ServiceTest {}
