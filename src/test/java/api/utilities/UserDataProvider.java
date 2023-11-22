package api.utilities;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.poiji.bind.Poiji;

import api.payload.User;

public class UserDataProvider {
	
	@DataProvider(name = "AllData")
	public User[] getAllData() throws IOException {
		String path = System.getProperty("user.dir") + "\\testData\\Userdata.xlsx";
		
		List<User> res = Poiji.fromExcel(new File(path), User.class);
        return res.toArray(new User[res.size()]);
	}
	
}
