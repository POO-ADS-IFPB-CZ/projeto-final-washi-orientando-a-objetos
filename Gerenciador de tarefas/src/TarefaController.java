import java.util.List;

public class TarefaController {
    private List<Tarefa> tarefas;
    private final String arquivo = "tarefas.dat";

    public TarefaController() {
        tarefas = ArquivoDAO.carregar(arquivo);
    }

    public void adicionarTarefa(Tarefa t) {
        tarefas.add(t);
        ArquivoDAO.salvar(tarefas, arquivo);
    }

    public List<Tarefa> listarTarefas() {
        return tarefas;
    }

    public boolean atualizarTarefa(int id, String titulo, String descricao, String prioridade, boolean concluida) {
        for (Tarefa t : tarefas) {
            if (t.getId() == id) {
                t.setTitulo(titulo);
                t.setDescricao(descricao);
                t.setPrioridade(prioridade);
                t.setConcluida(concluida);
                ArquivoDAO.salvar(tarefas, arquivo);
                return true;
            }
        }
        return false;
    }

    public boolean removerTarefa(int id) {
        boolean removed = tarefas.removeIf(t -> t.getId() == id);
        if (removed) ArquivoDAO.salvar(tarefas, arquivo);
        return removed;
    }
}