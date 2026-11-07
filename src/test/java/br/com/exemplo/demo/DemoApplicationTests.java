package br.com.exemplo.demo;

import br.com.exemplo.demo.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DemoApplicationTests {

    // Alteracao
    @Autowired
    private ProdutoRepository repository;

    @Test
    void populaOBancoComProdutosDeExemplo() {
        assertThat(repository.count()).isEqualTo(3);
    }
}
