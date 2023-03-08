import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin extends JFrame implements ActionListener {

    private final AcessoDAO acessoDAO;
    private final JTextField campoLogin;
    private final JTextField campoSenha;
    private final JButton botaoLogin;

    public TelaLogin(AcessoDAO acessoDAO) {
        this.acessoDAO = acessoDAO;

        setTitle("Tela de Login");

        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel painel = new JPanel(new GridLayout(3, 2));

        JLabel labelLogin = new JLabel("Login:");
        campoLogin = new JTextField();
        painel.add(labelLogin);
        painel.add(campoLogin);

        JLabel labelSenha = new JLabel("Senha:");
        campoSenha = new JTextField();
        painel.add(labelSenha);
        painel.add(campoSenha);

        botaoLogin = new JButton("Login");
        botaoLogin.addActionListener(this);
        painel.add(botaoLogin);

        add(painel);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botaoLogin) {
            String login = campoLogin.getText();
            String senha = campoSenha.getText();

            if (acessoDAO.conferirLogin(login, senha)) {
                JOptionPane.showMessageDialog(null, "Login efetuado com sucesso!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Login ou senha incorretos!");
            }
        }
    }
}
