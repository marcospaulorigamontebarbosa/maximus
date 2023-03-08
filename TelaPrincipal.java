import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {

    private JButton btnAvaliacao, btnPrestadorServico, btnTipoPrestadorServico, btnCategoria, btnAcesso, btnBuscadorServico;
    private Controlador controlador;

    public TelaPrincipal() {
        super("Maximus");

        // Configurações da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        // Criação dos botões
        btnAvaliacao = new JButton("Avaliação");
        btnPrestadorServico = new JButton("Prestador de Serviço");
        btnTipoPrestadorServico = new JButton("Tipo de Prestador de Serviço");
        btnCategoria = new JButton("Categoria");
        btnAcesso = new JButton("Acesso");
        btnBuscadorServico = new JButton("Buscador de Serviço");

        // Configura as ações dos botões
        btnAvaliacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getControlador().exibeTela("telaAvaliacao");
            }
        });

        btnPrestadorServico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getControlador().exibeTela("telaPrestadorServico");
            }
        });

        btnTipoPrestadorServico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getControlador().exibeTela("telaTipoPrestador");
            }
        });

        btnCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getControlador().exibeTela("telaCategoria");
            }
        });

        btnAcesso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getControlador().exibeTela("telaAcesso");
            }
        });

        btnBuscadorServico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getControlador().exibeTela("telaBuscadorDeServico");
            }
        });

        // Adiciona os botões na tela
        add(btnAvaliacao);
        add(btnPrestadorServico);
        add(btnTipoPrestadorServico);
        add(btnCategoria);
        add(btnAcesso);
        add(btnBuscadorServico);

    }

    public Controlador getControlador() {
        return controlador;
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }
}
