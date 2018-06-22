package pro.novatechsolutions.app.aagvideos.Client;

public interface OnServiceResponseListener<T>  {

    void onSuccess(T object);
    void onFailure(ClientException e);
}
