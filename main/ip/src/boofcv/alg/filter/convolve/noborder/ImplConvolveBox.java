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

package boofcv.alg.filter.convolve.noborder;

import boofcv.struct.image.*;

/**
 * <p>
 * Convolves a box filter across an image.  A box filter is equivalent to convolving a kernel with all 1's.
 * </p>
 * <p>
 * Do not modify.  Auto generated by GenerateImplConvolveBox.
 * </p>
 * 
 * @author Peter Abeles
 */
public class ImplConvolveBox {

	public static void horizontal( ImageUInt8 input , ImageInt16 output , int radius , boolean includeBorder) {
		final int kernelWidth = radius*2 + 1;

		final int startY = includeBorder ? 0 : radius;
		final int endY = includeBorder ? input.height : input.height - radius;

		for( int y = startY; y < endY; y++ ) {
			int indexIn = input.startIndex + input.stride*y;
			int indexOut = output.startIndex + output.stride*y + radius;

			int total = 0;

			int indexEnd = indexIn + kernelWidth;
			
			for( ; indexIn < indexEnd; indexIn++ ) {
				total += input.data[indexIn] & 0xFF;
			}
			output.data[indexOut++] = (short)total;

			indexEnd = indexIn + input.width - kernelWidth;
			for( ; indexIn < indexEnd; indexIn++ ) {
				total -= input.data[ indexIn - kernelWidth ] & 0xFF;
				total += input.data[ indexIn ] & 0xFF;

				output.data[indexOut++] = (short)total;
			}
		}
	}

	public static void vertical( ImageUInt8 input , ImageInt16 output , int radius , boolean includeBorder ) {
		final int kernelWidth = radius*2 + 1;

		final int startX = includeBorder ? 0 : radius;
		final int endX = includeBorder ? input.width : input.width - radius;

		final int backStep = kernelWidth*input.stride;

		for( int x = startX; x < endX; x++ ) {
			int indexIn = input.startIndex + x;
			int indexOut = output.startIndex + output.stride*radius + x;

			int total = 0;
			int indexEnd = indexIn + input.stride*kernelWidth;
			for( ; indexIn < indexEnd; indexIn += input.stride) {
				total += input.data[indexIn] & 0xFF;
			}

			output.data[indexOut] = (short)total;
		}

		// change the order it is processed in to reduce cache misses
		for( int y = radius+1; y < output.height-radius; y++ ) {
			int indexIn = input.startIndex + (y+radius)*input.stride+startX;
			int indexOut = output.startIndex + y*output.stride+startX;

			for( int x = startX; x < endX; x++ ,indexIn++,indexOut++) {
				int total = output.data[ indexOut - output.stride]  - (input.data[ indexIn - backStep ]& 0xFF);
				total += input.data[ indexIn ]& 0xFF;

				output.data[indexOut] = (short)total;
			}
		}
	}

	public static void horizontal( ImageUInt8 input , ImageSInt32 output , int radius , boolean includeBorder) {
		final int kernelWidth = radius*2 + 1;

		final int startY = includeBorder ? 0 : radius;
		final int endY = includeBorder ? input.height : input.height - radius;

		for( int y = startY; y < endY; y++ ) {
			int indexIn = input.startIndex + input.stride*y;
			int indexOut = output.startIndex + output.stride*y + radius;

			int total = 0;

			int indexEnd = indexIn + kernelWidth;
			
			for( ; indexIn < indexEnd; indexIn++ ) {
				total += input.data[indexIn] & 0xFF;
			}
			output.data[indexOut++] = total;

			indexEnd = indexIn + input.width - kernelWidth;
			for( ; indexIn < indexEnd; indexIn++ ) {
				total -= input.data[ indexIn - kernelWidth ] & 0xFF;
				total += input.data[ indexIn ] & 0xFF;

				output.data[indexOut++] = total;
			}
		}
	}

	public static void vertical( ImageUInt8 input , ImageSInt32 output , int radius , boolean includeBorder ) {
		final int kernelWidth = radius*2 + 1;

		final int startX = includeBorder ? 0 : radius;
		final int endX = includeBorder ? input.width : input.width - radius;

		final int backStep = kernelWidth*input.stride;

		for( int x = startX; x < endX; x++ ) {
			int indexIn = input.startIndex + x;
			int indexOut = output.startIndex + output.stride*radius + x;

			int total = 0;
			int indexEnd = indexIn + input.stride*kernelWidth;
			for( ; indexIn < indexEnd; indexIn += input.stride) {
				total += input.data[indexIn] & 0xFF;
			}

			output.data[indexOut] = total;
		}

		// change the order it is processed in to reduce cache misses
		for( int y = radius+1; y < output.height-radius; y++ ) {
			int indexIn = input.startIndex + (y+radius)*input.stride+startX;
			int indexOut = output.startIndex + y*output.stride+startX;

			for( int x = startX; x < endX; x++ ,indexIn++,indexOut++) {
				int total = output.data[ indexOut - output.stride]  - (input.data[ indexIn - backStep ]& 0xFF);
				total += input.data[ indexIn ]& 0xFF;

				output.data[indexOut] = total;
			}
		}
	}

