package com.example.max.recyclecontainersfinder;

public class RecycleProperties {
    private String Id;
    private String TypeId;
    private String StatusId;

    public RecycleProperties(String id, String typeId, String statusId){
        Id = id;
        TypeId = typeId;
        StatusId = statusId;
    }
    public RecycleProperties(){
        Id = TypeId = StatusId = "";
    }

    public String getId(){
        return Id;
    }

    public String getTypeId(){
        return TypeId;
    }

    public String getStatusIdId(){
        return StatusId;
    }
}
