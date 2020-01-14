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

import java.io.File;
import java.io.FileWriter;

import org.apache.uima.fit.factory.AggregateBuilder;
import org.apache.uima.fit.factory.AnalysisEngineFactory;

public class GenerateAggregateAnalysisDescriptor {

	public void generateDescriptor(String fileName) throws Exception {

		AggregateBuilder builder = new AggregateBuilder();

		builder.add(AnalysisEngineFactory.createEngineDescription(SimpleTokenAnnotator.class));
		builder.add(AnalysisEngineFactory.createEngineDescription(SimpleSentenceAnnotator.class));

		File file = new File(fileName);
		file.getParentFile().mkdirs();
		builder.createAggregateDescription().toXML(new FileWriter(new File(fileName)));
	}


	public static void main(String[] args) throws Exception {

		new GenerateAggregateAnalysisDescriptor().generateDescriptor(args[0] + "aaeDescriptor.xml");
	}

}
