package components;

import java.io.IOException;
import java.nio.file.Path;

/**
 * This interface provides the structure for file components in
 * our applications. Note that by doing so we make it possible
 * for customly provided descendent classes to have their methods
 * called from this framework.
 *
 * @author Richard McKenna, Ritwik Banerjee
 */
public interface AppFileComponent {

    void savelevel(AppDataComponent data, Path to, int x) throws IOException;

    void saveData(AppDataComponent data, Path filePath) throws IOException;

    boolean loadData(AppDataComponent data, Path filePath) throws IOException;

    int getval();

    void exportData(AppDataComponent data, Path filePath) throws IOException;
}
