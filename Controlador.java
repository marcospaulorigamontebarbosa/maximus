import javax.swing.*;

public class Controlador {
    private TelaPrincipal telaPrincipal;
    private TelaLogin telaLogin;
    private TelaCategoria telaCategoria;
    private TelaTipoPrestador telaTipoPrestador;
    private TelaAvaliacao telaAvaliacao;
    private TelaAcesso telaAcesso;
    private TelaBuscadorDeServico telaBuscadorDeServico;

    private TelaPrestadorServico telaPrestadorServico;

    public Controlador() {
    }

    public void escondeTodasTelas(){
        getTelaCategoria().setVisible(false);
        getTelaLogin().setVisible(false);
        getTipoPrestador().setVisible(false);
        getTelaAvaliacao().setVisible(false);
        getTelaPrincipal().setVisible(false);
        getTelaAcesso().setVisible(false);
        getTelaPrestadorServico().setVisible(false);
        getTelaBuscadorDeServico().setVisible(false);
    }

    public void exibeTela(String opcao){
        escondeTodasTelas();

        if(opcao.equalsIgnoreCase("telaLogin")){
            getTelaLogin().setVisible(true);
        }else if(opcao.equalsIgnoreCase("telaCategoria")){
            getTelaCategoria().setVisible(true);
        }else if(opcao.equalsIgnoreCase("telaTipoPrestador")){
            getTipoPrestador().setVisible(true);
        }else if(opcao.equalsIgnoreCase("telaAvaliacao")){
            getTelaAvaliacao().setVisible(true);
        }else if (opcao.equalsIgnoreCase("telaPrincipal")){
            getTelaPrincipal().setVisible(true);
        }else if(opcao.equalsIgnoreCase("telaAcesso")){
            getTelaAcesso().setVisible(true);
        }else if(opcao.equalsIgnoreCase("telaPrestadorServico")){
            getTelaPrestadorServico().setVisible(true);
        }else if(opcao.equalsIgnoreCase("telaBuscadorDeServico")){
            getTelaBuscadorDeServico().setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "Opção inválida!");
        }
    }

    public TelaPrincipal getTelaPrincipal() {
        return telaPrincipal;
    }

    public void setTelaPrincipal(TelaPrincipal telaPrincipal) {
        this.telaPrincipal = telaPrincipal;
    }

    public TelaLogin getTelaLogin() {
        return telaLogin;
    }

    public void setTelaLogin(TelaLogin telaLogin) {
        this.telaLogin = telaLogin;
    }

    public TelaCategoria getTelaCategoria() {
        return telaCategoria;
    }

    public void setTelaCategoria(TelaCategoria telaCategoria) {
        this.telaCategoria = telaCategoria;
    }

    public TelaTipoPrestador getTipoPrestador() {
        return telaTipoPrestador;
    }

    public void setTipoPrestador(TelaTipoPrestador telaTipoPrestador) {
        this.telaTipoPrestador = telaTipoPrestador;
    }

    public TelaAvaliacao getTelaAvaliacao() {
        return telaAvaliacao;
    }

    public void setTelaAvaliacao(TelaAvaliacao telaAvaliacao) {
        this.telaAvaliacao = telaAvaliacao;
    }

    public TelaAcesso getTelaAcesso() {
        return telaAcesso;
    }

    public void setTelaAcesso(TelaAcesso telaAcesso) {
        this.telaAcesso = telaAcesso;
    }

    public TelaPrestadorServico getTelaPrestadorServico() {
        return telaPrestadorServico;
    }

    public void setTelaPrestadorServico(TelaPrestadorServico telaPrestadorServico) {
        this.telaPrestadorServico = telaPrestadorServico;
    }

    public TelaBuscadorDeServico getTelaBuscadorDeServico() {
        return telaBuscadorDeServico;
    }

    public void setTelaBuscadorDeServico(TelaBuscadorDeServico telaBuscadorDeServico) {
        this.telaBuscadorDeServico = telaBuscadorDeServico;
    }
}
