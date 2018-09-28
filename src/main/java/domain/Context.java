package domain;

/**
 * Author: Marlon
 * Created on: 28-9-2018
 */
public class Context {
    private String name;
    private Context context;
    private String contextType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getContextType() {
        return contextType;
    }

    public void setContextType(String contextType) {
        this.contextType = contextType;
    }
}
