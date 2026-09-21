package GradedIndividualTask;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Produto {

    private final int codigo;
    private String nome;
    private Categoria categoria;
    private BigDecimal precoUnitario;
    private int quantidadeEstoque;
    private boolean ativo;

    public Produto(int codigo, String nome, Categoria categoria,
                   BigDecimal precoUnitario, int quantidadeEstoque) {
        if (quantidadeEstoque < 0) {
            throw new IllegalArgumentException("O estoque inicial não pode ser negativo.");
        }
        this.codigo = codigo;
        setNome(nome);
        setCategoria(categoria);
        setPrecoUnitario(precoUnitario);
        this.quantidadeEstoque = quantidadeEstoque;
        this.ativo = true;
    }


    // indica se o produto está ativo e possui estoque suficiente para a quantidade pedida.
    public boolean temEstoque(int quantidade) {
        return ativo && quantidade > 0 && quantidadeEstoque >= quantidade;
    }

    // saída de estoque (venda ou baixa manual).
    public void baixarEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        if (quantidade > quantidadeEstoque) {
            throw new IllegalStateException("Estoque insuficiente de " + nome + ": disponível " + quantidadeEstoque + ", solicitado " + quantidade + ".");
        }
        quantidadeEstoque -= quantidade;
    }

    // entrada de estoque (reposição).
    public void adicionarEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        quantidadeEstoque += quantidade;
    }

    // remove o produto do cardápio sem apagar o histórico de vendas.
    public void desativar() {
        this.ativo = false;
    }

    public void reativar() {
        this.ativo = true;
    }

    // ---------- Getters e setters ----------

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }
        this.nome = nome.trim();
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("A categoria é obrigatória.");
        }
        this.categoria = categoria;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        if (precoUnitario == null || precoUnitario.signum() <= 0) {
            throw new IllegalArgumentException("O preço deve ser maior que zero.");
        }
        this.precoUnitario = precoUnitario.setScale(2, RoundingMode.HALF_UP);
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public boolean isAtivo() {
        return ativo;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s) - R$ %.2f | estoque: %d",
                codigo, nome, categoria, precoUnitario, quantidadeEstoque);
    }
}
