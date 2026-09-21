package GradedIndividualTask;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Pagamento {

    private TipoPagamento tipo;
    private BigDecimal valorPago;

    public Pagamento(TipoPagamento tipo, BigDecimal valorPago) {
        setTipo(tipo);
        setValorPago(valorPago);
    }

    // O pagamento é suficiente quando o valor pago cobre o total do pedido. */
    public boolean eSuficiente(BigDecimal total) {
        return valorPago.compareTo(total) >= 0;
    }

    // Troco só existe em dinheiro; PIX e cartão não geram troco. */
    public BigDecimal calcularTroco(BigDecimal total) {
        if (!eSuficiente(total)) {
            throw new IllegalStateException("Pagamento insuficiente: não há troco a calcular.");
        }
        if (tipo != TipoPagamento.DINHEIRO) {
            return BigDecimal.ZERO.setScale(2);
        }
        return valorPago.subtract(total);
    }

    public TipoPagamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoPagamento tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("O tipo de pagamento é obrigatório.");
        }
        this.tipo = tipo;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        if (valorPago == null || valorPago.signum() <= 0) {
            throw new IllegalArgumentException("O valor pago deve ser maior que zero.");
        }
        this.valorPago = valorPago.setScale(2, RoundingMode.HALF_UP);
    }
}