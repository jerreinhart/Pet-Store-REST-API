package pet.store.entity;

import jakarta.persistence.Entity;
import java.util.HashSet; 
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Data;

@Entity
@Data
public class PetStore {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long storeId;
private String storeName;
private String storeAddress;
private String storeCity;
private String storeState;
private Long storeZip;
private Long storePhone;

@EqualsAndHashCode.Exclude
@ToString.Exclude
@ManyToMany(cascade = CascadeType.PERSIST)
@JoinTable(name = "store_customer", joinColumns = @JoinColumn(name = "store_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))

private Set<Customer> customers = new HashSet<>();

@EqualsAndHashCode.Exclude
@ToString.Exclude
@OneToMany(mappedBy = "petStore", cascade = CascadeType.ALL, orphanRemoval = true)

private Set<Employee> employees = new HashSet<>();
}
