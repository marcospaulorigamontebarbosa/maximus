import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaPrestadorServico extends JFrame {

    private static final long serialVersionUID = 1L;
    private PrestadorServicoDAO prestadorServicoDAO;
    private Controlador controlador;
    private DefaultTableModel modeloTabela;
    private JTable tabela;
    private JTextField campoNomePrestador;
    private JTextField campoDocumento;
    private JTextField campoDataNascimento;
    private JTextField campoTelefone;
    private JTextField campoIdTipo;
    private JTextField campoIdCategoria;
    private JTextField campoIdAcesso;
    private JButton botaoAdicionar;
    private JButton botaoEditar;
    private JButton botaoRemover;
    private JButton botaoVoltar;

    public TelaPrestadorServico(PrestadorServicoDAO prestadorServicoDAO) throws SQLException {
        this.prestadorServicoDAO = prestadorServicoDAO;
        inicializaComponentes();
        atualizarTabela();
    }

    private void inicializaComponentes() {
        // painel para a tabela
        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Nome", "Documento", "Data Nascimento", "Telefone", "IdTipo", "IdCategoria", "IdAcesso"}, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);
        tabela.setFillsViewportHeight(true);
        painelTabela.add(scrollPane);

        // painel para o formulário
        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(new BoxLayout(painelFormulario, BoxLayout.Y_AXIS));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelNome = new JLabel("Nome:");
        JLabel labelDocumento = new JLabel("Documento:");
        JLabel labelDataNascimento = new JLabel("Data Nascimento:");
        JLabel labelTelefone = new JLabel("Telefone:");
        JLabel labelIdTipo = new JLabel("Id Tipo:");
        JLabel labelIdCategoria = new JLabel("Id Categoria:");
        JLabel labelIdAcesso = new JLabel("Id Acesso:");

        campoNomePrestador = new JTextField();
        campoNomePrestador.setMaximumSize(new Dimension(Integer.MAX_VALUE, campoNomePrestador.getPreferredSize().height));
        campoDocumento = new JTextField();
        campoDocumento.setMaximumSize(new Dimension(Integer.MAX_VALUE, campoDocumento.getPreferredSize().height));
        campoDataNascimento = new JTextField();
        campoDataNascimento.setMaximumSize(new Dimension(Integer.MAX_VALUE, campoDataNascimento.getPreferredSize().height));
        campoTelefone = new JTextField();
        campoTelefone.setMaximumSize(new Dimension(Integer.MAX_VALUE, campoTelefone.getPreferredSize().height));
        campoIdTipo = new JTextField();
        campoIdTipo.setMaximumSize(new Dimension(Integer.MAX_VALUE, campoIdTipo.getPreferredSize().height));
        campoIdCategoria = new JTextField();
        campoIdCategoria.setMaximumSize(new Dimension(Integer.MAX_VALUE, campoIdCategoria.getPreferredSize().height));
        campoIdAcesso = new JTextField();
        campoIdAcesso.setMaximumSize(new Dimension(Integer.MAX_VALUE, campoIdAcesso.getPreferredSize().height));

        botaoAdicionar = new JButton("Adicionar");
        botaoEditar = new JButton("Editar");
        botaoRemover = new JButton("Remover");
        botaoVoltar = new JButton("Voltar");
        botaoEditar.setEnabled(false);
        botaoRemover.setEnabled(false);
        painelFormulario.add(labelNome);
        painelFormulario.add(campoNomePrestador);
        painelFormulario.add(labelDocumento);
        painelFormulario.add(campoDocumento);
        painelFormulario.add(labelDataNascimento);
        painelFormulario.add(campoDataNascimento);
        painelFormulario.add(labelTelefone);
        painelFormulario.add(campoTelefone);
        painelFormulario.add(labelIdTipo);
        painelFormulario.add(campoIdTipo);
        painelFormulario.add(labelIdCategoria);
        painelFormulario.add(campoIdCategoria);
        painelFormulario.add(labelIdAcesso);
        painelFormulario.add(campoIdAcesso);
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
                    adicionarPrestadorServico();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    editarPrestadorServico();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        botaoRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    removerPrestadorServico();
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
                PrestadorServico prestadorServico = null;
                try {
                    prestadorServico = prestadorServicoDAO.buscarPorId(id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                campoNomePrestador.setText(prestadorServico.getNomeCompleto());
                campoDocumento.setText(prestadorServico.getCpfCnpj());
                campoDataNascimento.setText(prestadorServico.getDataNascimento());
                campoTelefone.setText(prestadorServico.getTelefone());
                campoIdCategoria.setText(String.valueOf(prestadorServico.getIdCategoria()));
                campoIdTipo.setText(String.valueOf(prestadorServico.getIdTipo()));
                campoIdAcesso.setText(String.valueOf(prestadorServico.getIdAcesso()));
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
        setTitle("CRUD de Prestador de Serviço");
        setSize(600, 500);
        setLocationRelativeTo(null);
    }



    private void atualizarTabela() throws SQLException {
        modeloTabela.setRowCount(0);
        List<PrestadorServico> prestadores = prestadorServicoDAO.buscarTodos();
        for (PrestadorServico prestadorServico : prestadores) {
            modeloTabela.addRow(new Object[]{
                    prestadorServico.getId(),
                    prestadorServico.getNomeCompleto(),
                    prestadorServico.getCpfCnpj(),
                    prestadorServico.getDataNascimento(),
                    prestadorServico.getTelefone(),
                    prestadorServico.getIdTipo(),
                    prestadorServico.getIdCategoria(),
                    prestadorServico.getIdAcesso()

            });
        }
    }

    private void limpaTela() throws SQLException {
        atualizarTabela();
        campoNomePrestador.setText("");
        campoDocumento.setText("");
        campoDataNascimento.setText("");
        campoTelefone.setText("");
        campoIdCategoria.setText("");
        campoIdTipo.setText("");
        campoIdAcesso.setText("");
        botaoEditar.setEnabled(false);
        botaoRemover.setEnabled(false);
    }

    private void adicionarPrestadorServico() throws SQLException {
        String nome = campoNomePrestador.getText().trim();
        String documento = campoDocumento.getText().trim();
        String dataNascimento = campoDataNascimento.getText().trim();
        String telefone = campoTelefone.getText().trim();
        String idCategoria = campoIdCategoria.getText().trim();
        String idTipo = campoIdTipo.getText().trim();
        String idAcesso = campoIdAcesso.getText().trim();

        if (nome.isEmpty() || documento.isEmpty() || dataNascimento.isEmpty() || telefone.isEmpty() || idCategoria.isEmpty() || idTipo.isEmpty() || idAcesso.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Existem campos Vazios.");
            return;
        }
        PrestadorServico prestadorServico = new PrestadorServico(
                documento,
                nome,
                dataNascimento,
                Integer.parseInt(idTipo),
                Integer.parseInt(idCategoria),
                Integer.parseInt(idAcesso),
                telefone
        );

        prestadorServicoDAO.inserir(prestadorServico);
        limpaTela();
    }

    private void editarPrestadorServico() throws SQLException {
        int linhaSelecionada = tabela.getSelectedRow();
        int id = (int) tabela.getValueAt(linhaSelecionada, 0);
        String nome = campoNomePrestador.getText().trim();
        String documento = campoDocumento.getText().trim();
        String dataNascimento = campoDataNascimento.getText().trim();
        String telefone = campoTelefone.getText().trim();
        String idCategoria = campoIdCategoria.getText().trim();
        String idTipo = campoIdTipo.getText().trim();
        String idAcesso = campoIdAcesso.getText().trim();

        if (nome.isEmpty() || documento.isEmpty() || dataNascimento.isEmpty() || telefone.isEmpty() || idCategoria.isEmpty() || idTipo.isEmpty() || idAcesso.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Existem campos Vazios.");
            return;
        }

        PrestadorServico prestadorServico = prestadorServicoDAO.buscarPorId(id);
        prestadorServico.setCpfCnpj(documento);
        prestadorServico.setNomeCompleto(nome);
        prestadorServico.setDataNascimento(dataNascimento);
        prestadorServico.setTelefone(telefone);
        prestadorServico.setIdAcesso(Integer.parseInt(idAcesso));
        prestadorServico.setIdCategoria(Integer.parseInt(idCategoria));
        prestadorServico.setIdTipo(Integer.parseInt(idTipo));
        prestadorServicoDAO.atualizar(prestadorServico);
        limpaTela();
    }

    private void removerPrestadorServico() throws SQLException {
        int linhaSelecionada = tabela.getSelectedRow();
        int id = (int) tabela.getValueAt(linhaSelecionada, 0);
        PrestadorServico prestadorServico = prestadorServicoDAO.buscarPorId(id);
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja remover o Prestador de serviço?");
        if (confirmacao == JOptionPane.YES_OPTION) {
            prestadorServicoDAO.excluirPorId(prestadorServico.getId());
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


