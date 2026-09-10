# Etapa 1 — Levantamento dos Casos de Uso

## 1.1 Diagrama de Casos de Uso

O diagrama apresenta as principais funcionalidades da Área do Pix e os atores envolvidos no sistema.

### Atores

- **Cliente:** utiliza o aplicativo para autenticar-se, consultar o saldo, cadastrar chaves Pix e realizar transferências.
- **Sistema BACEN:** responsável por validar a existência da chave Pix de destino.

### Casos de Uso

- **Autenticar Usuário**
- **Consultar Saldo**
- **Cadastrar Chave Pix**
- **Realizar Pix**
- **Validar Chave Pix**

### Relacionamentos

O caso de uso **Realizar Pix** inclui a autenticação do usuário e a validação da chave Pix. A validação da chave é realizada por meio da comunicação com o **Sistema BACEN**.

![Diagrama de Casos de Uso](01_casos_de_uso.png)

---

## 1.2 Descrição Textual — Realizar Pix

### Identificação

| Item | Descrição |
|---|---|
| **Caso de uso** | Realizar Pix |
| **Ator principal** | Cliente |
| **Ator secundário** | Sistema BACEN |
| **Objetivo** | Permitir que o cliente realize uma transferência via Pix |

### Pré-condições

- O cliente deve estar autenticado no aplicativo.
- O cliente deve possuir uma conta ativa.
- A conta deve possuir saldo suficiente para realizar a transferência.

### Fluxo Principal

1. O cliente acessa a opção **Realizar Pix**.
2. O sistema apresenta a tela de transferência.
3. O cliente informa a chave Pix do destinatário.
4. O sistema solicita ao BACEN a validação da chave.
5. O BACEN verifica a chave e retorna os dados do destinatário.
6. O sistema apresenta os dados do destinatário ao cliente.
7. O cliente informa o valor da transferência.
8. O sistema verifica se existe saldo suficiente na conta.
9. O sistema debita o valor da conta do cliente.
10. O sistema registra a transação no extrato.
11. O sistema gera o comprovante da operação.
12. O sistema apresenta o comprovante ao cliente.
13. O caso de uso é encerrado com sucesso.

### Fluxos de Exceção

#### [FE-01] Saldo Insuficiente

1. O sistema verifica o saldo da conta.
2. O saldo disponível é menor que o valor informado.
3. O sistema informa ao cliente que o saldo é insuficiente.
4. O débito não é realizado.
5. O Pix não é concluído.

#### [FE-02] Chave Pix Inválida

1. O sistema envia a chave Pix para validação no BACEN.
2. O BACEN informa que a chave não existe ou é inválida.
3. O sistema informa o cliente sobre a chave inválida.
4. A transferência não é realizada.
5. O caso de uso é encerrado.
