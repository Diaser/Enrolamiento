package com.dan.almedici.enrolamiento.to;

public class UserRegistration {

    private static UserRegistration instance = null;

    private UserRegistration(){}

    public static UserRegistration getInstance(){
        if(instance == null){
            synchronized (UserRegistration.class){
                if(instance == null)
                    instance =  new UserRegistration();
            }
        }
        return instance;
    }
    private String name;
    private String firstSecondName;
    private String lastSecondName;
    private String email;
    private String birthday;
    private String gender;
    private String rfc;
    private String curp;
    private String password;
    private String repeatPassword;
    private String cell;
    private String sms;
    private String profilePicture;
    private String inePictureFront;
    private String inePictureBack;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstSecondName() {
        return firstSecondName;
    }

    public void setFirstSecondName(String firstSecondName) {
        this.firstSecondName = firstSecondName;
    }

    public String getLastSecondName() {
        return lastSecondName;
    }

    public void setLastSecondName(String lastSecondName) {
        this.lastSecondName = lastSecondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getInePictureFront() {
        return inePictureFront;
    }

    public void setInePictureFront(String inePictureFront) {
        this.inePictureFront = inePictureFront;
    }

    public String getInePictureBack() {
        return inePictureBack;
    }

    public void setInePictureBack(String inePictureBack) {
        this.inePictureBack = inePictureBack;
    }
}
