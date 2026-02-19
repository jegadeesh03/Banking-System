package model;

public class Customer {

	    private int id;
	    private String name;
	    private String city;
	    private String mobile;

	    public Customer() {}

	    public Customer(String name, String city, String mobile) {
	        this.name = name;
	        this.city = city;
	        this.mobile = mobile;
	    }

	    public Customer(int id, String name, String city, String mobile) {
	        this.id = id;
	        this.name = name;
	        this.city = city;
	        this.mobile = mobile;
	    }

	    // getters & setters
	    public int getId() { return id; }
	    public String getName() { return name; }
	    public String getCity() { return city; }
	    public String getMobile() { return mobile; }

	    public void setName(String name) { this.name = name; }
	    public void setCity(String city) { this.city = city; }
	    public void setMobile(String mobile) { this.mobile = mobile; }
	}

