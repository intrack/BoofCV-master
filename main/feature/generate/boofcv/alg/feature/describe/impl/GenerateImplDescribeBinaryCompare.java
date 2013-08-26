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

package boofcv.alg.feature.describe.impl;

import boofcv.misc.AutoTypeImage;
import boofcv.misc.CodeGeneratorBase;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * @author Peter Abeles
 */
public class GenerateImplDescribeBinaryCompare extends CodeGeneratorBase {

	AutoTypeImage imageType;

	@Override
	public void generate() throws FileNotFoundException {
		printClass(AutoTypeImage.F32);
		printClass(AutoTypeImage.U8);
	}

	private void printClass( AutoTypeImage imageType ) throws FileNotFoundException {
		this.imageType = imageType;
		className = "ImplDescribeBinaryCompare_"+imageType.getAbbreviatedType();
		out = new PrintStream(new FileOutputStream(className + ".java"));
		printPreamble();
		printFunctions();

		out.print("}\n");
	}

	private void printPreamble() throws FileNotFoundException {
		setOutputFile(className);
		out.print("import boofcv.alg.feature.describe.DescribePointBinaryCompare;\n " +
				"import boofcv.alg.feature.describe.DescribePointBrief;\n" +
				"import boofcv.alg.feature.describe.brief.BinaryCompareDefinition_I32;\n" +
				"import boofcv.struct.feature.TupleDesc_B;\n" +
				"import boofcv.struct.image.*;\n" +
				"import georegression.struct.point.Point2D_I32;\n" +
				"\n" +
				"import java.util.Arrays;\n" +
				"\n" +
				"/**\n" +
				" * <p>\n" +
				" * Implementation of {@link DescribePointBinaryCompare} for a specific image type.\n" +
				" * </p>\n" +
				" *\n" +
				" * <p>\n" +
				" * WARNING: Do not modify.  Automatically generated by {@link GenerateImplDescribeBinaryCompare}.\n" +
				" * </p>\n" +
				" *\n" +
				" * @author Peter Abeles\n" +
				" */\n" +
				"public class "+className+" extends DescribePointBinaryCompare<"+imageType.getImageName()+"> {\n" +
				"\n" +
				"\tpublic "+className+"(BinaryCompareDefinition_I32 definition) {\n" +
				"\t\tsuper(definition);\n" +
				"\t}\n\n");
	}

	public void printFunctions() {
		String bitwise = imageType.getBitWise();
		String sumType = imageType.getSumType();

		out.print("\t@Override\n" +
				"\tpublic void processInside( int c_x , int c_y , TupleDesc_B feature )\n" +
				"\t{\n" +
				"\t\tArrays.fill(feature.data, 0);\n" +
				"\n" +
				"\t\tint index = image.startIndex + image.stride*c_y + c_x;\n" +
				"\n" +
				"\t\tfor( int i = 0; i < definition.compare.length; i += 32 ) {\n" +
				"\t\t\tint end = Math.min(definition.compare.length,i+32);\n" +
				"\t\t\t"+sumType+" valA = image.data[index + offsetsA[i]]"+bitwise+";\n" +
				"\t\t\t"+sumType+" valB = image.data[index + offsetsB[i]]"+bitwise+";\n" +
				"\n" +
				"\t\t\tint desc = valA < valB ? 1 : 0;\n" +
				"\t\t\tfor( int j = i+1; j < end; j++ ) {\n" +
				"\t\t\t\tvalA = image.data[index + offsetsA[j]]"+bitwise+";\n" +
				"\t\t\t\tvalB = image.data[index + offsetsB[j]]"+bitwise+";\n" +
				"\n" +
				"\t\t\t\tdesc *= 2;\n" +
				"\t\t\t\tif( valA < valB ) {\n" +
				"\t\t\t\t\tdesc += 1;\n" +
				"\t\t\t\t}\n" +
				"\t\t\t}\n" +
				"\n" +
				"\t\t\tfeature.data[ i/32 ] = desc;\n" +
				"\t\t}\n" +
				"\t}\n\n");

		out.print("\t@Override\n" +
				"\tpublic void processBorder( int c_x , int c_y , TupleDesc_B feature ) {\n" +
				"\t\tArrays.fill(feature.data, 0);\n" +
				"\n" +
				"\t\tint index = image.startIndex + image.stride*c_y + c_x;\n" +
				"\n" +
				"\t\tfor( int i = 0; i < definition.compare.length; i += 32 ) {\n" +
				"\t\t\tint end = Math.min(definition.compare.length,i+32);\n" +
				"\t\t\tint desc = 0;\n" +
				"\t\t\tfor( int j = i; j < end; j++ ) {\n" +
				"\t\t\t\tPoint2D_I32 c = definition.compare[j];\n" +
				"\t\t\t\tPoint2D_I32 p_a = definition.samplePoints[c.x];\n" +
				"\t\t\t\tPoint2D_I32 p_b = definition.samplePoints[c.y];\n" +
				"\n" +
				"\t\t\t\tif( image.isInBounds(p_a.x + c_x , p_a.y + c_y) &&\n" +
				"\t\t\t\t\t\timage.isInBounds(p_b.x + c_x , p_b.y + c_y) ){\n" +
				"\t\t\t\t\t"+sumType+" valA = image.data[index + offsetsA[j]]"+bitwise+";\n" +
				"\t\t\t\t\t"+sumType+" valB = image.data[index + offsetsB[j]]"+bitwise+";\n" +
				"\n" +
				"\t\t\t\t\tdesc *= 2;\n" +
				"\n" +
				"\t\t\t\t\tif( valA < valB ) {\n" +
				"\t\t\t\t\t\tdesc += 1;\n" +
				"\t\t\t\t\t}\n" +
				"\t\t\t\t}\n" +
				"\t\t\t}\n" +
				"\t\t\tfeature.data[ i/32 ] = desc;\n" +
				"\t\t}\n" +
				"\t}\n\n");
	}

	public static void main( String args[] ) throws FileNotFoundException {
		GenerateImplDescribeBinaryCompare app = new GenerateImplDescribeBinaryCompare();
		app.generate();
	}

}
