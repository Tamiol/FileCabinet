import pl.pasturczak.Folder;
import pl.pasturczak.MultiFolder;

import java.util.List;

class TestMultiFolder extends TestFolder implements MultiFolder {

    private final List<Folder> folders;

    TestMultiFolder(String name, String size, List<Folder> folders) {
        super(name, size);
        this.folders = folders;
    }

    @Override
    public List<Folder> getFolders() {
        return folders;
    }
}
