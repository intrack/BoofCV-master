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

package boofcv.struct;

/**
 * @author Peter Abeles
 */
public class FastQueueArray_F64 extends FastQueue<double[]> {
	int length;

	public FastQueueArray_F64(int arrayLength) {
		this.length = arrayLength;
		init(10,double[].class,true);
	}

	@Override
	protected double[] createInstance() {
		return new double[length];
	}
}
