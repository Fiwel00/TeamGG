package common.filehelper;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import errorhandling.ConsoleHelper;
import teamgg.data.relationship.dto.RelationshipEnriched;
import teamgg.webservice.UrlConnectionWrapper;

public class WriteFile {

	private WriteFile() {}
	
	public static void writeFile(List<RelationshipEnriched> readRelationShips) throws JAXBException {
		try {
			FileWriter myWriter = new FileWriter("filename.txt");
			UrlConnectionWrapper<Object> urlConnectionWrapper = new UrlConnectionWrapper<>("", RelationshipEnriched.class);
			for (RelationshipEnriched relationshipUI : readRelationShips) {

				String marshall = urlConnectionWrapper.marshall(relationshipUI);
				ConsoleHelper.info(WriteFile.class, marshall);
				myWriter.write(marshall);

			}
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}
