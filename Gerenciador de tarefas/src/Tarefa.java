import java.io.Serializable;

public class Tarefa implements Serializable{
    private static final long serialVersionUID = 1L;

    private int id;
    private String titulo;
    private String descricao;
    private String prioridade;
    private boolean concluida;
    private Usuario usuario;
    private Categoria categoria;

    public Tarefa(int id, String titulo, String descricao, String prioridade, Usuario usuario, Categoria categoria) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.concluida = false;
        this.usuario = usuario;
        this.categoria = categoria;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getPrioridade() { return prioridade; }
    public void setPrioridade(String prioridade) { this.prioridade = prioridade; }

    public boolean isConcluida() { return concluida; }
    public void setConcluida(boolean concluida) { this.concluida = concluida; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    @Override
    public String toString() {
        return id + " - " + titulo + " (" + prioridade + ") - "
                + (concluida ? "Concluída" : "Pendente");
    }
}