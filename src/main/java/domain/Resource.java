package domain;

/**
 * Author: Marlon
 * Created on: 28-9-2018
 */
public class Resource extends IntentionalElement {
    private String name;
    private Context context;
    private String transport;
    private String IEType;

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

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getIEType() {
        return IEType;
    }

    public void setIEType(String IEType) {
        this.IEType = IEType;
    }
}
