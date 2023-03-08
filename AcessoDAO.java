import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AcessoDAO {

    private Connection conexao;

    public AcessoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(Acesso acesso) throws SQLException {
        String sql = "INSERT INTO acessos (login, senha) VALUES (?, ?)";
        PreparedStatement stmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        stmt.setString(1, acesso.getLogin());
        stmt.setString(2, acesso.getSenha());
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            acesso.setId(rs.getInt(1));
        }
        rs.close();
        stmt.close();
    }

    public List<Acesso> buscarTodos() throws SQLException {
        List<Acesso> acessos = new ArrayList<>();
        String sql = "SELECT * FROM acessos";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Acesso acesso = new Acesso(rs.getString("login"), rs.getString("senha"));
            acesso.setId(rs.getInt("id"));
            acessos.add(acesso);
        }
        rs.close();
        stmt.close();
        return acessos;
    }

    public Acesso buscarPorId(int id) throws SQLException {
        Acesso acesso = null;
        String sql = "SELECT * FROM acessos WHERE id = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            acesso = new Acesso(rs.getString("login"), rs.getString("senha"));
            acesso.setId(rs.getInt("id"));
        }
        rs.close();
        stmt.close();
        return acesso;
    }

    public void atualizar(Acesso acesso) throws SQLException {
        String sql = "UPDATE acessos SET login = ?, senha = ? WHERE id = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, acesso.getLogin());
        stmt.setString(2, acesso.getSenha());
        stmt.setInt(3, acesso.getId());
        stmt.executeUpdate();
        stmt.close();
    }

    public void excluir(Acesso acesso) throws SQLException {
        String sql = "DELETE FROM acessos WHERE id = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, acesso.getId());
        stmt.executeUpdate();
        stmt.close();
    }

    public boolean conferirLogin(String login, String senha) {
        String sql = "SELECT * FROM acessos WHERE login = ? AND senha = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
