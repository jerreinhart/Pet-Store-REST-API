package pet.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

public class StoreService {

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private PetStoreDao petStoreDao;

	public PetStoreData savePetStore(PetStoreData petStoreData) {
		Long storeId = petStoreData.getStoreId();
		PetStore petStore = findOrCreatePetStore(storeId);
		copyPetStoreFields(petStore, petStoreData);
		PetStore dbPetStore = petStoreDao.save(petStore);

		return new PetStoreData(dbPetStore);
	}

	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		petStore.setStoreId(petStoreData.getStoreId());
		petStore.setStoreName(petStoreData.getStoreName());
		petStore.setStoreAddress(petStoreData.getStoreAddress());
		petStore.setStoreCity(petStoreData.getStoreCity());
		petStore.setStoreState(petStoreData.getStoreState());
		petStore.setStoreZip(petStoreData.getStoreZip());
		petStore.setStorePhone(petStoreData.getStorePhone());
	}

	private PetStore findOrCreatePetStore(Long storeId) {
		PetStore petStore;

		if (Objects.isNull(storeId)) {
			petStore = new PetStore();
		} else {
			petStore = findPetStoreById(storeId);
		}

		return petStore;
	}

	private PetStore findPetStoreById(Long storeId) {
		return petStoreDao.findById(storeId)
				.orElseThrow(() -> new NoSuchElementException("PetStore with ID = " + storeId + " does not exist"));
	}

	@Transactional(readOnly = false)
	public PetStoreCustomer saveCustomer(PetStoreCustomer customerData) {
		Long customerId = customerData.getCustomerId();
		Customer customer = findOrCreateCustomer(customerId);

		setFieldsInCustomer(customer, customerData);
		return new PetStoreCustomer(customerDao.save(customer));
	}

	private void setFieldsInCustomer(Customer customer, PetStoreCustomer customerData) {
		customer.setCustomerId(customerData.getCustomerId());
		customer.setCustomerFirstName(customerData.getCustomerFirstName());
		customer.setCustomerLastName(customerData.getCustomerLastName());
		customer.setCustomerEmail(customerData.getCustomerEmail());
	}

	private Customer findOrCreateCustomer(Long customerId) {
		Customer customer;
		if (Objects.isNull(customerId)) {
			customer = new Customer();
		} else {
			customer = findCustomerById(customerId);
		}
		return customer;
	}

	private Customer findCustomerById(Long customerId) {
		return customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException("Customer with ID = " + customerId + " was not found."));

	}
	
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
		PetStore petStore = findPetStoreById(petStoreId);
		Employee employee = findOrCreateEmployee(petStoreId, petStoreEmployee.getEmployeeId());
		copyEmployeeFields(employee, petStoreEmployee);
		employee.setPetStore(petStore);
		petStore.getEmployees().add(employee);
		
		Employee dbEmployee = employeeDao.save(employee);
		
		return new PetStoreEmployee(dbEmployee);

}

	private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
		employee.setEmployeeId(petStoreEmployee.getEmployeeId());
		employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
		employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
		employee.setEmployeePosition(petStoreEmployee.getEmployeePosition());
	}

	private Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {
Employee employee;
		
		if (Objects.isNull(employeeId)) {
			employee = new Employee();
		} else {
			employee = findEmployeeById(petStoreId, employeeId);
		}
		
		return employee;
	}

	private Employee findEmployeeById(Long petStoreId, Long employeeId) {
		Employee employee = employeeDao.findById(employeeId)
				.orElseThrow(() -> new NoSuchElementException(
						"Employee with ID = " + employeeId + " does not exist"));
			
			if(employee.getPetStore().getStoreId() == petStoreId) {
				return employee;
			} else {
				throw new IllegalArgumentException("Pet store with ID = " + petStoreId + " does not have an employee with ID = " + employeeId);
			}
	}
	
	@Transactional
	public List<PetStoreData> retrieveAllPetStores() {
		List<PetStore> petStores = petStoreDao.findAll();
		List<PetStoreData> results = new LinkedList<>();
		
		for (PetStore petStore : petStores) {
			PetStoreData petStoreData = new PetStoreData(petStore);
			
			petStoreData.getEmployees().clear();
			petStoreData.getCustomers().clear();
			
			results.add(petStoreData);
		}
		
		return results;
}
	public PetStoreData retrievePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		return new PetStoreData(petStore);
	}
	public void deletePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		petStoreDao.delete(petStore);
	}
}
