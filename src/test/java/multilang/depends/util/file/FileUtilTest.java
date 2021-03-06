package multilang.depends.util.file;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileUtilTest {

	@Test
	public void test_uniform_path_1() {
		String r = FileUtil.uniformPath("/home/user/abc/a.cpp");
		assertEquals("/home/user/abc/a.cpp",r);
	}

	@Test
	public void test_uniform_path_1w() {
		String r = FileUtil.uniformPath("C:\\home\\user\\abc\\a.cpp");
		assertEquals("C:/home/user/abc/a.cpp",r);
	}

	
	@Test
	public void test_uniform_path_2() {
		String r = FileUtil.uniformPath("/home/./user/abc/./a.cpp");
		assertEquals("/home/user/abc/a.cpp",r);
	}
	
	
	@Test
	public void test_uniform_path_3() {
		String r = FileUtil.uniformPath("/home/./user/abc/../a.cpp");
		assertEquals("/home/user/a.cpp",r);
	}
}
