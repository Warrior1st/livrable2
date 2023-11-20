package com.example.service_novigrad_livrable2;

import android.os.Parcel;
import android.os.Parcelable;

public class ServiceModal implements Parcelable {
    //creating variables for our different fields.
    private String serviceName;
    private String succursales;
    private String exigences;
    private String serviceImg;
    private String serviceLink;
    private String serviceId;
    private String horaires;
    
    


    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }


    //creating an empty constructor.
    public ServiceModal() {

    }

    protected ServiceModal(Parcel in) {
        serviceName = in.readString();
        serviceId = in.readString();
        succursales = in.readString();
        exigences = in.readString();
        serviceImg = in.readString();
        serviceLink = in.readString();
        horaires = in.readString();
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

    public String getSuccursales() {
        return succursales;
    }

    public void setSuccursales(String succursales) {
        this.succursales = succursales;
    }

    public String getExigences() {
        return exigences;
    }

    public void setExigences(String exigences) {
        this.exigences = exigences;
    }

    public String getServiceImg() {
        return serviceImg;
    }

    public void setServiceImg(String serviceImg) {
        this.serviceImg = serviceImg;
    }

    public String getServiceLink() {
        return serviceLink;
    }

    public void setServiceLink(String serviceLink) {
        this.serviceLink = serviceLink;
    }

    public String getHoraires(){return horaires;}
    public void setHoraires(String horaires){this.horaires = horaires;}

    public ServiceModal(String serviceId, String serviceName, String succursales, String exigences, String serviceImg, String serviceLink, String horaires) {
        this.serviceName = serviceName;
        this.serviceId = serviceId;
        this.succursales = succursales;
        this.exigences = exigences;
        this.serviceImg = serviceImg;
        this.serviceLink = serviceLink;
        this.horaires = horaires;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(serviceName);
        dest.writeString(serviceId);
        dest.writeString(succursales);
        dest.writeString(exigences);
        dest.writeString(serviceImg);
        dest.writeString(serviceLink);
        dest.writeString(horaires);
    }
}
