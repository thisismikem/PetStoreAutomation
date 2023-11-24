package api.utilities;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;

import api.payload.Pet;
import api.payload.User;

public class DataProviders {
	
	@DataProvider(name = "UserData")
	public User[] getUserData() throws IOException {
		String path = System.getProperty("user.dir") + "\\testData\\userdata.xlsx";
		
		List<User> res = Poiji.fromExcel(new File(path), User.class);
        return res.toArray(new User[res.size()]);
	}
	

	@DataProvider(name = "PetData")
	public Pet[] getAllPetData() throws IOException {
		String path = System.getProperty("user.dir") + "\\testData\\petdata.xlsx";
		
		PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
                .headerCount(2) // since we have two header rows
                .build();

		List<Pet> pets = Poiji.fromExcel(new File(path), Pet.class, options);
		
        return pets.toArray(new Pet[pets.size()]);
	}
}
