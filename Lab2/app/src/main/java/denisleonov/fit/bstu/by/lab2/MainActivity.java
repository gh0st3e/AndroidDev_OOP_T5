package denisleonov.fit.bstu.by.lab2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public String Result = "";

    public void showMsg(String msg){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setMessage(msg);
        b.setTitle("Ошибка").setPositiveButton("Закрыть", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog ad = b.create();
        ad.show();
    }

    private int getFirstNum(){
        EditText efn = findViewById(R.id.firstNumber);



        if (efn.getText().toString().length()<1){
            showMsg("Введите первое число");
            return -1;
        }
        int firstNumber = Integer.parseInt(efn.getText().toString());
        return firstNumber;
    }

    private int getSecondNum(){
        EditText esn = findViewById(R.id.secondNumber);

        if (esn.getText().toString().length()<1){
            showMsg("Введите второе число");
            return -1;
        }
        int secondNumber = Integer.parseInt(esn.getText().toString());
        return secondNumber;
    }

    public void findNodBtn(View view) {
        int fn = getFirstNum();
        int sn = getSecondNum();
        if(fn>0 && sn > 0){
            TextView result = findViewById(R.id.nodResult);
            result.setVisibility(View.VISIBLE);
            int res = gcd(fn,sn);
            String sres = Integer.toString(res);
            result.setText(sres);
        }
    }

    public int gcd(int a,int b) {
        Result="";
        if(b>a){
            int tmp = a;
            a = b;
            b = tmp;
        }
        while (b !=0) {
            int tmp = a%b; //остаток
            int mod = (a - tmp)/b; //частное
            Result+= Integer.toString(a) + " = " + Integer.toString(b)+" * "+ Integer.toString(mod) + " + " + Integer.toString(tmp)+"\n";
            a = b;
            b = tmp;
        }
        Log.d("Log_02",Result);
        return a;
    }

    public void showEvclideBtn(View view) {
        int fn = getFirstNum();
        int sn = getSecondNum();
        if (fn>0 && sn>0){
            gcd(fn,sn);
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setMessage(Result);
            b.setTitle("Алгоритм Евклида").setPositiveButton("Закрыть", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog ad = b.create();
            ad.show();
        }

    }

    public void findNokBtn(View view) {
        int fn = getFirstNum();
        int sn = getSecondNum();
        if (fn>0 && sn>0){
            TextView result = findViewById(R.id.nokResult);
            result.setVisibility(View.VISIBLE);
            int res = lcm(fn,sn);
            String sres = Integer.toString(res);
            result.setText(sres);
        }
    }

    int lcm(int a,int b){
        return a / gcd(a,b) * b;
    }
}