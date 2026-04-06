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
public class WhiteBlackColorFilter extends BlackWhiteColorFilter {
    @Override
    public void filter(ColorMatrix colorMatrix) {
        super.filter(colorMatrix);

        float[] inverted = getInvertMatrix();
        colorMatrix.postConcat(new ColorMatrix(inverted));
    }
}
