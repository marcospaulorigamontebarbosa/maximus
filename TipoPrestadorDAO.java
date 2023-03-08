import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoPrestadorDAO {
    private Connection conexao;

    public TipoPrestadorDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(TipoPrestador tipoPrestador) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = conexao.prepareStatement("INSERT INTO tipos_prestador (descricao) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tipoPrestador.getDescricao());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                tipoPrestador.setId(rs.getInt(1));
            }
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    public TipoPrestador buscarPorId(int id) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        TipoPrestador tipoPrestador = null;
        try {
            ps = conexao.prepareStatement("SELECT * FROM tipos_prestador WHERE id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                tipoPrestador = new TipoPrestador(rs.getString("descricao"));
                tipoPrestador.setId(rs.getInt("id"));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
        return tipoPrestador;
    }

    public List<TipoPrestador> buscarTodos() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TipoPrestador> tiposprestador = new ArrayList<>();
        try {
            ps = conexao.prepareStatement("SELECT * FROM tipos_prestador");
            rs = ps.executeQuery();
            while (rs.next()) {
                TipoPrestador tipoPrestador = new TipoPrestador(rs.getString("descricao"));
                tipoPrestador.setId(rs.getInt("id"));
                tiposprestador.add(tipoPrestador);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
        return tiposprestador;
    }

    public void atualizar(TipoPrestador tipoPrestador) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = conexao.prepareStatement("UPDATE tipos_prestador SET descricao = ? WHERE id = ?");
            ps.setString(1, tipoPrestador.getDescricao());
            ps.setInt(2, tipoPrestador.getId());
            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    public void excluir(TipoPrestador tipoPrestador) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = conexao.prepareStatement("DELETE FROM tipos_prestador WHERE id = ?");
            ps.setInt(1, tipoPrestador.getId());
            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    public List<TipoPrestador> buscarPorDescricao(String descricao) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TipoPrestador> tiposPrestadores = new ArrayList<>();
        try {
            ps = conexao.prepareStatement("SELECT * FROM tipos_prestador WHERE descricao LIKE ?");
            ps.setString(1, "%" + descricao + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                TipoPrestador tipoPrestador = new TipoPrestador(rs.getString("descricao"));
                tipoPrestador.setId(rs.getInt("id"));
                tiposPrestadores.add(tipoPrestador);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
        return tiposPrestadores;
    }
}
