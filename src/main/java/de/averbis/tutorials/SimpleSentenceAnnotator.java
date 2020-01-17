/*
 * Copyright 2020 Averbis GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.averbis.tutorials;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;

import de.averbis.extraction.types.Sentence;

/**
 * Simple sentence splitter which uses a basic regular expression to separate the text into sentences.
 */
public class SimpleSentenceAnnotator extends JCasAnnotator_ImplBase {

	private static Pattern pattern = Pattern.compile("([.?!]+)(\\s+)([^\\p{Ll}])");


	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {

		String text = jcas.getDocumentText();
		Matcher matcher = SimpleSentenceAnnotator.pattern.matcher(text);
		int start = 0;
		while (matcher.find(start)) {
			int eosSymbEnd = matcher.end(1);

			this.createSentence(jcas, start, eosSymbEnd);
			start = matcher.start(3);
		}
		if (start < text.length()) {
			this.createSentence(jcas, start, text.length());
		}

	}


	private void createSentence(JCas aJCas, int begin, int end) {

		Sentence sentence = new Sentence(aJCas, begin, end);
		sentence.addToIndexes();
	}
}
