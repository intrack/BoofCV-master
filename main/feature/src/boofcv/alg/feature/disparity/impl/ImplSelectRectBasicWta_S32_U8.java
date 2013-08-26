/*
 * Copyright (c) 2011-2013, Peter Abeles. All Rights Reserved.
 *
 * This file is part of BoofCV (http://boofcv.org).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package boofcv.alg.feature.disparity.impl;

import boofcv.alg.feature.disparity.SelectRectBasicWta;
import boofcv.struct.image.ImageUInt8;

/**
 * <p>
 * Implementation of {@Link SelectRectBasicWta} for scores of type S32.
 * </p>
 *
 * <p>
 * DO NOT MODIFY. Generated by {@link GenerateSelectRectBasicWta}.
 * </p>
 *
 * @author Peter Abeles
 */
public class ImplSelectRectBasicWta_S32_U8 extends SelectRectBasicWta<int[],ImageUInt8>
{
	@Override
	public void process(int row, int[] scores) {

		int indexDisparity = imageDisparity.startIndex + row*imageDisparity.stride + radiusX + minDisparity;

		for( int col = minDisparity; col <= imageWidth-regionWidth; col++ ) {
			// make sure the disparity search doesn't go outside the image border
			int localMax = maxDisparityAtColumnL2R(col);

			int indexScore = col-minDisparity;

			int bestDisparity = 0;
			int scoreBest = scores[indexScore];
			indexScore += imageWidth;

			for( int i = 1; i < localMax; i++ ,indexScore += imageWidth) {
				int s = scores[indexScore];
				if( s < scoreBest ) {
					scoreBest = s;
					bestDisparity = i;
				}
			}

			imageDisparity.data[indexDisparity++] = (byte)bestDisparity;
		}
	}

	@Override
	public Class<ImageUInt8> getDisparityType() {
		return ImageUInt8.class;
	}
}