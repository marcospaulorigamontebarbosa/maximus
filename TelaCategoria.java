import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaCategoria extends JFrame {

    private static final long serialVersionUID = 1L;
    private CategoriaDAO categoriaDAO;
    private Controlador controlador;
    private DefaultTableModel modeloTabela;
    private JTable tabela;
    private JTextField campoDescricao;
    private JButton botaoAdicionar;
    private JButton botaoEditar;
    private JButton botaoRemover;
    private JButton botaoVoltar;

    public TelaCategoria(CategoriaDAO categoriaDAO) throws SQLException {
        this.categoriaDAO = categoriaDAO;
        inicializaComponentes();
        atualizarTabela();
    }

    private void inicializaComponentes() {
        // painel para a tabela
        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Descrição"}, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);
        tabela.setFillsViewportHeight(true);
        painelTabela.add(scrollPane);

        // painel para o formulário
        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(new BoxLayout(painelFormulario, BoxLayout.Y_AXIS));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel labelDescricao = new JLabel("Descrição:");
        campoDescricao = new JTextField();
        campoDescricao.setMaximumSize(new Dimension(Integer.MAX_VALUE, campoDescricao.getPreferredSize().height));
        botaoAdicionar = new JButton("Adicionar");
        botaoEditar = new JButton("Editar");
        botaoRemover = new JButton("Remover");
        botaoVoltar = new JButton("Voltar");
        botaoEditar.setEnabled(false);
        botaoRemover.setEnabled(false);
        painelFormulario.add(labelDescricao);
        painelFormulario.add(campoDescricao);
        painelFormulario.add(botaoAdicionar);
        painelFormulario.add(botaoEditar);
        painelFormulario.add(botaoRemover);
        painelFormulario.add(botaoVoltar);

        // painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.add(painelTabela, BorderLayout.CENTER);
        painelPrincipal.add(painelFormulario, BorderLayout.EAST);
        getContentPane().add(painelPrincipal);

        // ações dos botões
        botaoAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    adicionarCategoria();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    editarCategoria();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        botaoRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    removerCategoria();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        botaoVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getControlador().exibeTela("telaPrincipal");
            }
        });

        tabela.getSelectionModel().addListSelectionListener(event -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada != -1) {
                botaoEditar.setEnabled(true);
                botaoRemover.setEnabled(true);
                int id = (int) tabela.getValueAt(linhaSelecionada, 0);
                Categoria categoria = null;
                try {
                    categoria = categoriaDAO.buscarPorId(id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                campoDescricao.setText(categoria.getDescricao());
            } else {
                botaoEditar.setEnabled(false);
                botaoRemover.setEnabled(false);
                campoDescricao.setText("");
            }
        });

        // configurações da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("CRUD de Categoria");
        setSize(500, 400);
        setLocationRelativeTo(null);
    }

    private void atualizarTabela() throws SQLException {
        modeloTabela.setRowCount(0);
        List<Categoria> categorias = categoriaDAO.buscarTodos();
        for (Categoria categoria : categorias) {
            modeloTabela.addRow(new Object[]{categoria.getId(), categoria.getDescricao()});
        }
    }

    private void adicionarCategoria() throws SQLException {
        String descricao = campoDescricao.getText().trim();
        if (descricao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha a descrição.");
            return;
        }
        Categoria categoria = new Categoria(descricao);
        categoriaDAO.inserir(categoria);
        atualizarTabela();
        campoDescricao.setText("");
    }

    private void editarCategoria() throws SQLException {
        int linhaSelecionada = tabela.getSelectedRow();
        int id = (int) tabela.getValueAt(linhaSelecionada, 0);
        String descricao = campoDescricao.getText().trim();
        if (descricao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha a descrição.");
            return;
        }
        Categoria categoria = categoriaDAO.buscarPorId(id);
        categoria.setDescricao(descricao);
        categoriaDAO.atualizar(categoria);
        atualizarTabela();
        campoDescricao.setText("");
        botaoEditar.setEnabled(false);
        botaoRemover.setEnabled(false);
    }

    private void removerCategoria() throws SQLException {
        int linhaSelecionada = tabela.getSelectedRow();
        int id = (int) tabela.getValueAt(linhaSelecionada, 0);
        Categoria categoria = categoriaDAO.buscarPorId(id);
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja remover a categoria " + categoria.getDescricao() + "?");
        if (confirmacao == JOptionPane.YES_OPTION) {
            categoriaDAO.excluir(categoria);
            atualizarTabela();
            campoDescricao.setText("");
            botaoEditar.setEnabled(false);
            botaoRemover.setEnabled(false);
        }
    }

    public Controlador getControlador() {
        return controlador;
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }
}


