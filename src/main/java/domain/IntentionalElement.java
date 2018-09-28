package domain;

/**
 * Author: Marlon
 * Created on: 28-9-2018
 */
public abstract class IntentionalElement {
    private String name;
    private Context context;
    private String elementType;
    private String transport;

    public IntentionalElement() {
    }

    public IntentionalElement(String name, Context context, String elementType, String transport) {
        this.name = name;
        this.context = context;
        this.elementType = elementType;
        this.transport = transport;
    }

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

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }
}
