package denisleonov.fit.bstu.by.lab3;

import android.net.Uri;

import java.io.Serializable;

class User implements Serializable {
    public String Surname;
    public String Name;
    public String Email;
    public String Phone;
    public String Link;
    public String Direction;
    public String Date;
    public String Img;

    User(String surname, String name, String email, String phone, String link, String direction, String date,String img){
        Surname = surname;
        Name = name;
        Email = email;
        Phone = phone;
        Link = link;
        Direction = direction;
        Date = date;
        Img = img;
    }

    @Override
    public  String toString(){
        return "Фамилия: " + Surname + " Имя: " + Name
                + "\nEmail: " + Email + " Телефон: " + Phone
                + "\nНаправление: " + Direction + "\nРодился: " + Date;
    }
}
