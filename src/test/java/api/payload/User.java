package api.payload;

import com.poiji.annotation.ExcelCellName;

import lombok.Data;

@Data
public class User {
	
	@ExcelCellName("UserID")
	private int id;
	
	@ExcelCellName("UserName")
	private String username;
	
	@ExcelCellName("FirstName")
	private String firstName;
	
	@ExcelCellName("lastName")
	private String lastName;
	
	@ExcelCellName("Email")
	private String email;
	
	@ExcelCellName("Password")
	private String password;
	
	@ExcelCellName("Phone")
	private String phone;
	
	private int userStatus;
	
	
}
