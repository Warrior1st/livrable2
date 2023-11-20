package com.example.service_novigrad_livrable2;

import android.os.Parcel;
import android.os.Parcelable;

public class ServiceModal implements Parcelable {
    //creating variables for our different fields.
    private String serviceName;
    private String formulaire;
    private String documents;
    private String serviceId;
    
    //creating an empty constructor.
    public ServiceModal() {

    }

    protected ServiceModal(Parcel in) {
        serviceName = in.readString();
        serviceId = in.readString();
        formulaire = in.readString();
        documents = in.readString();
    }

    public static final Creator<ServiceModal> CREATOR = new Creator<ServiceModal>() {
        @Override
        public ServiceModal createFromParcel(Parcel in) {
            return new ServiceModal(in);
        }

        @Override
        public ServiceModal[] newArray(int size) {
            return new ServiceModal[size];
        }
    };

    //creating getter and setter methods.
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
    public String getFormulaire(){return formulaire;}
    public void setFormulaire(String formulaire){this.formulaire = formulaire;}
    public String getDocuments(){return documents;}
    public void setDocuments(String documents){this.documents = documents;}




    public ServiceModal(String serviceId, String serviceName, String formulaire, String documents) {
        this.serviceName = serviceName;
        this.serviceId = serviceId;
        this.formulaire = formulaire;
        this.documents = documents;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(serviceName);
        dest.writeString(serviceId);
        dest.writeString(formulaire);
        dest.writeString(documents);
    }
}
