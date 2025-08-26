import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaTarefas extends JFrame {
    private final TarefaController tc;
    private final UsuarioController uc;
    private final CategoriaController cc;

    private JTable tabela;
    private DefaultTableModel modelo;

    private JTextField txtTitulo;
    private JTextField txtDescricao;
    private JComboBox<String> cbPrioridade;
    private JComboBox<Usuario> cbUsuario;
    private JComboBox<Categoria> cbCategoria;
    private JCheckBox chkConcluida;

    public TelaTarefas(TarefaController tc, UsuarioController uc, CategoriaController cc) {
        this.tc = tc;
        this.uc = uc;
        this.cc = cc;

        setTitle("Gerenciar Tarefas");
        setSize(900, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        // Topo – formulário
        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));

        topo.add(new JLabel("Título:"));
        txtTitulo = new JTextField(12);
        topo.add(txtTitulo);

        topo.add(new JLabel("Descrição:"));
        txtDescricao = new JTextField(18);
        topo.add(txtDescricao);

        topo.add(new JLabel("Prioridade:"));
        cbPrioridade = new JComboBox<>(new String[]{"Baixa", "Média", "Alta"});
        topo.add(cbPrioridade);

        topo.add(new JLabel("Usuário:"));
        cbUsuario = new JComboBox<>();
        topo.add(cbUsuario);

        topo.add(new JLabel("Categoria:"));
        cbCategoria = new JComboBox<>();
        topo.add(cbCategoria);

        chkConcluida = new JCheckBox("Concluída");
        topo.add(chkConcluida);

        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar");
        JButton btnRemover = new JButton("Remover");
        topo.add(btnAdicionar);
        topo.add(btnEditar);
        topo.add(btnRemover);

        add(topo, BorderLayout.NORTH);

        // Tabela
        modelo = new DefaultTableModel(
                new Object[]{"ID", "Título", "Descrição", "Prioridade", "Usuário", "Categoria", "Concluída"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
            @Override public Class<?> getColumnClass(int c) {
                return switch (c) {
                    case 0 -> Integer.class;
                    case 6 -> Boolean.class;
                    default -> String.class;
                };
            }
        };
        tabela = new JTable(modelo);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Carrega combos e tabela
        carregarCombos();
        atualizarTabela();

        // Eventos
        btnAdicionar.addActionListener(e -> {
            String titulo = txtTitulo.getText().trim();
            String desc = txtDescricao.getText().trim();
            String prioridade = (String) cbPrioridade.getSelectedItem();
            Usuario usuario = (Usuario) cbUsuario.getSelectedItem();
            Categoria categoria = (Categoria) cbCategoria.getSelectedItem();
            boolean concluida = chkConcluida.isSelected();

            if (titulo.isEmpty() || desc.isEmpty() || usuario == null || categoria == null) {
                JOptionPane.showMessageDialog(this, "Preencha título, descrição, usuário e categoria.");
                return;
            }

            int id = proximoIdTarefa();
            Tarefa t = new Tarefa(id, titulo, desc, prioridade, usuario, categoria);
            // se sua Tarefa tiver o campo concluida no construtor, ajuste; senão, atualizamos após criar:
            if (concluida) {
                tc.adicionarTarefa(t);
                tc.atualizarTarefa(id, titulo, desc, prioridade, true);
            } else {
                tc.adicionarTarefa(t);
            }

            limparCampos();
            atualizarTabela();
        });

        btnEditar.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Selecione uma tarefa para editar.");
                return;
            }
            int id = (Integer) modelo.getValueAt(row, 0);

            String atualTitulo = (String) modelo.getValueAt(row, 1);
            String atualDesc = (String) modelo.getValueAt(row, 2);
            String atualPrioridade = (String) modelo.getValueAt(row, 3);
            boolean atualConcluida = (Boolean) modelo.getValueAt(row, 6);

            String novoTitulo = JOptionPane.showInputDialog(this, "Título:", atualTitulo);
            if (novoTitulo == null) return;
            String novaDesc = JOptionPane.showInputDialog(this, "Descrição:", atualDesc);
            if (novaDesc == null) return;
            String[] ops = {"Baixa", "Média", "Alta"};
            String novaPrioridade = (String) JOptionPane.showInputDialog(
                    this, "Prioridade:", "Editar Prioridade",
                    JOptionPane.PLAIN_MESSAGE, null, ops, atualPrioridade);
            if (novaPrioridade == null) return;

            int resp = JOptionPane.showConfirmDialog(this, "Marcar como concluída?", "Concluída",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            boolean novaConcluida = (resp == JOptionPane.YES_OPTION);

            tc.atualizarTarefa(id, novoTitulo.trim(), novaDesc.trim(), novaPrioridade, novaConcluida);
            atualizarTabela();
        });

        btnRemover.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Selecione uma tarefa para remover.");
                return;
            }
            int id = (Integer) modelo.getValueAt(row, 0);
            tc.removerTarefa(id);
            atualizarTabela();
        });
    }

    private int proximoIdTarefa() {
        int max = 0;
        for (Tarefa t : tc.listarTarefas()) {
            if (t.getId() > max) max = t.getId();
        }
        return max + 1;
    }

    private void carregarCombos() {
        cbUsuario.removeAllItems();
        for (Usuario u : uc.listarUsuarios()) {
            cbUsuario.addItem(u);
        }
        cbCategoria.removeAllItems();
        for (Categoria c : cc.listarCategorias()) {
            cbCategoria.addItem(c);
        }
    }

    private void limparCampos() {
        txtTitulo.setText("");
        txtDescricao.setText("");
        cbPrioridade.setSelectedIndex(0);
        chkConcluida.setSelected(false);
        if (cbUsuario.getItemCount() > 0) cbUsuario.setSelectedIndex(0);
        if (cbCategoria.getItemCount() > 0) cbCategoria.setSelectedIndex(0);
    }

    private void atualizarTabela() {
        modelo.setRowCount(0);
        for (Tarefa t : tc.listarTarefas()) {
            String nomeUsuario = (t.getUsuario() != null) ? t.getUsuario().getNome() : "";
            String nomeCategoria = (t.getCategoria() != null) ? t.getCategoria().getNome() : "";
            modelo.addRow(new Object[]{
                    t.getId(),
                    t.getTitulo(),
                    t.getDescricao(),
                    t.getPrioridade(),
                    nomeUsuario,
                    nomeCategoria,
                    t.isConcluida()
            });
        }
        // manter combos sincronizados com possíveis mudanças externas
        carregarCombos();
    }
}