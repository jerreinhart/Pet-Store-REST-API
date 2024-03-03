package pet.store.controller.model;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Data
@NoArgsConstructor

public class PetStoreData {
	private Long storeId;
	private String storeName;
	private String storeAddress;
	private String storeCity;
	private String storeState;
	private Long storeZip;
	private Long storePhone;
	private Set<PetStoreEmployee> employees = new HashSet<>();
	private Set<PetStoreCustomer> customers = new HashSet<>();

	public PetStoreData(PetStore petStore) {
		storeId = petStore.getStoreId();
		storeName = petStore.getStoreName();
		storeAddress = petStore.getStoreAddress();
		storeCity = petStore.getStoreCity();
		storeState = petStore.getStoreState();
		storeZip = petStore.getStoreZip();
		storePhone = petStore.getStorePhone();

		for (Customer customer : petStore.getCustomers()) {
			customers.add(new PetStoreCustomer(customer));
		}
		for (Employee employee : petStore.getEmployees()) {
			employees.add(new PetStoreEmployee(employee));
		}
	}
		@Data
		@NoArgsConstructor
		public static class PetStoreCustomer {
			private Long customerId;
			private String customerFirstName;
			private String customerLastName;
			private String customerEmail;

	
		public PetStoreCustomer(Customer customer) {
		customerId = customer.getCustomerId();
		customerFirstName = customer.getCustomerFirstName();
		customerLastName = customer.getCustomerLastName();
		customerEmail = customer.getCustomerEmail();

		}
	}
@Data
@NoArgsConstructor

public static class PetStoreEmployee {
	private Long employeeId;
	private String employeeFirstName;
	private String employeeLastName;
	private Long employeePhone;
	private String employeePosition;
	
	public PetStoreEmployee(Employee employee) {
		employeeId = employee.getEmployeeId();
		employeeFirstName = employee.getEmployeeFirstName();
		employeeLastName = employee.getEmployeeLastName();
		employeePhone = employee.getEmployeePhone();
		employeePosition = employee.getEmployeePosition();
	}
}
}

