import com.sun.security.auth.module.JndiLoginModule;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaTipoPrestador extends JFrame {

    private static final long serialVersionUID = 1L;
    private TipoPrestadorDAO tipoPrestadorDAO;
    private Controlador controlador;
    private DefaultTableModel modeloTabela;
    private JTable tabela;
    private JTextField campoDescricao;
    private JButton botaoAdicionar;
    private JButton botaoEditar;
    private JButton botaoRemover;
    private JButton botaoVoltar;

    public TelaTipoPrestador(TipoPrestadorDAO tipoPrestadorDAO) throws SQLException {
        this.tipoPrestadorDAO = tipoPrestadorDAO;
        criarComponentes();
        atualizarTabela();
    }

    private void criarComponentes() {
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
                    adicionarTipoPrestador();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    editarTipoPrestador();
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
                TipoPrestador tipoPrestador = null;
                try {
                    tipoPrestador = tipoPrestadorDAO.buscarPorId(id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                campoDescricao.setText(tipoPrestador.getDescricao());
            } else {
                botaoEditar.setEnabled(false);
                botaoRemover.setEnabled(false);
                campoDescricao.setText("");
            }
        });

        // configurações da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("CRUD de Tipo de Prestador");
        setSize(500, 400);
        setLocationRelativeTo(null);

    }


    private void atualizarTabela() throws SQLException {
        modeloTabela.setRowCount(0);
        List<TipoPrestador> tiposPrestador = tipoPrestadorDAO.buscarTodos();
        for (TipoPrestador tipoPrestador : tiposPrestador) {
            modeloTabela.addRow(new Object[]{tipoPrestador.getId(), tipoPrestador.getDescricao()});
        }
    }

    private void adicionarTipoPrestador() throws SQLException {
        String descricao = campoDescricao.getText().trim();
        if (descricao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha a descrição.");
            return;
        }
        TipoPrestador tipoPrestador = new TipoPrestador(descricao);
        tipoPrestadorDAO.inserir(tipoPrestador);
        atualizarTabela();
        campoDescricao.setText("");
    }

    private void editarTipoPrestador() throws SQLException {
        int linhaSelecionada = tabela.getSelectedRow();
        int id = (int) tabela.getValueAt(linhaSelecionada, 0);
        String descricao = campoDescricao.getText().trim();
        if (descricao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha a descrição.");
            return;
        }
        TipoPrestador tipoPrestador = tipoPrestadorDAO.buscarPorId(id);
        tipoPrestador.setDescricao(descricao);
        tipoPrestadorDAO.atualizar(tipoPrestador);
        atualizarTabela();
        campoDescricao.setText("");
        botaoEditar.setEnabled(false);
        botaoRemover.setEnabled(false);
    }

    private void removerCategoria() throws SQLException {
        int linhaSelecionada = tabela.getSelectedRow();
        int id = (int) tabela.getValueAt(linhaSelecionada, 0);
        TipoPrestador tipoPrestador = tipoPrestadorDAO.buscarPorId(id);
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja remover a categoria " + tipoPrestador.getDescricao() + "?");
        if (confirmacao == JOptionPane.YES_OPTION) {
            tipoPrestadorDAO.excluir(tipoPrestador);
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


