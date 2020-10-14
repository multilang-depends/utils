package com.github.gangz.source.text;
import static org.junit.Assert.*;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;


public class TestIdentifierSpliter {

	static IdentifierSplitter  splitter;
	
	@BeforeClass
	public static void createSplitter(){
		splitter = new IdentifierSplitter();
	}
	
	@Test
	public void T_should_be_T() {
		List<String> result = splitter.split("T");
		String[] expected = {"T"};
		assertArrayEquals(expected, result.toArray());
	}
	
	@Test
	public void Hello_World_should_be_Hello_World() {
		List<String> result = splitter.split("Hello_World");
		String[] expected = {"Hello","World"};
		assertArrayEquals(expected, result.toArray());
	}

	@Test
	public void should_ignore_leading_underscore() {
		List<String> result = splitter.split("_Hello_World");
		String[] expected = {"Hello","World"};
		assertArrayEquals(expected, result.toArray());
	}
	
	@Test
	public void continue_under_score_should_be_ignored() {
		List<String> result = splitter.split("_Hello__World");
		String[] expected = {"Hello","World"};
		assertArrayEquals(expected, result.toArray());
	}
	
	@Test
	public void CamelCase_should_be_splitted() {
		List<String> result = splitter.split("HelloWorld");
		String[] expected = {"Hello","World"};
		assertArrayEquals(expected, result.toArray());
	}
	
	@Test
	public void ContinueUpperCase_should_be_treat_as_one_word() {
		List<String> result = splitter.split("DNAReader");
		String[] expected = {"DNA","Reader"};
		assertArrayEquals(expected, result.toArray());
	}
	
	@Test
	public void ContinueUpperCase_with_underscore_should_be_two_words() {
		List<String> result = splitter.split("FONT_SIZE");
		String[] expected = {"FONT","SIZE"};
		assertArrayEquals(expected, result.toArray());
	}
	
	@Test
	public void end_with_ContinueUpperCase_should_be_handled_correctly() {
		List<String> result = splitter.split("_readDNA");
		String[] expected = {"read","DNA"};
		assertArrayEquals(expected, result.toArray());
	}
	
	@Test
	public void pre_defined_word_should_not_be_splitted() {
		splitter.addPredefinedWord("JUnit4");
		List<String> result = splitter.split("JUnit4Runner");
		String[] expected = {"JUnit4","Runner"};
		assertArrayEquals(expected, result.toArray());
	}
}
