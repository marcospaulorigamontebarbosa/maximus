import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    private Connection conexao;

    public CategoriaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(Categoria categoria) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = conexao.prepareStatement("INSERT INTO categorias (descricao) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, categoria.getDescricao());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                categoria.setId(rs.getInt(1));
            }
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    public Categoria buscarPorId(int id) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Categoria categoria = null;
        try {
            ps = conexao.prepareStatement("SELECT * FROM categorias WHERE id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                categoria = new Categoria(rs.getString("descricao"));
                categoria.setId(rs.getInt("id"));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
        return categoria;
    }

    public List<Categoria> buscarTodos() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Categoria> categorias = new ArrayList<>();
        try {
            ps = conexao.prepareStatement("SELECT * FROM categorias");
            rs = ps.executeQuery();
            while (rs.next()) {
                Categoria categoria = new Categoria(rs.getString("descricao"));
                categoria.setId(rs.getInt("id"));
                categorias.add(categoria);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
        return categorias;
    }

    public void atualizar(Categoria categoria) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = conexao.prepareStatement("UPDATE categorias SET descricao = ? WHERE id = ?");
            ps.setString(1, categoria.getDescricao());
            ps.setInt(2, categoria.getId());
            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    public void excluir(Categoria categoria) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = conexao.prepareStatement("DELETE FROM categorias WHERE id = ?");
            ps.setInt(1, categoria.getId());
            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    public List<Categoria> buscarPorDescricao(String descricao) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Categoria> categorias = new ArrayList<>();
        try {
            ps = conexao.prepareStatement("SELECT * FROM categorias WHERE descricao LIKE ?");
            ps.setString(1, "%" + descricao + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                Categoria categoria = new Categoria(rs.getString("descricao"));
                categoria.setId(rs.getInt("id"));
                categorias.add(categoria);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
        return categorias;
    }
}
