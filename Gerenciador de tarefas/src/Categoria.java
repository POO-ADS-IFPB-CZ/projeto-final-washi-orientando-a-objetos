import java.io.Serializable;

public class Categoria implements Serializable{
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String cor;

    public Categoria(int id, String nome, String cor) {
        this.id = id;
        this.nome = nome;
        this.cor = cor;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }

    @Override
    public String toString() {
        return id + " - " + nome + " [" + cor + "]";
    }
}