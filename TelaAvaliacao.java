import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaAvaliacao extends JFrame {

    private static final long serialVersionUID = 1L;
    private AvaliacaoDAO avaliacaoDAO;
    private Controlador controlador;
    private DefaultTableModel modeloTabela;
    private JTable tabela;
    private JTextField campoNota;
    private JTextField campoIdPrestador;
    private JButton botaoAdicionar;
    private JButton botaoEditar;
    private JButton botaoRemover;
    private JButton botaoVoltar;

    public TelaAvaliacao(AvaliacaoDAO avaliacaoDAO) throws SQLException {
        this.avaliacaoDAO = avaliacaoDAO;
        inicializaComponentes();
        atualizarTabela();
    }

    private void inicializaComponentes() {
        // painel para a tabela
        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Nota", "IDPrestador"}, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);
        tabela.setFillsViewportHeight(true);
        painelTabela.add(scrollPane);

        // painel para o formulário
        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(new BoxLayout(painelFormulario, BoxLayout.Y_AXIS));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel labelNota = new JLabel("Nota:");
        JLabel labelIdPrestador = new JLabel("IdPrestador:");
        campoNota = new JTextField();
        campoNota.setMaximumSize(new Dimension(Integer.MAX_VALUE, campoNota.getPreferredSize().height));
        campoIdPrestador = new JTextField();
        campoIdPrestador.setMaximumSize(new Dimension(Integer.MAX_VALUE, campoIdPrestador.getPreferredSize().height));
        botaoAdicionar = new JButton("Adicionar");
        botaoEditar = new JButton("Editar");
        botaoRemover = new JButton("Remover");
        botaoVoltar = new JButton("Voltar");
        botaoEditar.setEnabled(false);
        botaoRemover.setEnabled(false);
        painelFormulario.add(labelNota);
        painelFormulario.add(campoNota);
        painelFormulario.add(labelIdPrestador);
        painelFormulario.add(campoIdPrestador);
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
                    adicionarAvaliacao();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    editarAvaliacao();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        botaoRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    removerAvaliacao();
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
                Avaliacao avaliacao = null;
                try {
                    avaliacao = avaliacaoDAO.buscarPorId(id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                campoNota.setText(String.valueOf(avaliacao.getNota()));
                campoIdPrestador.setText(String.valueOf(avaliacao.getIdPrestadorServico()));
            } else {
                botaoEditar.setEnabled(false);
                botaoRemover.setEnabled(false);
                campoNota.setText("");
                campoIdPrestador.setText("");
            }
        });

        // configurações da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("CRUD de Avaliação");
        setSize(600, 500);
        setLocationRelativeTo(null);
    }



    private void atualizarTabela() throws SQLException {
        modeloTabela.setRowCount(0);
        List<Avaliacao> avaliacoes = avaliacaoDAO.buscarTodos();
        for (Avaliacao avaliacao : avaliacoes) {
            modeloTabela.addRow(new Object[]{avaliacao.getId(), avaliacao.getNota(), avaliacao.getIdPrestadorServico()});
        }
    }

    private void limpaTela() throws SQLException {
        atualizarTabela();
        campoNota.setText("");
        campoIdPrestador.setText("");
        botaoEditar.setEnabled(false);
        botaoRemover.setEnabled(false);
    }

    private void adicionarAvaliacao() throws SQLException {
        String nota = campoNota.getText().trim();
        String idPrestador = campoIdPrestador.getText().trim();

        if (nota.isEmpty() || idPrestador.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Existem campos Vazios.");
            return;
        }
        Avaliacao avaliacao = new Avaliacao(Integer.parseInt(nota), Integer.parseInt(idPrestador));
        avaliacaoDAO.inserir(avaliacao);
        limpaTela();
    }

    private void editarAvaliacao() throws SQLException {
        int linhaSelecionada = tabela.getSelectedRow();
        int id = (int) tabela.getValueAt(linhaSelecionada, 0);
        String nota = campoNota.getText().trim();
        String idPrestador = campoIdPrestador.getText().trim();

        if (nota.isEmpty() || idPrestador.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Existem campos Vazios.");
            return;
        }
        Avaliacao avaliacao = avaliacaoDAO.buscarPorId(id);
        avaliacao.setNota(Integer.parseInt(nota));
        avaliacao.setIdPrestadorServico(Integer.parseInt(idPrestador));
        avaliacaoDAO.atualizar(avaliacao);
        limpaTela();
    }

    private void removerAvaliacao() throws SQLException {
        int linhaSelecionada = tabela.getSelectedRow();
        int id = (int) tabela.getValueAt(linhaSelecionada, 0);
        Avaliacao avaliacao = avaliacaoDAO.buscarPorId(id);
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja remover a avaliação ?");
        if (confirmacao == JOptionPane.YES_OPTION) {
            avaliacaoDAO.excluir(avaliacao);
            limpaTela();
        }
    }

    public Controlador getControlador() {
        return controlador;
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }
}


