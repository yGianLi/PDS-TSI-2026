package GradedIndividualTask;

import java.math.BigDecimal;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        Locale.setDefault(Locale.forLanguageTag("pt-BR"));

        // 1. Cadastro de produtos
        Produto coxinha = new Produto(1, "Coxinha", Categoria.LANCHE, new BigDecimal("6.50"), 20);
        Produto suco = new Produto(2, "Suco", Categoria.BEBIDA, new BigDecimal("5.00"), 15);
        Produto brigadeiro = new Produto(3, "Brigadeiro", Categoria.DOCE, new BigDecimal("3.00"), 2);

        System.out.println("--- Cardápio ---");
        System.out.println(coxinha);
        System.out.println(suco);
        System.out.println(brigadeiro);

        // 2. Venda do exemplo do enunciado: 2 coxinhas + 1 suco, pago com R$ 20,00 em dinheiro
        System.out.println("\n--- Venda 1 (exemplo do enunciado) ---");
        Pedido pedido1 = new Pedido(1);
        pedido1.adicionarItem(coxinha, 2);
        pedido1.adicionarItem(suco, 1);

        System.out.printf("Total do pedido: R$ %.2f%n", pedido1.calcularTotal());

        Pagamento pagamento1 = new Pagamento(TipoPagamento.DINHEIRO, new BigDecimal("20.00"));
        pedido1.finalizar(pagamento1);

        System.out.println();
        System.out.println(pedido1.gerarResumo());

        System.out.println("\nEstoque após a venda:");
        System.out.println(coxinha);
        System.out.println(suco);

        // 3. Exceção: estoque insuficiente
        System.out.println("\n--- Venda 2 (estoque insuficiente) ---");
        Pedido pedido2 = new Pedido(2);
        try {
            pedido2.adicionarItem(brigadeiro, 5);
        } catch (IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        // 4. Exceção: pagamento insuficiente
        System.out.println("\n--- Venda 3 (pagamento insuficiente) ---");
        Pedido pedido3 = new Pedido(3);
        pedido3.adicionarItem(coxinha, 3);
        Pagamento pagamento3 = new Pagamento(TipoPagamento.PIX, new BigDecimal("10.00"));
        try {
            pedido3.finalizar(pagamento3);
        } catch (IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        System.out.println("Status do pedido 3: " + pedido3.getStatus());

        // 5. Venda com PIX (sem troco)
        System.out.println("\n--- Venda 4 (PIX) ---");
        Pedido pedido4 = new Pedido(4);
        pedido4.adicionarItem(brigadeiro, 2);
        pedido4.adicionarItem(suco, 2);
        pedido4.finalizar(new Pagamento(TipoPagamento.PIX, pedido4.calcularTotal()));
        System.out.println(pedido4.gerarResumo());
    }
}
