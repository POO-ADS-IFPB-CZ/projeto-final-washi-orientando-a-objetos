import java.util.List;

public class CategoriaController {
    private List<Categoria> categorias;
    private final String arquivo = "categorias.dat";

    public CategoriaController() {
        categorias = ArquivoDAO.carregar(arquivo);
    }

    public void adicionarCategoria(Categoria c) {
        categorias.add(c);
        ArquivoDAO.salvar(categorias, arquivo);
    }

    public List<Categoria> listarCategorias() {
        return categorias;
    }

    public boolean atualizarCategoria(int id, String nome, String cor) {
        for (Categoria c : categorias) {
            if (c.getId() == id) {
                c.setNome(nome);
                c.setCor(cor);
                ArquivoDAO.salvar(categorias, arquivo);
                return true;
            }
        }
        return false;
    }

    public boolean removerCategoria(int id) {
        boolean removed = categorias.removeIf(c -> c.getId() == id);
        if (removed) ArquivoDAO.salvar(categorias, arquivo);
        return removed;
    }
}