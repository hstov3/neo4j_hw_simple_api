/**
 * 
 */
package hs.neo4j_hw.simple_api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Harry
 *
 */
final class EmployeeService {
	 
    private static Map<Integer, Employee> employees = new HashMap<Integer, Employee>();
	 
    EmployeeService() { }
	
    Employee findById(int id) {
    	return employees.get(id);
    }

    Employee add(int empl_id, final String name) {
    	Employee empl = new Employee(empl_id, name);
    	employees.put(empl_id, empl);
    	return empl;
    }

    public List<Employee> findAll() {
    	return new ArrayList<Employee>(employees.values());
    }
	 
}
