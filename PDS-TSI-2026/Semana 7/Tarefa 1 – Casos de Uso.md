## Tarefa 1 – Casos de Uso

### 1. Atores

| Ator | Descrição |
|---|---|
| **Cliente** | Consulta o cardápio, monta o pedido e paga. |
| **Atendente** | Registra a venda, recebe o pagamento e emite o comprovante. |
| **Gerente** | Administra produtos, preços, estoque e consulta as vendas. |

> O Gerente pode ser modelado como especialização de Atendente (ele também pode operar vendas).

### 2. Casos de uso principais

**Cliente**
- UC01 – Consultar cardápio
- UC02 – Montar pedido (escolher produtos e quantidades)
- UC03 – Finalizar pedido e pagar
  **Atendente**
- UC04 – Registrar venda
- UC05 – Receber pagamento
- UC06 – Emitir comprovante
  **Gerente**
- UC07 – Cadastrar produto
- UC08 – Alterar preço
- UC09 – Atualizar estoque (entrada ou saída)
- UC10 – Remover produto do cardápio
- UC11 – Consultar vendas realizadas
  **Relacionamentos entre casos de uso**
- UC04 `«include»` UC05 e UC06.
- UC03 `«include»` UC05.
- UC02 `«include»` UC01, porque o cliente escolhe a partir do cardápio.
- UC05 `«include»` "Validar estoque e valor pago", que é a regra de finalização.
### 3. Descrição textual dos casos de uso

#### UC04 – Registrar venda

- **Objetivo:** registrar um pedido, receber o pagamento e concluir a venda, baixando o estoque.
- **Atores:** Atendente (principal) e Cliente (secundário).
- **Pré-condições:** atendente autenticado e produtos cadastrados no cardápio.
- **Pós-condições:** venda registrada com data e hora, estoque atualizado e comprovante emitido.
  **Fluxo principal**

1. O Atendente inicia um novo pedido.
2. O Cliente informa os produtos e as quantidades desejadas.
3. Para cada item, o sistema verifica se o produto existe e se há estoque suficiente.
4. O sistema cria o item, calcula o subtotal (preço unitário × quantidade) e atualiza o total do pedido.
5. Os passos 2 a 4 se repetem até o Cliente terminar de escolher.
6. O Atendente solicita o fechamento do pedido.
7. O sistema exibe o valor total.
8. O Cliente informa a forma de pagamento (dinheiro, PIX ou cartão) e o valor pago.
9. O sistema verifica se o pedido tem ao menos um item, se o estoque é suficiente e se o valor pago cobre o total.
10. O sistema calcula o troco (quando houver), dá baixa no estoque e registra a data e a hora da venda.
11. O sistema emite o comprovante e o Atendente o entrega ao Cliente.
    **Fluxos alternativos e exceções**

- **A1 – Produto sem estoque (passo 3):** o sistema informa que o produto está indisponível e não adiciona o item. O fluxo volta ao passo 2.
- **A2 – Quantidade maior que o estoque (passo 3):** o sistema informa o estoque disponível e pede uma nova quantidade. O fluxo volta ao passo 2.
- **A3 – Valor pago insuficiente (passo 9):** o sistema recusa o pagamento e mostra o valor faltante. O Cliente informa outro valor ou outra forma de pagamento, e o fluxo volta ao passo 8.
- **A4 – Pedido sem itens (passo 6):** o sistema impede o fechamento e exige ao menos um item.
- **A5 – Cancelamento:** em qualquer ponto antes do passo 10, o Atendente cancela o pedido. Nada é registrado e o estoque não é alterado.
- **A6 – Pagamento em dinheiro com valor maior que o total:** o sistema calcula e exibe o troco antes de concluir. Nos pagamentos por PIX e cartão, o valor pago é igual ao total.
#### UC09 – Atualizar estoque

- **Objetivo:** ajustar a quantidade em estoque de um produto (entrada ou saída).
- **Ator:** Gerente.
- **Pré-condições:** gerente autenticado e produto cadastrado.
- **Pós-condições:** estoque do produto atualizado.
  **Fluxo principal**

1. O Gerente acessa o cadastro de produtos e busca o produto pelo código ou nome.
2. O sistema exibe os dados do produto e o estoque atual.
3. O Gerente escolhe o tipo de movimentação (entrada ou saída) e informa a quantidade.
4. O sistema valida a quantidade.
5. O sistema atualiza o estoque e confirma a operação.
   **Fluxos alternativos e exceções**

- **A1 – Produto não encontrado (passo 1):** o sistema informa o erro e permite nova busca.
- **A2 – Saída maior que o estoque (passo 4):** o sistema recusa a operação, pois o estoque não pode ficar negativo.
- **A3 – Quantidade inválida (passo 4):** se for zero, negativa ou não numérica, o sistema pede uma nova quantidade.
---