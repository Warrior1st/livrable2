package com.example.service_novigrad_livrable2;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;

public class ServiceModal implements Parcelable {
    public String serviceId;
    public String serviceName;

    public String succursales;

    public String horaires;
    public String servicePrice;
    public String exigences;

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

    public String getServiceId(){
        return serviceId;
    }
    public void setServiceId(String id){
        this.serviceId = id;
    }

    public ServiceModal(){

    }

    protected ServiceModal(Parcel in){
        serviceId = in.readString();
        serviceName = in.readString();
        succursales = in.readString();

        horaires = in.readString();

        servicePrice = in.readString();

        exigences = in.readString();
    }
    public String getServiceName(){
        return serviceName;
    }
    public void setServiceName(String name){
        serviceName = name;
    }
    public String getHoraires(){
        return horaires;
    }
    public void setHoraires(String horaire){
        horaires = horaire;
    }
    public String getServicePrice(){
        return servicePrice;
    }
    public void setServicePrice(String servicePrice){
        this.servicePrice = servicePrice;
    }
    public String getExigences(){
        return exigences;
    }
    public void setExigence(String exigences){
        this.exigences = exigences;

    }

    public ServiceModal(String serviceId, String serviceName, String succursales, String horaires, String servicePrice, String exigences){
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.succursales = succursales;
        this.horaires = horaires;
        this.servicePrice = servicePrice;
        this.exigences = exigences;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(serviceName);
        dest.writeString(serviceId);
        dest.writeString(succursales);
        dest.writeString(horaires);
        dest.writeString(servicePrice);
        dest.writeString(exigences);
    }
}