	public static void horizontal( ImageSInt16 input , ImageInt16 output , int radius , boolean includeBorder) {
		final int kernelWidth = radius*2 + 1;

		final int startY = includeBorder ? 0 : radius;
		final int endY = includeBorder ? input.height : input.height - radius;

		for( int y = startY; y < endY; y++ ) {
			int indexIn = input.startIndex + input.stride*y;
			int indexOut = output.startIndex + output.stride*y + radius;

			int total = 0;

			int indexEnd = indexIn + kernelWidth;
			
			for( ; indexIn < indexEnd; indexIn++ ) {
				total += input.data[indexIn] ;
			}
			output.data[indexOut++] = (short)total;

			indexEnd = indexIn + input.width - kernelWidth;
			for( ; indexIn < indexEnd; indexIn++ ) {
				total -= input.data[ indexIn - kernelWidth ] ;
				total += input.data[ indexIn ] ;

				output.data[indexOut++] = (short)total;
			}
		}
	}

	public static void vertical( ImageSInt16 input , ImageInt16 output , int radius , boolean includeBorder ) {
		final int kernelWidth = radius*2 + 1;

		final int startX = includeBorder ? 0 : radius;
		final int endX = includeBorder ? input.width : input.width - radius;

		final int backStep = kernelWidth*input.stride;

		for( int x = startX; x < endX; x++ ) {
			int indexIn = input.startIndex + x;
			int indexOut = output.startIndex + output.stride*radius + x;

			int total = 0;
			int indexEnd = indexIn + input.stride*kernelWidth;
			for( ; indexIn < indexEnd; indexIn += input.stride) {
				total += input.data[indexIn] ;
			}

			output.data[indexOut] = (short)total;
		}

		// change the order it is processed in to reduce cache misses
		for( int y = radius+1; y < output.height-radius; y++ ) {
			int indexIn = input.startIndex + (y+radius)*input.stride+startX;
			int indexOut = output.startIndex + y*output.stride+startX;

			for( int x = startX; x < endX; x++ ,indexIn++,indexOut++) {
				int total = output.data[ indexOut - output.stride]  - (input.data[ indexIn - backStep ]);
				total += input.data[ indexIn ];

				output.data[indexOut] = (short)total;
			}
		}
	}

	public static void horizontal( ImageFloat32 input , ImageFloat32 output , int radius , boolean includeBorder) {
		final int kernelWidth = radius*2 + 1;

		final int startY = includeBorder ? 0 : radius;
		final int endY = includeBorder ? input.height : input.height - radius;

		for( int y = startY; y < endY; y++ ) {
			int indexIn = input.startIndex + input.stride*y;
			int indexOut = output.startIndex + output.stride*y + radius;

			float total = 0;

			int indexEnd = indexIn + kernelWidth;
			
			for( ; indexIn < indexEnd; indexIn++ ) {
				total += input.data[indexIn] ;
			}
			output.data[indexOut++] = total;

			indexEnd = indexIn + input.width - kernelWidth;
			for( ; indexIn < indexEnd; indexIn++ ) {
				total -= input.data[ indexIn - kernelWidth ] ;
				total += input.data[ indexIn ] ;

				output.data[indexOut++] = total;
			}
		}
	}

	public static void vertical( ImageFloat32 input , ImageFloat32 output , int radius , boolean includeBorder ) {
		final int kernelWidth = radius*2 + 1;

		final int startX = includeBorder ? 0 : radius;
		final int endX = includeBorder ? input.width : input.width - radius;

		final int backStep = kernelWidth*input.stride;

		for( int x = startX; x < endX; x++ ) {
			int indexIn = input.startIndex + x;
			int indexOut = output.startIndex + output.stride*radius + x;

			float total = 0;
			int indexEnd = indexIn + input.stride*kernelWidth;
			for( ; indexIn < indexEnd; indexIn += input.stride) {
				total += input.data[indexIn] ;
			}

			output.data[indexOut] = total;
		}

		// change the order it is processed in to reduce cache misses
		for( int y = radius+1; y < output.height-radius; y++ ) {
			int indexIn = input.startIndex + (y+radius)*input.stride+startX;
			int indexOut = output.startIndex + y*output.stride+startX;

			for( int x = startX; x < endX; x++ ,indexIn++,indexOut++) {
				float total = output.data[ indexOut - output.stride]  - (input.data[ indexIn - backStep ]);
				total += input.data[ indexIn ];

				output.data[indexOut] = total;
			}
		}
	}

}
