import javax.swing.*;

public class TelaPrincipal extends JFrame {

    private final UsuarioController uc;
    private final CategoriaController cc;
    private final TarefaController tc;

    public TelaPrincipal() {
        setTitle("Gerenciador de Tarefas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Instanciar controllers (os seus)
        uc = new UsuarioController();
        cc = new CategoriaController();
        tc = new TarefaController();

        // Painel principal
        JPanel painel = new JPanel();
        painel.setLayout(new java.awt.GridLayout(4, 1, 10, 10));

        // Botões
        JButton btnUsuarios = new JButton("Gerenciar Usuários");
        JButton btnCategorias = new JButton("Gerenciar Categorias");
        JButton btnTarefas = new JButton("Gerenciar Tarefas");
        JButton btnSair = new JButton("Sair");

        painel.add(btnUsuarios);
        painel.add(btnCategorias);
        painel.add(btnTarefas);
        painel.add(btnSair);
        add(painel);

        // Abrir telas reais (sem JOptionPane)
        btnUsuarios.addActionListener(e -> new TelaUsuarios(uc).setVisible(true));
        btnCategorias.addActionListener(e -> new TelaCategorias(cc).setVisible(true));
        btnTarefas.addActionListener(e -> new TelaTarefas(tc, uc, cc).setVisible(true));
        btnSair.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}