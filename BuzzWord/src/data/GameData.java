package data;

import apptemplate.AppTemplate;
import components.AppDataComponent;

public class GameData implements AppDataComponent {

    private String         Pass;
    private String         user;
    private String         loguser;
    private String         logpass;

    public  AppTemplate    appTemplate;

    public GameData(AppTemplate appTemplate) {
        this(appTemplate, false);
    }

    public GameData(AppTemplate appTemplate, boolean initiateGame) {
        if (initiateGame) {
            this.appTemplate = appTemplate;
        } else {
            this.appTemplate = appTemplate;
        }
    }

    public String getLoguser(){ return loguser; }

    public String getLogpass(){ return logpass; }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return Pass;
    }

    public void setLoguser(String loguser){
        this.loguser = loguser;
    }

    public void setLogpass(String logpass){
        this.logpass = logpass;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPass(String pass)
    {
        this.Pass = pass;
    }

    @Override
    public void reset() {
        appTemplate.getWorkspaceComponent().reloadWorkspace();
    }


}
