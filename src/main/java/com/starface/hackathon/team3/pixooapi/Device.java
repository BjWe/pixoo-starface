package com.starface.hackathon.team3.pixooapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Device {
    public String DeviceName;
    public long DeviceId;
    public String DevicePrivateIP;
    public String DeviceMac;

    @Override
    public String toString() {
        return String.format("%s (%s) [%s]", DeviceName, DevicePrivateIP, DeviceMac);
    }
}
