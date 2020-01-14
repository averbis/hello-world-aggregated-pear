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

import de.averbis.extraction.types.Token;

public class SimpleTokenAnnotator extends JCasAnnotator_ImplBase {

	private static Pattern pattern = Pattern.compile("[|.+*;,!?/\n\r	 :@_()\"`„”““—’‘'¿-]");


	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {

		String text = jcas.getDocumentText();

		int annoBegin = 0;
		Matcher m = SimpleTokenAnnotator.pattern.matcher(text);
		int oldEnd = 0;

		while (m.find()) {
			Token token = new Token(jcas, annoBegin + oldEnd, annoBegin + m.start());
			token.addToIndexes();
			oldEnd = m.end();
		}
		Token token = new Token(jcas, annoBegin + oldEnd, annoBegin + text.length());
		token.addToIndexes();
	}

}
