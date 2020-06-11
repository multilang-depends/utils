package multilang.depends.util.file;

import static org.junit.Assert.*;

import multilang.depends.util.file.path.UnixPathFilenameWritter;
import org.junit.Test;

public class UnixPathFilenameWritterTest {

	@Test
	public void testRewriteFilename() {
		UnixPathFilenameWritter r = new UnixPathFilenameWritter();
		assertEquals("/abc/123.cpp",r.reWrite("/abc/123.cpp"));
		assertEquals("/abc/123.cpp",r.reWrite("\\abc\\123.cpp"));
		assertEquals("abc/123.cpp",r.reWrite("abc/123.cpp"));
	}

}
