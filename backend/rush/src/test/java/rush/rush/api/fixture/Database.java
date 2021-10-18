package rush.rush.api.fixture;

import org.springframework.data.jpa.repository.JpaRepository;

import static org.assertj.core.api.Assertions.assertThat;

public class Database {

    public static void clearAll(JpaRepository repository) {
        repository.deleteAll();
        assertThat(repository.findAll()).isEmpty();
    }
}
