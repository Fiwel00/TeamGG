package teamgg.ui.components.staticelement;

import javax.swing.JLabel;

import common.filehelper.ReadFile;
import errorhandling.TeamGGException;
import errorhandling.customexception.FileNotFoundException;

public abstract class StaticComponent extends JLabel{

    /**
     *
     */
    private static final String HTML_FILE_TYPE = ".html";
    protected String componenthtml;
    protected String COMPONENT_FILE_PATH;


    public StaticComponent(String COMPONENT_FILE_PATH) throws FileNotFoundException {
        super();
        this.COMPONENT_FILE_PATH = COMPONENT_FILE_PATH + HTML_FILE_TYPE;
        
        componenthtml = ReadFile.read(this.COMPONENT_FILE_PATH);
        setText(componenthtml);
        
        setVisible(true);
    }

    public String getComponenthtml() {
        return componenthtml;
    }

    public void setComponenthtml(String html) {
        componenthtml = html;
        setText(html);
    }

    public abstract void update() throws TeamGGException;

}
