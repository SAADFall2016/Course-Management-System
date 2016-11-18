package system_source_code;

public class Person {

	private Address Address;

	private String Name;

	private String PhoneNumber;

	/**
 * 
 */
	private Integer UUID;

	Person(int UUID, String Name, String Address, String PhoneNumber) {
		this.UUID = UUID;
		this.Name = Name;
		this.Address = new Address().createAddress(Address);
		this.PhoneNumber = PhoneNumber;
	}

	public Address getAddress() {
		return Address;
	}

	public String getName() {
		return Name;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public Integer getUUID() {
		return UUID;
	}

	public void setAddress(Address address) {
		Address = address;
	}

	public void setName(String name) {
		Name = name;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public void setUUID(Integer uUID) {
		UUID = uUID;
	}

}