package teamgg.ui.components.staticelement;

import javax.swing.JLabel;

import errorhandling.TeamGGException;

public abstract class StaticComponent extends JLabel{

    protected String componenthtml;
    protected String COMPONENT_FILE_PATH;


    public StaticComponent(String COMPONENT_FILE_PATH) {
        super();
        setVisible(true);

        this.COMPONENT_FILE_PATH = COMPONENT_FILE_PATH;
    }



    protected abstract void init() throws TeamGGException;
    public abstract void update() throws TeamGGException;

}
