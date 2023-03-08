public class Avaliacao {
    private int id;
    private int nota;
    private int idPrestadorServico;

    public Avaliacao(int nota, int idPrestadorServico) {
        this.nota = nota;
        this.idPrestadorServico = idPrestadorServico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public int getIdPrestadorServico() {
        return idPrestadorServico;
    }

    public void setIdPrestadorServico(int idPrestadorServico) {
        this.idPrestadorServico = idPrestadorServico;
    }
}
