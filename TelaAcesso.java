import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaAcesso extends JFrame {

    private static final long serialVersionUID = 1L;
    private AcessoDAO acessoDAO;
    private Controlador controlador;
    private DefaultTableModel modeloTabela;
    private JTable tabela;
    private JTextField campoLogin;
    private JTextField campoSenha;
    private JButton botaoAdicionar;
    private JButton botaoEditar;
    private JButton botaoRemover;
    private JButton botaoVoltar;

    public TelaAcesso(AcessoDAO acessoDAO) throws SQLException {
        this.acessoDAO = acessoDAO;
        inicializaComponentes();
        atualizarTabela();
    }

    public Controlador getControlador() {
        return controlador;
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    private void inicializaComponentes() {
        // painel para a tabela
        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Login", "Senha"}, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);
        tabela.setFillsViewportHeight(true);
        painelTabela.add(scrollPane);

        // painel para o formulário
        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(new BoxLayout(painelFormulario, BoxLayout.Y_AXIS));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel labelLogin = new JLabel("Login:");
        JLabel labelSenha = new JLabel("Senha:");
        campoLogin = new JTextField();
        campoLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, campoLogin.getPreferredSize().height));
        campoSenha = new JTextField();
        campoSenha.setMaximumSize(new Dimension(Integer.MAX_VALUE, campoSenha.getPreferredSize().height));
        botaoAdicionar = new JButton("Adicionar");
        botaoEditar = new JButton("Editar");
        botaoRemover = new JButton("Remover");
        botaoVoltar = new JButton("Voltar");
        botaoEditar.setEnabled(false);
        botaoRemover.setEnabled(false);
        painelFormulario.add(labelLogin);
        painelFormulario.add(campoLogin);
        painelFormulario.add(labelSenha);
        painelFormulario.add(campoSenha);
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
                    adicionarAcesso();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    editarAcesso();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        botaoRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    removerAcesso();
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
                Acesso acesso = null;
                try {
                    acesso = acessoDAO.buscarPorId(id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                campoLogin.setText(String.valueOf(acesso.getLogin()));
                campoSenha.setText(String.valueOf(acesso.getSenha()));
            } else {
                try {
                    limpaTela();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // configurações da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("CRUD de Acesso");
        setSize(600, 500);
        setLocationRelativeTo(null);
    }

    private void atualizarTabela() throws SQLException {
        modeloTabela.setRowCount(0);
        List<Acesso> acessos = acessoDAO.buscarTodos();
        for (Acesso acesso : acessos) {
            modeloTabela.addRow(new Object[]{acesso.getId(), acesso.getLogin(), acesso.getSenha()});
        }
    }

    private void limpaTela() throws SQLException {
        atualizarTabela();
        campoLogin.setText("");
        campoSenha.setText("");
        botaoEditar.setEnabled(false);
        botaoRemover.setEnabled(false);
    }

    private void adicionarAcesso() throws SQLException {
        String login = campoLogin.getText().trim();
        String senha = campoSenha.getText().trim();

        if (login.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Existem campos Vazios.");
            return;
        }
        Acesso acesso = new Acesso(login, senha);
        acessoDAO.inserir(acesso);
        limpaTela();
    }

    private void editarAcesso() throws SQLException {
        int linhaSelecionada = tabela.getSelectedRow();
        int id = (int) tabela.getValueAt(linhaSelecionada, 0);
        String login = campoLogin.getText().trim();
        String senha = campoSenha.getText().trim();

        if (login.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Existem campos Vazios.");
            return;
        }
        Acesso acesso = acessoDAO.buscarPorId(id);
        acesso.setLogin(login);
        acesso.setSenha(senha);
        acessoDAO.atualizar(acesso);
        limpaTela();
    }

    private void removerAcesso() throws SQLException {
        int linhaSelecionada = tabela.getSelectedRow();
        int id = (int) tabela.getValueAt(linhaSelecionada, 0);
        Acesso acesso = acessoDAO.buscarPorId(id);
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja remover o acesso ?");
        if (confirmacao == JOptionPane.YES_OPTION) {
            acessoDAO.excluir(acesso);
            limpaTela();
        }
    }

}


