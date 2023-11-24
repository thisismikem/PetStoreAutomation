package api.payload;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelCellRange;
import com.poiji.annotation.ExcelSheet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelSheet("Sheet1")
public class Pet {
	
	@ExcelCellName("PetID")
	private int id;
	
	@ExcelCellRange
	private Category category;
	
	@ExcelCellName("PetName")
	private String name;
	
	@ExcelCellName("PhotoUrls")
	private List<String> photoUrls;
	
	@ExcelCellRange
	private ArrayList<Tag> tags;	//this doesn't work. list of custom objects
	
	@ExcelCellName("Status")
	private String status;
	
	@Data
	@JsonAutoDetect(fieldVisibility = Visibility.ANY)
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Category {
		@ExcelCellName("CategoryID")
		private int id;
		
		@ExcelCellName("CategoryName")
		private String name;
	}
	
	@Data
	@JsonAutoDetect(fieldVisibility = Visibility.ANY)
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Tag {
		@ExcelCellName("TagID")
		private int id;
		
		@ExcelCellName("TagName")
		private String name;
	}
	
}
