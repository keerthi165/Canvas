package com.zersey.canvas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
   private CanvasView canvasView;
   private ImageButton current_paint,current_brush;
   private float smallBrush,mediumBrush,largeBrush;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        canvasView = findViewById(R.id.canvas);
        LinearLayout paintLayout = (LinearLayout) findViewById(R.id.paint_colors);
        current_paint = (ImageButton)paintLayout.getChildAt(0);
        current_paint.setImageDrawable(getResources().getDrawable(R.drawable.btn_pressed));
        smallBrush = getResources().getInteger(R.integer.small_size);
        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);
        current_brush = findViewById(R.id.brush_btn);
        current_brush.setOnClickListener(this);
        ImageButton erase_btn = findViewById(R.id.erase_btn);
        erase_btn.setOnClickListener(this);
    }


    public void paintClicked(View view) {
        if(view!= current_paint){
            canvasView.setErase(false);
            ImageButton img = (ImageButton)view;
            canvasView.setColor(view.getTag().toString());
            img.setImageDrawable(getResources().getDrawable(R.drawable.btn_pressed));
            current_paint.setImageResource(android.R.color.transparent);
            current_paint.setBackgroundColor(Color.parseColor(current_paint.getTag().toString()));
            current_paint=(ImageButton)view;
        }
    }

    @Override
    public void onClick(View v) {
             if(v.getId()==R.id.brush_btn){
                 final Dialog brushesDialog = new Dialog(this);
                 brushesDialog.setTitle("Select the brush size:");
                 brushesDialog.setContentView(R.layout.brush_dialog);
                 brushesDialog.show();
                 Button smallBtn = brushesDialog.findViewById(R.id.sml_btn);
                 Button medBtn = brushesDialog.findViewById(R.id.med_btn);
                 Button lrgBtn = brushesDialog.findViewById(R.id.lrg_btn);
                 smallBtn.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         canvasView.setBrushSize(smallBrush);
                         canvasView.setErase(false);
                         brushesDialog.dismiss();
                     }
                 });
                 medBtn.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         canvasView.setBrushSize(mediumBrush);
                         canvasView.setErase(false);
                         brushesDialog.dismiss();
                     }
                 });
                 lrgBtn.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         canvasView.setBrushSize(largeBrush);
                         canvasView.setErase(false);
                         brushesDialog.dismiss();
                     }
                 });
             }
             else if(v.getId()==R.id.erase_btn){
                 final Dialog brushesDialog = new Dialog(this);
                 brushesDialog.setTitle("Select the eraser size:");
                 brushesDialog.setContentView(R.layout.brush_dialog);
                 brushesDialog.show();
                 Button smallBtn = brushesDialog.findViewById(R.id.sml_btn);
                 Button medBtn = brushesDialog.findViewById(R.id.med_btn);
                 Button lrgBtn = brushesDialog.findViewById(R.id.lrg_btn);
                 smallBtn.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         canvasView.setBrushSize(smallBrush);
                         brushesDialog.dismiss();
                     }
                 });
                 medBtn.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         canvasView.setBrushSize(mediumBrush);
                         brushesDialog.dismiss();
                     }
                 });
                 lrgBtn.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         canvasView.setBrushSize(largeBrush);
                         brushesDialog.dismiss();
                     }
                 });
                 canvasView.setErase(true);
             }
    }


}
