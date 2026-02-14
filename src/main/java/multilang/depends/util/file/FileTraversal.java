/*
MIT License

Copyright (c) 2018-2019 Gang ZHANG

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package multilang.depends.util.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Recursively visit every file in the given root path using the 
 * extended IFileVisitor
 *
 */
public class FileTraversal {
	/**
	 * The visitor interface 
	 * Detail operation should be implemented here
	 */
	public interface IFileVisitor {
		void visit(File file);
	}
	
	IFileVisitor visitor;
	private ArrayList<String> extensionFilters = new ArrayList<>();
	private ArrayList<String> excludePatterns = new ArrayList<>();
	private List<PathMatcher> excludeMatchers = new ArrayList<>();
	boolean shouldVisitDirectory = false;
	boolean shouldVisitFile = true;
	public FileTraversal(IFileVisitor visitor){
		this.visitor = visitor;
	}

	public FileTraversal(IFileVisitor visitor,boolean shouldVisitDirectory,boolean shouldVisitFile){
		this.visitor = visitor;
		this.shouldVisitDirectory = shouldVisitDirectory;
		this.shouldVisitFile = shouldVisitFile;
	}
	
	public void travers(String path) {
		File dir = new File(path);
		travers(dir);
	}

	public void travers(File root) {
		File[] files = root.listFiles();
		if (files == null)
			return;
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				if (!isExcluded(files[i])) {
					travers(files[i]);
					if (shouldVisitDirectory) {
						invokeVisitor(files[i]);
					}
				}
			} else {
				if (shouldVisitFile) {
					invokeVisitor( files[i]);
				}
			}
		}		
	}

	private void invokeVisitor(File f) {
		if (isExcluded(f)) {
			return;
		}
		if (extensionFilters.size()==0) {
			visitor.visit(f);
		}else {
			for (String ext:extensionFilters) {
				if (f.getAbsolutePath().toLowerCase().endsWith(ext.toLowerCase())) {
					visitor.visit(f);
				}
			}
		}
	}

	private boolean isExcluded(File f) {
		if (excludeMatchers.isEmpty()) {
			return false;
		}
		String filePath = f.getAbsolutePath();
		for (PathMatcher matcher : excludeMatchers) {
			if (matcher.matches(Paths.get(filePath))) {
				return true;
			}
		}
		return false;
	}

	public FileTraversal extensionFilter(String ext) {
		this.extensionFilters.add(ext.toLowerCase());
		return this;
	}

	public void extensionFilter(String[] fileSuffixes) {
		for (String fileSuffix:fileSuffixes){
			extensionFilter(fileSuffix);
		}
	}

	public FileTraversal excludeFilter(String[] patterns) {
		for (String pattern : patterns) {
			this.excludePatterns.add(pattern);
			String globPattern = pattern.startsWith("glob:") ? pattern : "glob:" + pattern;
			PathMatcher matcher = FileSystems.getDefault().getPathMatcher(globPattern);
			this.excludeMatchers.add(matcher);
			
			// Also add matchers with **/ prefix for subdirectory matching
			// This allows "Projects/**" to match "/path/to/Projects/file.py"
			if (!pattern.startsWith("glob:**") && !pattern.startsWith("**")) {
				// Pattern for matching contents within the excluded directory
				String subdirPattern = "glob:**/" + pattern;
				PathMatcher subdirMatcher = FileSystems.getDefault().getPathMatcher(subdirPattern);
				this.excludeMatchers.add(subdirMatcher);
				
				// Also add pattern without the trailing /** to match the directory itself
				// e.g., "Projects/**" -> "**/Projects" to match "/path/to/Projects"
				if (pattern.endsWith("/**")) {
					String dirPattern = "glob:**/" + pattern.substring(0, pattern.length() - 3);
					PathMatcher dirMatcher = FileSystems.getDefault().getPathMatcher(dirPattern);
					this.excludeMatchers.add(dirMatcher);
				}
			}
		}
		return this;
	}
}