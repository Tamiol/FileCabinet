import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pasturczak.Cabinet;
import pl.pasturczak.FileCabinet;
import pl.pasturczak.Folder;

import java.util.List;
import java.util.Optional;

class FileCabinetTest {

    private Cabinet fileCabinet;

    @BeforeEach
    void setUp() {
        Folder folder1 = new TestFolder("folder1", "SMALL");
        Folder folder2 = new TestFolder("folder2", "MEDIUM");
        Folder folder3 = new TestFolder("folder3", "SMALL");
        Folder folder4 = new TestFolder("folder4", "LARGE");

        Folder multiFolder2 = new TestMultiFolder(
                "multiFolder2",
                "LARGE",
                List.of(folder4)
        );

        Folder multiFolder1 = new TestMultiFolder(
                "multiFolder1",
                "LARGE",
                List.of(folder3, multiFolder2)
        );

        fileCabinet = new FileCabinet(
                List.of(folder1, folder2, multiFolder1)
        );
    }

    @Test
    public void testCount() {
        Assertions.assertEquals(6, fileCabinet.count());
    }

    @Test
    public void findMultiFolderByName() {
        Optional<Folder> folder = fileCabinet.findFolderByName("multiFolder2");
        Assertions.assertTrue(folder.isPresent());
    }

    @Test
    public void tryToFindNullNameFolder() {
        Optional<Folder> folder = fileCabinet.findFolderByName(null);
        Assertions.assertFalse(folder.isPresent());
    }

    @Test
    public void findFolderBySize() {
        List<Folder> folder = fileCabinet.findFoldersBySize("SMALL");
        Assertions.assertEquals(2, folder.size());
    }

    @Test
    public void tryToFindWrongSizeFolder() {
        List<Folder> folder = fileCabinet.findFoldersBySize("WrongSize");
        Assertions.assertEquals(0, folder.size());

    }
}