package GradedIndividualTask;

import java.math.BigDecimal;

public class ItemPedido {

    private final Produto produto;
    private int quantidade;

    public ItemPedido(Produto produto, int quantidade) {
        if (produto == null) {
            throw new IllegalArgumentException("O produto é obrigatório.");
        }
        this.produto = produto;
        setQuantidade(quantidade);
    }

    // Subtotal = preço unitário × quantidade.
    public BigDecimal calcularSubtotal() {
        return produto.getPrecoUnitario().multiply(BigDecimal.valueOf(quantidade));
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return String.format("%dx %s (R$ %.2f) = R$ %.2f",
                quantidade, produto.getNome(), produto.getPrecoUnitario(), calcularSubtotal());
    }
}
