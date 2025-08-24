public class Main {
    public static void main(String[] args) {
        UsuarioController uc = new UsuarioController();
        CategoriaController cc = new CategoriaController();
        TarefaController tc = new TarefaController();

        // Adicionando novos dados
        Usuario u1 = new Usuario(1, "Washington", "wash@example.com");
        uc.adicionarUsuario(u1);

        Categoria cat1 = new Categoria(1, "Estudos", "Azul");
        cc.adicionarCategoria(cat1);

        Tarefa t1 = new Tarefa(1, "Estudar Java", "Focar em MVC e Swing", "Alta", u1, cat1);
        tc.adicionarTarefa(t1);

        // Listando dados
        System.out.println("Usuarios:");
        for (Usuario u : uc.listarUsuarios()) {
            System.out.println(u);
        }

        System.out.println("Categorias:");
        for (Categoria c : cc.listarCategorias()) {
            System.out.println(c);
        }

        System.out.println("Tarefas:");
        for (Tarefa t : tc.listarTarefas()) {
            System.out.println(t);
        }
    }
}