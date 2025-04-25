﻿import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
class Mercadinho extends JFrame {
    private final JTable tabelaEstoque;
    private final DefaultTableModel modeloTabela;
    private final ArrayList<Produto> listaProdutos;
    private JTextField campoCodigoBarras;

    // Construtor
    public Mercadinho() {
        // Configurações da janela principal
        setTitle("Controle de Estoque do Mercadinho");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializa a lista de produtos
        listaProdutos = new ArrayList<>();

        // Campo para o código de barras
        campoCodigoBarras = new JTextField(15);
        campoCodigoBarras.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    processarCodigoDeBarras(campoCodigoBarras.getText());
                    campoCodigoBarras.setText("");
                }
            }
        });

        // Label para o campo de código de barras
        JLabel labelCodigoBarras = new JLabel("Código de Barras:");

        // Painel para o campo de código de barras
        JPanel painelCodigoBarras = new JPanel();
        painelCodigoBarras.add(labelCodigoBarras);
        painelCodigoBarras.add(campoCodigoBarras);

        // Configura a tabela para exibir os produtos
        modeloTabela = new DefaultTableModel(new Object[]{"Produto", "Quantidade"}, 0);
        tabelaEstoque = new JTable(modeloTabela);

        // Adiciona a tabela a um painel com barra de rolagem
        JScrollPane painelTabela = new JScrollPane(tabelaEstoque);
        add(painelTabela, BorderLayout.CENTER);

        // Botão para adicionar novos produtos manualmente
        JButton btnAdicionar = new JButton("Adicionar Produto");
        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarProduto();
            }
        });

        // Botão para atualizar a quantidade de produtos
        JButton btnAtualizar = new JButton("Atualizar Quantidade");
        btnAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarProduto();
            }
        });

        // Painel para os botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnAtualizar);
        add(painelBotoes, BorderLayout.SOUTH);

        // Adiciona o campo de código de barras acima da tabela
        add(painelCodigoBarras, BorderLayout.NORTH);

        // Mostra a janela
        setVisible(true);
    }

    // Método para processar a entrada do código de barras
    private void processarCodigoDeBarras(String codigo) {
        // Simula que cada código de barras mapeia para um produto específico
        String nomeProduto = "Produto " + codigo; // Exemplo simples, mapeando o código de barras para um nome de produto
        int quantidade = 1; // Vamos assumir que cada leitura adiciona 1 unidade

        boolean produtoExistente = false;

        // Verifica se o produto já existe no estoque
        for (int i = 0; i < listaProdutos.size(); i++) {
            Produto produto = listaProdutos.get(i);
            if (produto.getNome().equals(nomeProduto)) {
                // Se o produto já existe, atualiza a quantidade
                produto.setQuantidade(produto.getQuantidade() + quantidade);
                modeloTabela.setValueAt(produto.getQuantidade(), i, 1);
                produtoExistente = true;
                break;
            }
        }

        // Se o produto não existe, adiciona-o
        if (!produtoExistente) {
            Produto novoProduto = new Produto(nomeProduto, quantidade);
            listaProdutos.add(novoProduto);
            modeloTabela.addRow(new Object[]{novoProduto.getNome(), novoProduto.getQuantidade()});
        }
    }

    // Método para adicionar um novo produto manualmente
    private void adicionarProduto() {
        String nomeProduto = JOptionPane.showInputDialog(this, "Digite o nome do produto:");
        if (nomeProduto != null && !nomeProduto.trim().isEmpty()) {
            int quantidade = Integer.parseInt(JOptionPane.showInputDialog(this, "Digite a quantidade do produto:"));
            Produto produto = new Produto(nomeProduto, quantidade);
            listaProdutos.add(produto);
            modeloTabela.addRow(new Object[]{produto.getNome(), produto.getQuantidade()});
        }
    }

    // Método para atualizar a quantidade de um produto existente
    private void atualizarProduto() {
        int linhaSelecionada = tabelaEstoque.getSelectedRow();
        if (linhaSelecionada != -1) {
            String nomeProduto = (String) modeloTabela.getValueAt(linhaSelecionada, 0);
            int novaQuantidade = Integer.parseInt(JOptionPane.showInputDialog(this, "Digite a nova quantidade para " + nomeProduto + ":"));
            listaProdutos.get(linhaSelecionada).setQuantidade(novaQuantidade);
            modeloTabela.setValueAt(novaQuantidade, linhaSelecionada, 1);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para atualizar.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Classe para representar um produto
    public class Produto {
        private String nome;
        private int quantidade;

        public Produto(String nome, int quantidade) {
            this.nome = nome;
            this.quantidade = quantidade;
        }

        public String getNome() {
            return nome;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }
    }

    // Método principal para iniciar o programa
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Mercadinho();
            }
        });
    }
}
