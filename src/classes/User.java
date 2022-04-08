package classes;

public class User {
	private int id;
	private String email;
	private String password;
	private String permission;
	private int storeId;
	
	public User(int id, String email, String password, String permission, int storeId) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.permission = permission;
		this.storeId = storeId;
	}
	
	public User(){
		super();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", permission=" + permission + ", store=" + storeId + "]";
	}
	
}
