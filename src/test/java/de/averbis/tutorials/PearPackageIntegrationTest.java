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
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.fit.util.SimpleNamedResourceManager;
import org.apache.uima.jcas.JCas;
import org.apache.uima.pear.tools.PackageBrowser;
import org.apache.uima.pear.tools.PackageInstaller;
import org.apache.uima.resource.PearSpecifier;
import org.apache.uima.util.XMLInputSource;
import org.junit.Assert;
import org.junit.Test;

import de.averbis.extraction.types.Sentence;
import de.averbis.extraction.types.Token;

/**
 * Tests that the PEAR was built, can be installed and produces the expected output.
 * <p>
 * This is not a regular unit test, but an integration test. First the PEAR file is built during the {@code package} phase of the Maven built. Integration tests run after that
 * during the {@code integration-test} phase.
 */
public class PearPackageIntegrationTest {

	@Test
	public void testProcessText() throws IOException, UIMAException {

		PackageBrowser packageBrowser = this.installPackage();
		XMLInputSource xmlInputSource = new XMLInputSource(packageBrowser.getComponentPearDescPath());
		PearSpecifier pearSpecifier = UIMAFramework.getXMLParser().parsePearSpecifier(xmlInputSource);

		AnalysisEngine analysisEngine = UIMAFramework.produceAnalysisEngine(pearSpecifier, new SimpleNamedResourceManager(), null);

		JCas jCas = JCasFactory.createJCas();
		jCas.setDocumentText("Test document. This is a test.");

		analysisEngine.process(jCas);

		Assert.assertEquals(8, JCasUtil.select(jCas, Token.class).size());
		Assert.assertEquals(2, JCasUtil.select(jCas, Sentence.class).size());

	}


	private PackageBrowser installPackage() {

		Collection<File> files = FileUtils.listFiles(new File("target/"), new String[] { "pear" }, false);

		Assert.assertEquals(1, files.size());

		File installDir = new File("target/generated-test-sources");
		File pearPackage = files.iterator().next();

		return PackageInstaller.installPackage(installDir, pearPackage, true, false);
	}
}
