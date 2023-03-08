public class TipoPrestador {
    private int id;
    private String descricao;

    public TipoPrestador(String descricao) {
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "TipoPrestador{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
