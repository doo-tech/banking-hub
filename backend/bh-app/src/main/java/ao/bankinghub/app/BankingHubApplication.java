package ao.bankinghub.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Composicao executavel do Banking Hub.
 *
 * <p>Monolito modular (ADR-0002): agrega os modulos {@code bh-*} num unico
 * artefacto implantavel. Esta classe nao contem logica de dominio — apenas
 * declara o alcance de varrimento de componentes.
 *
 * <p>Plataforma de Onboarding Bancario para Angola, ao abrigo do
 * Aviso n.o 1/23 do Banco Nacional de Angola, de 30 de Janeiro de 2023.
 */
@SpringBootApplication(scanBasePackages = "ao.bankinghub")
@EnableJpaRepositories(basePackages = "ao.bankinghub")
@EntityScan(basePackages = "ao.bankinghub")
public class BankingHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingHubApplication.class, args);
    }
}
