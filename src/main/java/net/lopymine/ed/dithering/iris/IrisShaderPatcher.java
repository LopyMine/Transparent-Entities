package net.lopymine.ed.dithering.iris;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IrisShaderPatcher {

	private static final String MARKER = "// transparent_entities:dithering";

	private static final Pattern MAIN_PATTERN = Pattern.compile("void\\s+main\\s*\\(\\s*(?:void)?\\s*\\)\\s*\\{");

	private static final String DITHER_BODY = """
		float de_easeInOutCubic(float x) {
		    return x < 0.5 ?
		        4.0 * x * x * x :
		        1.0 - pow(-2.0 * x + 2.0, 3.0) / 2.0;
		}

		const mat4 DE_DITHER_MAT = mat4(
		    1.0 / 17.0,  9.0 / 17.0,  3.0 / 17.0,  11.0 / 17.0,
		    13.0 / 17.0, 5.0 / 17.0,  15.0 / 17.0, 7.0 / 17.0,
		    4.0 / 17.0,  12.0 / 17.0, 2.0 / 17.0,  10.0 / 17.0,
		    16.0 / 17.0, 8.0 / 17.0,  14.0 / 17.0, 6.0 / 17.0
		);

		void de_applyDitherMatrix(float visibility, vec2 fragCoord, float ditherPixelSize) {
		    vec2 cell = fragCoord / ditherPixelSize;
		    int x = int(cell.x);
		    int y = int(cell.y);
		    if (visibility < DE_DITHER_MAT[x % 4][y % 4]) {
		        discard;
		    }
		}

		float de_distanceVisibility(float cameraDistance, float ditherMinValue, float ditherNearDistance, float ditherFarDistance) {
		    float v = clamp(smoothstep(ditherNearDistance, ditherFarDistance, cameraDistance), ditherMinValue, 1.0);
		    return de_easeInOutCubic(v);
		}

		void de_applyDistanceDither(float cameraDistance, vec2 fragCoord, float ditherMinValue, float ditherPixelSize, float ditherNearDistance, float ditherFarDistance) {
		    de_applyDitherMatrix(de_distanceVisibility(cameraDistance, ditherMinValue, ditherNearDistance, ditherFarDistance), fragCoord, ditherPixelSize);
		}

		float te_sphericalVertexDistance() {
		    vec3 ndc = vec3(gl_FragCoord.xy / vec2(viewWidth, viewHeight), gl_FragCoord.z) * 2.0 - 1.0;
		    vec4 viewPos = gbufferProjectionInverse * vec4(ndc, 1.0);
		    return length(viewPos.xyz / viewPos.w);
		}
		""";

	private static final String CALL_BLOCK =
		"\n\tfloat sphericalVertexDistance = te_sphericalVertexDistance();\n"
			+ "\tde_applyDistanceDither(sphericalVertexDistance, gl_FragCoord.xy, DitherMinValue, DitherPixelSize, DitherNearDistance, DitherFarDistance);\n";

	public static String patchFragmentShader(String source) {
		if (source == null || isPatched(source)) {
			return source;
		}
		Matcher main = MAIN_PATTERN.matcher(source);
		if (!main.find()) {
			return source;
		}

		StringBuilder header = new StringBuilder();
		header.append(MARKER).append(" begin\n");
		if (!source.contains("viewWidth")) {
			header.append("uniform float viewWidth;\n");
		}
		if (!source.contains("viewHeight")) {
			header.append("uniform float viewHeight;\n");
		}
		if (!source.contains("gbufferProjectionInverse")) {
			header.append("uniform mat4 gbufferProjectionInverse;\n");
		}

		header.append("uniform float DitherMinValue;\n");
		header.append("uniform float DitherPixelSize;\n");
		header.append("uniform float DitherNearDistance;\n");
		header.append("uniform float DitherFarDistance;\n");
		header.append(DITHER_BODY);
		header.append(MARKER).append(" end\n\n");

		int mainStart = main.start();
		int braceEnd = main.end();

		return source.substring(0, mainStart)
			+ header
			+ source.substring(mainStart, braceEnd)
			+ CALL_BLOCK
			+ source.substring(braceEnd);
	}

	public static boolean isPatched(String source) {
		return source != null && source.contains(MARKER);
	}
}
