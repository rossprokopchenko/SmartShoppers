package classes;

import java.io.FileWriter;
import java.util.ArrayList;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class MaintainUser {
	private ArrayList<User> users = new ArrayList<User>();
	private String path;
	
	public MaintainUser(String path) throws Exception {
		this.path = path;
	
		load(path);
	}
	
	public boolean userExists(String email, String password) {
		for(User u: users) {
			if(u.getEmail().equals(email) && u.getPassword().equals(password)) {
				return true;
			}
		}
		
		return false;
	}
	
	public String getPath() {
		return path;
	}
	
	public void add(User user) throws Exception {
		this.users.add(user);
		this.update(path);
	}
	
	public int generateUserId() {
		ArrayList<Integer> userIds = new ArrayList<Integer>();
		int id = 0;
		
		for(User u : users) {
			userIds.add(u.getId());
		}
		
		while (userIds.contains(id)) {
			id++;
		}
		
		return id;
	}
	
	public ArrayList<User> getUsers() {
		return users;
	}
	
	public User getUserByEmail(String email) {
		for (User user : users) {
			if (user.getEmail().equals(email)) {
				return user;
			}
		}
		
		return null;
	}
	
	public ArrayList<String> getUserEmails() {
		ArrayList<String> userEmails = new ArrayList<String>();
		
		for (User user : users) {
			userEmails.add(user.getEmail());
		}
		
		return userEmails;
	}
	
	public void updateUser(int id, User newUser) throws Exception{
		users.set(id, newUser);
		this.update(path);
	}
	
	public void deleteUser(int id) throws Exception {
		users.remove(id);
		this.update(path);
	}
	
	public void load(String path) throws Exception{
		CsvReader reader = new CsvReader(path); 
		reader.readHeaders();
		
		while(reader.readRecord()){ 
			User user = new User();
			//id,email,password
			user.setId(Integer.valueOf(reader.get("id")));
			user.setEmail(reader.get("email"));
			user.setPassword(reader.get("password"));
			user.setPermission(reader.get("permission"));
			user.setStoreId(Integer.valueOf(reader.get("storeId")));
			users.add(user);
		}
	}
	
	public void update(String path) throws Exception{
		try {		
				CsvWriter csvOutput = new CsvWriter(new FileWriter(path, false), ',');
				//id,email,password,permission,storeId
				csvOutput.write("id");
		    	csvOutput.write("email");
				csvOutput.write("password");
				csvOutput.write("permission");
				csvOutput.write("storeId");
				csvOutput.endRecord();

				// else assume that the file already has the correct header line
				// write out a few records
				for(User u: users){
					csvOutput.write(String.valueOf(u.getId()));
					csvOutput.write(u.getEmail());
					csvOutput.write(u.getPassword());
					csvOutput.write(u.getPermission());
					csvOutput.write(String.valueOf(u.getStoreId()));
					csvOutput.endRecord();
				}
				csvOutput.close();
			
			}catch (Exception e) {
				e.printStackTrace();
			}
	}
}
