import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Main {

    // DAO
    private static AcessoDAO acessoDAO;
    private static CategoriaDAO categoriaDAO;
    private static TipoPrestadorDAO tipoPrestadorDAO;
    private static AvaliacaoDAO avaliacaoDAO;
    private static PrestadorServicoDAO prestadorServicoDAO;




    // Telas
    private static TelaPrincipal telaPrincipal;
    private static TelaLogin telaLogin;
    private static  TelaCategoria telaCategoria;
    private static TelaTipoPrestador telaTipoPrestador;
    private static TelaAvaliacao telaAvaliacao;
    private static TelaAcesso telaAcesso;
    private static TelaPrestadorServico telaPrestadorServico;
    private static  TelaBuscadorDeServico telaBuscadorDeServico;


    // Controlador
    private static Controlador controlador;


    public static  void inicializaDAO() throws SQLException {
        acessoDAO = new AcessoDAO(BancoDeDados.conectar());
        categoriaDAO = new CategoriaDAO(BancoDeDados.conectar());
        tipoPrestadorDAO = new TipoPrestadorDAO(BancoDeDados.conectar());
        avaliacaoDAO = new AvaliacaoDAO(BancoDeDados.conectar());
        prestadorServicoDAO = new PrestadorServicoDAO(BancoDeDados.conectar());
    }

    public static void inicilizaTelas() throws SQLException {
        telaLogin = new TelaLogin(acessoDAO);
        telaCategoria = new TelaCategoria(categoriaDAO);
        telaTipoPrestador = new TelaTipoPrestador(tipoPrestadorDAO);
        telaAvaliacao = new TelaAvaliacao(avaliacaoDAO);
        telaPrincipal = new TelaPrincipal();
        telaAcesso = new TelaAcesso(acessoDAO);
        telaPrestadorServico = new TelaPrestadorServico(prestadorServicoDAO);
        telaBuscadorDeServico = new TelaBuscadorDeServico(categoriaDAO, prestadorServicoDAO);
    }

    public static void inicializaControlador(){
        controlador = new Controlador();
        controlador.setTelaCategoria(telaCategoria);
        controlador.setTelaLogin(telaLogin);
        controlador.setTipoPrestador(telaTipoPrestador);
        controlador.setTelaAvaliacao(telaAvaliacao);
        controlador.setTelaPrincipal(telaPrincipal);
        controlador.setTelaAcesso(telaAcesso);
        controlador.setTelaPrestadorServico(telaPrestadorServico);
        controlador.setTelaBuscadorDeServico(telaBuscadorDeServico);

        telaPrincipal.setControlador(controlador);
        telaAcesso.setControlador(controlador);
        telaAvaliacao.setControlador(controlador);
        telaCategoria.setControlador(controlador);
        telaPrestadorServico.setControlador(controlador);
        telaTipoPrestador.setControlador(controlador);
        telaBuscadorDeServico.setControlador(controlador);
    }


    public static void main(String[] args) throws SQLException {

        inicializaDAO();
        inicilizaTelas();
        inicializaControlador();
        controlador.exibeTela("telaPrincipal");
    }

}
