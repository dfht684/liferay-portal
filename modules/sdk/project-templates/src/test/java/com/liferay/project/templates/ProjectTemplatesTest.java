/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.project.templates;

import aQute.bnd.main.bnd;

import com.liferay.project.templates.internal.util.FileUtil;
import com.liferay.project.templates.internal.util.Validator;
import com.liferay.project.templates.util.FileTestUtil;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.BuildTask;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Lawrence Lee
 * @author Andrea Di Giorgi
 */
public class ProjectTemplatesTest {

	@BeforeClass
	public static void setUpClass() throws IOException {
		String gradleDistribution = System.getProperty("gradle.distribution");

		if (Validator.isNull(gradleDistribution)) {
			Properties properties = FileTestUtil.readProperties(
				"gradle-wrapper/gradle/wrapper/gradle-wrapper.properties");

			gradleDistribution = properties.getProperty("distributionUrl");
		}

		_gradleDistribution = URI.create(gradleDistribution);

		_httpProxyHost = System.getProperty("http.proxyHost");
		_httpProxyPort = System.getProperty("http.proxyPort");
		_repositoryUrl = System.getProperty("repository.url");
	}

	@After
	public void tearDown() throws Exception {
		File velocityLog = new File("velocity.log");

		if (velocityLog.exists()) {
			velocityLog.delete();
		}
	}

