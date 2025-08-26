import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoDAO {

    public static <T extends Serializable> void salvar(List<T> lista, String nomeArquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.out.println("Erro ao salvar em " + nomeArquivo + ": " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> List<T> carregar(String nomeArquivo) {
        List<T> lista = new ArrayList<>();
        File f = new File(nomeArquivo);
        if (!f.exists()) return lista;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            lista = (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar " + nomeArquivo + ": " + e.getMessage());
        }
        return lista;
    }
}