package com.starface.hackathon.team3.pixooapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceResponse {
    public int ReturnCode;
    public String ReturnMessage;
    public List<Device> DeviceList;
}
