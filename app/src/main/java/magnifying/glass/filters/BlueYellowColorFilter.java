/*
 * Modified fork of https://github.com/kloener/visor-android
 * Copyright (c) 2015 Christian Illies
 * File modified by cuzi <https://github.com/cvzi/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package magnifying.glass.filters;

import android.graphics.ColorMatrix;

/**
 * Created by Christian Illies on 02.08.15.
 */
public class BlueYellowColorFilter extends BlackWhiteColorFilter {

    @Override
    public void filter(ColorMatrix colorMatrix) {
        float[] blueYellowMatrix = getInvertedBlueYellowMatrix();
        colorMatrix.postConcat(new ColorMatrix(blueYellowMatrix));
    }

    /**
     * inverts the colors of the {@link ColorMatrix} by using blue as black and yellow as white.
     * @return
     */
    public float[] getInvertedBlueYellowMatrix() {
        return new float[] {
                 3,        3,       1,    0, -512,
                 3,        3,       1,    0, -512,
            -0.75f,     0.0f,    0.7f,    0,  128,
                 0,        0,       0,    1,    0
        };
    }
}
