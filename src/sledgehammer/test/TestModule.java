package sledgehammer.test;

import java.io.IOException;
import java.util.Scanner;

import sledgehammer.SledgeHammer;
import sledgehammer.module.Module;
import sledgehammer.util.Printable;
import zombie.core.Core;

/**
 * Class designed to handle common operations for testing <Module>s.
 * 
 * @author Jab
 *
 * @param <M>
 *            The <Module> being tested.
 */
public abstract class TestModule<M extends Module> extends Printable {

	/** The <Module> being tested. */
	private M module;
	
	private Scanner scanner = new Scanner(System.in);

	/**
	 * Initializes <SledgeHammer> operations in a debug setting.
	 */
	public void initializeSledgehammer() {
		Core.GameSaveWorld = "servertest";
		SledgeHammer.instance = new SledgeHammer(true);
		SledgeHammer.instance.init();
		setModule(createModule());
		SledgeHammer.instance.getModuleManager().registerModule(getModule());
		SledgeHammer.instance.getModuleManager().onLoad(true);
		SledgeHammer.instance.start();
	}

	/**
	 * Pauses the execution of the test. Often used to observe data-sets affected
	 * during the course of the test.
	 */
	public void pause() {
		println("Press ENTER to continue the test.");
		do {				
			try {
				Thread.sleep(500L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		while(!scanner.hasNextLine());
		scanner.nextLine();
	}

	/**
	 * @return Returns the <Module> being tested.
	 */
	public M getModule() {
		return this.module;
	}

	/**
	 * (Internal Method)
	 * 
	 * Sets the <Module> being tested.
	 * 
	 * @param module
	 *            The <Module> to set.
	 */
	private void setModule(M module) {
		this.module = module;
	}

	/**
	 * Use this method to create and assign the <Module> being tested.
	 * 
	 * @return The <Module> instance to be tested.
	 */
	public abstract M createModule();

	/**
	 * This method executes the test.
	 */
	public abstract void run();
}
