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
import java.io.FileOutputStream;

import org.apache.uima.fit.factory.AggregateBuilder;
import org.apache.uima.fit.factory.AnalysisEngineFactory;

/**
 * Generates an aggregate descriptor (a pipeline) containing the {@ink SimpleTokenAnnotator} and the {@link SimpleSentenceAnnotator} and writes this to disk. This becomes the
 * top-level analysis component for the PEAR. It is called during the build using the {@code exec-maven-plugin}. See the {@code pom.xml} file in the root folder of the project.
 */
public class GenerateAggregateAnalysisDescriptor {

	public void generateDescriptor(File file) throws Exception {

		AggregateBuilder builder = new AggregateBuilder();

		builder.add(AnalysisEngineFactory.createEngineDescription(SimpleTokenAnnotator.class));
		builder.add(AnalysisEngineFactory.createEngineDescription(SimpleSentenceAnnotator.class));

		file.getParentFile().mkdirs();
		try (FileOutputStream os = new FileOutputStream(file)) {
			builder.createAggregateDescription().toXML(os);
		}
	}


	public static void main(String[] args) throws Exception {

		new GenerateAggregateAnalysisDescriptor().generateDescriptor(new File(args[0], "aaeDescriptor.xml"));
	}

}
