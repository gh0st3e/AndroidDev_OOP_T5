package dl.fit.bstu.by.lab8;

import android.net.Uri;

public class User {
    public String Name;
    public String Email;
    public String Phone;
    public String ImgPath;

    public User(String name, String email, String phone, String imgPath) {
        Name = name;
        Email = email;
        Phone = phone;
        ImgPath = imgPath;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getPhone() {
        return this.Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public String getEmail() {
        return this.Email;
    }

    public void setEmail(String capital) {
        this.Email = capital;
    }

    public String  getImg() {
        return this.ImgPath;
    }
    public void setImg(String img) {
        this.ImgPath = img;
    }
}
