/*
 * 
 */
package hs.neo4j_hw.simple_api;

/**
 * Simple POJO object containing Employee properties. 
 * 
 * 
 * @author Harry
 *
 */
final class Employee {

    private int emp_id;
    private String name;
	 
    Employee(int emp_id, String name) {
        this.emp_id = emp_id;
        this.name = name;
    }

	final int getEmp_id() {
		return emp_id;
	}

	final void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

	final String getName() {
		return name;
	}

	final void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Employee [emp_id=" + emp_id + ", name=" + name + "]";
	}

}
