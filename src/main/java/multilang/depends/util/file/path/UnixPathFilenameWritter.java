package multilang.depends.util.file.path;

public class UnixPathFilenameWritter implements FilenameWritter{
	@Override
	public String reWrite(String originalPath) {
		return originalPath.replaceAll("\\\\", "/");
	}
}
