import java.sql.*;
import java.util.ArrayList;

public class PrestadorServicoDAO {
    private Connection conexao;

    public PrestadorServicoDAO(Connection conexao){
        this.conexao = conexao;
    }
    //Create
    public void inserir(PrestadorServico prestadorServico) throws SQLException{
        String sql = "INSERT INTO prestadores_servico (cpf_cnpj, nome_completo, data_nascimento, id_tipo, id_categoria, id_acesso, telefone) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement resposta = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            resposta.setString(1, prestadorServico.getCpfCnpj());
            resposta.setString(2, prestadorServico.getNomeCompleto());
            resposta.setString(3, prestadorServico.getDataNascimento());
            resposta.setInt(4, prestadorServico.getIdTipo());
            resposta.setInt(5, prestadorServico.getIdCategoria());
            resposta.setInt(6, prestadorServico.getIdAcesso());
            resposta.setString(7, prestadorServico.getTelefone());

            resposta.executeUpdate();

            ResultSet resultado = resposta.getGeneratedKeys();
            if(resultado.next()) {
                int id = resultado.getInt(1);
                prestadorServico.setId(id);

                System.out.println("Prestador de Serviço cadastrado com sucesso!");
            }
        }catch (SQLException e) {
            System.out.println("Erro ao inserir o Prestador de Serviço"+e);
        }
    }
    //Read
    public PrestadorServico buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM prestadores_servico WHERE id = ?";
        try (PreparedStatement retorno = conexao.prepareStatement(sql)) {
            retorno.setInt(1, id);

            ResultSet resultado = retorno.executeQuery();
            if (resultado.next()) {
                PrestadorServico prestadorServico = new PrestadorServico(resultado.getString("cpf_cnpj"), resultado.getString("nome_completo"), resultado.getString("data_nascimento"), resultado.getInt("id_tipo"), resultado.getInt("id_categoria"), resultado.getInt("id_acesso"), resultado.getString("telefone"));
                prestadorServico.setId(resultado.getInt("id"));
                return prestadorServico;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar o prestador de serviço por ID: " + e.getMessage());
        }
        return null;
    }
    //Update
    public void atualizar(PrestadorServico prestadorServico) throws SQLException {
        String sql = "UPDATE prestadores_servico SET cpf_cnpj = ?, nome_completo = ?, data_nascimento = ?, id_tipo = ?, id_categoria = ?, id_acesso = ?, telefone = ?  WHERE id = ?";
        try (PreparedStatement resposta = conexao.prepareStatement(sql)) {
            resposta.setString(1, prestadorServico.getCpfCnpj());
            resposta.setString(2, prestadorServico.getNomeCompleto());
            resposta.setString(3, prestadorServico.getDataNascimento());
            resposta.setInt(4, prestadorServico.getIdTipo());
            resposta.setInt(5, prestadorServico.getIdCategoria());
            resposta.setInt(6, prestadorServico.getIdAcesso());
            resposta.setString(7, prestadorServico.getTelefone());
            resposta.setInt(8, prestadorServico.getId());

            resposta.executeUpdate();

            System.out.println("Prestador de Serviço Atualizado com sucesso!!!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o Prestador de Serviço" );
        }
    }
    // Delete
    public void excluirPorId(int id) throws SQLException{
        String sql = "DELETE FROM prestadores_servico WHERE id = ?";
        try (PreparedStatement resultado = conexao.prepareStatement(sql)) {
            resultado.setInt(1, id);
            resultado.executeUpdate();
            System.out.println("Prestador de Serviço excluído com sucesso!!!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir o Prestador de Serviço");
        }
    }

    // retorna lista de categorias
    public ArrayList<PrestadorServico> buscarTodos() {
        ArrayList<PrestadorServico> prestadorServico = new ArrayList<PrestadorServico>();
        try {
            String query = "SELECT * FROM prestadores_servico";
            Statement resposta = conexao.createStatement();
            ResultSet resultado = resposta.executeQuery(query);
            while (resultado.next()) {
                int id = resultado.getInt("id");
                String cpfCnpj = resultado.getString("cpf_cnpj");
                String nomeCompleto = resultado.getString("nome_completo");
                String dataNascimento = resultado.getString("data_nascimento");
                int idTipo = resultado.getInt("id_tipo");
                int idCategoria = resultado.getInt("id_categoria");
                int idAcesso = resultado.getInt("id_acesso");
                String telefone = resultado.getString("telefone");
                PrestadorServico prestador = new PrestadorServico(cpfCnpj, nomeCompleto, dataNascimento, idTipo, idCategoria, idAcesso, telefone);
                prestador.setId(id);
                prestadorServico.add(prestador);
            }
            resultado.close();
            resposta.close();
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta SQL.");
        }
        return prestadorServico;
    }

    public PrestadorServico buscarPorCategoriaId(int categoriaID) {
        String query = "SELECT * FROM prestadores_servico WHERE id_categoria ="+categoriaID;
        try(PreparedStatement resultado = conexao.prepareStatement(query)) {



            ResultSet rs = resultado.executeQuery(query);
            PrestadorServico prestador = null;

            while (rs.next()) {
                int id = rs.getInt("id");
                String cpfCnpj = rs.getString("cpf_cnpj");
                String nomeCompleto = rs.getString("nome_completo");
                String dataNascimento = rs.getString("data_nascimento");
                int idTipo = rs.getInt("id_tipo");
                int idCategoria = rs.getInt("id_categoria");
                int idAcesso = rs.getInt("id_acesso");
                String telefone = rs.getString("telefone");
                prestador = new PrestadorServico(cpfCnpj, nomeCompleto, dataNascimento, idTipo, idCategoria, idAcesso, telefone);
                prestador.setId(id);
                return prestador;

            }
            resultado.close();
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta SQL."+e);
        }

        return null;
    }
}