	@Test
	public void testBuildTemplate() throws Exception {
		File projectDir = _buildTemplateWithGradle(null, "hello-world-portlet");

		_testExists(projectDir, "bnd.bnd");
		_testExists(
			projectDir, "src/main/resources/META-INF/resources/init.jsp");
		_testExists(
			projectDir, "src/main/resources/META-INF/resources/view.jsp");

		_testContains(
			projectDir, "build.gradle", "apply plugin: \"com.liferay.plugin\"");
		_testContains(
			projectDir,
			"src/main/java/hello/world/portlet/portlet/HelloWorldPortlet.java",
			"public class HelloWorldPortlet extends MVCPortlet {");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/hello.world.portlet-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateActivator() throws Exception {
		File projectDir = _buildTemplateWithGradle("activator", "bar-activator");

		_testExists(projectDir, "bnd.bnd");

		_testContains(
			projectDir, "build.gradle", "apply plugin: \"com.liferay.plugin\"");
		_testContains(
			projectDir, "src/main/java/bar/activator/BarActivator.java",
			"public class BarActivator implements BundleActivator {");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/bar.activator-1.0.0.jar");

		File gradleBundleFile = new File(projectDir, "build/libs/bar.activator-1.0.0.jar");

		File mavenProjectDir = _buildTemplateWithMaven(
			"activator", "bar-activator",
			"-DartifactId=bar-activator",
			"-Dpackage=bar.activator",
			"-DclassName=BarActivator",
			"-DprojectType=standalone");

		_executeMaven(mavenProjectDir, new String[] { _TASK_PATH_PACKAGE });

		_testExists(mavenProjectDir, "target/bar-activator-1.0.0.jar");

		File mavenBundleFile = new File(mavenProjectDir, "target/bar-activator-1.0.0.jar");

		_executeBndDiff(gradleBundleFile, mavenBundleFile);
	}

	@Test
	public void testBuildTemplateApi() throws Exception {
		File projectDir = _buildTemplateWithGradle("api", "foo");

		_testExists(projectDir, "bnd.bnd");

		_testContains(
			projectDir, "build.gradle", "apply plugin: \"com.liferay.plugin\"");
		_testContains(
			projectDir, "src/main/java/foo/api/Foo.java",
			"public interface Foo");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/foo-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateContentTargetingReport() throws Exception {
		File projectDir = _buildTemplateWithGradle("contenttargetingreport", "foo-bar");

		_testExists(projectDir, "bnd.bnd");

		_testContains(
			projectDir, "src/main/java/foo/bar/content/targeting/report/FooBarReport.java",
			"public class FooBarReport extends BaseJSPReport");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/foo.bar-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateContentTargetingRule() throws Exception {
		File projectDir = _buildTemplateWithGradle("contenttargetingrule", "foo-bar");

		_testExists(projectDir, "bnd.bnd");

		_testContains(
			projectDir, "src/main/java/foo/bar/content/targeting/rule/FooBarRule.java",
			"public class FooBarRule extends BaseJSPRule");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/foo.bar-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateContentTargetingTrackingAction() throws Exception {
		File projectDir = _buildTemplateWithGradle("contenttargetingtrackingaction", "foo-bar");

		_testExists(projectDir, "bnd.bnd");

		_testContains(
			projectDir, "src/main/java/foo/bar/content/targeting/tracking/action/FooBarTrackingAction.java",
			"public class FooBarTrackingAction extends BaseJSPTrackingAction");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/foo.bar-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateControlMenuEntry() throws Exception {
		File projectDir = _buildTemplateWithGradle("controlmenuentry", "foo-bar");

		_testExists(projectDir, "bnd.bnd");

		_testContains(
				projectDir, "src/main/java/foo/bar/control/menu/FooBarProductNavigationControlMenuEntry.java",
				"public class FooBarProductNavigationControlMenuEntry",
				"extends BaseProductNavigationControlMenuEntry",
				"implements ProductNavigationControlMenuEntry");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/foo.bar-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateFragment() throws Exception {
		File projectDir = _buildTemplateWithGradle(
			"fragment", "loginHook", "--host-bundle-symbolic-name",
			"com.liferay.login.web", "--host-bundle-version", "1.0.0");

		_testContains(
			projectDir, "bnd.bnd", "Bundle-SymbolicName: loginhook",
			"Fragment-Host: com.liferay.login.web;bundle-version=\"1.0.0\"");
		_testContains(
			projectDir, "build.gradle", "apply plugin: \"com.liferay.plugin\"");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/loginhook-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateMVCPortlet() throws Exception {
		File projectDir = _buildTemplateWithGradle("mvcportlet", "foo");

		_testExists(projectDir, "bnd.bnd");
		_testExists(
			projectDir, "src/main/resources/META-INF/resources/init.jsp");
		_testExists(
			projectDir, "src/main/resources/META-INF/resources/view.jsp");

		_testContains(
			projectDir, "build.gradle", "apply plugin: \"com.liferay.plugin\"");
		_testContains(
			projectDir, "src/main/java/foo/portlet/FooPortlet.java",
			"public class FooPortlet extends MVCPortlet {");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/foo-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateMVCPortletWithPackage() throws Exception {
		File projectDir = _buildTemplateWithGradle(
			"mvcportlet", "foo", "--package-name", "com.liferay.test");

		_testExists(projectDir, "bnd.bnd");
		_testExists(
			projectDir, "src/main/resources/META-INF/resources/init.jsp");
		_testExists(
			projectDir, "src/main/resources/META-INF/resources/view.jsp");

		_testContains(
			projectDir, "build.gradle", "apply plugin: \"com.liferay.plugin\"");
		_testContains(
			projectDir,
			"src/main/java/com/liferay/test/portlet/FooPortlet.java",
			"public class FooPortlet extends MVCPortlet {");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/com.liferay.test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateMVCPortletWithPortletSuffix()
		throws Exception {

		File projectDir = _buildTemplateWithGradle("mvcportlet", "portlet-portlet");

		_testExists(projectDir, "bnd.bnd");
		_testExists(
			projectDir, "src/main/resources/META-INF/resources/init.jsp");
		_testExists(
			projectDir, "src/main/resources/META-INF/resources/view.jsp");

		_testContains(
			projectDir, "build.gradle", "apply plugin: \"com.liferay.plugin\"");
		_testContains(
			projectDir,
			"src/main/java/portlet/portlet/portlet/PortletPortlet.java",
			"public class PortletPortlet extends MVCPortlet {");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/portlet.portlet-1.0.0.jar");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateOnExistingDirectory() throws Exception {
		File projectDir = new File(temporaryFolder.getRoot(), "bar-activator");

		Assert.assertTrue(projectDir.mkdirs());

		File file = new File(projectDir, "foo.txt");

		Assert.assertTrue(file.createNewFile());

		_buildTemplateWithGradle("activator", projectDir.getName());
	}

	@Test
	public void testBuildTemplatePanelApp() throws Exception {
		File projectDir = _buildTemplateWithGradle(
			"panelapp", "gradle.test", "--class-name", "Foo");

		_testExists(projectDir, "bnd.bnd");
		_testExists(projectDir, "build.gradle");

		_testContains(
			projectDir, "src/main/java/gradle/test/application/list/FooPanelApp.java",
			"public class FooPanelApp extends BasePanelApp");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/gradle.test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplatePortlet() throws Exception {
		File projectDir = _buildTemplateWithGradle(
			"portlet", "gradle.test", "--class-name", "Foo");

		_testExists(projectDir, "bnd.bnd");
		_testExists(
			projectDir, "src/main/resources/META-INF/resources/init.jsp");
		_testExists(
			projectDir, "src/main/resources/META-INF/resources/view.jsp");

		_testContains(
			projectDir, "build.gradle", "apply plugin: \"com.liferay.plugin\"");
		_testContains(
			projectDir, "src/main/java/gradle/test/portlet/FooPortlet.java",
			"package gradle.test.portlet;",
			"javax.portlet.display-name=gradle.test",
			"public class FooPortlet extends GenericPortlet {",
			"printWriter.print(\"gradle.test Portlet");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/gradle.test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplatePortletConfigurationIcon() throws Exception {
		File projectDir = _buildTemplateWithGradle(
		"portletconfigurationicon", "icontest", "--package-name", "blade.test");

		_testExists(projectDir, "bnd.bnd");

		_testContains(projectDir, "build.gradle",
				"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			projectDir, "src/main/java/blade/test/portlet/configuration/icon/IcontestPortletConfigurationIcon.java",
			"public class IcontestPortletConfigurationIcon",
			"extends BasePortletConfigurationIcon");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/blade.test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplatePortletProvider() throws Exception {
		File projectDir = _buildTemplateWithGradle(
		"portletprovider", "provider.test");

		_testExists(projectDir, "bnd.bnd");
		_testExists(projectDir, "build.gradle");

		_testContains(
			projectDir, "src/main/java/provider/test/constants/ProviderTestPortletKeys.java",
			"package provider.test.constants;",
			"public class ProviderTestPortletKeys",
			"public static final String ProviderTest = \"ProviderTest\";");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/provider.test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplatePortletToolbarContributor() throws Exception {
		File projectDir = _buildTemplateWithGradle(
		"portlettoolbarcontributor", "toolbartest", "--package-name", "blade.test");

		_testExists(projectDir, "bnd.bnd");

		_testContains(projectDir, "build.gradle",
				"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			projectDir,
			"src/main/java/blade/test/portlet/toolbar/contributor/ToolbartestPortletToolbarContributor.java",
			"public class ToolbartestPortletToolbarContributor",
			"implements PortletToolbarContributor");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/blade.test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateService() throws Exception {
		File projectDir = _buildTemplateWithGradle(
			"service", "servicepreaction", "--class-name", "FooAction",
			"--service", "com.liferay.portal.kernel.events.LifecycleAction");

		_testExists(projectDir, "bnd.bnd");

		_testContains(
			projectDir, "build.gradle", "apply plugin: \"com.liferay.plugin\"");

		String importLine =
			"import com.liferay.portal.kernel.events.LifecycleAction;";
		String classLine =
			"public class FooAction implements LifecycleAction {";

		File actionJavaFile = _testContains(
			projectDir, "src/main/java/servicepreaction/FooAction.java",
			"package servicepreaction;", importLine,
			"service = LifecycleAction.class", classLine);

		Path actionJavaPath = actionJavaFile.toPath();

		List<String> lines = Files.readAllLines(
			actionJavaPath, StandardCharsets.UTF_8);

		try (BufferedWriter bufferedWriter = Files.newBufferedWriter(
				actionJavaPath, StandardCharsets.UTF_8)) {

			for (String line : lines) {
				_write(bufferedWriter, line);

				if (line.equals(classLine)) {
					_write(
						bufferedWriter, "@Override",
						"public void processLifecycleEvent(",
						"LifecycleEvent lifecycleEvent)",
						"throws ActionException {", "System.out.println(",
						"\"login.event.pre=\" + lifecycleEvent);", "}");
				}
				else if (line.equals(importLine)) {
					_write(
						bufferedWriter,
						"import com.liferay.portal.kernel.events." +
							"LifecycleEvent;",
						"import com.liferay.portal.kernel.events." +
							"ActionException;");
				}
			}
		}

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/servicepreaction-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateServiceBuilder() throws Exception {
		_testBuildTemplateServiceBuilder(
			"guestbook", "com.liferay.docs.guestbook");
	}

	@Test
	public void testBuildTemplateServiceBuilderWithDashes() throws Exception {
		_testBuildTemplateServiceBuilder(
			"backend-integration", "com.liferay.backend.integration");
	}

	@Test
	public void testBuildTemplateServiceWrapper() throws Exception {
		File projectDir = _buildTemplateWithGradle(
			"servicewrapper", "serviceoverride", "--service",
			"com.liferay.portal.kernel.service.UserLocalServiceWrapper");

		_testExists(projectDir, "bnd.bnd");

		_testContains(
			projectDir, "build.gradle", "apply plugin: \"com.liferay.plugin\"");
		_testContains(
			projectDir, "src/main/java/serviceoverride/Serviceoverride.java",
			"package serviceoverride;",
			"import com.liferay.portal.kernel.service.UserLocalServiceWrapper;",
			"service = ServiceWrapper.class",
			"public class Serviceoverride extends UserLocalServiceWrapper {",
			"public Serviceoverride() {");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/serviceoverride-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateSimulationPanelEntry() throws Exception {
		File projectDir = _buildTemplateWithGradle(
		"simulationpanelentry", "simulator", "--package-name", "test.simulator");

		_testExists(projectDir, "bnd.bnd");

		_testContains(projectDir, "build.gradle",
				"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			projectDir,
			"src/main/java/test/simulator/application/list/SimulatorSimulationPanelApp.java",
			"public class SimulatorSimulationPanelApp",
			"extends BaseJSPPanelApp");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/test.simulator-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateContextContributor() throws Exception {
		File projectDir = _buildTemplateWithGradle(
		"templatecontextcontributor", "blade-test");

		_testExists(projectDir, "bnd.bnd");

		_testContains(projectDir, "build.gradle",
				"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			projectDir,
			"src/main/java/blade/test/theme/contributor/BladeTestTemplateContextContributor.java",
			"public class BladeTestTemplateContextContributor",
			"implements TemplateContextContributor");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/blade.test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateWithPackageName() throws Exception {
		File projectDir = _buildTemplateWithGradle(
			null, "barfoo", "--package-name", "foo.bar");

		_testExists(
			projectDir, "src/main/resources/META-INF/resources/init.jsp");
		_testExists(
			projectDir, "src/main/resources/META-INF/resources/view.jsp");

		_testContains(projectDir, "bnd.bnd", "Bundle-SymbolicName: foo.bar");
		_testContains(
			projectDir, "build.gradle", "apply plugin: \"com.liferay.plugin\"");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/foo.bar-1.0.0.jar");
	}

	@Test
	public void testListTemplates() throws Exception {
		Set<String> templates = new HashSet<>(
			Arrays.asList(ProjectTemplates.getTemplates()));

		final Set<String> expectedTemplates = new HashSet<>();

		try (DirectoryStream<Path> directoryStream =
				FileTestUtil.getProjectTemplatesDirectoryStream()) {

			for (Path path : directoryStream) {
				Path fileNamePath = path.getFileName();

				String fileName = fileNamePath.toString();

				String template = fileName.substring(
					FileTestUtil.PROJECT_TEMPLATE_DIR_PREFIX.length());

				expectedTemplates.add(template);
			}
		}

		Assert.assertEquals(expectedTemplates, templates);
	}

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	private File _buildTemplateWithGradle(String template, String name, String... args)
		throws Exception {

		File destinationDir = new File(temporaryFolder.getRoot(), "gradle");

		List<String> completeArgs = new ArrayList<>(args.length + 6);

		completeArgs.add("--destination");
		completeArgs.add(destinationDir.getPath());
		completeArgs.add("--name");
		completeArgs.add(name);

		if (Validator.isNotNull(template)) {
			completeArgs.add("--template");
			completeArgs.add(template);
		}

		for (String arg : args) {
			completeArgs.add(arg);
		}

		ProjectTemplates.main(
			completeArgs.toArray(new String[completeArgs.size()]));

		File projectDir = new File(destinationDir, name);

		_testExists(projectDir, ".gitignore");
		_testExists(projectDir, "build.gradle");
		_testExists(projectDir, "gradlew");
		_testExists(projectDir, "gradlew.bat");
		_testExists(projectDir, "gradle/wrapper/gradle-wrapper.jar");
		_testExists(projectDir, "gradle/wrapper/gradle-wrapper.properties");
		_testNotExists(projectDir, "pom.xml");

		return projectDir;
	}

	private File _buildTemplateWithMaven(String template, String name, String... args)
		throws Exception {

		File destinationDir = new File(temporaryFolder.getRoot(), "maven");

		List<String> completeArgs = new ArrayList<>();

		completeArgs.add("archetype:generate");
		completeArgs.add("-B");
		completeArgs.add("-DarchetypeArtifactId=com.liferay.project.templates." + template);
		completeArgs.add("-DarchetypeGroupId=com.liferay");
		completeArgs.add("-DarchetypeVersion=1.0.0");
		completeArgs.add("-DgroupId=com.test");
		completeArgs.add("-Dversion=1.0.0");

		for (String arg : args) {
			completeArgs.add(arg);
		}

		_executeMaven(destinationDir, completeArgs.toArray(new String[0]));

		File projectDir = new File(destinationDir, name);

		_testExists(projectDir, "pom.xml");
		_testNotExists(projectDir, "gradlew");
		_testNotExists(projectDir, "gradlew.bat");
		_testNotExists(projectDir, "gradle/wrapper/gradle-wrapper.jar");
		_testNotExists(projectDir, "gradle/wrapper/gradle-wrapper.properties");

		return projectDir;
	}

	private void _executeBndDiff(File gradleBundleFile, File mavenBundleFile) throws Exception {
		StringBuilder exclusions = new StringBuilder();
			exclusions.append("Archiver-Version, ");
			exclusions.append("Build-Jdk, ");
			exclusions.append("Built-By, ");
			exclusions.append("Javac-Debug, ");
			exclusions.append("Javac-Deprecation, ");
			exclusions.append("Javac-Encoding, ");
			exclusions.append("*pom.properties, ");
			exclusions.append("*pom.xml");
		
		String[] args = {
				"diff",
				"-i",
				exclusions.toString(),
				gradleBundleFile.getPath(),
				mavenBundleFile.getPath()};
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		PrintStream ps = new PrintStream(output);
		
		System.setOut(ps);
		
		bnd main = new bnd();
		
		try {
			main.start(args);
		} finally {
			main.close();
		}
		
		Assert.assertEquals("Gradle Jar does not match Maven Jar", "", new String(output.toByteArray()));
	}

	private void _executeGradle(
			File projectDir, String taskPath, String... testTaskPaths)
		throws IOException {

		if (Validator.isNotNull(_repositoryUrl)) {
			File buildGradleFile = new File(projectDir, "build.gradle");

			Path buildGradlePath = buildGradleFile.toPath();

			String buildGradle = FileTestUtil.read(buildGradlePath);

			buildGradle = buildGradle.replace(
				"\"" + _REPOSITORY_CDN_URL + "\"",
				"\"" + _repositoryUrl + "\"");

			Files.write(
				buildGradlePath, buildGradle.getBytes(StandardCharsets.UTF_8));
		}

		GradleRunner gradleRunner = GradleRunner.create();

		if (Validator.isNotNull(_httpProxyHost) &&
			Validator.isNotNull(_httpProxyPort)) {

			gradleRunner.withArguments(
				"-Dhttp.proxyHost=" + _httpProxyHost,
				"-Dhttp.proxyPort=" + _httpProxyPort, taskPath);
		}
		else {
			gradleRunner.withArguments(taskPath);
		}

		gradleRunner.withGradleDistribution(_gradleDistribution);
		gradleRunner.withProjectDir(projectDir);

		BuildResult buildResult = gradleRunner.build();

		if (testTaskPaths.length == 0) {
			testTaskPaths = new String[] {taskPath};
		}

		for (String testTaskPath : testTaskPaths) {
			BuildTask buildTask = buildResult.task(testTaskPath);

			Assert.assertNotNull(
				"Build task \"" + testTaskPath + "\" not found", buildTask);

			Assert.assertEquals(
				"Unexpected outcome for task \"" + buildTask.getPath() + "\"",
				TaskOutcome.SUCCESS, buildTask.getOutcome());
		}
	}

	private void _executeMaven(File projectDir, String[] args)
		throws Exception {

		File deps = new File("build/mavenEmbedderDeps.txt");

		String[] lines = FileUtil.readFile(deps);

		URL[] urls = new URL[lines.length];

		for (int i = 0; i < lines.length; i++) {
			File file = new File(lines[i]);

			URI uri = file.toURI();

			urls[i] = uri.toURL();
		}

		try (URLClassLoader classLoader = new URLClassLoader(urls, null)) {

			Class<?> mavenCLIClazz = classLoader.loadClass("org.apache.maven.cli.MavenCli");

			Object mavenCli = mavenCLIClazz.newInstance();

			ByteArrayOutputStream output = new ByteArrayOutputStream();
			ByteArrayOutputStream errorOutput = new ByteArrayOutputStream();

			Method doMain = mavenCLIClazz.getMethod("doMain", String[].class, String.class, PrintStream.class, PrintStream.class);

			Thread currentThread = Thread.currentThread();

			ClassLoader contextClassLoader = currentThread.getContextClassLoader();

			currentThread.setContextClassLoader(classLoader);

			try {
				Integer retcode = (Integer) doMain.invoke(mavenCli,args, projectDir.getAbsolutePath(), new PrintStream(output), new PrintStream(errorOutput));

				Assert.assertEquals(new String(errorOutput.toByteArray()), 0, retcode.intValue());
			}
			finally {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	private void _testBuildTemplateServiceBuilder(
			String name, String packageName)
		throws Exception {

		File projectDir = _buildTemplateWithGradle(
			"servicebuilder", name, "--package-name", packageName);

		String apiProjectName = name + "-api";
		String serviceProjectName = name + "-service";

		_testContains(
			projectDir, "settings.gradle",
			"include \"" + apiProjectName + "\", \"" + serviceProjectName +
				"\"");
		_testContains(
			projectDir, apiProjectName + "/bnd.bnd", "Export-Package:\\",
			packageName + ".exception,\\", packageName + ".model,\\",
			packageName + ".service,\\", packageName + ".service.persistence");
		_testContains(
			projectDir, serviceProjectName + "/bnd.bnd",
			"Liferay-Service: true");
		_testContains(
			projectDir, serviceProjectName + "/build.gradle",
			"compileOnly project(\":" + apiProjectName + "\")");

		_executeGradle(projectDir, ":" + serviceProjectName + ":buildService");

		_executeGradle(
			projectDir, "build", ":" + apiProjectName + ":build",
			":" + serviceProjectName + ":build");

		_testExists(
			projectDir,
			apiProjectName + "/build/libs/" + packageName + ".api-1.0.0.jar");
		_testExists(
			projectDir,
			serviceProjectName + "/build/libs/" + packageName +
				".service-1.0.0.jar");
	}

	private File _testContains(File dir, String fileName, String... strings)
		throws IOException {

		File file = _testExists(dir, fileName);

		String content = FileTestUtil.read(file.toPath());

		for (String s : strings) {
			Assert.assertTrue(
				"Not found in " + fileName + ": " + s, content.contains(s));
		}

		return file;
	}

	private File _testExists(File dir, String fileName) {
		File file = new File(dir, fileName);

		Assert.assertTrue("Missing " + fileName, file.exists());

		return file;
	}

	private File _testNotExists(File dir, String fileName) {
		File file = new File(dir, fileName);

		Assert.assertFalse("Unexpected " + fileName, file.exists());

		return file;
	}

	private void _write(Writer writer, String... lines) throws IOException {
		for (String line : lines) {
			writer.write(line);
			writer.write(System.lineSeparator());
		}
	}

	private static final String _REPOSITORY_CDN_URL =
		"https://cdn.lfrs.sl/repository.liferay.com/nexus/content/groups/" +
			"public";

	private static final String _TASK_PATH_BUILD = ":build";
	private static final String _TASK_PATH_PACKAGE = "package";

	private static URI _gradleDistribution;
	private static String _httpProxyHost;
	private static String _httpProxyPort;
	private static String _repositoryUrl;

}