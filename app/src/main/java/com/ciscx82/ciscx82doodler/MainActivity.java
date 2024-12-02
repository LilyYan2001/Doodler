package com.ciscx82.ciscx82doodler;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {
    // params
    DoodleView view;
    Button clearButton, colorButton;
    SeekBar selectSize, selectOpacity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get references for each param
        view = findViewById(R.id.d_view);
        clearButton = findViewById(R.id.clear_button);
        colorButton = findViewById(R.id.color_button);
        selectSize = findViewById(R.id.brushSize_bar);
        selectOpacity = findViewById(R.id.opacity_bar);

        // button onclick listeners
        clearButton.setOnClickListener(v -> view.clearCanvas());

        colorButton.setOnClickListener(v -> openColorPicker());

        // listener for brush size seekbar
        selectSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
                view.setBrushSize(value);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // listener for opacity seekbar
        selectOpacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
                view.setOpacity(value);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void openColorPicker() {
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, R.color.black, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                view.setBrushColor(color);
            }
        });
        ambilWarnaDialog.show();
    }
}