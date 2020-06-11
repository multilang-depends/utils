package multilang.depends.util.file;

import static org.junit.Assert.*;

import multilang.depends.util.file.path.WindowsPathFilenameWritter;
import org.junit.Test;

public class WindowsPathFilenameWritterTest {

	@Test
	public void testRewriteFilename() {
		WindowsPathFilenameWritter r = new WindowsPathFilenameWritter();
		assertEquals("\\abc\\123.cpp",r.reWrite("/abc/123.cpp"));
		assertEquals("\\abc\\123.cpp",r.reWrite("\\abc\\123.cpp"));
	}

}
