/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.ViewFlipper;
/*
 * Main Activity class that loads {@link MainFragment}.
 */


public class MainActivity extends Activity {

    //ViewFlipper v_flipper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        //        int images[] = {R.drawable.logo,R.drawable.logo,R.drawable.logo};
        //        v_flipper = findViewById(R.id.v_flipper);
        //
        //        /*
        //        for(int i =0; i < images.length;i++){
        //            flipperImages(images[i]);
        //        }
        //
        //another way
        //
        //for(int image: images){
        // flipperImages(image);
        //}

    }
/*
    public void flipperImages(int image){

        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(4000);
        v_flipper.setAutoStart(true);

        // animation
        v_flipper.setInAnimation(this,android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(this,android.R.anim.slide_out_right);
    }
    */
}
