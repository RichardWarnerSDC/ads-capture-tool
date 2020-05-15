package offlineads;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

class FileHandlerTest {

    @Test
    public void FilesWithMatchingExtensionShouldReturnTrue() {
        assertEquals(true, FileHandler.hasValidExtension(new File("test.pdf"), "pdf"));
        assertEquals(false, FileHandler.hasValidExtension(new File("test.php"), "pdf"));
        assertEquals(true, FileHandler.hasValidExtension(new File("test.rtf.pdf"), "pdf"));
    }
}