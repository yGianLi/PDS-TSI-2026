package GradedIndividualTask;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {

    private final int numero;
    private final List<ItemPedido> itens;
    private StatusPedido status;
    private LocalDateTime dataHora;
    private Pagamento pagamento;

    public Pedido(int numero) {
        this.numero = numero;
        this.itens = new ArrayList<>();
        this.status = StatusPedido.ABERTO;
    }

    // ---------- Itens ----------

    public void adicionarItem(Produto produto, int quantidade) {
        exigirPedidoAberto();
        if (produto == null) {
            throw new IllegalArgumentException("O produto é obrigatório.");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        ItemPedido existente = buscarItem(produto);
        int quantidadeTotal = quantidade + (existente == null ? 0 : existente.getQuantidade());

        if (!produto.temEstoque(quantidadeTotal)) {
            throw new IllegalStateException(
                    "Produto indisponível ou sem estoque suficiente: " + produto.getNome()
                            + " (disponível: " + produto.getQuantidadeEstoque() + ").");
        }

        if (existente == null) {
            itens.add(new ItemPedido(produto, quantidade));
        } else {
            existente.setQuantidade(quantidadeTotal);
        }
    }

    public void removerItem(ItemPedido item) {
        exigirPedidoAberto();
        itens.remove(item);
    }

    private ItemPedido buscarItem(Produto produto) {
        for (ItemPedido item : itens) {
            if (item.getProduto().getCodigo() == produto.getCodigo()) {
                return item;
            }
        }
        return null;
    }

    // ---------- Cálculos e validações ----------

    // rotal = soma dos subtotais dos itens.
    public BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemPedido item : itens) {
            total = total.add(item.calcularSubtotal());
        }
        return total;
    }

    // verifica se todos os produtos do pedido têm estoque suficiente.
    public boolean validarEstoque() {
        for (ItemPedido item : itens) {
            if (!item.getProduto().temEstoque(item.getQuantidade())) {
                return false;
            }
        }
        return true;
    }

    // ---------- Finalização ----------

    public void finalizar(Pagamento pagamento) {
        exigirPedidoAberto();
        if (pagamento == null) {
            throw new IllegalArgumentException("O pagamento é obrigatório.");
        }
        if (itens.isEmpty()) {
            throw new IllegalStateException("O pedido precisa ter pelo menos um item.");
        }
        if (!validarEstoque()) {
            throw new IllegalStateException("Há produtos sem estoque suficiente no pedido.");
        }
        BigDecimal total = calcularTotal();
        if (!pagamento.eSuficiente(total)) {
            BigDecimal falta = total.subtract(pagamento.getValorPago());
            throw new IllegalStateException(
                    String.format("Pagamento insuficiente: faltam R$ %.2f.", falta));
        }

        for (ItemPedido item : itens) {
            item.getProduto().baixarEstoque(item.getQuantidade());
        }
        this.pagamento = pagamento;
        this.dataHora = LocalDateTime.now();
        this.status = StatusPedido.FINALIZADO;
    }

    public void cancelar() {
        exigirPedidoAberto();
        this.status = StatusPedido.CANCELADO;
    }

    private void exigirPedidoAberto() {
        if (status != StatusPedido.ABERTO) {
            throw new IllegalStateException("O pedido não está aberto (status: " + status + ").");
        }
    }

    // ---------- Resumo ----------

    public String gerarResumo() {
        StringBuilder sb = new StringBuilder();
        sb.append("========== COMPROVANTE ==========\n");
        sb.append("Pedido nº ").append(numero).append('\n');
        if (dataHora != null) {
            sb.append("Data/hora: ")
                    .append(dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                    .append('\n');
        }
        sb.append("Status: ").append(status).append("\n\n");
        sb.append("Itens:\n");
        for (ItemPedido item : itens) {
            sb.append("  - ").append(item).append('\n');
        }
        BigDecimal total = calcularTotal();
        sb.append(String.format("%nTotal: R$ %.2f%n", total));
        if (pagamento != null) {
            sb.append("Pagamento: ").append(pagamento.getTipo()).append('\n');
            sb.append(String.format("Valor pago: R$ %.2f%n", pagamento.getValorPago()));
            sb.append(String.format("Troco: R$ %.2f%n", pagamento.calcularTroco(total)));
        }
        sb.append("=================================");
        return sb.toString();
    }

    // ---------- Getters ----------

    public int getNumero() {
        return numero;
    }

    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public StatusPedido getStatus() {
        return status;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }
}
