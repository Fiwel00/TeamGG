package teamgg;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Platform;

import errorhandling.ConsoleHelper;
import errorhandling.TeamGGException;
import teamgg.ui.views.MainView;

public class Main {

	private static final String CONFIG_FILE_PATH = "\\TeamGG\\src\\main\\java\\resources\\configuration\\config.properties";

	public static void main(String[] args) {

		try {
			// initi configuration with path of config
			ConfigFile.setFilePath(CONFIG_FILE_PATH);

			// api initialisation
			Orianna.setDefaultPlatform(Platform.EUROPE_WEST);
			Orianna.setRiotAPIKey(ConfigFile.getApiKey());
			Orianna.setDefaultLocale("en_US");

			// start application
			new MainView(ConfigFile.getWidth(), ConfigFile.getHeight());


		} catch (TeamGGException exception) {
			ConsoleHelper.error(exception);
		}

	}
}
