/**
 * 
 */
package hs.neo4j_hw.simple_api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service layer class to interact to underlying store.  This can be used for mocking the behavior of the graph store
 * and is for test purposes only.  
 * 
 * @author Harry Stovall
 *
 */
final class MapBackedEmployeeService {
	 
    private static final Map<Integer, Employee> employees = new HashMap<>();
	 
    MapBackedEmployeeService() { }
	
    Employee findById(int id) {
    	return employees.get(id);
    }

    Employee add(final Employee empl) {
    	employees.put(empl.getEmp_id(), empl);
    	return empl;
    }

    public List<Employee> findAll() {
    	return new ArrayList<Employee>(employees.values());
    }
	 
}
