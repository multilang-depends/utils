package multilang.depends.util.file.path;

public class WindowsPathFilenameWritter implements FilenameWritter{
	@Override
	public String reWrite(String originalPath) {
		return originalPath.replaceAll("/","\\\\");
	}
}
