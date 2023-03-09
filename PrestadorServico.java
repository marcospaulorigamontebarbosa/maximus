public class PrestadorServico {
    private int id;
    private String cpfCnpj;
    private String nomeCompleto;
    private String dataNascimento;
    private int idTipo;
    private int idCategoria;
    private int idAcesso;
    private String telefone;

    public PrestadorServico(String cpfCnpj, String nomeCompleto, String dataNascimento, int idTipo, int idCategoria, int idAcesso, String telefone) {
        this.cpfCnpj = cpfCnpj;
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.idTipo = idTipo;
        this.idCategoria = idCategoria;
        this.idAcesso = idAcesso;
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdAcesso() {
        return idAcesso;
    }

    public void setIdAcesso(int idAcesso) {
        this.idAcesso = idAcesso;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void cadastrar(){
        System.out.println("Nome: ");
    }

    @Override
    public String toString() {
        return "\n-Nome: " + getNomeCompleto() + "\n-Telefone: " + getTelefone();
    }
}

