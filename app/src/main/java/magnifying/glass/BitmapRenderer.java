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
package magnifying.glass;

import android.graphics.Bitmap;

/**
 * Created by handspiel on 11.08.15.
 */
public interface BitmapRenderer {
    /**
     * renders bitmaps.
     * You can also use it as a setter method.
     * @param bitmap
     */
    void renderBitmap(Bitmap bitmap);
}
