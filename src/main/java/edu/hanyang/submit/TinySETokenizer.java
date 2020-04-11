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
		// Lucene�� ���׸��� ���̺귯���� �ʱ�ȭ
		SimpleAnalyzer analyzer = new SimpleAnalyzer();
		PorterStemmer stemmer = new PorterStemmer();
	}
	
	public List<String> split(String text) {
		// �ؽ�Ʈ�� �ʱ�ȭ�� ���׸Ӹ� �̿��Ͽ� ������ tokenizing
		// and stemming �Ŀ� array list�� return
	
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
				//get current�� �ϳ��� ��̿� �ִ´�
			}
			stream.close();
		} catch (IOException e) {throw new RuntimeException(e); }

		return result;
	}

	
	public void clean() {
		SimpleAnalyzer analyzer = new SimpleAnalyzer();
		analyzer.close();
		// analyzer�� close �ϴ� �ڵ�
	}

}
