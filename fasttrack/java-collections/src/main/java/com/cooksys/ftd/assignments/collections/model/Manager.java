package com.cooksys.ftd.assignments.collections.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * TODO: Implement this class <br>
 * <br>
 * A manager is a type of employee that can be a superior to another employee.
 * <br>
 * A manager should have a name, and, optionally, a manager superior to them.
 */
public class Manager implements Employee {

	private Manager manager;

	private String name;

	/**
	 * TODO: Implement this constructor.
	 *
	 * @param name the name of the manager to be created
	 */
	public Manager(String name) {
		this.name = name;
	}

	/**
	 * TODO: Implement this constructor.
	 *
	 * @param name    the name of the manager to be created
	 * @param manager the direct manager of the manager to be created
	 */
	public Manager(String name, Manager manager) {
		this.name = name;
		this.manager = manager;
	}

	/**
	 * TODO: Implement this getter.
	 *
	 * @return the name of the manager
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * TODO: Implement this getter.
	 *
	 * @return {@code true} if this manager has a manager, or {@code false}
	 *         otherwise
	 */
	@Override
	public boolean hasManager() {
		if (manager != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * TODO: Implement this getter.
	 *
	 * @return the manager of this manager, or {@code null} if it has none
	 */
	@Override
	public Manager getManager() {
		return manager;
	}

	/**
	 * TODO: Implement this method. <br>
	 * <br>
	 * Retrieves the manager's chain of command as a {@code List<Manager>}, starting
	 * with their direct {@code Manager}, followed by that {@code Manager}'s
	 * {@code Manager}, and so on, until the top of the hierarchy is reached. <br>
	 * <br>
	 * The returned list should never be or contain {@code null}. <br>
	 * <br>
	 * If the manager does not have a {@code Manager}, an empty
	 * {@code List<Manager>} should be returned.
	 *
	 * @return a {@code List<Manager>} that represents the manager's chain of
	 *         command,
	 */
	@Override
	public List<Manager> getChainOfCommand() {
		List<Manager> chain = new ArrayList<Manager>();
		boolean managerExists = true;
		Manager ourManager = getManager();
		if (ourManager == null) {
			return chain;
		}

		while (managerExists) {
			if (ourManager.hasManager()) {
				Manager nextManager = ourManager.getManager();
				chain.add(ourManager);
				ourManager = nextManager;
			} else {
				chain.add(ourManager);
				managerExists = false;
			}
		}

		return chain;
	}

	@Override
	public int hashCode() {
		return Objects.hash(manager, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Manager other = (Manager) obj;
		return Objects.equals(manager, other.manager) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Manager {manager=" + manager + ", name=" + name + "}";
	}

}
