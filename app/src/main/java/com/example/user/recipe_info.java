package com.example.user;

public class recipe_info {

    int ID;
    String Name;
    String Url;

    public recipe_info(int ID,String Name, String Url){
        this.ID = ID;
        this.Name = Name;
        this.Url = Url;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
