import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoDAO {

    // Salvar lista de objetos em arquivo
    public static <T extends Serializable> void salvar(List<T> lista, String nomeArquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(lista);
            System.out.println("Dados salvos em " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    // Carregar lista de objetos do arquivo
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> List<T> carregar(String nomeArquivo) {
        List<T> lista = new ArrayList<>();
        File arquivo = new File(nomeArquivo);

        if (!arquivo.exists()) {
            return lista; // retorna lista vazia se não existir
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            lista = (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
        }

        return lista;
    }
}