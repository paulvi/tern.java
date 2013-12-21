package tern.server.nodejs.process;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import tern.TernException;

/**
 * Test with node.js process.
 * 
 */
public class NodejsProcessTest {

	private static final int TIMEOUT = 1000;

	/**
	 * Test when node.js base dir is not well configurated.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	public void badNodejsBaseDir() throws IOException, InterruptedException {

		File nodejsBaseDir = new File(".");
		File nodejsTernBaseDir = new File(".");
		File projectDir = new File(".");

		NodejsProcess process = new NodejsProcess(nodejsBaseDir,
				nodejsTernBaseDir, projectDir);
		process.addProcessListener(new NodejsProcessAdapter() {

			@Override
			public void onData(NodejsProcess server, String line) {
				System.err.println(line);
			}
		});
		try {
			process.start(TIMEOUT);
			Assert.assertTrue(false);
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage(), true);
		}
	}

	@Test
	public void badNodejsTernBaseDir() throws IOException,
			InterruptedException, TernException {

		File nodejsBaseDir = PathHelper.getNodejsBasedir();
		File nodejsTernBaseDir = new File(".");
		File projectDir = new File(".");

		NodejsProcess process = new NodejsProcess(nodejsBaseDir,
				nodejsTernBaseDir, projectDir);

		final StringBuilder error = new StringBuilder();
		process.addProcessListener(new NodejsProcessAdapter() {

			@Override
			public void onError(NodejsProcess server, String line) {
				error.append(line);
			}
		});

		try {
			process.start(TIMEOUT);
		} catch (Exception e) {

		}
		Assert.assertNotEquals(0, error.toString().length());
		System.err.println(error.toString());
		// TernDoc doc = new TernDoc();
		// doc.addFile("test.js", "var a = '';", null);
		//
		// String baseURL = "http://localhost:" + port;
		// JSONObject json = TernProtocolHelper.makeRequest(baseURL, doc, false,
		// null, null);

	}

	@Test
	public void ok() throws IOException, InterruptedException, TernException {

		File nodejsBaseDir = PathHelper.getNodejsBasedir();
		File nodejsTernBaseDir = PathHelper.getNodejsTernBaseDir();
		File projectDir = new File(".");

		NodejsProcess process = new NodejsProcess(nodejsBaseDir,
				nodejsTernBaseDir, projectDir);

		final StringBuilder error = new StringBuilder();
		process.addProcessListener(new NodejsProcessAdapter() {

			@Override
			public void onError(NodejsProcess server, String line) {
				error.append(line);
			}
		});

		try {
			process.start(TIMEOUT);
		} catch (Exception e) {

		}
		Assert.assertEquals(0, error.toString().length());
		System.err.println(error.toString());

	}
}