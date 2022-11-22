package org.maven.apache.search;

/**
 * 这个 Class 支持了模糊搜索所需要的字符的转化
 * 可以并支持模糊搜索的 Mapper Interface 一同使用。
 * @author james
 *
 */
public class FuzzySearch {
	
	public static String getFuzzyName(String name) {
		return "%" + name + "%";
	}

}
