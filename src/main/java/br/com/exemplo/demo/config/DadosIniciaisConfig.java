package br.com.exemplo.demo.config;

import br.com.exemplo.demo.model.Produto;
import br.com.exemplo.demo.repository.ProdutoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DadosIniciaisConfig {

    @Bean
    CommandLineRunner inserirProdutos(ProdutoRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Produto("Teclado", new BigDecimal("129.90")));
                repository.save(new Produto("Mouse", new BigDecimal("59.90")));
                repository.save(new Produto("Monitor", new BigDecimal("899.90")));
            }
        };
    }
}
