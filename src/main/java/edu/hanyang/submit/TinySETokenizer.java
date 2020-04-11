package edu.hanyang.submit;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.tartarus.snowball.ext.PorterStemmer;

import edu.hanyang.indexer.Tokenizer;

public class TinySETokenizer implements Tokenizer {

	public void setup() {
		// Lucene analyzer & Porter Stemming
		// Lucene과 스테머의 라이브러리를 초기화
		SimpleAnalyzer analyzer = new SimpleAnalyzer();
		PorterStemmer stemmer = new PorterStemmer();
	}
	
	public List<String> split(String text) {
		// 텍스트를 초기화된 스테머를 이용하여 각각을 tokenizing
		// and stemming 후에 array list로 return
	
		List<String> result = new ArrayList<String>();
		SimpleAnalyzer analyzer = new SimpleAnalyzer();
		PorterStemmer stemmer = new PorterStemmer();
		
		try {
			TokenStream stream = analyzer.tokenStream(null, new StringReader(text));
			stream.reset();
			CharTermAttribute term = stream.getAttribute(CharTermAttribute.class);
			
			while(stream.incrementToken()) {
				stemmer.setCurrent(term.toString());
				stemmer.stem();
				result.add(stemmer.getCurrent());
				//get current를 하나씩 어레이에 넣는다
			}
			stream.close();
		} catch (IOException e) {throw new RuntimeException(e); }

		return result;
	}

	
	public void clean() {
		SimpleAnalyzer analyzer = new SimpleAnalyzer();
		analyzer.close();
		// analyzer를 close 하는 코드
	}

}
