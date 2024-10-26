package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView result, historial;

    private Button sum, res, div, mult, reset;

    private Button num1, num2, num3, num4, num5, num6, num7, num8, num9, num0;

    private Button coma, igual;

    private String buffer; // guarda el valor introducido hasta al momento con los botones numericos
    private double calculo; // guarda el resultado de la operacion realizada
    private int tipoOperacion; // 1 = suma, 2 = resta, 3 = division, 4 = multiplicacion
    private boolean isSymbolSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        result = findViewById(R.id.result);
        historial = findViewById(R.id.historial);

        sum = findViewById(R.id.suma);
        res = findViewById(R.id.resta);
        div = findViewById(R.id.div);
        mult = findViewById(R.id.mult);
        reset = findViewById(R.id.reset);

        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);
        num5 = findViewById(R.id.num5);
        num6 = findViewById(R.id.num6);
        num7 = findViewById(R.id.num7);
        num8 = findViewById(R.id.num8);
        num9 = findViewById(R.id.num9);
        num0 = findViewById(R.id.num0);

        coma = findViewById(R.id.coma);
        igual = findViewById(R.id.igual);

        buffer = "";
        calculo = 0;
        tipoOperacion = -1;
        isSymbolSelected = false;

        Button[] nums = {num1, num2, num3, num4, num5, num6, num7, num8, num9, num0};

        // añadir listeners de clics a los botones de numeros
        for (Button num : nums) {
            num.setOnClickListener(view -> {
                buffer += num.getText(); // añade al buffer el nuevo numero pulsado
                result.setText(buffer); // muestra el estado actual del buffer
            });
        }

        // pulsar boton de coma
        coma.setOnClickListener(view -> {
            // si ya hay una coma en el numero que estamos introduciendo no se pueden introducir más
            if (!buffer.contains(".")) {
                buffer += coma.getText();
                result.setText(buffer);
            }
        });

        Button[] simbolos = {sum, res, mult, div};

        // añadir listeners de clic a los simbolos de operaciones
        for (Button simbolo : simbolos) {
            simbolo.setOnClickListener(view -> {
                // solo funciona si ya hay algun numero introducido
                if (!buffer.isEmpty()) {
                    // dependiendo del boton pulsado asignamos un valor a tipoOperacion para usarlo mas tarde
                    switch (simbolo.getText().toString()) {
                        case "+":
                            tipoOperacion = 1;
                            break;
                        case "-":
                            tipoOperacion = 2;
                            break;
                        case "/":
                            tipoOperacion = 3;
                            break;
                        case "x":
                            tipoOperacion = 4;
                            break;
                    }
                    // calculo inicia en 0 asi que le sumamos el primer numero que haya sido introducido
                    calculo += Double.parseDouble(buffer);
                    // texto mostrado en el cuadro superior para que el usuario recuerde cual es su operacion
                    historial.setText(buffer+" "+simbolo.getText()+" ");
                    // vaciamos el contenedor principal y la variable que guarda los numeros introducidos
                    result.setText("");
                    buffer = "";
                    isSymbolSelected = true;
                }
            });
        }

        // listener pulsacion del boton de igual
        igual.setOnClickListener(view -> {
            // solo funciona si ya hay algun numero introducido
            if (!buffer.isEmpty() && isSymbolSelected) {
                // dependiendo del simbolo que hayamos elegido antes añadiremos el ultimo
                // numero introducido mediante una operacion u otra
                switch (tipoOperacion) {
                    case 1:
                        calculo += Double.parseDouble(buffer);
                        break;
                    case 2:
                        calculo -= Double.parseDouble(buffer);
                        break;
                    case 3:
                        calculo /= Double.parseDouble(buffer);
                        break;
                    case 4:
                        calculo *= Double.parseDouble(buffer);
                        break;
                }

                // actualizamos el historial para que el usuario vea toda la operacion escrita
                historial.setText(historial.getText()+buffer+" = ");
                // mostramos el resultado obtenido
                result.setText(String.valueOf(calculo));
                // reiniciamos todas las variables a sus valores por defecto
                tipoOperacion = -1;
                buffer = String.valueOf(calculo); // el buffer no se vacia para poder realizar operaciones encadenadas
                calculo = 0;
                isSymbolSelected = false;
            }
        });

        // añadir listener al boton reset (C)
        reset.setOnClickListener(view -> {
            // reinicia todas las variables a sus valores por defecto
            historial.setText("");
            result.setText("");
            buffer = "";
            calculo = 0;
            tipoOperacion = -1;
            isSymbolSelected = false;
        });
    }
}