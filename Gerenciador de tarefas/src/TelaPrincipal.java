import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Gerenciador de Tarefas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(4, 1, 10, 10));

        // Botões de navegação
        JButton btnUsuarios = new JButton("Gerenciar Usuários");
        JButton btnCategorias = new JButton("Gerenciar Categorias");
        JButton btnTarefas = new JButton("Gerenciar Tarefas");
        JButton btnSair = new JButton("Sair");

        painel.add(btnUsuarios);
        painel.add(btnCategorias);
        painel.add(btnTarefas);
        painel.add(btnSair);

        add(painel);

        // Eventos dos botões
        btnUsuarios.addActionListener(e -> JOptionPane.showMessageDialog(this, "Tela de Usuários"));
        btnCategorias.addActionListener(e -> JOptionPane.showMessageDialog(this, "Tela de Categorias"));
        btnTarefas.addActionListener(e -> JOptionPane.showMessageDialog(this, "Tela de Tarefas"));
        btnSair.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal tela = new TelaPrincipal();
            tela.setVisible(true);
        });
    }
}