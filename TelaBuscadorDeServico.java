import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaBuscadorDeServico extends JFrame {

    private static final long serialVersionUID = 1L;
    private CategoriaDAO categoriaDAO;
    private PrestadorServicoDAO prestadorServicoDAO;
    private Controlador controlador;
    private DefaultTableModel modeloTabela;
    private JTable tabela;
    private JTextField campoBusca;
    private JButton botaoPesquisar;

    private JButton botaoVoltar;

    public TelaBuscadorDeServico(CategoriaDAO categoriaDAO, PrestadorServicoDAO prestadorServicoDAO){
        this.prestadorServicoDAO = prestadorServicoDAO;
        this.categoriaDAO = categoriaDAO;
        criarComponentes();
    }

    private void criarComponentes() {
        // painel para a tabela
        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Categoria"}, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);
        tabela.setFillsViewportHeight(true);
        painelTabela.add(scrollPane);

        // painel para o formulário
        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(new BoxLayout(painelFormulario, BoxLayout.Y_AXIS));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel labelDescricao = new JLabel("Pesquisa:");
        campoBusca = new JTextField();
        campoBusca.setMaximumSize(new Dimension(Integer.MAX_VALUE, campoBusca.getPreferredSize().height));
        botaoPesquisar = new JButton("Pesquisar");

        botaoVoltar = new JButton("Voltar");

        painelFormulario.add(labelDescricao);
        painelFormulario.add(campoBusca);
        painelFormulario.add(botaoPesquisar);
        painelFormulario.add(botaoVoltar);

        // painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.add(painelTabela, BorderLayout.CENTER);
        painelPrincipal.add(painelFormulario, BorderLayout.EAST);
        getContentPane().add(painelPrincipal);


        botaoPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    atualizarTabela();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        botaoVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoBusca.setText("");
                modeloTabela.setRowCount(0);
                getControlador().exibeTela("telaPrincipal");
            }
        });


        tabela.getSelectionModel().addListSelectionListener(event -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada != -1) {

                int id = (int) tabela.getValueAt(linhaSelecionada, 0);
                PrestadorServico prestadorServico = prestadorServicoDAO.buscarPorCategoriaId(id);
                if(prestadorServico == null){
                    JOptionPane.showMessageDialog(null, "Não há Prestador de Serviço disponível!");
                }else{
                    JOptionPane.showMessageDialog(null, prestadorServico.toString());
                }


            }
        });

        // configurações da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Buscador de Serviço");
        setSize(500, 400);
        setLocationRelativeTo(null);

    }


    private void atualizarTabela() throws SQLException {
        modeloTabela.setRowCount(0);
        List<Categoria> categorias = categoriaDAO.buscarPorDescricao(campoBusca.getText().trim());
        for (Categoria categoria : categorias) {
            modeloTabela.addRow(new Object[]{categoria.getId(), categoria.getDescricao()});
        }
    }

    private void editarTipoPrestador() throws SQLException {
        int linhaSelecionada = tabela.getSelectedRow();
        int id = (int) tabela.getValueAt(linhaSelecionada, 0);
        String descricao = campoBusca.getText().trim();
        if (descricao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha a descrição.");
            return;
        }

        atualizarTabela();
        campoBusca.setText("");

    }

    public Controlador getControlador() {
        return controlador;
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }
}


