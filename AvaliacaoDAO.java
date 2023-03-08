import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO {

    private Connection connection;

    public AvaliacaoDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(Avaliacao avaliacao) throws SQLException {
        String sql = "INSERT INTO avaliacoes (nota, id_prestador_servico) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, avaliacao.getNota());
            statement.setInt(2, avaliacao.getIdPrestadorServico());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    avaliacao.setId(id);
                }
            }
        }
    }

    public Avaliacao buscarPorId(int id) throws SQLException {
        String sql = "SELECT nota, id_prestador_servico FROM avaliacoes WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int nota = resultSet.getInt("nota");
                    int idPrestadorServico = resultSet.getInt("id_prestador_servico");
                    Avaliacao avaliacao = new Avaliacao(nota, idPrestadorServico);
                    avaliacao.setId(id);
                    return avaliacao;
                } else {
                    return null;
                }
            }
        }
    }

    public void atualizar(Avaliacao avaliacao) throws SQLException {
        String sql = "UPDATE avaliacoes SET nota = ?, id_prestador_servico = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, avaliacao.getNota());
            statement.setInt(2, avaliacao.getIdPrestadorServico());
            statement.setInt(3, avaliacao.getId());
            statement.executeUpdate();
        }
    }

    public void excluir(Avaliacao avaliacao) throws SQLException {
        String sql = "DELETE FROM avaliacoes WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, avaliacao.getId());
            statement.executeUpdate();
        }
    }

    public List<Avaliacao> buscarTodos() throws SQLException {
        List<Avaliacao> avaliacoes = new ArrayList<>();
        String sql = "SELECT id, nota, id_prestador_servico FROM avaliacoes";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int nota = resultSet.getInt("nota");
                    int idPrestadorServico = resultSet.getInt("id_prestador_servico");
                    Avaliacao avaliacao = new Avaliacao(nota, idPrestadorServico);
                    avaliacao.setId(id);
                    avaliacoes.add(avaliacao);
                }
            }
        }
        return avaliacoes;
    }
}